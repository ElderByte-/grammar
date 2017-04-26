package com.elderbyte.grammar.stringExpr;

import com.elderbyte.grammar.core.scanner.*;

public class QueryExpressionScanner {

    public static ExpressionScanner build(){
        return ExpressionScanner.start()

                .withLiteralStringToggle("'")
                .withLiteralStringToggle("\"")

                .build(new TerminalTokenManager(new OperatorSet(),
                        new Token(TokenType.Literal, "true"),
                        new Token(TokenType.Literal, "false"),
                        new Token(TokenType.Whitespace, " "),
                        new Token(TokenType.Whitespace, "\t"),
                        new Token(TokenType.Parentheses_Open, "("),
                        new Token(TokenType.Parentheses_Closed, ")"),

                        new Token(TokenType.Keyword, "at"),
                        new Token(TokenType.Keyword, "as"),
                        new Token(TokenType.Keyword, "from"),
                        new Token(TokenType.Keyword, "to")
                ));
    }
}