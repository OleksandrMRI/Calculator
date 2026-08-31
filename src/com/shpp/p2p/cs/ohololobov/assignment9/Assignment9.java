package com.shpp.p2p.cs.ohololobov.assignment9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class contains logic of calculator.
 * Receives arguments throw String[] args of main method,
 * in form of expression and equalities of variables, f.e. {"2+3/c","c=1"}
 * If expression contains variable, value of variable must be used
 * to calculate expression, else numeric expression must be calculated.
 * This program execute expression with simple binary operation /+-*^
 * and use as base for parsing reverse Polish notation.
 */
public class Assignment9 {
    private final static List<List<Double>> results = new ArrayList<>();
    /**
     * Constant to use parsing in many methods of class Parser
     */
    final static MathExpressionParser REVERSE_POLISH_NOTATION_PARSER = ReversePolishNotationMathExpressionParser.getInstance();
    /**
     * HashMap with key as source expression string without spaces in lower case
     * and value as result of parsing this string to List prepared to calculation
     */
    private static final HashMap<String, List<String>> expressionsDataBase = new HashMap<>();
    /**
     * HashMap with keys as source strings of variable equality without spaces in lower case
     * and values as Records with two fields name of variable
     * and result of parsing right part of equality to List preparing to calculation.
     */
    private static final HashMap<String, ParsedVariableRecord> variablesDataBase = new HashMap<>();
    private static double result;
    private static List<String> tokensExpressionList;
    private static Map<String, List<List<String>>> variablesMap;

    /**
     * The method start logic of finding result if calculating expression,
     * that is getting from String[] args. It gets string arg[0] without spaces as expression
     * and HashMap with keys as variable names and values as Records with three fields value:
     * right part of variable equality string as string, boolean value,
     * when left part in source equality of variable string has unary minus
     * and source strings of variable equality without spaces in lower case.
     * If String[] args is empty or expression string is empty method finishes the program.
     *
     * @param args String[] contains strings of expression and variables.
     */
    static void main(String[] args) throws InterruptedException {
        variablesMap = null;
        if (args.length > 0) {
            tokensExpressionList = parseExpression(args[0]);
            variablesMap = parseVariablesToMap(args);
        } else {
            System.out.println("Main() has no arguments. Please, enter arguments of main() method");
            System.exit(0);
        }
        ConsoleCalculator reversPolishNotationConsoleCalculator = new ReversPolishNotationConsoleCalculator();
        if(args.length>1) {
            int size;
            int counter;
            do {
//            List<String> tokensExpressionListCopy= new ArrayList<>(tokensExpressionList);
                result = reversPolishNotationConsoleCalculator.calculate(tokensExpressionList, variablesMap);
                addToResultsList(reversPolishNotationConsoleCalculator.getResultList());
                System.err.println("Result:  " + result);
                Thread.sleep(1000);

                System.out.println("counter: " + (counter = reversPolishNotationConsoleCalculator.getVariablesCount()));
                System.out.println("size: " + (size = reversPolishNotationConsoleCalculator.numberOfMainVariableValues(variablesMap)));

            }
            while (counter < size);
            for (List<Double> result : results) {
                System.out.println(result);
            }
        }else{
            result = reversPolishNotationConsoleCalculator.calculate(tokensExpressionList, variablesMap);
            System.out.println("Result:  " + result);
        }
    }
    public static double getResult(){
        return result;
    }

    public static List<String> getExpressionTokenList(){
        return tokensExpressionList;
    }

    public static Map<String, List<List<String>>> getVariablesMap(){
        return variablesMap;
    }

    public static List<List<Double>> results(){
        return results;
    }

    private static void addToResultsList(List<Double> resultList) {
        List<Double> result = new ArrayList<>();
        if(resultList.size()==1){
            result.add(resultList.getFirst());
            results.add(result);
        } else {
            int counter = 0;
            for (int i = resultList.size()-1; i >=0 ; i--) {
                result.add(resultList.get(i));
                if(++counter>1) break;
            }
            results.add(result);
        }
    }

    /**
     * The method contains steps and logic of receiving
     * Map of parsed variables from arguments of main().
     *
     * @param args String[] arguments of main method
     * @return Map of parsed variable with key as variable name and value as parsed value
     */
    static Map<String, List<List<String>>> parseVariablesToMap(String[] args) {
        Map<String, List<List<String>>> parsedVariables = new HashMap<>();
        String formatedVariableString;
        for (int i = 1; i < args.length; i++) {
            formatedVariableString = REVERSE_POLISH_NOTATION_PARSER.formatRawString(args[i]);

            if (formatedVariableString.isEmpty()) {
                continue;
            }
            VariableEqualityRecord variableEqualityRecord = REVERSE_POLISH_NOTATION_PARSER.getVariableEqualityRecord(formatedVariableString);
            ParsedVariableRecord parsedVariableRecord = getParsedVariableRecord(variableEqualityRecord);

            String variableName = parsedVariableRecord.variableName();
            if (!parsedVariables.containsKey(variableName)) {
                parsedVariables.put(parsedVariableRecord.variableName(), new ArrayList<>());
            }
            parsedVariables.get(variableName).add(parsedVariableRecord.parsedVariableValue());
        }
        return parsedVariables;
    }

