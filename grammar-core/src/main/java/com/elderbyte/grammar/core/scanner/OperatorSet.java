package com.elderbyte.grammar.core.scanner;

import com.elderbyte.grammar.core.dom.expressions.Arity;
import com.elderbyte.grammar.core.dom.expressions.Operator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Holds operator definitions for a parser
 */
public class OperatorSet {

    private final Map<String, Operator> operators = new HashMap<>();
    private final  Map<String, Operator> unaryOperators = new HashMap<>();

    public OperatorSet(Operator... operators){
        this(Arrays.asList(operators));
    }

    public OperatorSet(Iterable<Operator> operators){
        for (Operator o : operators){
            addOperator(o);
        }
    }

    Iterable<Operator> getAllOperators(){
        return operators.values();
    }


    public Operator findOperator(Token token){
        Operator op = operators.get(token.getValue());

        if(op != null && token.hasUnaryMark() && op.getArity() != Arity.Unary){
            op = proxyUnary(op);
        }

        return op;
    }

    private Operator proxyUnary(Operator original){
        Operator unary = unaryOperators.get(original.getSign());
        if(unary == null){
            unary = new Operator(
                    original.getSign(),
                    99,
                    original.isLeftAssociative(),
                    Arity.Unary);
            unaryOperators.put(unary.getSign(), unary);
        }
        return unary;
    }

    private void addOperator(Operator o){
        this.operators.put(o.getSign(), o);
        for (String synonym : o.getSignSynonyms()){
            this.operators.put(synonym, o);
        }
    }


}
