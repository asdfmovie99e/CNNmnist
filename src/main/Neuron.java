package main;

public class Neuron {
private int id;
private double inputvalue;
private double outputvalue;


    Neuron (int id, double inputvalue, double outputvalue)
{
    this.id=id;
    this.inputvalue=inputvalue;
    this.outputvalue=outputvalue;
}

    public void setInputvalue(Double inputvalue) {
        this.inputvalue = inputvalue;
    }

    public Double getOutputvalue() {
        return outputvalue;
    }
}
