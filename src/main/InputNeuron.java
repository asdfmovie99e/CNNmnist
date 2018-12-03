package main;


public class InputNeuron extends Neuron {



    public InputNeuron (int id) {
    super();
    this.id=id;
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

    public void receiveInput(int input) {
        this.inputSum = input;
    }
}
