package main;

import helper.MathHelper;

public class OutputNeuron extends Neuron{

    public OutputNeuron(int identNumber) {
        super(identNumber);
    }

    @Override
    public void resetInput() {
        lastInputValue = 0;
    }

    @Override
    public void receiveInput(double input) {
        lastInputValue += input;
    }

    @Override
    public double calculateOutput() {
        lastOutputValue = MathHelper.getSigmoidApprox(lastInputValue);
        return lastOutputValue;
    }

    public void modWeight(Double targetWeight) {
        Double smallDelta = targetWeight - calculateOutput();
        lastSmallDelta = smallDelta;
        Double ableitung = MathHelper.getSigmoidApprox(lastInputValue) * (1 - MathHelper.getSigmoidApprox(lastInputValue));
        for(Edge edge: incomingEdges){
            edge.modWeight(smallDelta, ableitung);
        }
    }
}