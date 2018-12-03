package main;


import helper.MathHelper;

public class Edge {
    private int id;
    private double weight;
    private Neuron previousNeuron;
    private Neuron nextNeuron;

    Edge(int id,Neuron previousNeuron, Neuron nextNeuron)
    {   this.id=id;
        this.weight=Math.random();
        this.previousNeuron=previousNeuron;
        this.nextNeuron=nextNeuron;

    }


    public Neuron getPreviousNeuron() {
        return previousNeuron;
    }

    public Neuron getNextNeuron() {
        return nextNeuron;
    }

    public void giveToNextNeuron()
    {  double newInputValue; //Dieser Wert wird an das nächste Neuron übergeben.

        newInputValue= previousNeuron.getOutputvalue() * weight;
        if(nextNeuron instanceof PoolHiddenNeuron){
            ((PoolHiddenNeuron)nextNeuron).receiveOutputFromPreviousEdge(newInputValue, this);
        } else {
            nextNeuron.receiveOutputFromPreviousEdge(newInputValue);
        }
    }


    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void modWeight(double smallDelta) {
        double ableitungInput = MathHelper.getSigmoidApprox(previousNeuron.getOutputvalue()) * (1 - MathHelper.getSigmoidApprox(previousNeuron.getOutputvalue()));
        double bigDelta = NetworkController.EPSILON * smallDelta * previousNeuron.getOutputvalue() * ableitungInput;
        weight += bigDelta;
    }
}
