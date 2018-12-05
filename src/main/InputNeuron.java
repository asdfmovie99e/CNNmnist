package main;

public class InputNeuron extends Neuron {


    public InputNeuron(int identNumber) {
        super(identNumber);
    }

    @Override
    public void resetInput()  {
       //nicht nötig
    }

    @Override
    public void receiveInput(double input) {
        this.lastInputValue = input;
    }

    @Override
    public double calculateOutput() {
        lastOutputValue =  ((Double)lastInputValue) / 255d;
        return lastOutputValue;
    }
}
