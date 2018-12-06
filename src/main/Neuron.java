package main;

import java.util.HashSet;

public abstract class Neuron {
    protected double lastInputValue = 0;
    protected double lastOutputValue = 0;
    protected double lastSmallDelta;
    protected HashSet<Edge> incomingEdges = new HashSet<Edge>();
    protected HashSet<Edge> outgoingEdges = new HashSet<Edge>();
    protected int identNumber;

    public int getIdentNumber() {
        return identNumber;
    }

    public Neuron(int identNumber){
        this.identNumber = identNumber;
    }

    public double getLastInputValue() {
        return lastInputValue;
    }

    public double getLastOutputValue() {
        return lastOutputValue;
    }

    public abstract void resetInput();

    public abstract void receiveInput(double input);

    public abstract double calculateOutput();

    public double getLastSmallDelta(){
        return lastSmallDelta;
    }

    public void sendOutputToNextEdge(){
        calculateOutput();
        for(Edge outgoingEdge: outgoingEdges){
            outgoingEdge.receiveInput(lastOutputValue);
        }
    }

    public void addOutgoingEdge(Edge edge){
        outgoingEdges.add(edge);
    }

    public void addIncomingEdge(Edge edge){
        incomingEdges.add(edge);
    }
}