    /**
     * The method contains steps of parsing expression from first element of String[] args of main()
     *
     * @param firstArg first element in String[] args of main method
     * @return List of tokens from parsing expression
     */
    static List<String> parseExpression(String firstArg) {
        List<String> parsedExpression;
        String expression = getFormatedExpression(firstArg);
        parsedExpression = getParsedExpression(expression);
        return parsedExpression;
    }

    /**
     * The method prepares string at args[0] to parsing,
     * convert letters to lower case and reduces all spaces,
     * check whether prepared string is empty
     *
     * @param firstMainArgument arg[0], String
     * @return not empty prepared string in lower case and without spaces
     */
    private static String getFormatedExpression(String firstMainArgument) {
        String expression;
        String formatedExpression = REVERSE_POLISH_NOTATION_PARSER.formatRawString(firstMainArgument);
        if (formatedExpression.isEmpty()) {
            throw new IllegalArgumentException("There is an empty string");
        } else {
            expression = formatedExpression;
        }
        return expression;
    }

    /**
     * The method parses expression string to List of tokens or receives List of tokens from database
     *
     * @param expression String, that is linear representation of a mathematical expression
     * @return List of token after parsing
     */
    private static List<String> getParsedExpression(String expression) {
        List<String> parsedExpression;
        if (!expressionsDataBase.containsKey(expression)) {
            ExpressionValidator.isValidExpression(expression);
            parsedExpression = REVERSE_POLISH_NOTATION_PARSER.pars(expression);
            expressionsDataBase.put(expression, parsedExpression);
        } else {
            parsedExpression = expressionsDataBase.get(expression);
        }
        return parsedExpression;
    }

    /**
     * The method contains steps and logic of receiving ParsedVariableRecord from VariableEqualityRecord
     * or from database of variables
     *
     * @param variableEqualityRecord record with fields: strings variable name, variable value,
     *                               boolean unary minus if variable name in source string war with unary minus
     *                               source string of variable
     * @return ParsedVariableRecord, record with fields: variable Strings variable name, List of parsed to tokens string
     */
    private static ParsedVariableRecord getParsedVariableRecord(VariableEqualityRecord variableEqualityRecord) {

        String sourceVariableEquality = variableEqualityRecord.sourceVariableEquality();
        String variableName;
        ParsedVariableRecord parsedVariableRecord;
        List<String> parsedVariableExpression;
        if (!variablesDataBase.containsKey(sourceVariableEquality)) {
            parsedVariableExpression = parsVariableExpression(variableEqualityRecord);
            variableName = variableEqualityRecord.variableName();
            parsedVariableRecord = new ParsedVariableRecord(variableName, parsedVariableExpression);
            variablesDataBase.put(
                    sourceVariableEquality,
                    parsedVariableRecord
            );
            System.out.println("variablesDataBase: "+variablesDataBase + "variablesDataBase SIZE: " + variablesDataBase.size());
        } else {
            parsedVariableRecord = getVariableFromDataBase(sourceVariableEquality);
        }

        return parsedVariableRecord;
    }

    /**
     * The method contains steps of receiving List of token from linear representation expression of value of variable
     *
     * @param variableEqualityRecord record with fields: strings variable name, variable value,
     *                               boolean unary minus if variable name in source string war with unary minus
     *                               source string of variable
     * @return List of token from linear representation expression of value of variable
     */
    private static List<String> parsVariableExpression(VariableEqualityRecord variableEqualityRecord) {
        String variableExpression = variableEqualityRecord.variableValue();
        ExpressionValidator.isValidExpression(variableExpression);
        List<String> reversePolishNotation = REVERSE_POLISH_NOTATION_PARSER.pars(variableExpression);
        return REVERSE_POLISH_NOTATION_PARSER.processVariableUnaryMinus(reversePolishNotation, variableEqualityRecord.unaryMinus());
    }

    /**
     * The method receives fields of ParsedVariableRecord from database and create new ParsedVariableRecord instance
     *
     * @param sourceVariableEquality String, source linear representation of expression of value of variable
     * @return ParsedVariableRecord, record with fields: variable Strings variable name, List of parsed to tokens string
     */
    private static ParsedVariableRecord getVariableFromDataBase(String sourceVariableEquality) {
        String variableName = variablesDataBase.get(sourceVariableEquality).variableName();
        List<String> parsedVariableExpression = variablesDataBase.get(sourceVariableEquality).parsedVariableValue();
        return new ParsedVariableRecord(variableName, parsedVariableExpression);
    }
}
