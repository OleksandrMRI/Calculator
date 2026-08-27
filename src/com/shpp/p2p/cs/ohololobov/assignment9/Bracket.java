package com.shpp.p2p.cs.ohololobov.assignment9;

/**
 * ENUM contains opening and closing brackets although logic of handling of ENUM`s objects
 */
public enum Bracket{
    OPENING_BRACKET("("),
    CLOSING_BRACKET(")");
    /**
     * string value of Bracket
     */
    private final String value;

    /**
     * constructor of instance of Bracket
     * @param value string value of Bracket
     */
    Bracket(String value) {
        this.value = value;
    }

    /**
     * getting string value of Bracket instance
     * @return string value of Bracket instance
     */
    public  String getValue() {
        return this.value;
    }

    /**
     * method create string of values of all instances of Bracket
     * @return bracket instances values as string
     */
    public static String bracketToString(){
        StringBuilder sb = new StringBuilder();
        for(Bracket bracket : Bracket.values()){
            sb.append(bracket.value);
        }
        return sb.toString();
    }
}