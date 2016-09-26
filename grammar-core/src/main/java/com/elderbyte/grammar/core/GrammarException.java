package com.elderbyte.grammar.core;

/**
 * Thrown when there was a problem in handling the code dome, such
 * as scanner or parse error, AST builder errors.
 *
 * */
public class GrammarException extends RuntimeException {


    public GrammarException(String message){
        super(message);
    }

    public GrammarException(String message, Exception cause){
        super(message, cause);

    }
}
