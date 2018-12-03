package main;

public class OutputNeuron extends Neuron {

    public OutputNeuron(int id) {
        super();
        this.id = id;
    }


    //deltalernregel noch in Bearbeitung



    public void sendDeltaToEdge(int targetWeight){
        smallDelta  = targetWeight - getOutputvalue();
        for(Edge edge: incomingEdgeSet){
            edge.modWeight(smallDelta);
        }

    }
}