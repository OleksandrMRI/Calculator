package com.shpp.p2p.cs.ohololobov.assignment9;

public enum Bracket{
    OPENING_BRACKET("("),
    CLOSING_BRACKET(")");

    private final String value;

    Bracket(String value) {
        this.value = value;
    }

    public  String getValue() {
        return this.value;
    }
    public static String bracketToString(){
        StringBuilder sb = new StringBuilder();
        for(Bracket bracket : Bracket.values()){
            sb.append(bracket.value);
        }
        return sb.toString();
    }
}