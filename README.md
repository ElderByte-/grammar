[![Build Status](https://travis-ci.org/ElderByte-/grammar.svg?branch=master)](https://travis-ci.org/ElderByte-/grammar) [![Download](https://api.bintray.com/packages/elderbyte/maven/grammar-core/images/download.svg) ](https://bintray.com/elderbyte/maven/grammar-core/_latestVersion) 

# grammar

_grammar_ is a shunting-yard based expression parser to generate an Abstract Syntaxt Tree (AST). At its heart it is a library to allow you to quickly create your own expression language. But off course, since it is so easy, grammar also ships with some default expression parsers and evaluators for basic math and boolean support. 

The tokenizer, parser and also evaluators are highly customizable. For example, you can configure the operators, the terminal symbols and customize about almost everything else.


## Out of the box math and boolean parser/evaluator support

_grammar_ is able to evaluate expressions like:

### Boolean expression examples
```java
res = eval("!(true | false) | (false & true)")
```
```java
res = eval("not(true or false) or (false and true)")
```

### Math expression examples
```java
res = eval("10 + 5.6 / 20 * (40 % 2)")
```

## Variable support
If your expression contains variables, _grammar_ supports them aswell:
```java
EvalContext<Double> context = new EvalContext<>();
context.setVariable("alpha", 15d);
res = eval("90 - alpha", context);
```
Just include a `EvalContext<Double> context` in your eval argument, and variable-reference nodes are substituted for you.



### Custom Parser

This is an example of the default math expression parser configuration - you can create one for your own needs:

**Math Parser**
```java

public class MathExpressionParser extends ExpressionParser {

    public MathExpressionParser(){
        super(new OperatorSet(
                new Operator("+", 2, true, Arity.Binary),
                new Operator("-", 2, true, Arity.Binary),
                new Operator("*", 3, true, Arity.Binary),
                new Operator("/", 3, true, Arity.Binary),
                new Operator("^", 4, false, Arity.Binary),
                new Operator("%", 4, false, Arity.Binary),

                new Operator("+", 99, true, Arity.Unary),
                new Operator("-", 99, true, Arity.Unary)
                        ),
                new Token(TokenType.Whitespace, " "),
                new Token(TokenType.Whitespace, "\t"),
                new Token(TokenType.Parentheses_Open, "("),
                new Token(TokenType.Parentheses_Closed, ")"),
                new Token(TokenType.Whitespace, ",")

        );
    }
}
```

**Boolean Parser**
```java
public class BooleanExpressionParser extends ExpressionParser  {

    public BooleanExpressionParser(){
        super(new OperatorSet(
                    new Operator("&", 3, true, Arity.Binary, "and"),
                    new Operator("|", 3, true, Arity.Binary, "or"),
                    new Operator("^", 3, true, Arity.Binary, "xor"),
                    new Operator("!", 99, true, Arity.Unary, "not", "~")
                ),
                new Token(TokenType.Literal, "true"),
                new Token(TokenType.Literal, "false"),
                new Token(TokenType.Whitespace, " "),
                new Token(TokenType.Whitespace, "\t"),
                new Token(TokenType.Parentheses_Open, "("),
                new Token(TokenType.Parentheses_Closed, ")")
        );
    }
}
```

## How to get started

You should have a look at our [Unit tests here](https://github.com/ElderByte-/grammar/tree/master/grammar-core/src/test/java/com/elderbyte/grammar) to get a feeling how to use this library.
