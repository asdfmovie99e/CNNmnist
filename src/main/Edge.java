package main;

public class Edge {
    protected double currentWeight;
    protected double lastInputValue;
    protected Neuron previousNeuron;
    protected Neuron nextNeuron;

    public Edge(Neuron previousNeuron, Neuron nextNeuron){
        currentWeight = Math.random() - 0.5d; //MUSS NOCH GEÄNDERT WERDEN DAMIT DIE GEWICHTE DIE AUS DER DB GELADEN WERDEN NICHT WIEDER GELÖSCHT WERDEN
        this.previousNeuron = previousNeuron;
        this.nextNeuron = nextNeuron;
    }

    public double getCurrentWeight() {
        return currentWeight;
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

    public void modWeight(Double smallDelta, Double ableitung) {
        double edgeInput = previousNeuron.getLastOutputValue();
        Double bigDelta = smallDelta * ableitung * NetworkController.EPSILON * edgeInput;
        currentWeight += bigDelta;
    }

}
