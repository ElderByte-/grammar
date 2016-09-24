package com.elderbyte.grammar.core.scanner;

/**
 * Represents a token emitted by the scanner.
 * This class is immutable
 */
public class Token {

    private final TokenType type;
    private final String value;

    private transient boolean unaryFlag = false;
    private transient boolean functionFlag = false;

    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/

    /**
     * Creates a new Token
     * @param type
     * @param value
     */
    public Token(TokenType type, String value){
        this.type = type;
        this.value= value;
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
        return "[" + getValue() +" "+ getType() + "]";
    }

    /**
     * Returns a copy of this token with the new given type.
     */
    public Token withOperator(TokenType type) {
        return new Token(type, this.value);
    }


    public void markUnary(){
        unaryFlag = true;
    }
    public boolean hasUnaryMark(){
        return unaryFlag;
    }

    public void markFunction() {
        functionFlag = true;
    }

    public boolean hasFunctionFlag(){
        return functionFlag;
    }
}
