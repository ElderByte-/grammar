package com.elderbyte.grammar.dom.expressions;

/**
 * Represents an expression with only one argument:
 *
 * !true
 *
 * -5
 *
 */
public class UnaryOperatorExpression extends UnaryExpression {

    private final Operator operator;


    public UnaryOperatorExpression(Operator operator, ExpressionNode expressionNode){
        super(expressionNode);
        this.operator = operator;
    }

    public Operator getOperator() {
        return operator;
    }

}
