package com.shpp.p2p.cs.ohololobov.assignment9;

import java.util.List;

public abstract class GraphCalculator {
    abstract double calculate(List<String> tokens, double variableValues);

    /**
     * The method parse numeric string to double? or if operand represents
     * as variable recursive calculate value of variable
     * and substitutes the double result of the variable
     * Map of parsed variables with keys as variables names
     * and values as list of tokens from variable values
     *
     * @param operand String, numeric or variable token
     * @return double, result of calculating
     */
    protected double receiveOperandValue(double variableValue, String operand) {
        double argument = 0;
        System.out.println("operand: " + operand);
        if (Assignment9.REVERSE_POLISH_NOTATION_PARSER.isLetter(operand.charAt(0))) {
            argument = variableValue;
        } else {
            System.out.println("argument: " + argument);
            argument = Double.parseDouble(operand);
        }
        return argument;
    }
}
