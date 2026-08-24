package com.shpp.p2p.cs.ohololobov.assignment9;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Assignment9 {
    final static Parser PARSER = Parser.getInstance();
    private static final HashMap<String, List<String>> expressionsDataBase = new HashMap<>();
    private static final HashMap<String, ParsedVariableRecord> variablesDataBase = new HashMap<>();


    static void main(String[] args) throws IOException {
        final HashMap<String, VariableEqualityRecord> variables = new HashMap<>();
        String expression = "";
        if (args.length > 0) {
            expression = args[0];

            boolean isExpression = false;
            for (int i = 1; i < args.length; i++) {
                String preparedVariableString = PARSER.prepareString(args[i]);
                if (ExpressionValidator.isEmpty(preparedVariableString,isExpression)) {
                    continue;
                }
                PARSER.extractVariableExpressionToMap(preparedVariableString, variables);
            }
        } else {
            throw new IOException("Main function has no arguments");
        }
        System.out.println(calculate(expression, variables));
    }

    private static double calculate(String expression, HashMap<String, VariableEqualityRecord> variables) {
        List<String> parsedExpression = getParsedExpression(expression);
        HashMap<String, List<String>> parsedVariables = getParsedVariablesHashMap(variables);
        return calculateExpression(parsedExpression, parsedVariables);
    }

    private static List<String> getParsedExpression(String expression) {
        String preparedExpression = PARSER.prepareString(expression);
        boolean isExpression = true;
        ExpressionValidator.isEmpty(preparedExpression,isExpression);
        List<String> parsedExpression = null;
        if (!expressionsDataBase.containsKey(preparedExpression)) {
            ExpressionValidator.isValidExpression(preparedExpression);
            boolean variableUnaryMinus = false;
            parsedExpression = PARSER.parsExpression(preparedExpression, variableUnaryMinus);
            expressionsDataBase.put(preparedExpression, parsedExpression);
        } else {
            parsedExpression = expressionsDataBase.get(preparedExpression);
        }
        return parsedExpression;
    }

    private static HashMap<String, List<String>> getParsedVariablesHashMap(HashMap<String, VariableEqualityRecord> variables) {
        HashMap<String, List<String>> parsedVariables = new HashMap<>();
        for (Map.Entry<String, VariableEqualityRecord> variableEntry : variables.entrySet()) {
            String sourceVariableEquality = variableEntry.getValue().sourceVariableEquality();

            if (!variablesDataBase.containsKey(sourceVariableEquality)) {
                List<String> parsedVariableExpression = parsVariableExpression(variableEntry);
                String variableName = variableEntry.getKey();
                parsedVariables.put(variableName, parsedVariableExpression);
                variablesDataBase.put(
                        sourceVariableEquality,
                        new ParsedVariableRecord(variableName, parsedVariableExpression)
                );
            } else {
                getVariableFromDataBase(sourceVariableEquality, parsedVariables);
            }
        }
        return parsedVariables;
    }

    private static void getVariableFromDataBase(String sourceVariableEquality,
                                                HashMap<String, List<String>> parsedVariables) {
        String variableName = variablesDataBase.get(sourceVariableEquality).variableName();
        List<String> parsedVariableExpression = variablesDataBase.get(sourceVariableEquality).parsedVariableValue();
        parsedVariables.put(variableName, parsedVariableExpression);
    }

    private static List<String> parsVariableExpression(Map.Entry<String, VariableEqualityRecord> variableEntry) {
        String variableExpression = variableEntry.getValue().variableValue();
        ExpressionValidator.containsValidLetterComponents(variableExpression);
        ExpressionValidator.isValidExpression(variableExpression);
        List<String> parsedVariableExpression = PARSER.parsExpression(variableExpression,variableEntry.getValue().unaryMinus());
        return parsedVariableExpression;
    }

    private static double calculateExpression(List<String> parsedExpression,
                                              HashMap<String, List<String>> parsedVariables) {
        double result = 0;

        result = getResultOneElementExpression(parsedExpression, parsedVariables, result);

        result = getResultMultiElementExpression(parsedExpression, parsedVariables, result);
        return result;
    }

    private static double getResultMultiElementExpression(List<String> parsedExpression,
                                                          HashMap<String, List<String>> parsedVariables,
                                                          double result) {
        int parsedExpressionSize;
        for (int i = 0; i < (parsedExpressionSize=parsedExpression.size()) && parsedExpressionSize > 1; i++) {
            System.out.println("Main 99 parsedExpression: "+ parsedExpression);
            if(Operator.getOperatorsList().contains(parsedExpression.get(i))) {
                String firstOperand = parsedExpression.get(i - 2);
                String secondOperand = parsedExpression.get(i - 1);
                String operatorSTR = parsedExpression.get(i);
                System.out.println("Main 104 operatorSTR+ "+ operatorSTR);
                double firstArgument = parsOperand(parsedVariables, firstOperand);
                double secondArgument = parsOperand(parsedVariables, secondOperand);

                result = Operator.makeOperation(operatorSTR, firstArgument, secondArgument);
                parsedExpression.remove(i);
                parsedExpression.remove(i - 1);
                parsedExpression.set(i - 2, "" + result);
                System.out.println("Main 112 result + "+result);
                i = 0;
            }
        }
//        System.out.println("result + "+result);
        return result;
    }

    private static double getResultOneElementExpression(List<String> parsedExpression,
                                                        HashMap<String, List<String>> parsedVariables,
                                                        double result) {
        if (parsedExpression.size() == 1) {
            result = parsOperand(parsedVariables, parsedExpression.getFirst());
        }
        return result;
    }

    private static double parsOperand(HashMap<String, List<String>> parsedVariables, String operand) {
        double firstArgument;
        if (PARSER.isLetter(operand.charAt(0))) {
            firstArgument = calculateExpression(parsedVariables.get(operand), parsedVariables);
        } else {
            firstArgument = Double.parseDouble(operand);
        }
        return firstArgument;
    }
}
