package com.elderbyte.grammar.core.scanner;

/**
 * Represents the high level type of a token.
 */
public enum TokenType {

    /**
     * This token is not emitted (usually whitespaces and other)
     */
    Whitespace,

    /**
     * Identifiers such as variable names abc, abc23
     */
    Identifier,

    /**
     * Values such as Numbers, strings, true / false and the like
     */
    Literal,

    /**
     * Special language keywords
     */
    Keyword,

    /**
     * An expression Operator
     */
    Operator,

    /**
     * (
     */
    Parentheses_Open,
    /**
     * )
     */
    Parentheses_Closed,


}
