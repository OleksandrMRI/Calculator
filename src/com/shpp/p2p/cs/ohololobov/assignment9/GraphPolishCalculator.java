package com.shpp.p2p.cs.ohololobov.assignment9;

import java.util.ArrayList;
import java.util.List;

public class GraphPolishCalculator extends GraphCalculator{
    /**
     * The method calculate result of expression from List of tokens of expression and Map of parsed variables
     *
     * @param tokensExpressionList List of tokens from linear representation of expression
     * @param variableValue  Map of parsed variables with keys as variables names
     *                         and values as list of tokens from variable values
     * @return double, result of calculating
     */
    public double calculate(List<String> tokensExpressionList,
                            double variableValue) {
        List<String> tokensExpressionListCopy= new ArrayList<>(tokensExpressionList);
        double result;
        if (tokensExpressionListCopy.size() == 1) {
            result = receiveOperandValue(variableValue, tokensExpressionListCopy.getFirst());
        }else {
            result = getResultMultiElementExpression(tokensExpressionListCopy, variableValue);
        }
        return result;
    }


    /**
     * The method calculate double result from List of tokens from expression string with then one element
     *
     * @param tokensExpressionList List of tokens from linear representation of expression
     * @param variableValue  Map of parsed variables with keys as variables names
     *                         and values as list of tokens from variable values
     * @return double, result of calculating
     */
    private double getResultMultiElementExpression(List<String> tokensExpressionList,
                                                   double variableValue) {
        double result = 0;
        int parsedExpressionSize;
        for (int i = 0; i < (parsedExpressionSize = tokensExpressionList.size()) && parsedExpressionSize > 1; i++) {
            System.out.println("Main 99 parsedExpression: " + tokensExpressionList);
            if (SimpleMathOperator.asMap().containsKey(tokensExpressionList.get(i))) {
                String firstOperand = tokensExpressionList.get(i - 2);
                String secondOperand = tokensExpressionList.get(i - 1);
                String operatorSTR = tokensExpressionList.get(i);
                System.out.println("Main 104 operatorSTR+ " + operatorSTR);
                double firstArgument = receiveOperandValue(variableValue, firstOperand);
                double secondArgument = receiveOperandValue(variableValue, secondOperand);

                result = SimpleMathOperator.executeOperation(operatorSTR, firstArgument, secondArgument);
                tokensExpressionList.remove(i);
                tokensExpressionList.remove(i - 1);
                tokensExpressionList.set(i - 2, "" + result);
                System.out.println("Main 112 result + " + result);
                i = 0;
            }
        }
        return result;
    }
}
