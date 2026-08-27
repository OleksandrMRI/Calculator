package com.shpp.p2p.cs.ohololobov.assignment9;

import java.util.HashMap;
import java.util.Map;

/**
 * ENUM contains used in program mathematical operators with their linear values,
 * ranks in mathematical execution priority and lambda functions of executable operation.
 * It although contains logic of handling of ENUM`s instances
 */
public enum SimpleMathOperator {
    PLUS("+", 1, Double::sum),
    MULTIPLICATION("*", 2, (double a, double b) -> a * b),
    DIVISION("/", 3, (double a, double b) -> a / b),
    POW("^", 4, Math::pow),
    //minus must always at last position be, for correct using line of operators in regular variableValue
    SUBTRACTION("-", 1, (double a, double b) -> a - b);

    /**
     * Map of SimpleMathOperators, where string values of operator are keys and SimpleMathOperators as values
     */
    private static Map<String, SimpleMathOperator> operatorsHashMap;

    /**
     * rank of SimpleMathOperator in mathematical execution priority
     */
    private final int rank;

    /**
     * string values of SimpleMathOperator
     */
    private final String value;

    /**
     * functional interface as value of hird parameter of SimpleMathOperator
     * represents lambda functions of executable operation
     */
    private final OperatorExecutingInterface operatorCalculatingInterface;

    /**
     * COnstructor of SimpleMathOperator
     *
     * @param value                        string values of SimpleMathOperator
     * @param rank                         rank of SimpleMathOperator in mathematical execution priority
     * @param operatorCalculatingInterface functional interface as value of hird parameter of SimpleMathOperator
     *                                     represents lambda functions of executable operation
     */
    SimpleMathOperator(String value, int rank, OperatorExecutingInterface operatorCalculatingInterface) {
        this.value = value;
        this.rank = rank;
        this.operatorCalculatingInterface = operatorCalculatingInterface;
    }

    /**
     * method creates anf fills Map of SimpleMathOperators,
     * where string values of operator are keys and SimpleMathOperators as values
     *
     * @return Map of string values of and SimpleMathOperators
     */
    public static Map<String, SimpleMathOperator> asMap() {
        if (operatorsHashMap == null) {
            operatorsHashMap = new HashMap<>();
            for (SimpleMathOperator simpleMathOperator : SimpleMathOperator.values()) {
                operatorsHashMap.put(simpleMathOperator.value, simpleMathOperator);
            }
        }

        return operatorsHashMap;
    }

    /**
     * method executes mathematical operation according to lambda function in third parameter of concrete SimpleMathOperator
     *
     * @param operatorSTR    string value of SimpleMathOperator
     * @param firstArgument  first args for calculating
     * @param secondArgument second args for calculating
     * @return double result of executable mathematical operation
     * @throws RuntimeException if during calculating will division by zero
     */
    public static double executeOperation(String operatorSTR, double firstArgument, double secondArgument) throws RuntimeException{
        double result = 0;
        SimpleMathOperator simpleMathOperator = SimpleMathOperator.getFromValue(operatorSTR);
        System.out.println("operator: " + simpleMathOperator);
        System.out.println("a , b: " + firstArgument + "," + secondArgument);
        if (simpleMathOperator != null) {
            result = switch (simpleMathOperator) {
                case PLUS -> SimpleMathOperator.PLUS.calculate(firstArgument, secondArgument);
                case SUBTRACTION -> SimpleMathOperator.SUBTRACTION.calculate(firstArgument, secondArgument);
                case MULTIPLICATION -> SimpleMathOperator.MULTIPLICATION.calculate(firstArgument, secondArgument);
                case DIVISION -> {if(secondArgument ==0){
                    throw new RuntimeException ("Division by zero");
                }
                    yield SimpleMathOperator.DIVISION.calculate(firstArgument, secondArgument);
                }
                case POW -> SimpleMathOperator.POW.calculate(firstArgument, secondArgument);
            };
        }
        System.out.println("Op result: " + result);

        return result;
    }

    /**
     * The method receive string value of SimpleMathOperator and return this SimpleMathOperator
     *
     * @param value strinf value of SimpleMathOperator
     * @return SimpleMathOperator
     */
    private static SimpleMathOperator getFromValue(String value) {
        for (SimpleMathOperator simpleMathOperator : SimpleMathOperator.values()) {
            if (simpleMathOperator.value.equals(value))
                return simpleMathOperator;
        }

        return null;
    }

    /**
     * Method creates, fills and return string of string values of all SimpleMathOperators
     *
     * @return string of string values of all SimpleMathOperator
     */
    public static String operatorsToString() {
        StringBuilder sb = new StringBuilder();
        for (SimpleMathOperator o : SimpleMathOperator.values()) {
            sb.append(o.value);
        }
        return sb.toString();
    }

    /**
     * The mettod is uses to getting string values of SimpleMathOperator
     * @return string values of SimpleMathOperator
     */
    public String getValue() {
        return this.value;
    }

    /**
     * The mettod is uses to getting rank value of SimpleMathOperator
     * @return string values of SimpleMathOperator
     */
    public int getRank() {
        return this.rank;
    }

    /**
     * The method calls lambda function from third parameter of SimpleMathOperator
     * @param firstArg first double argument to calculating
     * @param secondDouble second double argument to calculating
     * @return double result of calculation
     */
    public double calculate(double firstArg, double secondDouble) {
        return this.operatorCalculatingInterface.calculate(firstArg, secondDouble);
    }
}