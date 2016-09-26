package com.elderbyte.grammar.core;

import com.elderbyte.grammar.core.dom.expressions.ExpressionNode;

/**
 * Parses the given code into an Abstrac Syntax Tree (AST)
 */
public interface IExpressionParser {

    /**
     * Parses the given expression into an Abstrac Syntax Tree (AST)
     * @param expression The expression string to parse
     * @return  Returns the top node of the AST
     */
    ExpressionNode parseExpression(String expression) throws GrammarException;

}
