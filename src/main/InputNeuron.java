package main;

public class InputNeuron extends Neuron {


    public InputNeuron(int identNumber) {
        super(identNumber);
    }

    @Override
    public void resetInput(){
        throw new UnsupportedOperationException();
    }

    @Override
    public void receiveInput(double input) {
        this.lastInputValue = input;
    }

    @Override
    public double calculateOutput() {
        return lastInputValue / 255d;
    }
}
