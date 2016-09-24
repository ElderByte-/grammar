package com.elderbyte.grammar;

import com.elderbyte.grammar.core.dom.expressions.BinaryOperatorExpression;
import com.elderbyte.grammar.core.dom.expressions.ExpressionNode;
import com.elderbyte.grammar.core.dom.expressions.LiteralValueExpression;
import com.elderbyte.grammar.logic.BooleanExpressionParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class BooleanExpressionParserTest {


    private BooleanExpressionParser booleanExpressionParser;

    @Before
    public void init(){
        booleanExpressionParser = new BooleanExpressionParser();
    }

    @Test
    public void testLiteralTrue(){
        ExpressionNode node = booleanExpressionParser.parseExpression("true");
        Assert.assertEquals(LiteralValueExpression.class, node.getClass());
        Assert.assertEquals("true", ((LiteralValueExpression)node).getValue());
    }

    @Test
    public void testLiteralFalse(){
        ExpressionNode node = booleanExpressionParser.parseExpression("false");
        Assert.assertEquals(LiteralValueExpression.class, node.getClass());
        Assert.assertEquals("false", ((LiteralValueExpression)node).getValue());
    }

    @Test
    public void testAnd(){
        ExpressionNode node = booleanExpressionParser.parseExpression("true & false");
        Assert.assertEquals(BinaryOperatorExpression.class, node.getClass());
        Assert.assertEquals("&", ((BinaryOperatorExpression)node).getOperator().getSign());

        Assert.assertEquals("false", ((LiteralValueExpression)((BinaryOperatorExpression)node).getLeft()).getValue());
        Assert.assertEquals("true", ((LiteralValueExpression)((BinaryOperatorExpression)node).getRight()).getValue());
    }


}
