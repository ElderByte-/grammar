package com.elderbyte.grammar;

import com.elderbyte.grammar.dom.expressions.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class MathExpressionParserTest {


    private MathExpressionParser mathExpressionParser;

    @Before
    public void init(){
        mathExpressionParser = new MathExpressionParser();
    }


    @Test
    public void testLiteralNumber(){
        ExpressionNode node = mathExpressionParser.parseExpression("5");
        Assert.assertEquals(LiteralValueExpression.class, node.getClass());
        Assert.assertEquals("5", ((LiteralValueExpression)node).getValue());
    }


    @Test
    public void testBinaryAdd(){
        ExpressionNode node = mathExpressionParser.parseExpression("5 + 6");


        Assert.assertEquals(BinaryOperatorExpression.class, node.getClass());
        BinaryOperatorExpression bex = (BinaryOperatorExpression)node;


        Assert.assertEquals(bex.getOperator().getSign(), "+");

        Assert.assertEquals(LiteralValueExpression.class, bex.getLeft().getClass());
        Assert.assertEquals("6", ((LiteralValueExpression)bex.getLeft()).getValue());

        Assert.assertEquals(LiteralValueExpression.class, bex.getRight().getClass());
        Assert.assertEquals("5", ((LiteralValueExpression)bex.getRight()).getValue());
    }

    @Test(expected = CodeDomException.class)
    public void testIllegalExpression(){
        mathExpressionParser.parseExpression("-");
    }


}
