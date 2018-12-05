package main;

import helper.MathHelper;

public class HiddenFullyMeshedNeuron extends Neuron{

    public HiddenFullyMeshedNeuron(int identNumber) {
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
}
