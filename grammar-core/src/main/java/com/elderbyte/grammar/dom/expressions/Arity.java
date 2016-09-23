package com.elderbyte.grammar.dom.expressions;

/**
 * The arity of a function or operation is the number of arguments or operands that the function takes.
 */
public enum  Arity {

    /**
     * No Argument
     * Example - function with no parameter: pi()
     */
    Nullary,

    /**
     * One argument:  -6
     */
    Unary,

    /**
     * Two arguments: 5 * 7
     */
    Binary,

    /**
     * Three arguments:  a ? b : c
     */
    Ternary,

    /**
     * Functions
     */
    Any
}
