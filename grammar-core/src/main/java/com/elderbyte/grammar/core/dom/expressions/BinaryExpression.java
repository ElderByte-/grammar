package com.elderbyte.grammar.core.dom.expressions;

import com.elderbyte.common.ArgumentNullException;

/**
 * Represents a binary expression, which splits the expression tree in two branches.
 * Usually used by Operators,
 * right [x] left
 *
 * @see BinaryOperatorExpression
 */
public abstract class BinaryExpression  extends ExpressionNode {

    private ExpressionNode left;
    private ExpressionNode right;

    public BinaryExpression(ExpressionNode left, ExpressionNode right) {
        setLeft(left);
        setRight(right);
    }

    public ExpressionNode getLeft() {
        return left;
    }

    public ExpressionNode getRight() {
        return right;
    }

    public void setLeft(ExpressionNode left) {
        if(left == null) throw new ArgumentNullException("left");
        this.left = left;
    }

    public void setRight(ExpressionNode right) {
        if(left == null) throw new ArgumentNullException("left");
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BinaryExpression that = (BinaryExpression) o;

        if (!getLeft().equals(that.getLeft())) return false;
        return getRight().equals(that.getRight());
    }

    @Override
    public int hashCode() {
        int result = getLeft().hashCode();
        result = 31 * result + getRight().hashCode();
        return result;
    }
}
