package com.elderbyte.grammar.core.eval;

import com.elderbyte.grammar.core.GrammarException;
import com.elderbyte.grammar.core.IExpressionParser;
import com.elderbyte.grammar.core.dom.expressions.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@SuppressWarnings("Duplicates")
public abstract class GenericExpressionEvaluator<T> {

    /***************************************************************************
     *                                                                         *
     * Fields                                                                  *
     *                                                                         *
     **************************************************************************/

    private IExpressionParser expressionParser;

    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/

    public GenericExpressionEvaluator(IExpressionParser expressionParser) {
        this.expressionParser = expressionParser;
    }

    /***************************************************************************
     *                                                                         *
     * Public API                                                              *
     *                                                                         *
     **************************************************************************/

    /**
     * Evaluates the given expression to its literal value.
     * @param expression The expression
     * @return The evaluated value
     * @throws EvalException Thrown when evaluation failed.
     */
    public T eval(String expression) throws EvalException {
        return eval(expression, null);
    }

    /**
     * Evaluates the given expression to its literal value, using the given variable
     * context to look up variable-references.
     * @param expression The expression
     * @param context The variable context
     * @return The evaluated value
     * @throws EvalException Thrown when evaluation failed.
     */
    public T eval(String expression, EvalContext<T> context) throws EvalException {

        try{
            ExpressionNode node = expressionParser.parseExpression(expression);
            return eval(node, context);
        }catch(GrammarException e){
            throw new EvalException("Evaluation of '" + expression +"' failed because it could not be parsed.", e);
        }
    }

    /**************************************************************************
     *                                                                         *
     * Private Methods                                                         *
     *                                                                         *
     **************************************************************************/

    protected abstract T evalLiteral(String literal);

    protected abstract T evalBinaryOperation(T left, Operator op, T right);

    protected abstract T evalUnaryOperation(Operator op, T right);

    /**
     * Subclasses may override this method to apply their own node handling
     * @param node The node to evaluate.
     * @param context The current variable context
     * @return The evaluated value
     */
    protected T evalNode(ExpressionNode node, EvalContext<T> context){
        return null;
    }


    private T eval(ExpressionNode node, EvalContext<T> context) {

        T val = evalNode(node, context);

        if(val != null) return val;

        if (node instanceof LiteralValueExpression) {

            return evalLiteral(((LiteralValueExpression) node).getValue());

        } else if (node instanceof BinaryOperatorExpression) {

            T right = eval(((BinaryOperatorExpression) node).getRight(), context);
            T left = eval(((BinaryOperatorExpression) node).getLeft(), context);
            Operator op = ((BinaryOperatorExpression) node).getOperator();

            return evalBinaryOperation(left, op, right);
        }else if(node instanceof UnaryOperatorExpression) {

            T value = eval(((UnaryOperatorExpression) node).getInner(), context);
            return evalUnaryOperation(((UnaryOperatorExpression) node).getOperator(), value);

        }else if(node instanceof VariableReference) {

            String variable = ((VariableReference) node).getName();

            if (context == null)
                throw new EvalException("There are variables such as '" + variable + "' in this expression but you did not provide a variable context!");

            return context.resolveVariable(variable)
                    .orElseThrow(() -> new EvalException("Unknown variable: " + variable));


        }else if(node instanceof  FunctionInvokationExpression){

            String func = ((FunctionInvokationExpression) node).getFunctionName();
            List<T> argumentValues = ((FunctionInvokationExpression) node).getArguments().stream()
                    .map(n -> eval(n, context)).collect(Collectors.toList());
            T value = context.invoke(func, argumentValues);
            return value;
        }else{
            throw new EvalException("Unsupported node encountered: " + node);
        }
    }

}
