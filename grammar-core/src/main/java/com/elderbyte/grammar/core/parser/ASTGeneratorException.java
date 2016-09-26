package com.elderbyte.grammar.core.parser;

import com.elderbyte.grammar.core.GrammarException;

/**
 * Thrown when generating the AST has failed
 */
public class ASTGeneratorException extends GrammarException {

    public ASTGeneratorException(String message){
        super(message);
    }
}
