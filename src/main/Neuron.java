package main;
/*
 @author Niklas Bruns
 @version 1.0
 */
import helper.MathHelper;

import java.util.HashMap;

public class Neuron {
protected int id;
private double inputSum = 0;
private double outputValue = 0;
private HashMap<Integer, Edge> edgeMap;

    Neuron(){
        edgeMap= new HashMap<Integer, Edge>();
    }

    public void receiveOutputFromPreviousEdge(Double prevOutput) {
        inputSum += prevOutput;
    }

    //Übergibt Outputwert an Edge
    public Double getOutputvalue() {
        outputValue= MathHelper.getSigmoidApprox(inputSum);
        return outputValue;
    }

    public void resetInputSum()
    {
        inputSum = 0;
    }
}