package com.elderbyte.grammar.scanner;

import com.elderbyte.grammar.dom.expressions.Arity;
import com.elderbyte.grammar.dom.expressions.Operator;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds operator definitions for a parser
 */
public class OperatorSet {

    private final Map<String, Operator> operators = new HashMap<>();
    private final  Map<String, Operator> unaryOperators = new HashMap<>();

    public OperatorSet(Operator... operators){
        for (Operator o : operators){
            this.operators.put(o.getSign(), o);
        }
    }

    public OperatorSet(Iterable<Operator> operators){
        for (Operator o : operators){
            this.operators.put(o.getSign(), o);
        }
    }

    Iterable<Operator> getAllOperators(){
        return operators.values();
    }


    public Operator findOperator(Token token){
        Operator op = operators.get(token.getValue());


        if(op != null && token.hasUnaryMark() && op.getArity() != Arity.Unary){
            op = proxyUnary(op, token);
        }

        return op;
    }

    private Operator proxyUnary(Operator original, Token token){
        Operator unary = unaryOperators.get(token.getValue());
        if(unary == null){
            unary = new Operator(
                    original.getSign(),
                    99,
                    original.isLeftAssociative(),
                    Arity.Unary);
        }
        return unary;
    }


}
