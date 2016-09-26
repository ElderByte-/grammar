package com.elderbyte.grammar.logic;

import com.elderbyte.grammar.core.ExpressionParser;
import com.elderbyte.grammar.core.dom.expressions.Arity;
import com.elderbyte.grammar.core.dom.expressions.Operator;
import com.elderbyte.grammar.core.scanner.OperatorSet;
import com.elderbyte.grammar.core.scanner.Token;
import com.elderbyte.grammar.core.scanner.TokenType;

public class BooleanExpressionParser extends ExpressionParser  {

    public BooleanExpressionParser(){
        super(new OperatorSet(
                    new Operator("&", 3, true, Arity.Binary, "and"),
                    new Operator("|", 3, true, Arity.Binary, "or"),
                    new Operator("^", 3, true, Arity.Binary, "xor"),

                    new Operator("!", 99, true, Arity.Unary, "not", "~")
                ),
                new Token(TokenType.Literal, "true"),
                new Token(TokenType.Literal, "false"),
                new Token(TokenType.Whitespace, " "),
                new Token(TokenType.Whitespace, "\t"),
                new Token(TokenType.Parentheses_Open, "("),
                new Token(TokenType.Parentheses_Closed, ")")
        );
    }
}
