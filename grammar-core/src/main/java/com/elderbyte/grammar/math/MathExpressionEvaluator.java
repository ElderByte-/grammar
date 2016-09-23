package com.elderbyte.grammar.math;

import com.elderbyte.grammar.IExpressionParser;
import com.elderbyte.grammar.dom.expressions.*;

import java.util.Map;

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

    /**
     * Evaluates a complex math expression dynamically.
     *
     * @param expression An expression such as '10 + 5 / 20 * (40 % 2)'
     * @return The result as double, or throws an exception.
     */
    public double eval(String expression) {
        ExpressionNode node = mathExpressionParser.parseExpression(expression);
        return eval(node, null);
    }

    public double eval(String expression, Map<String, Double> context) {
        ExpressionNode node = mathExpressionParser.parseExpression(expression);
        return eval(node, context);
    }

    public double eval(ExpressionNode node, Map<String, Double> context) {


        if (node instanceof LiteralValueExpression) {

            return Double.parseDouble(((LiteralValueExpression) node).getValue());

        } else if (node instanceof BinaryOperatorExpression) {
            double right = eval(((BinaryOperatorExpression) node).getLeft(), context);
            double left = eval(((BinaryOperatorExpression) node).getRight(), context);
            Operator op = ((BinaryOperatorExpression) node).getOperator();

            return evalBinaryOperation(left, op, right);
        }else if(node instanceof UnaryOperatorExpression) {
            double value = eval(((UnaryOperatorExpression) node).getInner(), context);

            return evalUnaryOperation(value, ((UnaryOperatorExpression) node).getOperator());

        }else if(node instanceof VariableReference){

            String variable = ((VariableReference) node).getName();

            if(context == null) throw new IllegalStateException("There are variables such as '"+variable+"' in this expression but you did not provide a variable context!");

            Double varValue = context.get(variable);
            if(varValue != null){
                return varValue;
            }else{
                throw new IllegalStateException("Unknown variable: " + variable);
            }

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