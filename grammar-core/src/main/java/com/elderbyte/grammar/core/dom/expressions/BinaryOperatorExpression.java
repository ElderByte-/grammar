package com.elderbyte.grammar.core.dom.expressions;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinaryOperatorExpression that = (BinaryOperatorExpression) o;
        return getOperator().equals(that.getOperator()) && (super.equals(o));
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getOperator().hashCode();
        return result;
    }
}
