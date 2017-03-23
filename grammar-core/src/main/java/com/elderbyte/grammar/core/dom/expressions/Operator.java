package com.elderbyte.grammar.core.dom.expressions;

import com.elderbyte.common.ArgumentNullException;

import java.util.Arrays;
import java.util.List;

/**
 * Represents an Operator in an expression
 */
public final class Operator {

    private final Arity arity;
    private final String sign;
    private final int precedence;
    private final boolean isLeftAssociative;
    private final List<String> signSynonyms;

    /**
     * Creates a new Operator
     * @param sign The operator symbol
     * @param precedence
     * @param isLeftAssociative
     * @param arity Is this Operator strictly binary, unary etc?
     * @param synonymSigns Alternative (synonym) signs for this operators
     */
    public Operator(String sign, int precedence, boolean isLeftAssociative, Arity arity, String... synonymSigns){

        if(sign == null) throw new ArgumentNullException("sign");
        if(arity == null) throw new ArgumentNullException("arity");

        this.arity = arity;
        this.sign = sign;
        this.precedence = precedence;
        this.isLeftAssociative = isLeftAssociative;
        this.signSynonyms = Arrays.asList(synonymSigns);
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

    public Iterable<String> getSignSynonyms() {
        return signSynonyms;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Operator operator = (Operator) o;

        if (getPrecedence() != operator.getPrecedence()) return false;
        if (isLeftAssociative() != operator.isLeftAssociative()) return false;
        if (getArity() != operator.getArity()) return false;
        return getSign().equals(operator.getSign());
    }

    @Override
    public int hashCode() {
        int result = getArity().hashCode();
        result = 31 * result + getSign().hashCode();
        result = 31 * result + getPrecedence();
        result = 31 * result + (isLeftAssociative() ? 1 : 0);
        return result;
    }
}
