package com.elderbyte.grammar.core.scanner;

import com.elderbyte.grammar.core.GrammarException;
import com.elderbyte.grammar.core.dom.expressions.Arity;
import com.elderbyte.grammar.core.dom.expressions.Operator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Holds operator definitions for a parser
 */
public class OperatorSet {

    private final Map<String, Map<Arity, Operator>> operators = new HashMap<>();

    public OperatorSet(Operator... operators){
        this(Arrays.asList(operators));
    }

    public OperatorSet(Iterable<Operator> operators){
        for (Operator o : operators){
            addOperator(o);
        }
    }

    public Iterable<String> getAllOperatorSigns(){
        return operators.keySet();
    }


    public Operator findOperator(Token token){
        Map<Arity, Operator> ops = operators.get(token.getValue());

        if(ops != null){
            Operator op = ops.get(token.getArity());

            if(op == null)
                throw new GrammarException("Operator '" + token.getValue() + "' doesn't support " + token.getArity());

            return op;
        }
        throw new GrammarException("Unknown Operator '" + token.getValue() + "'");
    }

    private void addOperator(Operator o){
        addOperator(o.getSign(), o);
        for (String synonym : o.getSignSynonyms()){
            addOperator(synonym, o);
        }
    }


    private void addOperator(String sign, Operator o){

        Map<Arity, Operator> op = operators.get(sign);

        if(op == null){
            op = new HashMap<>();
            operators.put(sign, op);
        }

       op.put(o.getArity(), o);
    }

}
