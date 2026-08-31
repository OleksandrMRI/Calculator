package com.shpp.p2p.cs.ohololobov.assignment9;

import acm.graphics.GOval;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.shpp.p2p.cs.ohololobov.assignment9.Assignment9.*;

public class graphic extends WindowProgram {
    private static final String EQUALITY = "y = x^2/100-100";
    private static final double GRAPH_START_COORDINATE_X = -200;
    private static final double GRAPH_END_COORDINATE_X = 200;
    private static final int NUMBER_OF_POINTS = 20000;


    public void run() {
        XYAxes xyAxes = new XYAxes(getWidth(),getHeight(),Color.GREEN);
        xyAxes.addCheckingLines();
        add(xyAxes);
        String formatedExpression = REVERSE_POLISH_NOTATION_PARSER.formatRawString(EQUALITY);
        if (formatedExpression.isEmpty()) {
            throw new IllegalArgumentException("There is an empty string");
        }
        VariableEqualityRecord variableEqualityRecord = REVERSE_POLISH_NOTATION_PARSER.getVariableEqualityRecord(formatedExpression);
        String equalityValue = variableEqualityRecord.variableValue();
        ExpressionValidator.isValidExpression(equalityValue);
        String variable = REVERSE_POLISH_NOTATION_PARSER.getPresentedInExpressionVariables(equalityValue).getFirst();
        System.out.println(variable);
        String[] args = createArgsArray(equalityValue, variable);
        System.out.println(Arrays.toString(args));
        List<String> tokensExpressionList = Assignment9.parseExpression(args[0]);
        calculateCoordinates(tokensExpressionList);
    }

    private void calculateCoordinates(List<String> tokensExpressionList) {
        GraphCalculator consoleCalculator = new GraphPolishCalculator();
        double step = (GRAPH_END_COORDINATE_X - GRAPH_START_COORDINATE_X) / NUMBER_OF_POINTS;
        double coordinateX = GRAPH_START_COORDINATE_X;
        for (int i = 0; i < NUMBER_OF_POINTS; i++) {

            double coordinateY = consoleCalculator.calculate(tokensExpressionList, coordinateX);

            addPoint(coordinateX + getWidth() / 2.0, -coordinateY + getHeight() / 2.0);
            coordinateX+=step;
        }
    }

    private void addPoint(double offsetX, double offsetY) {
        GOval gOval = new GOval(offsetX, offsetY, 1, 1);
        gOval.setColor(Color.BLUE);
        gOval.setFilled(true);
        add(gOval);
    }


    private String[] createArgsArray(String variableValue, String variable) {
        double stepX = (GRAPH_END_COORDINATE_X - GRAPH_START_COORDINATE_X) / NUMBER_OF_POINTS;
        double start = GRAPH_START_COORDINATE_X;
        List<String> argsList = new ArrayList<>();
        argsList.add(variableValue);
        for (int i = 0; i < NUMBER_OF_POINTS; i++) {
            argsList.add(variable + "=" + (start));
            start += stepX;
        }
        String[] args = new String[argsList.size()];
        for (int i = 0; i < args.length; i++) {
            args[i] = argsList.get(i);
        }
        return args;
    }
}
