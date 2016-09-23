package com.elderbyte.grammar.generators;


import com.elderbyte.grammar.dom.expressions.ExpressionNode;

/**
 * Generates source code of the given AST node
 */
public interface ICodeGenerator {

    /**
     * Generate source code
     * @param node Expression Node node
     * @return
     */
    String generate(ExpressionNode node);

}
