package com.shpp.p2p.cs.ohololobov.assignment9;

import java.util.HashMap;
import java.util.function.UnaryOperator;

public enum MathFunction {
    SIN("sin", Math::sin),
    COS("cos", Math::cos),
    TAN("tan", Math::tan),
    CTAN("ctan", (double arg) -> Math.tan(90-arg)),
    ATAN("atan", Math::atan),
    ACTAN("actan", (double arg) -> Math.atan(1/arg)),
    ASIN("asin", Math::asin),
    SQRT("sqrt", Math::sqrt);

    private final String value;
    private final FunctionCalculatingInterface functionCalculatingInterface;
    private static HashMap<String, MathFunction> mathFunctionHashMap;

    MathFunction(String value, FunctionCalculatingInterface functionCalculatingInterface) {
        this.value = value;
        this.functionCalculatingInterface = functionCalculatingInterface;
    }

    public static String mathFunctionsToStringWithOrSeparatorRegEx() {
        StringBuilder sb = new StringBuilder("(");
        int counter = 0;
        for (MathFunction mathFunction : MathFunction.values()) {
            if (counter++ == MathFunction.values().length - 1) {
                sb.append(mathFunction.value).append(")");
            } else {
                sb.append(mathFunction.value).append("|");
            }
        }
        return sb.toString();
    }

    public String getValue() {
        return value;
    }

    double calculateFunction(String functionValue, double arg) throws IllegalArgumentException {
        MathFunction function = switch (functionValue){
            case "sin" -> mathFunctionHashMap.get("sin");
            case "cos" -> mathFunctionHashMap.get("cos");
            case "tan" -> mathFunctionHashMap.get("tan");
            case "ctan" -> mathFunctionHashMap.get("ctan");
            case "atan" -> mathFunctionHashMap.get("atan");
            case "actan" -> {
                if(arg == 0){
                    throw new ArithmeticException("Division by zero");
                }
                yield mathFunctionHashMap.get("actan");
            }
            case "asin" -> mathFunctionHashMap.get("asin");
            case "sqrt" -> mathFunctionHashMap.get("sqrt");
            default -> throw new RuntimeException("There is no such function or incorrect name of function");
        };
        return function.functionCalculatingInterface.calculate(arg);
    }

    public static HashMap<String, MathFunction> mathFunctionAsHashMap() {
        if (mathFunctionHashMap == null) {
            mathFunctionHashMap = new HashMap<>();
            for (MathFunction mathFunction : MathFunction.values()) {
                mathFunctionHashMap.put(mathFunction.getValue(), mathFunction);
            }
        }
        return mathFunctionHashMap;
    }

}
