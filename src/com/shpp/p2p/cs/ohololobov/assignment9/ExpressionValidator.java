package com.shpp.p2p.cs.ohololobov.assignment9;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;

public class ExpressionValidator {

    public static boolean isValidExpression(String expression) {

        isValidNumberOfBrackets(expression);

        for (ValidationRegEx validationRegEx : ValidationRegEx.values()) {
            validate(expression, validationRegEx);
        }

        return true;
    }

    static boolean validate(String expression, ValidationRegEx validationRegEx) {
        String patternRegEx = validationRegEx.pattern();
        String exceptionMSG = validationRegEx.exceptionMSG();
        boolean isPositiveExpectation = validationRegEx.isPositiveExpectation();
        System.out.println("ExpressionValidator 76 " + patternRegEx + "-" + isPositiveExpectation);
        if (Pattern.compile(patternRegEx).matcher(expression).find()) {
            System.out.println("ExpressionValidator 78 Pattern: " + patternRegEx);
            isPositiveExpectation = !isPositiveExpectation;
            if(expression.equals("a")){
                System.out.println("expression.equals(\"a\")");
            }
        }
        if (isPositiveExpectation) {
            if(expression.equals("a")){
                System.out.println("expression.equals(\"a\") exception");
            }
            throw new IllegalArgumentException(exceptionMSG);
        }
        return true;
    }

    static boolean isValidNumberOfBrackets(String expression) {
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


    static boolean containsValidLetterComponents(String expression) {
        Map<String, MathFunction> mathFunctionHashMap = MathFunction.mathFunctionAsHashMap();
        System.out.println("ExpressionValidator expression 109: " + expression);
        String[] letterComponent = expression.split("[^" + ValidationRegEx.getLetterRegEx() + "]");
        System.out.println("ExpressionValidator 111 Arrays.toString(letterComponent+ " + Arrays.toString(letterComponent));
        for (String s : letterComponent) {
            if(s.isEmpty()){
                continue;
            }
            System.out.println("ExpressionValidator 113 component: " + s);
            System.out.println("ExpressionValidator 114 !mathFunctionHashMap.containsKey(s.toLowerCase())" + !mathFunctionHashMap.containsKey(s.toLowerCase()));
            if (s.length() != 1 && !mathFunctionHashMap.containsKey(s.toLowerCase())) {
                throw new IllegalArgumentException("Invalid variable value string");
            }
        }
        return true;
    }


    public static boolean isEmpty(String arg, boolean isExpression) {
        boolean isEmpty = false;
        if (isExpression) {
            if (arg.isEmpty()) {
                throw new IllegalArgumentException("There is empty expression");
            }
        } else {
            if (arg.isEmpty()) {
                isEmpty = true;
            }
        }
        return isEmpty;
    }
}
