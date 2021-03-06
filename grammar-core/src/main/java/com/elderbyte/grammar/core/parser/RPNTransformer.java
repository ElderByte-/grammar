package com.elderbyte.grammar.core.parser;

import com.elderbyte.grammar.core.GrammarException;
import com.elderbyte.grammar.core.dom.expressions.Operator;
import com.elderbyte.common.ArgumentNullException;
import com.elderbyte.grammar.core.scanner.OperatorSet;
import com.elderbyte.grammar.core.scanner.Token;
import com.elderbyte.grammar.core.scanner.TokenType;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Transforms a stream of tokens into Reverse Polish Notation
 *
 */
public class RPNTransformer {

    /***************************************************************************
     *                                                                         *
     * Private Fields                                                          *
     *                                                                         *
     **************************************************************************/

    private final OperatorSet operatorSet;

    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/

    /**
     * Creates a new RPNTransformer
     * @param operatorSet
     */
    public RPNTransformer(OperatorSet operatorSet){

        if(operatorSet == null) throw new ArgumentNullException("operatorSet");

        this.operatorSet = operatorSet;
    }

    /***************************************************************************
     *                                                                         *
     * Public API                                                              *
     *                                                                         *
     **************************************************************************/

    /**
     * Transfroms the given token stream into RPN.
     * Runtime complexity is O(n)
     *
     * @param tokenStream
     * @return A token stream in RPN order
     *
     * @exception GrammarException Thrown when the transformation failed.
     */
    public Stream<Token> toReversePolishNotation(Stream<Token> tokenStream){

        List<Token> tokens = tokenStream.collect(Collectors.toList());
        Stack<Token> operatorStack = new Stack<>();
        List<Token> rpn = new ArrayList<>();


        for(int i=0; i<tokens.size(); i++){
            Token token = tokens.get(i);
            Token next = i+1<tokens.size() ? tokens.get(i+1) : null;

            token = markIfFunction(token, next);

            if(isFunctionOrOperator(token)) {

                Token o1 = token;

                while (!operatorStack.isEmpty()) {

                    Token o2 = operatorStack.peek();

                    if (isFunctionOrOperator(o2) &&
                            // o1 is left-associative and its precedence is less than or equal to that of o2, or
                            ((isLeftAssociative(o1) && precedence(o1) <= precedence(o2)) |
                                    (!isLeftAssociative(o1) && precedence(o1) < precedence(o2))
                            )) {
                        // then pop o2 off the operator stack, onto the output queue;
                        rpn.add(operatorStack.pop());
                    } else {
                        break;
                    }
                }
                operatorStack.push(o1);
            }else if(isLiteral(token) || isIdentifier(token)){
                rpn.add(token);
            }else if(isLeftParenthesis(token)){
                operatorStack.push(token);
            }else if (isRightParenthesis(token)){

                boolean success = false;
                while (!operatorStack.isEmpty()){
                    Token op = operatorStack.pop();
                    if(isLeftParenthesis(op)){
                        success = true;
                        break;
                    }else{
                        rpn.add(op);
                    }
                }
                if(!success)
                    throw new ParenthesisMismatchException("No closing Parenthesis found for open Parenthesis!");
            }else{
                throw new GrammarException(String.format("Unexpected Token '%s' in RPN Expression!", token));
            }
        }

        while (!operatorStack.isEmpty()){
            Token op = operatorStack.pop();
            if(isParenthesis(op)){
                throw  new ParenthesisMismatchException("");
            }
            rpn.add(op);
        }
        return rpn.stream();
    }


    /***************************************************************************
     *                                                                         *
     * Private methods                                                         *
     *                                                                         *
     **************************************************************************/

    private boolean isParenthesis(Token token){
        return isLeftParenthesis(token) || isRightParenthesis(token);
    }

    private boolean isLeftParenthesis(Token token){
        return token.getType() == TokenType.Parentheses_Open;
    }

    private boolean isRightParenthesis(Token token){
        return token.getType() == TokenType.Parentheses_Closed;
    }

    private int precedence(Token token){

        if(token.getType() == TokenType.Operator){
            Operator op = operatorSet.findOperator(token);
            return op.getPrecedence();
        }else if(token.hasFunctionFlag()){
            return 0; //?
        }else{
            throw new GrammarException("Unexpected token " + token);
        }
    }

    private boolean isLeftAssociative(Token token){

        if(token.getType() == TokenType.Operator){
            Operator op = operatorSet.findOperator(token);
            return op.isLeftAssociative();
        }else if(token.hasFunctionFlag()){
            return true; // ??
        }else{
            throw new GrammarException("Unexpected token " + token);
        }
    }

    private boolean isLiteral(Token token){
        return token.getType() == TokenType.Literal;
    }

    private boolean isIdentifier(Token token){
        return token.getType() == TokenType.Identifier;
    }

    private boolean isFunctionOrOperator(Token token){
        return token.hasFunctionFlag() || token.getType() == TokenType.Operator;
    }

    private Token markIfFunction(Token token, Token next){
        if(next != null){
            if(token.getType() == TokenType.Identifier && next.getType() == TokenType.Parentheses_Open){
                // Token is a function invocation
                return token.asFunction();
            }
        }
        return token;
    }
}
