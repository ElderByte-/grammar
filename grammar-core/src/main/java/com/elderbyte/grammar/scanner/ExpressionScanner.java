package com.elderbyte.grammar.scanner;

import com.elderbyte.grammar.dom.expressions.Operator;
import com.elderbyte.common.ArgumentNullException;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Scans an input string and turns it into a stream of tokens.
 *
 */
public class ExpressionScanner {

    /***************************************************************************
     *                                                                         *
     * Private Fields                                                          *
     *                                                                         *
     **************************************************************************/

    private final Map<String, Token> terminalMap = new HashMap<>();
    private final Predicate<String> isIdentifierPredicate;
    private final Predicate<String> isLiteralValuePredicate;

    private static final Pattern DefaultIdentifierPattern = Pattern.compile("^[a-zA-Z]+\\w+$");

    private static final Pattern NumberPattern = Pattern.compile("^[0-9]+$");
    private static final Pattern FloatingPointNumberPattern = Pattern.compile("^[0-9]+\\.[0-9]+$");


    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/

    /**
     * Creates a new expression scanner using the given operator-set.
     * Uses the default word-pattern matcher, which matches alpha-characters as words.
     * @param operatorSet
     */
    public ExpressionScanner(OperatorSet operatorSet){
        this(operatorSet, DefaultIdentifierPattern);
    }

    /**
     * Creates a new expression scanner using the given operator-set and word regex.
     * @param operatorSet
     */
    public ExpressionScanner(OperatorSet operatorSet, Pattern isWordRegex){
        this(
            defaultTerminals(operatorSet),

                // Default identifier
                x -> isWordRegex.matcher(x).matches(),

                // Default literal values (strings not supported by default)
                x -> x.equals("true") || x.equals("false") || NumberPattern.matcher(x).matches() || FloatingPointNumberPattern.matcher(x).matches());
    }


    /**
     * Creates a new ExpressionTokenizer with the given Operators
     */
    public ExpressionScanner(
            Iterable<Token> terminalTokens,
            Predicate<String> isIdentifierPredicate,
            Predicate<String> isLiteralValuePredicate){

        if(terminalTokens == null) throw new ArgumentNullException("terminalTokens");
        if(isIdentifierPredicate == null) throw new ArgumentNullException("isWordPredicate");

        this.isIdentifierPredicate = isIdentifierPredicate;
        this.isLiteralValuePredicate = isLiteralValuePredicate;
        for(Token op : terminalTokens){
            terminalMap.put(op.getValue(), op);
        }
    }


    /***************************************************************************
     *                                                                         *
     * Public API                                                              *
     *                                                                         *
     **************************************************************************/

    /**
     * Turns the given string into a token stream.
     * @param expression
     * @return
     * @throws ArgumentNullException If expression is null.
     */
    public Stream<Token> tokenize(String expression){

        if(expression == null) throw new ArgumentNullException("expression");


        List<Token> tokens = new ArrayList<>();


        RawTokenizer tokenizer = new RawTokenizer(expression, terminalMap.keySet());

        for(String rawToken : tokenizer.tokenize()){
            Token t = emit(rawToken);
            if(t != null){
                tokens.add(t);
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
        Token t = getTerminal(currentWord);

        if(t == null){
            if(isIdentifier(currentWord)){
                t = new Token(TokenType.Identifier, currentWord);
            }else if(isLiteral(currentWord)){
                t = new Token(TokenType.Literal, currentWord);
            }
        }

        if(t != null && t.getType() != TokenType.Whitespace){
            return t;
        }else
            return null;
    }

    private boolean isIdentifier(String word){
        return isIdentifierPredicate.test(word);
    }

    private boolean isLiteral(String word){
        return isLiteralValuePredicate.test(word);
    }

    private Token getTerminal(String terminal) {
        if(terminalMap.containsKey(terminal)){
            return terminalMap.get(terminal);
        }
        return null;
    }


    private static Iterable<Token> defaultTerminals(OperatorSet operatorSet){

        if(operatorSet == null) throw new ArgumentNullException("operatorSet");


        List<Token> defaultTerminals = new ArrayList<>();

        defaultTerminals.add(new Token(TokenType.Whitespace, " "));
        defaultTerminals.add(new Token(TokenType.Whitespace, "\t"));
        defaultTerminals.add(new Token(TokenType.Whitespace, ","));

        defaultTerminals.add(new Token(TokenType.Parentheses_Open, "("));
        defaultTerminals.add(new Token(TokenType.Parentheses_Closed, ")"));

        for(Operator o : operatorSet.getAllOperators()){
            defaultTerminals.add(new Token(TokenType.Operator, o.getSign()));
        }

        return defaultTerminals;
    }

}
