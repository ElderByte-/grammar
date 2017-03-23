package com.elderbyte.grammar.core.dom.expressions;

import com.elderbyte.common.ArgumentNullException;

/**
 * Represents a reference to a variable
 */
public class VariableReference extends ExpressionNode {

    private final String name;

    public VariableReference(String name){
        if(name == null) throw new ArgumentNullException("name");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "VarRef(" + name + ")";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VariableReference that = (VariableReference) o;

        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}
