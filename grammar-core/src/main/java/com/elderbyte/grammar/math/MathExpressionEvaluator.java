package com.elderbyte.grammar.math;

import com.elderbyte.grammar.IExpressionParser;
import com.elderbyte.grammar.dom.expressions.*;
import com.elderbyte.grammar.eval.GenericExpressionEvaluator;

/**
 * Evaluates math expressions
 */
public class MathExpressionEvaluator extends GenericExpressionEvaluator<Double> {


    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/


    public MathExpressionEvaluator(){
        this(new MathExpressionParser());
    }

    public MathExpressionEvaluator(IExpressionParser mathExpressionParser) {
        super(mathExpressionParser);
    }


    /**************************************************************************
     *                                                                         *
     * Private Methods                                                         *
     *                                                                         *
     **************************************************************************/


    @Override
    protected Double evalLiteral(String literal) {
        return Double.parseDouble(literal);
    }

    @Override
    protected Double evalBinaryOperation(Double left, Operator op, Double right) {
        switch (op.getSign()) {

            case "+":
                return left + right;

            case "-":
                return left - right;

            case "*":
                return left * right;

            case "/":
                return left / right;

            case "%":
                return left % right;

            case "^":
                return (Double)(double)(((int)(double)left) ^ ((int)(double)right)); // Hello Java o.0


            default:
                throw new IllegalStateException("Unsupported operator found: " + op);
        }
    }

    @Override
    protected Double evalUnaryOperation(Operator op, Double right) {
        switch (op.getSign()) {

            case "+":
                return right;

            case "-":
                return -right;

            default:
                throw new IllegalStateException("Unsupported operator found: " + op);

        }
    }


}