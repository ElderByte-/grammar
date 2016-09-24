package com.elderbyte.grammar.math;

import com.elderbyte.grammar.core.IExpressionParser;
import com.elderbyte.grammar.core.dom.expressions.*;
import com.elderbyte.grammar.core.eval.GenericExpressionEvaluator;

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

            case "^": // We interpret ^ as pow as commonly done in math, not xor as in java.
                return Math.pow(left, right);


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