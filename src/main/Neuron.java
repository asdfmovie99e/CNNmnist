package main;
/*
 @author Niklas Bruns
 @version 1.0
 */
import helper.MathHelper;

import java.util.HashMap;
import java.util.HashSet;

public abstract class Neuron {
protected int id;
protected double inputSum = 0;
protected double outputValue = 0;
protected double smallDelta;
protected HashSet<Edge> incomingEdgeSet; //
protected HashSet<Edge> outgoingEdgeSet;

    Neuron(){
        outgoingEdgeSet = new HashSet<Edge>();
        incomingEdgeSet = new HashSet<Edge>();
    }

    public void receiveOutputFromPreviousEdge(Double prevOutput) {
        inputSum += prevOutput;
    }

    //Übergibt Outputwert an Edge
    public Double getOutputvalue() {
        outputValue= MathHelper.getSigmoidApprox(inputSum);
        return outputValue;
    }

    public int getId(){
        return id;
    }

    public double getSmallDelta(){
        return smallDelta;
    }

    public void resetInputSum()
    {
        inputSum = 0;
    }

    public void activateOutgoingEdges(){
        for(Edge edge: outgoingEdgeSet){
            edge.giveToNextNeuron();
        }
    }

   public void addIncomingEdge(Edge edge){
        incomingEdgeSet.add(edge);
   }
    public void addOutgoingEdge(Edge edge){
        outgoingEdgeSet.add(edge);
    }
}
