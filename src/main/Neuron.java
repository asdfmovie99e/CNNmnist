package main;

import java.util.HashSet;

public abstract class Neuron {
    protected double lastInputValue = 0;
    protected double lastOutputValue = 0;
    protected HashSet<Edge> incomingEdges = new HashSet<Edge>();
    protected HashSet<Edge> outgoingEdges = new HashSet<Edge>();
    protected int identNumber;

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

    public void sendOutputToNextEdge(){
        calculateOutput();
        for(Edge outgoingEdge: outgoingEdges){
            outgoingEdge.receiveInput(lastOutputValue);
        }
    }
}
