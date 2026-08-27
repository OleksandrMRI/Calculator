package com.shpp.p2p.cs.ohololobov.assignment9;

import java.util.List;
import java.util.Map;

/**
 * This class calculate result of expression that was parsed according to reverse polish notation
 */
public class ReversPolishNotationCalculator implements Calculator{
    /**
     * The method calculate result of expression from List of tokens of expression and Map of parsed variables
     *
     * @param parsedExpression List of tokens from linear representation of expression
     * @param parsedVariables  Map of parsed variables with keys as variables names
     *                         and values as list of tokens from variable values
     * @return double, result of calculating
     */
    public double calculate(List<String> parsedExpression,
                                    Map<String, List<String>> parsedVariables) {
        double result = 0;

        result = getResultOneElementExpression(parsedExpression, parsedVariables, result);

        result = getResultMultiElementExpression(parsedExpression, parsedVariables, result);
        return result;
    }

    /**
     * The method receives double value from string value if List of tokens from expression string contains only one element
     *
     * @param parsedExpression List of tokens from linear representation of expression
     * @param parsedVariables  Map of parsed variables with keys as variables names
     *                         and values as list of tokens from variable values
     * @return double, result of calculating
     */
    private double getResultOneElementExpression(List<String> parsedExpression,
                                                        Map<String, List<String>> parsedVariables,
                                                        double result) {
        if (parsedExpression.size() == 1) {
            result = parsOperand(parsedVariables, parsedExpression.getFirst());
        }
        return result;
    }

    /**
     * The method calculate double result from List of tokens from expression string with then one element
     *
     * @param parsedExpression List of tokens from linear representation of expression
     * @param parsedVariables  Map of parsed variables with keys as variables names
     *                         and values as list of tokens from variable values
     * @return double, result of calculating
     */
    private double getResultMultiElementExpression(List<String> parsedExpression,
                                                          Map<String, List<String>> parsedVariables,
                                                          double result) {
        int parsedExpressionSize;
        for (int i = 0; i < (parsedExpressionSize = parsedExpression.size()) && parsedExpressionSize > 1; i++) {
            System.out.println("Main 99 parsedExpression: " + parsedExpression);
            if (SimpleMathOperator.asMap().containsKey(parsedExpression.get(i))) {
                String firstOperand = parsedExpression.get(i - 2);
                String secondOperand = parsedExpression.get(i - 1);
                String operatorSTR = parsedExpression.get(i);
                System.out.println("Main 104 operatorSTR+ " + operatorSTR);
                double firstArgument = parsOperand(parsedVariables, firstOperand);
                double secondArgument = parsOperand(parsedVariables, secondOperand);

                result = SimpleMathOperator.executeOperation(operatorSTR, firstArgument, secondArgument);
                parsedExpression.remove(i);
                parsedExpression.remove(i - 1);
                parsedExpression.set(i - 2, "" + result);
                System.out.println("Main 112 result + " + result);
                i = 0;
            }
        }
        return result;
    }

    /**
     * The method parse numeric strig to double? or if operand represents
     * as variable recursive calculate value of variable
     * and substitutes the double result of the variable
     Map of parsed variables with keys as variables names
     *                         and values as list of tokens from variable values
     * @param operand String, numeric or variable token
     * @return double, result of calculating
     */
    private double parsOperand(Map<String, List<String>> parsedVariables, String operand) {
        double argument=0;
        if (Assignment9.REVERSE_POLISH_NOTATION_PARSER.isLetter(operand.charAt(0))) {
            if(parsedVariables.containsKey(operand)) argument = calculate(parsedVariables.get(operand), parsedVariables);
        } else {
            argument = Double.parseDouble(operand);
        }
        return argument;
    }
}
