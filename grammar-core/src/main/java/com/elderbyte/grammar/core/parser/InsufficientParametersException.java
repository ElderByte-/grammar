package com.elderbyte.grammar.core.parser;

/**
 * Thrown when there where insufficent parameters for an operator
 */
public class InsufficientParametersException extends ASTGeneratorException {

    public InsufficientParametersException(String message){
        super(message);
    }
}
