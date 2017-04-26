package com.elderbyte.grammar.core.scanner;

import com.elderbyte.common.ArgumentNullException;
import com.elderbyte.grammar.core.GrammarException;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Scans an input string and turns it into a stream of tokens.
 *
 */
public class ExpressionScanner {


    public static class Builder {

        private static final Pattern DefaultIdentifierPattern = Pattern.compile("^[a-zA-Z]+\\w+$");
        private static final Pattern NumberPattern = Pattern.compile("^[0-9]+$");
        private static final Pattern FloatingPointNumberPattern = Pattern.compile("^[0-9]+\\.[0-9]+$");


        private Predicate<String> isIdentifierPredicate = x -> DefaultIdentifierPattern.matcher(x).matches();
        private Predicate<String> isLiteralValuePredicate = x -> NumberPattern.matcher(x).matches() || FloatingPointNumberPattern.matcher(x).matches();
        private Set<String> stringToggles = new HashSet<>();

        private Builder(){}


        public Builder withIdentifierPattern(Pattern isWordRegex){
            isIdentifierPredicate = x -> isWordRegex.matcher(x).matches();
            return this;
        }

        public Builder withLiteralStringToggle(String toggle){
            stringToggles.add(toggle);
            return this;
        }

        public Builder withLiteral(Predicate<String> isLiteral){
            this.isLiteralValuePredicate = isLiteral;
            return this;
        }


        public ExpressionScanner build(TerminalTokenManager terminalManager){
            return new ExpressionScanner(
                    terminalManager,
                    isIdentifierPredicate,
                    isLiteralValuePredicate,
                    stringToggles);
        }

    }

    /**
     * Creates a new expression-scanner builder
     */
    public static Builder start(){
        return new Builder();
    }


    /***************************************************************************
     *                                                                         *
     * Private Fields                                                          *
     *                                                                         *
     **************************************************************************/

    private final TerminalTokenManager terminalManager;
    private final Predicate<String> isIdentifierPredicate;
    private final Predicate<String> isLiteralValuePredicate;
    private final RawTokenizer tokenizer;

    private final Collection<String> stringTogglers = new ArrayList<>();

    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/



    public ExpressionScanner(
            TerminalTokenManager terminalManager,
            Predicate<String> isIdentifierPredicate,
            Predicate<String> isLiteralValuePredicate){
        this(terminalManager, isIdentifierPredicate, isLiteralValuePredicate, new ArrayList<>());
    }

    /**
     * Creates a new ExpressionTokenizer with the given Operators
     */
    public ExpressionScanner(
            TerminalTokenManager terminalManager,
            Predicate<String> isIdentifierPredicate,
            Predicate<String> isLiteralValuePredicate,
            Collection<String> stringToggles){

        if(terminalManager == null) throw new ArgumentNullException("terminalManager");
        if(isIdentifierPredicate == null) throw new ArgumentNullException("isWordPredicate");

        this.terminalManager = terminalManager;
        this.isIdentifierPredicate = isIdentifierPredicate;
        this.isLiteralValuePredicate = isLiteralValuePredicate;

        this.tokenizer = RawTokenizer.start()
                .withDelemiting(terminalManager.getTerminalKeywords())
                .withStringToggle(stringToggles)
                .build();

        this.stringTogglers.addAll(stringToggles);
    }


    /***************************************************************************
     *                                                                         *
     * Public API                                                              *
     *                                                                         *
     **************************************************************************/

    /**
     * Turns the given string into a token stream.
     * @param expression The expression to parse
     * @return A token stream
     * @throws ArgumentNullException If expression is null.
     */
    public Stream<Token> tokenize(String expression){

        if(expression == null) throw new ArgumentNullException("expression");

        List<Token> tokens = new ArrayList<>();

        Token previous = null;

        for(String rawToken : tokenizer.tokenize(expression)){
            Token t = emit(rawToken);
            if(t != null){

                if(t.getType() == TokenType.Operator){
                    if(previous == null || previous.getType() == TokenType.Operator){
                        t = t.asUnary();
                    }
                }

                tokens.add(t);
                previous = t;
            }
        }

        return tokens.stream();
    }

    /***************************************************************************
     *                                                                         *
     * Private methods                                                         *
     *                                                                         *
     **************************************************************************/

    private Token emit(String currentWord){
        Token t = terminalManager.findTerminal(currentWord);

        if(t == null){
            if(isLiteral(currentWord)) {
                t = new Token(TokenType.Literal, currentWord);
            }else if(literalString(currentWord).isPresent()){
                String w = literalString(currentWord).get();
                t = new Token(TokenType.Literal, w);
            }else if(isIdentifier(currentWord)){
                t = new Token(TokenType.Identifier, currentWord);
            }else{
                throw new GrammarException("Unexpected token: '" + currentWord + "' <---");
            }
        }

        if(t.getType() != TokenType.Whitespace){
            return t;
        }else
            return null;
    }

    private boolean isIdentifier(String token){
        return isIdentifierPredicate.test(token);
    }

    private boolean isLiteral(String token){
        return isLiteralValuePredicate.test(token);
    }

    private Optional<String> literalString(String token){
        for(String toggler : stringTogglers){
            if(token.startsWith(toggler) && token.endsWith(toggler)){
                return Optional.of(token.substring(toggler.length(), token.length()-toggler.length()));
            }
        }
        return Optional.empty();
    }
}
