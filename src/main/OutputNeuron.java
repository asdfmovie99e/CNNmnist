package main;

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
        lastOutputValue += input;
    }

    @Override
    public double calculateOutput() {
        return lastInputValue;
    }
}
