package com.elderbyte.grammar;

import com.elderbyte.grammar.logic.BooleanExpressionEvaluator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class BooleanExpressionEvaluatorTest {

    private BooleanExpressionEvaluator booleanExpressionEvaluator;

    @Before
    public void init(){
        booleanExpressionEvaluator = new BooleanExpressionEvaluator();
    }

    @Test
    public void testLiteralTrue(){
        Assert.assertEquals(true, booleanExpressionEvaluator.eval("true"));
    }

    @Test
    public void testLiteralFalse(){
        Assert.assertEquals(false, booleanExpressionEvaluator.eval("false"));
    }

    @Test
    public void testSimpleNot(){
        Assert.assertEquals(true, booleanExpressionEvaluator.eval("!false"));
        Assert.assertEquals(false, booleanExpressionEvaluator.eval("!true"));

    }

    @Test
    public void testAnd(){
        Assert.assertEquals(false, booleanExpressionEvaluator.eval("false & false"));
        Assert.assertEquals(false, booleanExpressionEvaluator.eval("true & false"));
        Assert.assertEquals(false, booleanExpressionEvaluator.eval("false & true"));
        Assert.assertEquals(true, booleanExpressionEvaluator.eval("true & true"));
    }

    @Test
    public void testOr(){
        Assert.assertEquals(false, booleanExpressionEvaluator.eval("false | false"));
        Assert.assertEquals(true, booleanExpressionEvaluator.eval("true | false"));
        Assert.assertEquals(true, booleanExpressionEvaluator.eval("false | true"));
        Assert.assertEquals(true, booleanExpressionEvaluator.eval("true | true"));
    }

    @Test
    public void testXOr(){
        Assert.assertEquals(false, booleanExpressionEvaluator.eval("false ^ false"));
        Assert.assertEquals(true, booleanExpressionEvaluator.eval("true ^ false"));
        Assert.assertEquals(true, booleanExpressionEvaluator.eval("false ^ true"));
        Assert.assertEquals(false, booleanExpressionEvaluator.eval("true ^ true"));
    }

    @Test
    public void testComplex1(){
        Assert.assertEquals(true, booleanExpressionEvaluator.eval("(true | false) | (false & true)"));
    }

    @Test
    public void testComplex2(){
        Assert.assertEquals(false, booleanExpressionEvaluator.eval("!(true | false) | (false & true)"));
    }


}
