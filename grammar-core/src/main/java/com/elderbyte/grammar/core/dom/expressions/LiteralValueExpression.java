package com.elderbyte.grammar.core.dom.expressions;


public class LiteralValueExpression extends ExpressionNode {

    private final String value;

    public LiteralValueExpression(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }


    @Override
    public String toString() {
        return "LiteralValue('" + value + "')";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LiteralValueExpression that = (LiteralValueExpression) o;

        return value != null ? value.equals(that.value) : that.value == null;

    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
