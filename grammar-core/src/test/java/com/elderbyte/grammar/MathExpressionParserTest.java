package com.elderbyte.grammar;

import com.elderbyte.grammar.core.CodeDomException;
import com.elderbyte.grammar.core.dom.expressions.*;
import com.elderbyte.grammar.math.MathExpressionParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


@SuppressWarnings("Duplicates")
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
    public void testLiteralFloatingNumber(){
        ExpressionNode node = mathExpressionParser.parseExpression("5.83");
        Assert.assertEquals(LiteralValueExpression.class, node.getClass());
        Assert.assertEquals("5.83", ((LiteralValueExpression)node).getValue());
    }

    @Test
    public void testUnaryLiteralNumber(){
        ExpressionNode node = mathExpressionParser.parseExpression("-5");
        Assert.assertEquals(UnaryOperatorExpression.class, node.getClass());
        Assert.assertEquals("-", ((UnaryOperatorExpression)node).getOperator().getSign());
        Assert.assertEquals("5", ((LiteralValueExpression)((UnaryOperatorExpression)node).getInner()).getValue());
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

    @Test
    public void testBinarySubtract(){
        ExpressionNode node = mathExpressionParser.parseExpression("5 - 6");


        Assert.assertEquals(BinaryOperatorExpression.class, node.getClass());
        BinaryOperatorExpression bex = (BinaryOperatorExpression)node;


        Assert.assertEquals(bex.getOperator().getSign(), "-");

        Assert.assertEquals(LiteralValueExpression.class, bex.getLeft().getClass());
        Assert.assertEquals("6", ((LiteralValueExpression)bex.getLeft()).getValue());

        Assert.assertEquals(LiteralValueExpression.class, bex.getRight().getClass());
        Assert.assertEquals("5", ((LiteralValueExpression)bex.getRight()).getValue());
    }

    @Test
    public void testUnaryPrecedence(){
        ExpressionNode node = mathExpressionParser.parseExpression("10 * -3");

        Assert.assertEquals(BinaryOperatorExpression.class, node.getClass());
        BinaryOperatorExpression bex = (BinaryOperatorExpression)node;

        Assert.assertEquals(bex.getOperator().getSign(), "*");
    }


    @Test(expected = CodeDomException.class)
    public void testIllegalExpression(){
        mathExpressionParser.parseExpression("-");
    }

}
