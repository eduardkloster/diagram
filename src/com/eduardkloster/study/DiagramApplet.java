package com.eduardkloster.study;


import java.awt.*;
import java.applet.*;

public class DiagramApplet extends Applet {
    private static final int INDENT = 30;
    private static final int DEGREE = 2;
    private final int listOfParametersLength = namesOfParameters.length;
    private final int[] params = new int[listOfParametersLength];
    private final static String[] namesOfParameters = {
        "January","February","March","April","May","June",
        "July","August","September","October","November","December"
    };
    private int lengthOfYAxis;
    private int lengthOfXAxis;
    private int max;
    private double delta;

    public void init(){
        setBackground(Color.DARK_GRAY);
        setLengthOfXAxis(getWidth()  - 2 * INDENT);
        setLengthOfYAxis(getHeight() - 2 * INDENT);
        for (int i = 0; i < listOfParametersLength ; i++) {
            params[i] = Integer.valueOf(getParameter(namesOfParameters[i]));
        }
        max = getMaxValue(params);
        max = correctValue(max,lengthOfValue(max));
        delta = (double)getLengthOfYAxis() / max;
        for (int i = 0; i < listOfParametersLength ; i++) {
            params[i] = (int)((double)params[i] * delta);
        }
    }

    private void drawBlocks(Graphics g){
        int widthOfColumn = (getLengthOfXAxis() -  2 * INDENT) / listOfParametersLength ;
        int stepOX;
        for (int i = 0; i < listOfParametersLength ; i++) {
            stepOX = INDENT * 2 +  (i * widthOfColumn);
            int rColor = (int) (255 * Math.random());
            int gColor = (int) (255 * Math.random());
            int bColor = (int) (255 * Math.random());
            g.setColor(new Color(rColor, gColor, bColor));
            g.fillRect(stepOX, getLengthOfYAxis() - params[i] + INDENT, widthOfColumn, params[i]);
            g.setColor(Color.BLACK);
            g.drawRect(stepOX, getLengthOfYAxis() - params[i] + INDENT, widthOfColumn, params[i]);
            g.setColor(Color.WHITE);
            g.drawString(namesOfParameters[i] + " " + (int)(params[i] / delta), stepOX - 15, getLengthOfYAxis()  + INDENT - params[i] - 5);
        }
    }

    private int getMaxValue (int[] values){
        int max = values[0];
        for (int i = 1; i < values.length ; i++) {
            if(max < values[i]){
                max = values[i];
            }
        }
        return max;
    }

    private void drawScale(Graphics g){
        int step = (int) Math.pow(10, lengthOfValue(max) - 1);
        int[] grade = new int[max / step];
        for (int i = 0; i < grade.length; i++) {
            grade[i] += step * i;
        }
        int[] grade2 = new int[grade.length];
        for (int i = 0; i < grade2.length; i++) {
            grade2[i] = (int)(Math.ceil(grade[i] * delta));
        }
        for (int i = 0; i < grade2.length; i++) {
            g.drawLine(INDENT - (DEGREE / 2), getLengthOfYAxis() + INDENT - grade2[i] , INDENT + (DEGREE / 2), getLengthOfYAxis() + INDENT - grade2[i]);
            g.drawString(String.valueOf(grade[i]), INDENT - 30, getLengthOfYAxis() + INDENT - grade2[i]);
        }
    }

    //а - а % 10 + (а % 10 > 5 ? 10 : 0)
    private int correctValue(int value, int length){
        return  (int)(Math.ceil(value / Math.pow(10, length)) * Math.pow(10,length));
    }

    private int lengthOfValue(int value){
        int count = 0;
        while(value / 10 != 0){
            value /= 10;
            count++;
        }
        return count;
    }


    private void drawAxis(Graphics g){
        g.setColor(Color.ORANGE);
        g.drawLine(INDENT,INDENT, INDENT, getLengthOfYAxis() + INDENT);
        g.drawLine(INDENT, INDENT + getLengthOfYAxis(), getLengthOfXAxis() + INDENT, INDENT + getLengthOfYAxis());
    }

    public void paint(Graphics g){
        drawAxis(g);
        drawBlocks(g);
        drawScale(g);
    }

    public int getLengthOfXAxis() {
        return lengthOfXAxis;
    }

    public void setLengthOfXAxis(int lengthOfXAxis) {
        this.lengthOfXAxis = lengthOfXAxis;
    }

    public int getLengthOfYAxis() {
        return lengthOfYAxis;
    }

    public void setLengthOfYAxis(int lengthOfYAxis) {
        this.lengthOfYAxis = lengthOfYAxis;
    }
}
