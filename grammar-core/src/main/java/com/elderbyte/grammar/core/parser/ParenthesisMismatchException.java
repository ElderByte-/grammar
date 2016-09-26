package com.elderbyte.grammar.core.parser;

import com.elderbyte.grammar.core.GrammarException;

/**
 * Thrown when parenthesis don't match, i.e. when an open one doesnt have its closing one.
 */
public class ParenthesisMismatchException extends GrammarException {
    public ParenthesisMismatchException(String message){
        super(message);
    }
}
