package com.shpp.p2p.cs.ohololobov.assignment9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Parser {
    private static Parser parserInstance;

    public static Parser getInstance() {
        if (parserInstance == null) {
            parserInstance = new Parser();
        }
        return parserInstance;
    }

    public HashMap<String, VariableEqualityRecord> extractVariableExpressionToMap(String variableArgument, HashMap<String, VariableEqualityRecord> variables) throws IllegalArgumentException {
        String[] variableArray = variableExpressionAsArray(variableArgument);
        String leftPartEquality = variableArray[0];
        VariableEqualityRecord variableEqualityRecord;
        int leftPartEqualityLength = leftPartEquality.length();
        char variableName = '0';
        if (leftPartEqualityLength == 2 && leftPartEquality.charAt(0) == '-' && isLetter(variableName = leftPartEquality.charAt(1))) {
            variableEqualityRecord = new VariableEqualityRecord(variableArray[1], true, variableArgument);
        } else if (leftPartEqualityLength == 1 && isLetter(variableName = leftPartEquality.charAt(0))) {
            variableEqualityRecord = new VariableEqualityRecord(variableArray[1], false, variableArgument);
        } else {
            System.out.println("variableName = " + variableName);
            throw new IllegalArgumentException("This ist invalid variable name");
        }
        String nameOfVariable = "" + variableName;
        variables.put(nameOfVariable, variableEqualityRecord);

        return variables;
    }

    private String[] variableExpressionAsArray(String varExpression) {
        System.out.println("varExpression: " + varExpression);
        String[] varExpressionArray;
        if ((varExpressionArray = varExpression.split("=")).length != 2) {
            throw new IllegalArgumentException("This ist invalid variable variableValue");
        } else return varExpressionArray;
    }

    String prepareString(String str) {
        return str.replace(" ", "").toLowerCase();
    }

    public List<String> parsExpression(String expression, boolean variableUnaryMinus) {
        String formatedString = addMultiplicationSing(expression);
        String[] expressionComponents = getExpressionComponentsArray(formatedString);
        List<String> parsedExpression = new ArrayList<>();
        List<String> weakOperators = new ArrayList<>();
        List<String> strongOperators = new ArrayList<>();
        List<String> minorOperator = Operator.getMinorOperatorsList();
        List<String> majorOperator = Operator.getMajorOperatorsList();
        int expressionComponentsLength = expressionComponents.length;
        for (int i = 0; i < expressionComponentsLength; i++) {
            int nextIndex = i + 1;
            String currentComponent = expressionComponents[i];

            if (minorOperator.contains(currentComponent)) {
                sortTokenIfMinorOperator(parsedExpression, strongOperators, weakOperators, expressionComponents, currentComponent, nextIndex);

            } else if (majorOperator.contains(currentComponent)) {
                sortTokenIfMajorOperator(strongOperators, expressionComponentsLength, i, expressionComponents, nextIndex, parsedExpression, currentComponent);
            } else if (Operator.POW.getValue().equals(currentComponent)) {
                sortTokenIfPowAndAfterPowAndSubtraction(expressionComponentsLength, i, expressionComponents, nextIndex, parsedExpression, strongOperators, currentComponent);
            } else if (i == 0 && isOperand(currentComponent)) {
                parsedExpression.add(currentComponent);
            }
            System.out.println("parsedExpression+ " + parsedExpression);
            System.out.println("strongOperators+ " + strongOperators);
            System.out.println("weakOperators+ " + weakOperators);

        }
        addOperators(parsedExpression, strongOperators);
        addOperators(parsedExpression, weakOperators);
        if (variableUnaryMinus) {
            parsedExpression.add("-1");
            parsedExpression.add("*");
        }
        return parsedExpression;
    }

    private void sortTokenIfPowAndAfterPowAndSubtraction(int expressionComponentsLength, int i, String[] expressionComponents, int nextIndex, List<String> parsedExpression, List<String> strongOperators, String currentComponent) {
        isInvalidExpression(expressionComponentsLength, i);
        sortTokens(expressionComponents, nextIndex, parsedExpression, strongOperators, currentComponent);
    }

    private void sortTokenIfMajorOperator(List<String> strongOperators,
                                          int expressionComponentsLength,
                                          int index,
                                          String[] expressionComponents,
                                          int nextIndex, List<String> parsedExpression,
                                          String currentComponent) {
//        System.out.println(strongOperators);
//        System.out.println(("StrongOperatorsgetLast: " + strongOperators.getLast()));
        if (!strongOperators.isEmpty() && (Operator.POW.getValue().equals(strongOperators.getLast()) || Operator.SUBTRACTION.getValue().equals(strongOperators.getLast()))) {
            System.out.println("In previous pow");
            isInvalidExpression(expressionComponentsLength, index);
            addOperators(parsedExpression, strongOperators);
            sortTokens(expressionComponents, nextIndex, parsedExpression, strongOperators, currentComponent);
        } else {
            sortTokenIfPowAndAfterPowAndSubtraction(expressionComponentsLength, index, expressionComponents, nextIndex, parsedExpression, strongOperators, currentComponent);
        }
    }

    private void sortTokenIfMinorOperator(List<String> parsedExpression,
                                          List<String> strongOperators,
                                          List<String> weakOperators,
                                          String[] expressionComponents,
                                          String currentComponent,
                                          int nextIndex) {

        String nextComponent;
        addOperators(parsedExpression, strongOperators);
        addOperators(parsedExpression, weakOperators);
        weakOperators.add(String.valueOf(currentComponent));
        if (isOperand(nextComponent = expressionComponents[nextIndex])) {
            parsedExpression.add(nextComponent);
        }
    }

    private void sortTokens(String[] expressionComponents,
                            int nextIndex,
                            List<String> parsedExpression,
                            List<String> strongOperators,
                            String currentComponent) {
        String nextComponent;
        if (isOperand(nextComponent = expressionComponents[nextIndex])) {
            parsedExpression.add(nextComponent);
            strongOperators.add(currentComponent);
        }
    }

    private void isInvalidExpression(int expressionComponentsLength, int i) {
        if (i == expressionComponentsLength) {
            throw new IllegalArgumentException("illegal argument at variableValue end");
        }
    }

    private boolean isOperand(String component) {
        boolean isOperand = true;
        if (Operator.getOperatorsList().contains(component)) isOperand = false;
        return isOperand;
    }

    private String[] getExpressionComponentsArray(String formatedString) {
        String[] componentsArray = new String[formatedString.length()];
        int componentsArrayIndex = 0;
        componentsArrayIndex = fillComponentsArray(formatedString, componentsArrayIndex, componentsArray);
        String[] resultArray = Arrays.copyOf(componentsArray, componentsArrayIndex);
        return resultArray;
    }

    private static int fillComponentsArray(String formatedString, int componentsArrayIndex, String[] componentsArray) {
        int formatedStringLength = formatedString.length();
        int previousOperandStartIndex = 0;
        String unaryMinus = "";
        for (int i = 0; i < formatedStringLength; i++) {
            String currentCharAsString = String.valueOf(formatedString.charAt(i));
            if (i == 0 && currentCharAsString.equals(Operator.SUBTRACTION.getValue())) {
                unaryMinus = "-";
            } else if (Operator.getOperatorsList().contains(currentCharAsString)) {
                componentsArrayIndex = addOperandToArray(formatedString, previousOperandStartIndex, i, componentsArrayIndex, unaryMinus, componentsArray);
                componentsArray[componentsArrayIndex] = currentCharAsString;
                previousOperandStartIndex = i + 1;
                componentsArrayIndex++;
            }
        }

        if (previousOperandStartIndex < formatedStringLength) {
            componentsArray[componentsArrayIndex] = formatedString.substring(previousOperandStartIndex, formatedStringLength);
            componentsArrayIndex++;
        }

        return componentsArrayIndex;
    }

    private static int addOperandToArray(String formatedString, int previousOperandStartIndex, int formatedStringIndex, int componentsArrayIndex, String unaryMinus, String[] componentsArray) {
        String operand = formatedString.substring(previousOperandStartIndex, formatedStringIndex);
        if (!operand.isEmpty()) {
            if (componentsArrayIndex == 2 && !unaryMinus.isEmpty()) {
                componentsArray[componentsArrayIndex] = "-" + operand;
                unaryMinus = "";
            }
            componentsArray[componentsArrayIndex] = operand;
            componentsArrayIndex++;
        }
        return componentsArrayIndex;
    }

    private String[] trimArrayToFilledSize(String[] componentsArray, int newArraySize) {
        return Arrays.copyOf(componentsArray, newArraySize);
    }

//    private int setStartIndexPreviousOperand(int index, int stringLength, int startIndexPreviousOperand) {
//        if (index < stringLength - 1) {
//            startIndexPreviousOperand = index + 1;
//        }
//        return
//    }

    private void addOperators(List<String> parsedExpression, List<String> operators) {
        for (int i = operators.size() - 1; i >= 0; i--) {
            parsedExpression.add(operators.get(i));
        }
        operators.clear();
    }

    private String addMultiplicationSing(String expression) {
        StringBuilder sb = new StringBuilder();
        int expressionLength = expression.length();
        int previousMissedMultipleSignIndex = 0;
        for (int i = 0; i < expressionLength; i++) {
            char currentChar = expression.charAt(i);
            int nextIndex = i + 1;
            if (i < expressionLength - 1 && currentChar >= '0' && currentChar <= '9' && isLetter(expression.charAt(nextIndex))) {
                sb.append(expression, previousMissedMultipleSignIndex, nextIndex).append("*");
                previousMissedMultipleSignIndex = nextIndex;
            }
        }
        sb.append(expression, previousMissedMultipleSignIndex, expressionLength);
        return sb.toString();
    }

    boolean isLetter(char ch) {
        return ch >= 'a' && ch <= 'z';
    }
}
