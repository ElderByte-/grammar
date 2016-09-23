package com.elderbyte.grammar.dom.expressions;

/**
 * Represents an Operator in an expression
 */
public final class Operator {

    private final Arity arity;
    private final String sign;
    private final int precedence;
    private final boolean isLeftAssociative;

    /**
     * Creates a new Operator
     * @param sign The operator symbol
     * @param precedence
     * @param isLeftAssociative
     * @param arity Is this Operator strictly binary, unary etc?
     */
    public Operator(String sign, int precedence, boolean isLeftAssociative, Arity arity){
        this.arity = arity;
        this.sign = sign;
        this.precedence = precedence;
        this.isLeftAssociative = isLeftAssociative;
    }

    public Arity getArity() {
        return arity;
    }

    public String getSign() {
        return sign;
    }

    public int getPrecedence() {
        return precedence;
    }

    public boolean isLeftAssociative() {
        return isLeftAssociative;
    }


    @Override
    public String toString() {
        return "Operator{" +
            "arity=" + arity +
            ", sign='" + sign + '\'' +
            ", precedence=" + precedence +
            ", isLeftAssociative=" + isLeftAssociative +
            '}';
    }
}
