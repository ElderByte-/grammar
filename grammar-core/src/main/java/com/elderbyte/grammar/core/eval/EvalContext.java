package com.elderbyte.grammar.core.eval;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A data context while executing an expression
 */
public class EvalContext<T> {

    /***************************************************************************
     *                                                                         *
     * Fields                                                                  *
     *                                                                         *
     **************************************************************************/

    private Map<String, T> variables = new HashMap<>();


    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/


    /***************************************************************************
     *                                                                         *
     * Public API                                                              *
     *                                                                         *
     **************************************************************************/

    public Optional<T> resolveVariable(String variableName){
        if(variables.containsKey(variableName)){
            return Optional.of(variables.get(variableName));
        }
        return Optional.empty();
    }

    public void setVariable(String variableName, T value){
        variables.put(variableName, value);
    }

    public T invoke(String func, List<T> argumentValues) {
        throw new IllegalStateException("Could not find and invoke function " + func + "(" +
                String.join(",", argumentValues.stream().map(Object::toString).collect(Collectors.toList())) +")" );
    }

    /**************************************************************************
     *                                                                         *
     * Private Methods                                                         *
     *                                                                         *
     **************************************************************************/

}
