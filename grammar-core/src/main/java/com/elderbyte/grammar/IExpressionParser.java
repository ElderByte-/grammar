package com.elderbyte.grammar;

import com.elderbyte.grammar.dom.expressions.ExpressionNode;

/**
 * Parses the given code into an Abstrac Syntax Tree (AST)
 */
public interface IExpressionParser {

    /**
     * Parses the given code into an Abstrac Syntax Tree (AST)
     * @param code
     * @return
     */
    ExpressionNode parseExpression(String code);

}
