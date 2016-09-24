package com.elderbyte.grammar.core.dom.expressions;

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


}
