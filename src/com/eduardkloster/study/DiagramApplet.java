package com.eduardkloster.study;

import java.awt.*;
import java.applet.*;
import java.util.*;

public class DiagramApplet extends Applet {
    private static final int INDENT = 50;
    private static final int DEGREE = 6;
    private final int listOfParametersLength = namesOfParameters.length;
    private final Integer[] params = new Integer[listOfParametersLength];
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
        setLengthOfXAxis(getWidth() - INDENT);
        setLengthOfYAxis(getHeight() - INDENT);
        for (int i = 0; i < listOfParametersLength ; i++) {
            params[i] = Integer.valueOf(getParameter(namesOfParameters[i]));
        }
        changeArrayToArrayList(params);
        ArrayList values = changeArrayToArrayList(params);
        max = (Integer) Collections.max(values);
        max = correctValue(max,lengthOfValue(max));
        if(max < getLengthOfYAxis()){
            delta = ((double)max / getLengthOfYAxis());
        }else{
            delta = ((double)getLengthOfYAxis() / max);
        }
        for (int i = 0; i < listOfParametersLength ; i++) {
            params[i] = (int)((double)params[i] * delta);
            System.out.println(params[i]);
        }
        System.out.println(delta);
    }

    private void drawBlocks(Graphics g){
        int widthOfColumn = (getLengthOfXAxis() - INDENT) / listOfParametersLength ;
        int stepOX;
        for (int i = 0; i < listOfParametersLength ; i++) {
            stepOX = INDENT + i * widthOfColumn;
            g.setColor(Color.RED);
            g.fillRect(stepOX,getLengthOfYAxis() -  params[i], widthOfColumn, params[i]);
            g.setColor(Color.BLACK);
            g.drawRect(stepOX,getLengthOfYAxis() - params[i], widthOfColumn, params[i]);
            g.setColor(Color.WHITE);
            g.drawString(namesOfParameters[i], stepOX - 5, getLengthOfYAxis() - params[i] - 5);
        }
    }

    private <T> ArrayList changeArrayToArrayList(T[] values){
        ArrayList result = new ArrayList();
        for (T value : values) {
            result.add(value);
        }
        return result;
    }

//    @Deprecated
//    private int getMaxValue (int[] values){
//        int max = values[0];
//        for (int i = 1; i < values.length ; i++) {
//            if(max < values[i]){
//                max = values[i];
//            }
//        }
//        return max;
//    }
//
//    @Deprecated
//    private void drawScale(Graphics g, int[] values){
//        int stepOY = getLengthOfYAxis() / listOfParametersLength;
//        int max = getMaxValue(values);
//        for (int i = 0; i < listOfParametersLength; i++) {
//            g.drawLine(INDENT - (DEGREE / 2), INDENT + i * stepOY, INDENT + (DEGREE / 2), INDENT + i * stepOY);
//            g.drawString(String.valueOf(max - stepOY * i), INDENT - 30, INDENT + i * stepOY);
//        }
//    }


    private void drawScale(Graphics g, Integer[] values){
        int stepOY = getLengthOfYAxis() / listOfParametersLength;
        max = correctValue(max,lengthOfValue(max));
        for (int i = 0; i < listOfParametersLength; i+=2) {
            g.drawLine(INDENT - (DEGREE / 2), INDENT + i * stepOY, INDENT + (DEGREE / 2), INDENT + i * stepOY);
            g.drawString(String.valueOf(max - stepOY * i), INDENT - 30, INDENT + i * stepOY);
        }
    }

    //а - а % 10 + (а % 10 > 5 ? 10 : 0)
    private int correctValue(int value, int length){
        return  (int)(Math.ceil(value / Math.pow(10,length - 1)) * Math.pow(10,length - 1));
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
        g.drawLine(INDENT,INDENT, INDENT, getLengthOfYAxis());
        g.drawLine(INDENT,getLengthOfYAxis(), getLengthOfXAxis(), getLengthOfYAxis());
    }

    public void paint(Graphics g){
        drawAxis(g);
        drawBlocks(g);
        drawScale(g, params);
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
