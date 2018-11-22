package main;

import helper.MathHelper;

public class Neuron {
protected int id;
private double inputvalue = 0;
private double outputvalue = 0;



    public void setInputvalue(Double inputvalue) {
        this.inputvalue = inputvalue;
    }

    //Übergibt Outputwert an Edge
    public Double getOutputvalue() {
        outputvalue= MathHelper.getSigmoidApprox(inputvalue);
        return outputvalue;
    }

}