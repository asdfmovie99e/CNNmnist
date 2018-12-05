package main;

public class Edge {
    protected double currentWeight;
    protected double lastInputValue;
    protected Neuron previousNeuron;
    protected Neuron nextNeuron;

    public Edge(Neuron previousNeuron, Neuron nextNeuron){
        currentWeight = Math.random() - 0.5d;
        this.previousNeuron = previousNeuron;
        this.nextNeuron = nextNeuron;
    }

    public void receiveInput(double lastInputValue) {
        this.lastInputValue = lastInputValue;
        sendToNextLayer();
    }

    public void setNewWeight(Double newWeight){
        currentWeight = newWeight;
    }

    private void sendToNextLayer(){
        double valueToPassOn = lastInputValue * currentWeight;
        nextNeuron.receiveInput(valueToPassOn);
    }

    public Neuron getPreviousNeuron() {
        return previousNeuron;
    }


    public Neuron getNextNeuron() {
        return nextNeuron;
    }

}
