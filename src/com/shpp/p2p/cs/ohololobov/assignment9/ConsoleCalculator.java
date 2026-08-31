package com.shpp.p2p.cs.ohololobov.assignment9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ConsoleCalculator{
    private final Map<String, Integer> variableCounter = new HashMap<>();
    private final List<String> variables = new ArrayList<>();
    private final List<Double> resultList = new ArrayList<>();
    private final double[] resultArray = new double[2];

    abstract double calculate(List<String> expressionTokens,
                              Map<String, List<List<String>>> variableValueTokens);

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
    protected double receiveOperandValue(Map<String, List<List<String>>> parsedVariables, String operand) {
        double argument = 0;
        System.out.println("operand: " + operand);
        System.out.println("variableCounter.get(operand): " + variableCounter.get(operand));
        if (Assignment9.REVERSE_POLISH_NOTATION_PARSER.isLetter(operand.charAt(0))) {
            if (parsedVariables.containsKey(operand)) {
                int numberOfVariableValues = parsedVariables.get(operand).size();
                System.out.println("numberOfVariableValues: " + numberOfVariableValues);
                if (!variableCounter.containsKey(operand)) {
                    variableCounter.put(operand, 0);
                    variables.add(operand);
                }
                System.out.println("after creation variableCounter.get(operand): " + variableCounter.get(operand));
                argument = calculate(parsedVariables.get(operand).get(variableCounter.get(operand)), parsedVariables);
                resultList.add(argument);
                int currentIndex = variables.indexOf(operand);
                int indexOfNextOperand = currentIndex + 1;
                int size = variables.size();
                int currentCount = variableCounter.get(operand);
                System.out.println("\nindexOfNextOperand: " + indexOfNextOperand);
                System.out.println("size: " + size);
                System.out.println("operand: " + operand + " variableCounter.get(operand): " + variableCounter.get(operand));
                System.out.println(" variables: " + variables);
//                System.out.println(" parsedVariables.get(a).size(): " + parsedVariables.get("a").size());
//                System.out.println(" variableCounter.get(a): " + variableCounter.get("a"));

                System.out.println();
                String nextOperand;

                if (size > (indexOfNextOperand)
                        && variableCounter.get(nextOperand = variables.get(indexOfNextOperand)) >= parsedVariables.get(nextOperand).size()) {

                    variableCounter.put(operand, ++currentCount);
                    variableCounter.put(nextOperand, 0);
                    System.out.println("operand: " + operand + " if variableCounter.get(operand): " + variableCounter.get(operand) +
                            "\nnextoperand: " + nextOperand + " if variableCounter.get(operand): " + variableCounter.get(nextOperand));
                } else if (size <= (indexOfNextOperand) && numberOfVariableValues > currentCount) {
                    System.out.println("for increment else variableCounter.get(operand): " + variableCounter.get(operand));
                    System.out.println("operand: " + operand + " else variableCounter.get(operand): " + variableCounter.get(operand));
                    currentCount++;
                    System.out.println("After increment else variableCounter.get(operand): " + variableCounter.get(operand));
                    variableCounter.put(operand, currentCount);
                    System.out.println("operand: " + operand + " else variableCounter.get(operand): " + variableCounter.get(operand));
                }
            } else {
                throw new RuntimeException("There is no variable \"" + operand + "\" in database");
            }
        } else {
            System.out.println("argument: " + argument);
            argument = Double.parseDouble(operand);
        }
        return argument;
    }

    public int getVariablesCount() {
        if (!variableCounter.isEmpty()) return variableCounter.get(variables.getFirst());
        return 0;
    }

    public int numberOfMainVariableValues(Map<String, List<List<String>>> parsedVariables) {
        return parsedVariables.get(variables.getFirst()).size();
    }

    public List<Double> getResultList() {
        return resultList;
    }

    public void addToResultList(double value) {
        resultList.add(value);
    }
}
