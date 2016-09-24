package com.elderbyte.grammar.core.scanner;

import com.elderbyte.common.ArgumentNullException;
import com.elderbyte.grammar.core.CodeDomException;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;
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

    private final TerminalTokenManager terminalManager;
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
     * @param terminalManager
     */
    public ExpressionScanner(TerminalTokenManager terminalManager){
        this(terminalManager, DefaultIdentifierPattern);
    }

    /**
     * Creates a new expression scanner using the given operator-set and word regex.
     * @param terminalManager
     */
    public ExpressionScanner(TerminalTokenManager terminalManager, Pattern isWordRegex){
        this(
                terminalManager,

                // Default identifier
                x -> isWordRegex.matcher(x).matches(),

                // Default literal values (strings not supported by default)
                x -> NumberPattern.matcher(x).matches() || FloatingPointNumberPattern.matcher(x).matches());
    }


    /**
     * Creates a new ExpressionTokenizer with the given Operators
     */
    public ExpressionScanner(
            TerminalTokenManager terminalManager,
            Predicate<String> isIdentifierPredicate,
            Predicate<String> isLiteralValuePredicate){

        if(terminalManager == null) throw new ArgumentNullException("terminalManager");
        if(isIdentifierPredicate == null) throw new ArgumentNullException("isWordPredicate");

        this.terminalManager = terminalManager;
        this.isIdentifierPredicate = isIdentifierPredicate;
        this.isLiteralValuePredicate = isLiteralValuePredicate;
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


        RawTokenizer tokenizer = new RawTokenizer(expression, terminalManager.getTerminalKeywords());

        Token previous = null;

        for(String rawToken : tokenizer.tokenize()){
            Token t = emit(rawToken);
            if(t != null){

                if(t.getType() == TokenType.Operator){
                    if(previous == null || previous.getType() == TokenType.Operator){
                        t.markUnary();
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
            if(isLiteral(currentWord)){
                t = new Token(TokenType.Literal, currentWord);
            }else if(isIdentifier(currentWord)){
                t = new Token(TokenType.Identifier, currentWord);
            }else{
                throw new CodeDomException("Unexpected token: " + currentWord);
            }
        }

        if(t.getType() != TokenType.Whitespace){
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



}
