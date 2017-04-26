package com.elderbyte.grammar.stringExpr;

import com.elderbyte.grammar.core.scanner.ExpressionScanner;
import com.elderbyte.grammar.core.scanner.Token;
import com.elderbyte.grammar.core.scanner.TokenType;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;


public class QueryStringExpressionTest {


    private ExpressionScanner queryScanner = QueryExpressionScanner.build();

    @Test
    public void testIdentifierKeywords(){

        List<Token> tokens = queryScanner.tokenize("hans at ABB as CEO").collect(Collectors.toList());

        Assert.assertEquals(TokenType.Identifier, tokens.get(0).getType());

        Assert.assertEquals(TokenType.Keyword, tokens.get(1).getType());
        Assert.assertEquals("at", tokens.get(1).getValue());

        Assert.assertEquals(TokenType.Identifier, tokens.get(2).getType());

        Assert.assertEquals(TokenType.Keyword, tokens.get(3).getType());
        Assert.assertEquals("as", tokens.get(3).getValue());

        Assert.assertEquals(TokenType.Identifier, tokens.get(4).getType());
    }

    @Test
    public void testliteralString(){

        List<Token> tokens = queryScanner.tokenize("hans at 'A B C' as CEO").collect(Collectors.toList());

        Assert.assertEquals(TokenType.Identifier, tokens.get(0).getType());

        Assert.assertEquals(TokenType.Keyword, tokens.get(1).getType());
        Assert.assertEquals("at", tokens.get(1).getValue());

        Assert.assertEquals(TokenType.Literal, tokens.get(2).getType());
        Assert.assertEquals("A B C", tokens.get(2).getValue());


        Assert.assertEquals(TokenType.Keyword, tokens.get(3).getType());
        Assert.assertEquals("as", tokens.get(3).getValue());

        Assert.assertEquals(TokenType.Identifier, tokens.get(4).getType());
    }


}
