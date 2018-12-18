package main;

/**
 * Repräsentiert eine Kante zwischen 2 Neuronen
 * @author Jens Krüger
 * @author Niklas Bruns
 * @author Marc Seibel
 * @version 1.0
 *
 */
public class Edge {
    protected double currentWeight;
    protected double lastInputValue;
    protected Neuron previousNeuron;
    protected Neuron nextNeuron;

    /**
     * @param previousNeuron Das Neuron, von welchem die Kante ausgeht.
     * @param nextNeuron Das Neuron, zu dem die Kante zeigt.
     */
    public Edge(Neuron previousNeuron, Neuron nextNeuron){
        currentWeight = Math.random() - 0.5d; //MUSS NOCH GEÄNDERT WERDEN DAMIT DIE GEWICHTE DIE AUS DER DB GELADEN WERDEN NICHT WIEDER GELÖSCHT WERDEN
        this.previousNeuron = previousNeuron;
        this.nextNeuron = nextNeuron;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    /**
     * Empfängt einen Input vom vorherigen Neuron und sendet es an das nächste Neuron weiter.
     * @param lastInputValue Der Wert, die vom vorherigen Neuron übergeben wird.
     */

    public void receiveInput(double lastInputValue) {
        this.lastInputValue = lastInputValue;
        sendToNextLayer();
    }

    public void setNewWeight(Double newWeight){
        currentWeight = newWeight;
    }

    /**
     * Multipliziert das Gewicht der Kante mit dem Input-wert
     */
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

    /**
     * Berechnet das neue Gewicht der Kante mittels Back Propagation. Muss immer von dem Neuron aufgerufen werden, das hinter der Kante ist.
     * @param smallDelta Der Kleindelta Wert aus dem nächsten Neuron.
     * @param ableitung Die Ableitung des Input-wertes des nächsten Neurons.
     */
    public void modWeight(Double smallDelta, Double ableitung) {
        double edgeInput = previousNeuron.getLastOutputValue();
        Double bigDelta = smallDelta * ableitung * NetworkController.EPSILON * edgeInput;
        currentWeight += bigDelta;
    }

}
