package com.elderbyte.grammar.core.dom.expressions;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionInvokationExpression extends ExpressionNode {

    private String functionName;
    private List<ExpressionNode> arguments = new ArrayList<>();


    public FunctionInvokationExpression(String functionName, ExpressionNode... arguments){
        this(functionName, Arrays.asList(arguments));
    }

    public FunctionInvokationExpression(String functionName, Collection<ExpressionNode> arguments){
        this.functionName = functionName;
        this.arguments.addAll(arguments);
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<ExpressionNode> getArguments() {
        return arguments;
    }

    public String toString(){
        return functionName + "(" + String.join(",", arguments.stream().map(a -> a.toString()).collect(Collectors.toList())) + ")";
    }


}
