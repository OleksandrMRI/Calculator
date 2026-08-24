package com.shpp.p2p.cs.ohololobov.assignment9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public enum Operator {
    PLUS("+", "minor", Double::sum),
    MULTIPLICATION("*", "major", (double a, double b) -> a * b),
    DIVISION("/", "major", (double a, double b) -> a / b),
    POW("^", "pow", Math::pow),
    //minus must always at last position be, for correct using line of operators in regular variableValue
    SUBTRACTION("-", "minor", (double a, double b) -> a - b);
    private static List<String> minorOperatorsList;
    private static List<String> majorOperatorsList;
    private static List<String> operatorsList;
    private static HashMap<String, Operator> operatorsHashMap;
    private final String rank;
    private final String value;
    private final OperatorCalculatingInterface operatorCalculatingInterface;

    Operator(String value, String rank, OperatorCalculatingInterface operatorCalculatingInterface) {
        this.value = value;
        this.rank = rank;
        this.operatorCalculatingInterface = operatorCalculatingInterface;
    }


    public static HashMap<String, Operator> asHashMap() {
        if (operatorsHashMap == null) {
            operatorsHashMap = new HashMap<>();
            for (Operator operator : Operator.values()) {
                operatorsHashMap.put(operator.value, operator);
            }
        }
        return operatorsHashMap;
    }

    public static double makeOperation(String operatorSTR, double firstArgument, double secondArgument) {
        double result = 0;
        Operator operator = Operator.getFromValue(operatorSTR);
        System.out.println("operator: "+operator);
        System.out.println("a , b: " + firstArgument + "," + secondArgument);
        if (operator != null) {
            result = switch (operator) {
                case PLUS -> Operator.PLUS.getCalculate(firstArgument, secondArgument);
                case SUBTRACTION -> Operator.SUBTRACTION.getCalculate(firstArgument, secondArgument);
                case MULTIPLICATION -> Operator.MULTIPLICATION.getCalculate(firstArgument, secondArgument);
                case DIVISION -> Operator.DIVISION.getCalculate(firstArgument, secondArgument);
                case POW -> Operator.POW.getCalculate(firstArgument, secondArgument);
            };
        }
        System.out.println("Op result: " + result);
        return result;
    }

    private static Operator getFromValue(String value) {
        for (Operator operator : Operator.values()) {
            if (operator.value.equals(value))
                return operator;
        }
        return null;
    }

    public static String operatorsToString(boolean withMinusSign) {
        StringBuilder sb = new StringBuilder();

            for (Operator o : Operator.values()) {
                if (o != Operator.SUBTRACTION) {
                    sb.append(o.value);
                }
            }
        if (withMinusSign) {
            sb.append(Operator.SUBTRACTION.value);
        }
        return sb.toString();
    }

    public String getValue() {
        return this.value;
    }

    public static List<String> getMinorOperatorsList() {

        String rank = "minor";

        if (minorOperatorsList == null) {
            minorOperatorsList = new ArrayList<>();
            fillList(rank);
        }
        return minorOperatorsList;
    }

    public static List<String> getMajorOperatorsList() {

        String rank = "major";
        if (majorOperatorsList == null) {
            majorOperatorsList = new ArrayList<>();
            fillList(rank);
        }
        return majorOperatorsList;
    }

    public static List<String> getOperatorsList() {
        String rank = "all";
        if (operatorsList == null) {
            operatorsList = new ArrayList<>();
            fillList(rank);

        }
        return operatorsList;
    }

    private static void fillList(String rank) {

        for (Operator operator : Operator.values()) {
            if (rank.equals("all")) {
                operatorsList.add(operator.getValue());
            } else if (operator.rank.equals("minor") && rank.equals(operator.rank)) {
                minorOperatorsList.add(operator.getValue());
            } else if (operator.rank.equals("major") && rank.equals(operator.rank)) {
                System.out.println(majorOperatorsList);
                majorOperatorsList.add(operator.getValue());
            }

        }
    }

    public double getCalculate(double a, double b) {
        return this.operatorCalculatingInterface.calculate(a, b);
    }


}