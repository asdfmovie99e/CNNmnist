package main;

import helper.MathHelper;

public class HiddenNeuron extends Neuron {

    public HiddenNeuron(int identNumber) {
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

    public void modWeight() {
        double smallDelta = 0;
       for(Edge edge: outgoingEdges){
           smallDelta += edge.getCurrentWeight() * edge.getNextNeuron().getLastSmallDelta();
       }



        lastSmallDelta = smallDelta;
        Double ableitung = MathHelper.getSigmoidApprox(lastInputValue) * (1 - MathHelper.getSigmoidApprox(lastInputValue));
        for (Edge edge : incomingEdges) {
            edge.modWeight(smallDelta, ableitung);
        }
    }
}
