package com.elderbyte.grammar.core.parser;

import com.elderbyte.grammar.core.CodeDomException;

/**
 * Thrown when generating the AST has failed
 */
public class ASTGeneratorException extends CodeDomException {

    public ASTGeneratorException(String message){
        super(message);
    }
}
