package com.elderbyte.grammar.parser;

import com.elderbyte.grammar.dom.expressions.*;
import com.elderbyte.grammar.scanner.OperatorSet;
import com.elderbyte.grammar.scanner.Token;
import com.elderbyte.grammar.scanner.TokenType;

import java.util.*;
import java.util.stream.Stream;

/**
 * Creates an Abstract Syntax tree from a stream of tokens in the RPN format.
 */
public class ASTGenerator {

    private final OperatorSet operatorSet;


    public ASTGenerator(OperatorSet operatorSet){
        this.operatorSet = operatorSet;
    }

    /**
     * Parses a RPN token stream into an AST
     * @param rpn
     * @return
     * @throws ASTGeneratorException
     */
    public ExpressionNode parse(Stream<Token> rpn){

        Stack<ExpressionNode> expressionNodeStack = new Stack<>();

        rpn.forEach(token -> {


            if(isOperator(token)){
                // token is Operator / Function
                // Get count of required params for this op / func
                int paramsNumber = getNumberOfParams(token, expressionNodeStack.size());

                List<ExpressionNode> params = new ArrayList<>();
                for(int i = 0; i < paramsNumber; i++){
                    params.add(expressionNodeStack.pop());
                }

                ExpressionNode expressionNode = buildExpression(token, params);
                expressionNodeStack.push(expressionNode);
            }else if(isLiteral(token)) {
                expressionNodeStack.push(new LiteralValueExpression(token.getValue()));
            }else if(isIdentfier(token)){
                expressionNodeStack.push(new VariableReference(token.getValue()));

            }else {
                throw new ASTGeneratorException("Unexpected token " + token + "!");
            }
        });

        if(expressionNodeStack.size() == 1){
            return expressionNodeStack.pop();
        }else{
            throw new ASTGeneratorException("Too many values for the given operators! Stack was " + expressionNodeStack.size());
        }
    }



    private ExpressionNode buildExpression(Token operator, List<ExpressionNode> params){
        if(operator.getType() == TokenType.Operator){
            Operator op = operatorSet.findOperator(operator.getValue());
            if(op.getArity() == Arity.Binary || (op.getArity() == Arity.UnaryOrBinary && params.size() == 2)){
                return new BinaryOperatorExpression(params.get(0), op, params.get(1));
            }else if(op.getArity() == Arity.Unary || op.getArity() == Arity.UnaryOrBinary){
                return new UnaryOperatorExpression(op, params.get(0));
            }
        }
        throw new ASTGeneratorException("Failed to build Expression. Unexpected Operator Token " + operator + " [" + operator.getType() + "]!");
    }

    private boolean isIdentfier(Token token){
        return token.getType() == TokenType.Identifier;
    }


    private boolean isLiteral(Token token){
        return token.getType() == TokenType.Literal;
    }

    private boolean isOperator(Token token){
        return token.getType() == TokenType.Operator || isFunction(token);
    }

    private boolean isFunction(Token token){
        return false;   // TODO
    }

    private int getNumberOfParams(Token token, int available) {

        int paramCount;

        if(token.getType() == TokenType.Operator){

            Operator op = operatorSet.findOperator(token.getValue());

            if(op.getArity() == Arity.Binary){
                paramCount = 2;
            }else if(op.getArity() == Arity.Unary) {
                paramCount = 1;
            }else if(op.getArity() == Arity.UnaryOrBinary){
                paramCount = Math.max(Math.min(available, 2), 1);
            }else{
                throw new IllegalStateException("Operator arity " + op.getArity() + " not yet supported!");
            }

            if(available < paramCount){
                throw new InsufficientParametersException("Not enough parameters for operator "+op+". Have " +  available + " but need " + paramCount);
            }
            return paramCount;
        }else{
            // Function? Not implemented yet. Would require look-ahead to count params...
            throw new IllegalStateException("Only operators are currently supported");
        }
    }

}
