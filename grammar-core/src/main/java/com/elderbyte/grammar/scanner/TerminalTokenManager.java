package com.elderbyte.grammar.scanner;

import com.elderbyte.common.ArgumentNullException;
import com.elderbyte.grammar.dom.expressions.Operator;

import java.util.*;

/**
 *
 */
public class TerminalTokenManager {


    private static Iterable<Token> defaultTerminals(OperatorSet operatorSet){

        if(operatorSet == null) throw new ArgumentNullException("operatorSet");


        List<Token> defaultTerminals = new ArrayList<>();

        defaultTerminals.add(new Token(TokenType.Whitespace, " "));
        defaultTerminals.add(new Token(TokenType.Whitespace, "\t"));
        defaultTerminals.add(new Token(TokenType.Whitespace, ","));

        defaultTerminals.add(new Token(TokenType.Parentheses_Open, "("));
        defaultTerminals.add(new Token(TokenType.Parentheses_Closed, ")"));

        for(Operator o : operatorSet.getAllOperators()){
            defaultTerminals.add(new Token(TokenType.Operator, o.getSign()));
        }

        return defaultTerminals;
    }

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


    public TerminalTokenManager(OperatorSet operatorSet){
        this(defaultTerminals(operatorSet));
    }

    public TerminalTokenManager(Iterable<Token> terminalTokens){
        for(Token op : terminalTokens){
            terminalMap.put(op.getValue(), op);
        }
    }


    /***************************************************************************
     *                                                                         *
     * Properties                                                              *
     *                                                                         *
     **************************************************************************/


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

    /**************************************************************************
     *                                                                         *
     * Private Methods                                                         *
     *                                                                         *
     **************************************************************************/

}
