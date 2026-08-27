package com.shpp.p2p.cs.ohololobov.assignment9;

import java.util.*;

/**
 * This class contains logic for parsing expression
 */
public class ReversePolishNotationMathExpressionParser extends MathExpressionParser {

    /**
     * temporary buffer stack, as list of operators, used as support for parsing postfix notation to reverse Polish notation
     */
    public static final List<SimpleMathOperator> OPERATORS_STACK_BUFFER = new ArrayList<>();
    /**
     * instance definition of instance of class Parser
     */
    private static ReversePolishNotationMathExpressionParser reversePolishNotationParserInstance;

    /**
     * Method for getting instance of Parser with pattern SingleTon
     *
     * @return instance of class Parser
     */
    public static ReversePolishNotationMathExpressionParser getInstance() {
        if (reversePolishNotationParserInstance == null) {
            reversePolishNotationParserInstance = new ReversePolishNotationMathExpressionParser();
        }
        return reversePolishNotationParserInstance;
    }

    /**
     * The method pars linear representation of expression to List of tokens in reverse Polish notation order
     *
     * @param expression string to parsing
     * @return List of tokens in form of reverse Polish notation
     */
    @Override
    public List<String> pars(String expression) {
        List<String> tokensListInPostfixNotation = super.pars(expression);
        Map<String, SimpleMathOperator> simpleMathOperatorHashMap = SimpleMathOperator.asMap();
        List<String> tokensListInReversePolishNotation = new ArrayList<>();
        int previousOperatorRank = 0;

        for (int currentIndex = 0; currentIndex < tokensListInPostfixNotation.size(); currentIndex++) {
            int nextIndex = currentIndex + 1;
            System.out.println(tokensListInPostfixNotation);
            String currentToken = tokensListInPostfixNotation.get(currentIndex);

            if (simpleMathOperatorHashMap.containsKey(currentToken)) {
                previousOperatorRank = parsDependsOnOperatorRank(
                        simpleMathOperatorHashMap,
                        currentToken,
                        previousOperatorRank,
                        tokensListInReversePolishNotation,
                        tokensListInPostfixNotation,
                        nextIndex
                );
                currentIndex++;
                System.out.println("parsedExpression+ " + tokensListInReversePolishNotation);
                System.out.println("strongOperators+ " + OPERATORS_STACK_BUFFER);
            } else if (currentIndex == 0 && isOperand(currentToken)) {
                System.out.println("first component: " + currentToken);
                tokensListInReversePolishNotation.add(currentToken);
            }
        }
        addOperators(tokensListInReversePolishNotation);
        return tokensListInReversePolishNotation;
    }

    /**
     * The method sort tokens in List depends on rank of operator tokens
     *
     * @param simpleMathOperatorMap             Map of SimpleMathOperator, where keys are values of SimpleMathOperators
     *                                          and values are their values
     * @param currentToken                      current token in list of tokens in postfix notation order
     * @param previousOperatorRank              rank of previous operator in list of tokens in postfix notation order
     * @param tokensListInReversePolishNotation list of tokens in reverse Polish notation order
     * @param tokensListInPostfixNotation       list of tokens in postfix notation order
     * @param nextIndex                         next index in list of tokens in postfix notation order
     * @return rank of current token
     */
    private int parsDependsOnOperatorRank(Map<String, SimpleMathOperator> simpleMathOperatorMap,
                                          String currentToken,
                                          int previousOperatorRank,
                                          List<String> tokensListInReversePolishNotation,
                                          List<String> tokensListInPostfixNotation,
                                          int nextIndex) {
        SimpleMathOperator currentOperator = simpleMathOperatorMap.get(currentToken);
        int currentRank = currentOperator.getRank();
        if (currentRank <= previousOperatorRank) {
            transferOperandsToTokensListInReversePolishNotation(tokensListInReversePolishNotation, currentRank);
        }
        tokensListInReversePolishNotation.add(tokensListInPostfixNotation.get(nextIndex));
        OPERATORS_STACK_BUFFER.add(currentOperator);

        return currentRank;
    }

    /**
     * The method process unary minus if such was present before variable name
     * in source linear representation of variable equality
     *
     * @param tokensListInReversePolishNotation list of tokens in reverse Polish notation order
     * @param variableUnaryMinus                boolean shows whether unary minus was present before variable name
     *                                          in source linear representation of variable equality
     * @return List<String> tokensListInReversePolishNotation, list of tokens in reverse Polish notation order
     */
    @Override
    public List<String> processVariableUnaryMinus(List<String> tokensListInReversePolishNotation, boolean variableUnaryMinus) {
        if (variableUnaryMinus) {
            tokensListInReversePolishNotation.add(UnaryMinus.UNARY_MINUS_MULTIPLICATOR);
            tokensListInReversePolishNotation.add(SimpleMathOperator.MULTIPLICATION.getValue());
        }
        return tokensListInReversePolishNotation;
    }

    /**
     * The method transfer value of necessary operators from temporary buffer list of operators
     * to list of tokens in reverse Polish notation order according to their ranks
     * and removing operators from buffer array if their values ware already transferred
     *
     * @param tokensListInReversePolishNotation necessary
     * @param currentRank                       rank of current operator from  list of tokens in postfix notation order
     */
    private void transferOperandsToTokensListInReversePolishNotation(List<String> tokensListInReversePolishNotation, int currentRank) {
        System.out.println("operatorsStackBuffer.size(): " + ReversePolishNotationMathExpressionParser.OPERATORS_STACK_BUFFER.size());
        for (int i = ReversePolishNotationMathExpressionParser.OPERATORS_STACK_BUFFER.size() - 1; i >= 0; i--) {
            System.out.println(ReversePolishNotationMathExpressionParser.OPERATORS_STACK_BUFFER);
            if (currentRank <= ReversePolishNotationMathExpressionParser.OPERATORS_STACK_BUFFER.get(i).getRank()) {
                tokensListInReversePolishNotation.add(ReversePolishNotationMathExpressionParser.OPERATORS_STACK_BUFFER.remove(i).getValue());
                System.out.println(tokensListInReversePolishNotation);
            }
        }
    }

    /**
     * The method adds remaining operators values from temporary buffer list of operators
     * to list of tokens in reverse Polish notation order and clean temporary buffer list of operators
     *
     * @param tokensListInPolishNotation list of tokens in reverse Polish notation
     */
    private void addOperators(List<String> tokensListInPolishNotation) {
        for (int i = ReversePolishNotationMathExpressionParser.OPERATORS_STACK_BUFFER.size() - 1; i >= 0; i--) {
            tokensListInPolishNotation.add(ReversePolishNotationMathExpressionParser.OPERATORS_STACK_BUFFER.get(i).getValue());
        }
        ReversePolishNotationMathExpressionParser.OPERATORS_STACK_BUFFER.clear();
    }
}
