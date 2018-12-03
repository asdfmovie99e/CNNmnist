package main;

public class OutputNeuron extends Neuron {

    public OutputNeuron(int id) {
        super();
        this.id = id;
    }


@Deprecated
    public void deltalearn(double targetWeigth) {

        double littleDelta = targetWeigth - getOutputvalue();
        double a = getOutputvalue();   //passendes Neuron?
        double delta = NetworkController.EPSILON * littleDelta * a;



    }


    public void sendDeltaToEdge(int targetWeight){
        smallDelta  = targetWeight - getOutputvalue();
        for(Edge edge: incomingEdgeSet){
            edge.modWeight(smallDelta);
        }

    }
}