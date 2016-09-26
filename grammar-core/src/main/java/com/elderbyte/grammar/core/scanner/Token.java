package com.elderbyte.grammar.core.scanner;

import com.elderbyte.grammar.core.dom.expressions.Arity;

/**
 * Represents a token emitted by the scanner.
 * This class is immutable
 */
public class Token {

    private final TokenType type;
    private final String value;

    private final transient boolean unaryFlag;
    private final transient boolean functionFlag;

    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/

    /**
     * Creates a new Token
     * @param type The type
     * @param value The token word
     */
    public Token(TokenType type, String value){
        this(type, value, false, false);
    }

    public Token(TokenType type, String value, boolean unary, boolean function){
        this.type = type;
        this.value= value;
        this.unaryFlag = unary;
        this.functionFlag = function;
    }

    /***************************************************************************
     *                                                                         *
     * Public Properties                                                       *
     *                                                                         *
     **************************************************************************/

    /**
     * Gets the token type
     * @return
     */
    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString(){
        return "[" + getValue() +" "+ getType() +  " u:" + hasUnaryMark() + "]";
    }

    /**
     * Returns a copy of this token with the new given type.
     */
    public Token asUnary() {
        return new Token(this.type, this.value, true, false);
    }

    public Token asFunction() {
        return new Token(this.type, this.value, false, true);
    }

    public boolean hasUnaryMark(){
        return unaryFlag;
    }

    public boolean hasFunctionFlag(){
        return functionFlag;
    }


    public Arity getArity(){
        return unaryFlag ? Arity.Unary : Arity.Binary;
    }
}
