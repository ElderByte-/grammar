package com.elderbyte.grammar.core.dom.utilities;

import com.elderbyte.grammar.core.dom.expressions.BinaryExpression;
import com.elderbyte.grammar.core.dom.expressions.ExpressionNode;
import com.elderbyte.grammar.core.dom.expressions.UnaryExpression;
import com.elderbyte.grammar.core.dom.expressions.UnaryOperatorExpression;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Provides static utility functions to handle AST
 */
public final class AstUtil {

    /**
     * Works similar to a string-replace method for an expression tree
     *
     * The whole AST Tree is recursively traversed and if a node matches (see matcher)
     * then it will be replaced with whatever the replacer() returns.
     *
     */
    public static ExpressionNode recursiveReplace(ExpressionNode current, Predicate<ExpressionNode> matcher, Function<ExpressionNode,ExpressionNode> replacer){

        if(matcher.test(current)){
            return replacer.apply(current);
        }

        // Traversal is currently hardcoded here, probably the visitor pattern could be used to support replacements.

        if(current instanceof UnaryExpression){
            ExpressionNode replaced = recursiveReplace(((UnaryOperatorExpression) current).getInner(), matcher, replacer);
            ((UnaryOperatorExpression) current).setInner(replaced);
        }

        if(current instanceof BinaryExpression){
            BinaryExpression binary = (BinaryExpression)current;

            ExpressionNode replacedLeft = recursiveReplace(binary.getLeft(), matcher, replacer);
            ExpressionNode replacedRight = recursiveReplace(binary.getRight(), matcher, replacer);

            binary.setLeft(replacedLeft);
            binary.setRight(replacedRight);
        }

        return current;
    }

}
