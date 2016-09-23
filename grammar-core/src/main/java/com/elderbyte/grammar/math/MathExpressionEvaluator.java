package com.elderbyte.grammar.math;

import com.elderbyte.grammar.IExpressionParser;
import com.elderbyte.grammar.dom.expressions.*;

/**
 * Evaluates math expressions
 */
public class MathExpressionEvaluator {

    /***************************************************************************
     *                                                                         *
     * Fields                                                                  *
     *                                                                         *
     **************************************************************************/

    private IExpressionParser mathExpressionParser;

    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/

    public MathExpressionEvaluator(IExpressionParser mathExpressionParser) {
        this.mathExpressionParser = mathExpressionParser;
    }

    /***************************************************************************
     *                                                                         *
     * Properties                                                              *
     *                                                                         *
     **************************************************************************/


    /***************************************************************************
     *                                                                         *
     * Public API                                                              *
     *                                                                         *
     **************************************************************************/


    public double eval(String expression) {
        ExpressionNode node = mathExpressionParser.parseExpression(expression);
        return eval(node);
    }

    public double eval(ExpressionNode node) {


        if (node instanceof LiteralValueExpression) {

            return Double.parseDouble(((LiteralValueExpression) node).getValue());

        } else if (node instanceof BinaryOperatorExpression) {
            double right = eval(((BinaryOperatorExpression) node).getLeft());
            double left = eval(((BinaryOperatorExpression) node).getRight());
            Operator op = ((BinaryOperatorExpression) node).getOperator();

            return evalBinaryOperation(left, op, right);
        }else if(node instanceof UnaryOperatorExpression){
            double value = eval(((UnaryOperatorExpression) node).getInner());

            return evalUnaryOperation(value, ((UnaryOperatorExpression) node).getOperator());
        }else{
            throw new IllegalStateException("Unsupported node encountered: " + node);
        }
    }



    /**************************************************************************
     *                                                                         *
     * Private Methods                                                         *
     *                                                                         *
     **************************************************************************/


    private double evalBinaryOperation(double left, Operator op, double right) {
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
                return (int)left ^ (int)right;


            default:
                throw new IllegalStateException("Unsupported operator found: " + op);

        }
    }

    private double evalUnaryOperation(double value, Operator op) {

        switch (op.getSign()) {

            case "+":
                return value;

            case "-":
                return -value;

            default:
                throw new IllegalStateException("Unsupported operator found: " + op);

        }
    }

}