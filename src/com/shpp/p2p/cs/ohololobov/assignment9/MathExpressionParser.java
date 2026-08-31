package com.shpp.p2p.cs.ohololobov.assignment9;

import java.util.ArrayList;
import java.util.List;

public abstract class MathExpressionParser implements Parser {
    /**
     * constant contains string that used as equality sign
     */
    public static final String EQUALITY = "=";
    public static final List<String> VARIABLES = new ArrayList<>();

    /**
     * The method pars linear representation of expression to List of tokens in necessary notation order
     *
     * @param expression string to parsing
     * @return List of tokens in order of necessary notation
     */
    @Override
    public List<String> pars(String expression) {
        return parsToListInPostfixNotation(expression);
    }

    /**
     * The method process unary minus if such was present before variable name
     * in source linear representation of variable equality
     *
     * @param tokensListInFinalNotation list of tokens in necessary notation order
     * @param variableUnaryMinus        boolean shows whether unary minus was present before variable name
     *                                  in source linear representation of variable equality
     * @return List<String> tokensListInFinalNotation, list of tokens in necessary notation order
     */
    public abstract List<String> processVariableUnaryMinus(List<String> tokensListInFinalNotation, boolean variableUnaryMinus);

    /**
     * The methods formates raw input string element from String[] args of main(),
     * removes spaces converts to lowercase and inserts missing multiplication signs into a mathematical expression;
     *
     * @param rawSourceString string element from String[] args of main()
     * @return formated string without spaces and in lowercase
     */
    public String formatRawString(String rawSourceString) {
        String prepareString = rawSourceString.replace(" ", "").toLowerCase();

        return addMultiplicationSing(prepareString);
    }

    /**
     * The method inserts missing multiplication signs into a mathematical expression where they are absent,
     * specifically where the syntax of mathematical notation requires them—such as between a number and a variable.
     *
     * @param expression linear representation of expression
     * @return formated expression with inserted missing multiplication sign
     */
    private String addMultiplicationSing(String expression) {
        StringBuilder sb = new StringBuilder();
        int expressionLength = expression.length();
        int previousMissedMultipleSignIndex = 0;
        for (int i = 0; i < expressionLength; i++) {
            char currentChar = expression.charAt(i);
            int nextIndex = i + 1;
            if (i < expressionLength - 1 && isNumber(currentChar) && isLetter(expression.charAt(nextIndex))) {
                sb.append(expression, previousMissedMultipleSignIndex, nextIndex).append("*");
                previousMissedMultipleSignIndex = nextIndex;
            }
        }
        sb.append(expression, previousMissedMultipleSignIndex, expressionLength);

        return sb.toString();
    }

    /**
     * The method contains logic of parsing linear representation of variable equality to VariableEqualityRecord,
     * throw splitting equality with "=" as separator, validating left and right parts of equality
     * and chacking beginning left part of equolity with unary minus to correct value parsing
     *
     * @param variableEqualityString String, lineal representation of variable equality from arguments of main()
     * @return variableEqualityRecord record with fields: strings variable name, variable value,
     * boolean unary minus if variable name in source string war with unary minus
     * source string of variable
     * @throws IllegalArgumentException exception throws when name of variable ist incorrect
     */
    public VariableEqualityRecord getVariableEqualityRecord(String variableEqualityString) throws IllegalArgumentException {
        String[] variableArray = variableExpressionAsArray(variableEqualityString);
        String leftPartEquality = variableArray[0];

        String rightPartEquality = variableArray[1];

        VariableEqualityRecord variableEqualityRecord;
        int leftPartEqualityLength = leftPartEquality.length();
        char variableName = '0';
        boolean isUnaryMinus;
        if (leftPartEqualityLength == 2 && leftPartEquality.charAt(0) == UnaryMinus.CHAR_VALUE && isLetter(variableName = leftPartEquality.charAt(1))) {
            isUnaryMinus = true;
        } else if (leftPartEqualityLength == 1 && isLetter(variableName = leftPartEquality.charAt(0))) {
            isUnaryMinus = false;
        } else {
            System.out.println("variableName = " + variableName);
            throw new IllegalArgumentException("This ist invalid variable name");
        }
        variableEqualityRecord = createVariableEqualityRecord(variableName, variableEqualityString, isUnaryMinus, rightPartEquality);

        return variableEqualityRecord;
    }

