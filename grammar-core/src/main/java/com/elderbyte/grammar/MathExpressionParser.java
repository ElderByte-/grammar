package com.elderbyte.grammar;

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
        mathOperators.add(new Operator("+", 2, true, false));
        mathOperators.add(new Operator("-", 2, true, false));
        mathOperators.add(new Operator("*", 3, true, false));
        mathOperators.add(new Operator("/", 3, true, false));
        mathOperators.add(new Operator("^", 4, false, false));
        mathOperators.add(new Operator("%", 4, false, false));

        mathOperators.add(new Operator("&", 3, true, false));
        mathOperators.add(new Operator("|", 3, true, false));
        mathOperators.add(new Operator("!", 5, true, true));

        return new OperatorSet(mathOperators);
    }
}