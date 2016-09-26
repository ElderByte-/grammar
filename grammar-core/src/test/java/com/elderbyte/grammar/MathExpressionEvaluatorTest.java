package com.elderbyte.grammar;


import com.elderbyte.grammar.core.eval.EvalContext;
import com.elderbyte.grammar.core.eval.EvalException;
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
    public void testUnary1(){
        double result = math.eval("20 - -30");
        Assert.assertEquals(50, result, 0);
    }

    @Test
    public void testUnary2(){
        double result = math.eval("20--30");
        Assert.assertEquals(50, result, 0);
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
    public void testPow(){
        double result = math.eval("2 ^ 5");
        Assert.assertEquals(Math.pow(2, 5), result, 0);
    }

    @Test
    public void testVariableContext(){

        EvalContext<Double> context = new EvalContext<>();
        context.setVariable("alpha", 15d);

        double result = math.eval("90 - alpha", context);
        Assert.assertEquals(75, result, 0);
    }

    @Test(expected = EvalException.class)
    public void testVariableContextMissing(){
        math.eval("90 - alpha");
    }

    @Test(expected = EvalException.class)
    public void testVariableMissing(){
        EvalContext<Double> context = new EvalContext<>();
        context.setVariable("huhu", 33d);
        math.eval("90 - alpha", context);
    }

    @Test(expected = EvalException.class)
    public void testFunctionEvalFail(){
        EvalContext<Double> context = new EvalContext<>();
        context.setVariable("huhu", 33d);
        math.eval("sin(45)", context);
    }

    /*
    @Test
    public void testFunctionEval(){
        EvalContext<Double> context = new EvalContext<>();
        // TODO Register sin function
        math.eval("sin(45)", context);
    }

    @Test
    public void testFunctionEval3arg(){
        EvalContext<Double> context = new EvalContext<>();
        // TODO Register sin function
        math.eval("sin(45) - 20", context);
    }*/

}
