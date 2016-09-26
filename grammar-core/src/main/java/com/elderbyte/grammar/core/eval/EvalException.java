package com.elderbyte.grammar.core.eval;

/**
 * Thrown when there was an issue evaluating an expression.
 */
public class EvalException extends RuntimeException {

    public EvalException(String message){
        this(message, null);
    }

    public EvalException(String message, Throwable cause){
        super(message, cause);
    }

}
