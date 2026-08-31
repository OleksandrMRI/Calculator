package com.shpp.p2p.cs.ohololobov.assignment9;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * This class ontains logic of validation of linear representation of mathematical expression
 * and linear representation of variable equality
 */
public class ExpressionValidator {
    /**
     * The method checks whether expression is valid mathematical expression
     *
     * @param expression input expression
     * @return true if validation was successful
     * @throws IllegalArgumentException when validation fails
     */
    public static boolean isValidExpression(String expression) throws IllegalArgumentException{
        containsValidLetterComponents(expression);
        isValidNumberOfBrackets(expression);

        for (ValidationRegEx validationRegEx : ValidationRegEx.values()) {
            validate(expression, validationRegEx);
        }

        return true;
    }

    /**
     * The method contains logic of validation with regular expression from ENUM ValidationRegEx
     * @param expression input expression for validation
     * @param validationRegEx instanse of ENUM ValidationRegEx
     * @return true if validation is successful
     * @throws IllegalArgumentException when validation fails
     */
    static boolean validate(String expression, ValidationRegEx validationRegEx) throws IllegalArgumentException{
        String patternRegEx = validationRegEx.pattern();
        String exceptionMSG = validationRegEx.exceptionMSG();
        boolean isPositiveExpectation = validationRegEx.isPositiveExpectation();
        System.out.println("ExpressionValidator 76 " + patternRegEx + "-" + isPositiveExpectation);
        if (Pattern.compile(patternRegEx).matcher(expression).find()) {
            System.out.println("ExpressionValidator 78 Pattern: " + patternRegEx);
            isPositiveExpectation = !isPositiveExpectation;
        }
        if (isPositiveExpectation) {
            throw new IllegalArgumentException(exceptionMSG);
        }
        return true;
    }

    /**
     * The method checks number and position of brackets according to postfix notatio of mathematical expression
     * @param expression input expression for validation
     * @return true if validation is successful
     * @throws IllegalArgumentException when validation fails
     */
    static boolean isValidNumberOfBrackets(String expression) throws IllegalArgumentException{
        int openingBracketCounter = 0;
        int closingBracketCounter = 0;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '(') {
                openingBracketCounter++;
            } else if (expression.charAt(i) == ')') {
                closingBracketCounter++;
            }
            if (openingBracketCounter < closingBracketCounter) {
                throw new IllegalArgumentException("Illegal number of Brackets");
            }
        }
        if (openingBracketCounter > closingBracketCounter) {
            throw new IllegalArgumentException("Illegal number of Brackets");
        }
        return true;
    }

    /**
     * The method checks letter elements of mathematical expression.
     * Expression must contain only solitary letters as variables
     * or group of letters corresponding to math function
     *
     * @param expression input expression for validation
     * @return true if validation is successful
     * @throws IllegalArgumentException when validation fails
     */
    static boolean containsValidLetterComponents(String expression) throws IllegalArgumentException {
        Map<String, OneArgumentFunction> mathFunctionHashMap = OneArgumentFunction.mathFunctionAsHashMap();
        System.out.println("ExpressionValidator expression 109: " + expression);
        String[] letterComponent = expression.split("[^" + ValidationRegEx.letterRegEx() + "]");
        System.out.println("ExpressionValidator 111 Arrays.toString(letterComponent+ " + Arrays.toString(letterComponent));
        for (String s : letterComponent) {
            if (s.isEmpty()) {
                continue;
            }
            System.out.println("ExpressionValidator 113 component: " + s);
            System.out.println("ExpressionValidator 114 !mathFunctionHashMap.containsKey(s.toLowerCase())" + !mathFunctionHashMap.containsKey(s.toLowerCase()));
            if (s.length() != 1 && !mathFunctionHashMap.containsKey(s.toLowerCase())) {
                throw new IllegalArgumentException("Invalid letters component");
            }
        }
        return true;
    }
}
