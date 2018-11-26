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
private HashMap<Integer, Edge> incomingEdgeMap;
private HashMap<Integer, Edge> outgoingEdgeMap;

    Neuron(){
        outgoingEdgeMap= new HashMap<Integer, Edge>();
        incomingEdgeMap= new HashMap<Integer, Edge>();
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

   public void addIncomingEdge(int edgeIdent, Edge edge){
        incomingEdgeMap.put(edgeIdent, edge);
   }
    public void addOutgoingEdge(int edgeIdent, Edge edge){
        outgoingEdgeMap.put(edgeIdent, edge);
    }
}
