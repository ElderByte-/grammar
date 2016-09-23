package com.elderbyte.grammar;


import com.elderbyte.grammar.math.MathExpressionEvaluator;
import com.elderbyte.grammar.math.MathExpressionParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MathExpressionEvaluatorTest {


    private MathExpressionEvaluator math;

    @Before
    public void init(){
        math = new MathExpressionEvaluator(new MathExpressionParser());
    }


    @Test
    public void testLiteralNumber(){
        double result = math.eval("5");
        Assert.assertEquals(5, result, 0);
    }

    @Test
    public void testAdd(){
        double result = math.eval("5 + 5");
        Assert.assertEquals(10, result, 0);
    }

    @Test
    public void testAdd2(){
        double result = math.eval("3 + 20");
        Assert.assertEquals(23, result, 0);
    }

    @Test
    public void testSub1(){
        double result = math.eval("20 - 12");
        Assert.assertEquals(8, result, 0);
    }

    @Test
    public void testSub2(){
        double result = math.eval("20 - 30");
        Assert.assertEquals(-10, result, 0);
    }

    @Test
    public void testComplex(){
        double result = math.eval("-180.5 + 5 * 2");
        Assert.assertEquals(-170.5, result, 0);
    }

    @Test
    public void testComplex2(){
        double result = math.eval("10 + 5 / 20 * (40 % 2)");
        Assert.assertEquals(10, result, 0);
    }

    @Test
    public void testVariableContext(){

        Map<String, Double> ctx = new HashMap<>();
        ctx.put("alpha", 15d);
        double result = math.eval("90 - alpha", ctx);
        Assert.assertEquals(75, result, 0);
    }

    @Test(expected = IllegalStateException.class)
    public void testVariableContextMissing(){
        math.eval("90 - alpha");
    }

    @Test(expected = IllegalStateException.class)
    public void testVariableMissing(){
        math.eval("90 - alpha", new HashMap<>());
    }


}
