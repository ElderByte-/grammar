package com.elderbyte.grammar.core.dom.expressions;


import org.junit.Assert;
import org.junit.Test;

public class ExpressionNodeEqualityTest {

    @Test
    public void test_LiteralValueExpression_equal(){
        Assert.assertTrue(new LiteralValueExpression("a").equals(new LiteralValueExpression("a")));
    }

    @Test
    public void test_LiteralValueExpression_not_equal(){
        Assert.assertFalse(new LiteralValueExpression("a").equals(new LiteralValueExpression("b")));
    }

    @Test
    public void test_VariableReference_equal(){
        Assert.assertTrue(new VariableReference("a").equals(new VariableReference("a")));
    }

    @Test
    public void test_VariableReference_not_equal(){
        Assert.assertFalse(new VariableReference("a").equals(new VariableReference("b")));
    }


    @Test
    public void test_binOp_equal(){

        ExpressionNode a = new BinaryOperatorExpression(
                new LiteralValueExpression("a"),
                operator("*", Arity.Binary),
                new LiteralValueExpression("b")
        );

        ExpressionNode b = new BinaryOperatorExpression(
                new LiteralValueExpression("a"),
                operator("*", Arity.Binary),
                new LiteralValueExpression("b")
        );


        Assert.assertTrue(a.equals(b));
    }

    @Test
    public void test_binOp_not_equal(){

        ExpressionNode a = new BinaryOperatorExpression(
                new LiteralValueExpression("a"),
                operator("*", Arity.Binary),
                new VariableReference("b")
        );

        ExpressionNode b = new BinaryOperatorExpression(
                new LiteralValueExpression("a"),
                operator("*", Arity.Binary),
                new LiteralValueExpression("b")
        );


        Assert.assertFalse(a.equals(b));
    }

    private Operator operator(String sign, Arity arity){
        return new Operator(sign, 1, false, arity);
    }


}