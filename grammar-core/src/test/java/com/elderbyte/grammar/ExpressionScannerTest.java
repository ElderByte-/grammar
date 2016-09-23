package com.elderbyte.grammar;

import com.elderbyte.grammar.dom.expressions.Arity;
import com.elderbyte.grammar.dom.expressions.Operator;
import com.elderbyte.grammar.scanner.ExpressionScanner;
import com.elderbyte.grammar.scanner.OperatorSet;
import com.elderbyte.grammar.scanner.Token;
import com.elderbyte.grammar.scanner.TokenType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by isnull on 23.09.16.
 */
public class ExpressionScannerTest {


    private ExpressionScanner scanner;

    @Before
    public void init(){

        scanner = new ExpressionScanner(new OperatorSet(
                new Operator("++", 10, true, Arity.Unary),
                new Operator("+", 2, true, Arity.Binary)
        ));
    }

    @Test
    public void testSimple(){
        List<Token> tokens = scanner.tokenize("1").collect(Collectors.toList());
        Assert.assertEquals(1, tokens.size());
        Assert.assertEquals("1", tokens.get(0).getValue());
    }


    @Test
    public void testSimilarStart(){
        List<Token> tokens = scanner.tokenize("++bar").collect(Collectors.toList());

        Assert.assertEquals(TokenType.Operator, tokens.get(0).getType());
        Assert.assertEquals("++", tokens.get(0).getValue());
        Assert.assertEquals("bar", tokens.get(1).getValue());
    }

    @Test
    public void testSimilarStartBoth(){
        List<Token> tokens = scanner.tokenize("foo + ++bar").collect(Collectors.toList());
        Assert.assertEquals("foo", tokens.get(0).getValue());
        Assert.assertEquals("+", tokens.get(1).getValue());
        Assert.assertEquals("++", tokens.get(2).getValue());
        Assert.assertEquals("bar", tokens.get(3).getValue());
    }

}
