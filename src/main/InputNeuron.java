package main;


import helper.MathHelper;

public class InputNeuron extends Neuron {



    public InputNeuron (int id) {
    super(id);
    }
@Deprecated
    public void receiveRawByte(byte b){
        double result;
        if (b < 0 ) {
            result =((double) ((128 + b)  + 128))/255;
        } else {
            result = ((double) b)/255;
        }
        setInputSum(result);
    }

    private void setInputSum(Double inputSum){
        this.inputSum = inputSum;
    }

    public void receiveInput(double input) {
        this.inputSum = input;
    }

    @Override
    public Double getOutputvalue() {
        return inputSum;
    }
}