    /**
     * The method contains logic to splitting linear representation of the variable equality to array with to elements:
     * first element is variable name and second is variable value
     *
     * @param varExpression linear representation of the variable equality
     * @return array with to elements:
     * first element is variable name and second is variable value
     */
    private String[] variableExpressionAsArray(String varExpression) throws IllegalArgumentException {
        System.out.println("varExpression: " + varExpression);
        String[] varExpressionArray;
        if ((varExpressionArray = varExpression.split(EQUALITY)).length != 2) {
            throw new IllegalArgumentException("This ist invalid variable equality");
        } else return varExpressionArray;
    }

    /**
     * The method creates new instance of VariableEqualityRecord;
     *
     * @param variableName         char representation of variable name
     * @param sourceVariableString source string, that linear represents variable equality
     * @param isUnaryMinus         boolean represents beginning of left part of variable equality with unary minus
     * @param variableValue        string represents linear value of variable
     * @return variableEqualityRecord record with fields: strings variable name, variable value,
     * boolean unary minus if variable name in source string war with unary minus
     * source string of variable
     */
    private VariableEqualityRecord createVariableEqualityRecord(char variableName, String sourceVariableString, boolean isUnaryMinus, String variableValue) {
        if (!isLetter(variableName)) {
            System.out.println("Variable name: " + variableName + " is incorrect");
        }
        String nameOfVariable = "" + variableName;

        return new VariableEqualityRecord(nameOfVariable, variableValue, isUnaryMinus, sourceVariableString);
    }

    /**
     * The method pars linear representation of expression to List of tokens in postfix notation order
     *
     * @param expression linear representation of expression
     * @return list of tokens in postfix notation order
     */
    private List<String> parsToListInPostfixNotation(String expression) {
        List<String> tokensListInPostfixNotation = new ArrayList<>();
        int previousOperandStartIndex = 0;
        int formatedStringLength = expression.length();
        for (int i = 0; i < formatedStringLength; i++) {
            String currentCharAsString = String.valueOf(expression.charAt(i));
            if (i == 0 && currentCharAsString.equals(UnaryMinus.VALUE)) {
                tokensListInPostfixNotation.add(UnaryMinus.UNARY_MINUS_MULTIPLICATOR);
                tokensListInPostfixNotation.add(SimpleMathOperator.MULTIPLICATION.getValue());
                previousOperandStartIndex = i + 1;
            } else if (SimpleMathOperator.asMap().containsKey(currentCharAsString)) {
                addOperandToArray(expression, previousOperandStartIndex, i, tokensListInPostfixNotation);
                tokensListInPostfixNotation.add(currentCharAsString);
                previousOperandStartIndex = i + 1;
            }
        }

        if (previousOperandStartIndex < formatedStringLength) {
            tokensListInPostfixNotation.add(expression.substring(previousOperandStartIndex, formatedStringLength));
        }

        return tokensListInPostfixNotation;
    }

    /**
     * The method finds bounds of operand in linear representation of expression
     * and adds this operand to list of tokens in postfix notation order
     *
     * @param expression                  linear representation of expression
     * @param operandStartIndex           start index of operand in linear representation of expression
     * @param operatorEndingIndex         ending index of operand in linear representation of expression
     * @param tokensListInPostfixNotation list of tokens in postfix notation order
     */
    private static void addOperandToArray(String expression, int operandStartIndex, int operatorEndingIndex, List<String> tokensListInPostfixNotation) {
        String operand = expression.substring(operandStartIndex, operatorEndingIndex);
        if (!operand.isEmpty()) {
            tokensListInPostfixNotation.add(operand);
        }
    }

    /**
     * Method checks if token operand ist, comparing with value of non operand tokens
     *
     * @param token token from list of tokens in postfix notation order
     * @return boolean true if token corresponds to operand
     */
    protected boolean isOperand(String token) {
        boolean isOperand = true;
        if (SimpleMathOperator.asMap().containsKey(token)) isOperand = false;
        return isOperand;
    }

    /**
     * The method check if char corresponds to letter in ASCII.
     *
     * @param ch checking char
     * @return boolean true if checking char corresponds to letter in ASCII.
     */
    boolean isLetter(char ch) {
        return ch >= 'a' && ch <= 'z';
    }

    /**
     * The method check if char corresponds to number in ASCII.
     *
     * @param ch checking char
     * @return boolean true if checking char corresponds to number in ASCII.
     */
    protected boolean isNumber(char ch) {
        return ch >= '0' && ch <= '9';
    }

    protected List<String> getPresentedInExpressionVariables (String expression){
        List<String> tokensInPostfixNotation = parsToListInPostfixNotation(expression);
        for (String token: tokensInPostfixNotation){
            if(token.length()==1&&isLetter(token.charAt(0))){
                VARIABLES.add(token);
            }
        }
        return VARIABLES;
    }
}
