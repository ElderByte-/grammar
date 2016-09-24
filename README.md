# grammar

_grammar_ is a shunting-yard based expression parser to generate an Abstract Syntaxt Tree (AST).

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
res = eval("10 + 5 / 20 * (40 % 2)")
```

## Variable support
If your expression contains variables, _grammar_ supports them aswell:
```java
res = eval("90 - alpha", context)
```
Just include a `Map<String, Double> context` in your eval argument, and variable-reference nodes are substituted for you.
