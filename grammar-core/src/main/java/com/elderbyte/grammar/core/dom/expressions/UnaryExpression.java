package com.elderbyte.grammar.core.dom.expressions;


import com.elderbyte.common.ArgumentNullException;

public abstract class UnaryExpression extends ExpressionNode {

    private ExpressionNode inner;

    public UnaryExpression(ExpressionNode expressionNode){
        setInner(expressionNode);
    }

    public ExpressionNode getInner() {
        return inner;
    }

    public void setInner(ExpressionNode inner){
        if(inner == null) throw new ArgumentNullException("inner");
        this.inner = inner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnaryExpression that = (UnaryExpression) o;

        return getInner().equals(that.getInner());
    }

    @Override
    public int hashCode() {
        return getInner().hashCode();
    }
}
