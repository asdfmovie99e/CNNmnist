package main;

public class OutputNeuron extends Neuron {

    public OutputNeuron(int id) {
        super();
        this.id = id;
    }


    //deltalernregel noch in Bearbeitung

    public void deltalearn(double targetWeigth) {

        double littleDelta = targetWeigth - getOutputvalue();
        double a = getOutputvalue();   //passendes Neuron?
        double delta = NetworkController.EPSILON * littleDelta * a;



    }
}