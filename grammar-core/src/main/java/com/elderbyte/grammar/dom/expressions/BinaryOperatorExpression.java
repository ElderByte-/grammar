package com.elderbyte.grammar.dom.expressions;

/**
 * Represents a binary expression with a defined operator:
 *
 * [left] (operator) [right]
 *
 * Examples:
 * (a + b)
 * (3 - 6)
 * (true | false)
 */
public class BinaryOperatorExpression extends BinaryExpression {

    private final Operator operator;

    /**
     * Creates a new BinaryOperatorExpression
     */
    public BinaryOperatorExpression(ExpressionNode left, Operator operator, ExpressionNode right) {
        super(left, right);
        this.operator = operator;
    }


    public Operator getOperator() {
        return operator;
    }


    @Override
    public String toString() {
        return "BinOP{" + getLeft() + " " + operator.getSign() + " " + getRight() + '}';
    }
}
