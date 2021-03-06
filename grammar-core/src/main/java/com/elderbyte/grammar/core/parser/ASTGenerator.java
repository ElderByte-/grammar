package com.elderbyte.grammar.core.parser;

import com.elderbyte.grammar.core.dom.expressions.*;
import com.elderbyte.grammar.core.scanner.OperatorSet;
import com.elderbyte.grammar.core.scanner.Token;
import com.elderbyte.grammar.core.scanner.TokenType;

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

            if(isOperatorOrFunction(token)){
                // token is Operator / Function
                // Get count of required params for this op / func
                int available = expressionNodeStack.size();
                int required;
                if(token.getType() == TokenType.Operator) {
                    required = requiredParameters(token);
                }else{
                    // In case of a function
                    required = available;
                }


                if(available < required){
                    throw new InsufficientParametersException("Not enough parameters for operator "+token+". Have " +  available + " but need " + required);
                }

                List<ExpressionNode> params = new ArrayList<>();
                for(int i = 0; i < required; i++){
                    params.add(expressionNodeStack.pop());
                }

                Collections.reverse(params); // They are reversed due RPN

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

    private ExpressionNode buildExpression(Token token, List<ExpressionNode> params){


        switch (token.getType()){
            case Operator:
                Operator op = operatorSet.findOperator(token);
                if(op.getArity() == Arity.Binary){
                    return new BinaryOperatorExpression(params.get(0), op, params.get(1));
                }else if(op.getArity() == Arity.Unary){
                    return new UnaryOperatorExpression(op, params.get(0));
                }
                break;

            case Identifier:
                if(token.hasFunctionFlag()){
                    return new FunctionInvokationExpression(token.getValue(), params);
                }
                break;

        }
        throw new ASTGeneratorException("Failed to build Expression. Unexpected Token: " + token);
    }

    private boolean isIdentfier(Token token){
        return token.getType() == TokenType.Identifier;
    }


    private boolean isLiteral(Token token){
        return token.getType() == TokenType.Literal;
    }

    private boolean isOperatorOrFunction(Token token){
        return token.getType() == TokenType.Operator || isFunction(token);
    }

    private boolean isFunction(Token token){
        return token.hasFunctionFlag();
    }

    private int requiredParameters(Token token) {

        int paramCount;

        if(token.getType() == TokenType.Operator){

            Operator op = operatorSet.findOperator(token);

            if(op.getArity() == Arity.Unary){
                paramCount = 1;
            }else if(op.getArity() == Arity.Binary) {
                paramCount = 2;
            }else if(op.getArity() == Arity.Ternary){
                paramCount = 3;
            }else{
                throw new ASTGeneratorException("Operator arity " + op.getArity() + " not yet supported!");
            }
            return paramCount;
        }else{
            // Function? Not implemented yet. Would require look-ahead to count params...
            throw new ASTGeneratorException("Only operators are currently supported");
        }
    }

}
