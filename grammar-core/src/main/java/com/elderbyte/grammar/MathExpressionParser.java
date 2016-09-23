package com.elderbyte.grammar;

import com.elderbyte.grammar.dom.expressions.Arity;
import com.elderbyte.grammar.dom.expressions.Operator;
import com.elderbyte.grammar.scanner.OperatorSet;

import java.util.ArrayList;
import java.util.List;

/**
 * A basic math expression parser
 */
public class MathExpressionParser extends ExpressionParser {

    public MathExpressionParser(){
        super(mathOpSet());
    }

    private static OperatorSet mathOpSet(){
        List<Operator> mathOperators = new ArrayList<>();

        mathOperators.add(new Operator("+", 2, true, Arity.Binary));
        mathOperators.add(new Operator("-", 2, true, Arity.Binary));
        mathOperators.add(new Operator("*", 3, true, Arity.Binary));
        mathOperators.add(new Operator("/", 3, true, Arity.Binary));
        mathOperators.add(new Operator("^", 4, false, Arity.Binary));
        mathOperators.add(new Operator("%", 4, false, Arity.Binary));

        mathOperators.add(new Operator("&", 3, true, Arity.Binary));
        mathOperators.add(new Operator("|", 3, true, Arity.Binary));

        mathOperators.add(new Operator("!", 99, true, Arity.Unary));

        return new OperatorSet(mathOperators);
    }
}
