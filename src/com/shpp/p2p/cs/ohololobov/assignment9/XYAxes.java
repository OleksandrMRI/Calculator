package com.shpp.p2p.cs.ohololobov.assignment9;

import acm.graphics.GCompound;
import acm.graphics.GLine;

import java.awt.*;

/**
 * This class draws middle horizontal and vertical lines
 */
public class XYAxes extends GCompound {
    private final int getWidth;
    private final int getHeight;
    private final Color color;

    /**
     * constructor receives sizes of canvas to used later in calculating of middle points
     *
     * @param getWidth  int, width of canvas
     * @param getHeight int, height of canvas
     * @param color     Color, color of lines
     */
    public XYAxes(int getWidth, int getHeight, Color color) {
        this.getWidth = getWidth;
        this.getHeight = getHeight;
        this.color = color;
    }

    /**
     * The method adds checking lines in GCompound
     */
    public void addCheckingLines() {
        add(createVerticalMiddleLine());
        add(createHorizontalMiddleLine());
        markAsComplete();

    }

    /**
     * The method creating vertical line at the middle of canvas
     *
     * @return GLine, vertical line at the middle of canvas
     */
    public GLine createVerticalMiddleLine() {
        GLine verticalMiddleLine = createGLine(getWidth / 2.0, 0, getWidth / 2.0, getHeight);
        return verticalMiddleLine;
    }

    /**
     * The method creating horizontal line at the middle of canvas
     *
     * @return GLine, horizontal line at the middle of canvas
     */
    public GLine createHorizontalMiddleLine() {
        GLine horizontalMiddleLine = createGLine(0, getHeight / 2.0, getWidth, getHeight / 2.0);
        return horizontalMiddleLine;
    }

    /**
     * The method creating GLine with specified coordinates and color
     *
     * @param offSetX1 double, coordinate offSetX1 first end of line
     * @param offSetY1 double, coordinate offSetY1 first end of line
     * @param offSetX2 double, coordinate offSetX2 second end of line
     * @param offSetY2 double, coordinate offSetY2 first end of line
     * @return - GLine, line with specified coordinates and color
     */
    private GLine createGLine(double offSetX1, double offSetY1, double offSetX2, double offSetY2) {
        GLine line = new GLine(offSetX1, offSetY1, offSetX2, offSetY2);
        line.setColor(color);
        return line;
    }
}