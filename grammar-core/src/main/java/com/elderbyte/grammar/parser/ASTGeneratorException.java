package com.elderbyte.grammar.parser;

import com.elderbyte.grammar.CodeDomException;

/**
 * Thrown when generating the AST has failed
 */
public class ASTGeneratorException extends CodeDomException {

    public ASTGeneratorException(String message){
        super(message);
    }
}
