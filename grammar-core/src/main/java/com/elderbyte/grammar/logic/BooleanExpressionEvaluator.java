package com.elderbyte.grammar.logic;

import com.elderbyte.grammar.core.IExpressionParser;
import com.elderbyte.grammar.core.dom.expressions.Operator;
import com.elderbyte.grammar.core.eval.GenericExpressionEvaluator;

/**
 * Evaluates boolean expressions
 */
public class BooleanExpressionEvaluator extends GenericExpressionEvaluator<Boolean> {



    public BooleanExpressionEvaluator(){
        this(new BooleanExpressionParser());
    }

    public BooleanExpressionEvaluator(IExpressionParser expressionParser) {
        super(expressionParser);
    }

    @Override
    protected Boolean evalLiteral(String literal) {
        return Boolean.parseBoolean(literal);
    }

    @Override
    protected Boolean evalBinaryOperation(Boolean left, Operator op, Boolean right) {
        switch (op.getSign()){

            case "&":
                return left & right;

            case "|":
                return left | right;

            case "^":
                return left ^ right;

            default:
                throw new IllegalStateException("Not supported binary operator " + op);
        }
    }

    @Override
    protected Boolean evalUnaryOperation(Operator op, Boolean right) {
        switch (op.getSign()){

            case "!":
                return !right;

            default:
                throw new IllegalStateException("Not supported binary operator " + op);
        }
    }
}
