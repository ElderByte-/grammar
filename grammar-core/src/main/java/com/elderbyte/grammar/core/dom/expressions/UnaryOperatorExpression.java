package com.elderbyte.grammar.core.dom.expressions;

import com.elderbyte.common.ArgumentNullException;

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
        if(operator == null) throw new ArgumentNullException("operator");
        this.operator = operator;
    }

    public Operator getOperator() {
        return operator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnaryOperatorExpression that = (UnaryOperatorExpression) o;
        return getOperator().equals(that.getOperator()) && super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getOperator().hashCode();
        return result;
    }
}
