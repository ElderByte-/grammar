package com.elderbyte.grammar.core.scanner;

import com.elderbyte.grammar.core.dom.expressions.Operator;

import java.util.*;

/**
 *
 */
public class TerminalTokenManager {

    /***************************************************************************
     *                                                                         *
     * Fields                                                                  *
     *                                                                         *
     **************************************************************************/

    private final Map<String, Token> terminalMap = new HashMap<>();


    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/


    public TerminalTokenManager(OperatorSet operatorSet, Token... terminals){

        List<Token> allTerminals = new ArrayList<>();

        for(Operator o : operatorSet.getAllOperators()){
            allTerminals.add(new Token(TokenType.Operator, o.getSign()));
        }

        allTerminals.addAll(Arrays.asList(terminals));

        addAll(allTerminals);
    }

    public TerminalTokenManager(Iterable<Token> terminalTokens){
        addAll(terminalTokens);
    }

    /***************************************************************************
     *                                                                         *
     * Public API                                                              *
     *                                                                         *
     **************************************************************************/

    public Token findTerminal(String terminal) {
        if(terminalMap.containsKey(terminal)){
            return terminalMap.get(terminal);
        }
        return null;
    }

    public Collection<String> getTerminalKeywords(){
        return terminalMap.keySet();
    }


    private void addAll(Iterable<Token> terminals){
        for(Token op : terminals){
            terminalMap.put(op.getValue(), op);
        }
    }

}
