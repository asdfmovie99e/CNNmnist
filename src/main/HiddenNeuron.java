package main;
/*
 @author Niklas Bruns
 @version 1.0
 */
public class HiddenNeuron extends Neuron {


    public HiddenNeuron (int id) {
        super(id);
}

public void sendDeltaToEdge(){
        double smallDelta = 0;
        for(Edge edge: outgoingEdgeSet){
            smallDelta += edge.getWeight() * edge.getNextNeuron().getSmallDelta();
        }
        for (Edge edge: incomingEdgeSet){
            edge.modWeight(smallDelta);
        }
}


}
