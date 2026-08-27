package com.shpp.p2p.cs.ohololobov.assignment9;

import com.shpp.cs.a.graphics.WindowProgram;

import java.util.List;

public class graphic  extends WindowProgram {
    public void run() {
        String expression = "a = 2b";
        VariableEqualityRecord variableEqualityRecord = Assignment9.REVERSE_POLISH_NOTATION_PARSER.getVariableEqualityRecord(expression);
        String variableValue = Assignment9.REVERSE_POLISH_NOTATION_PARSER.formatRawString(variableEqualityRecord.variableValue());
        List<String> token = new ReversePolishNotationMathExpressionParser().pars(variableValue);
        ReversPolishNotationCalculator calculator = new ReversPolishNotationCalculator();
        int numberOfPoints = 20000;
        double minB = -200;
        double maxB = 200;
        double step = (maxB-minB)/20000;
        for (int i = 0; i < numberOfPoints; i++) {

//            createPoint(minB,calculator.calculate())
//            double minB += step
        }

    }



}
