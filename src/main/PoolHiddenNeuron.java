package main;



public class PoolHiddenNeuron extends HiddenNeuron {

    double maxinput = -100000000000000000000000d;
    Edge maxEdge = null;

    //Konstruktor
    PoolHiddenNeuron(int id){
        super(id);
    }


    public void receiveOutputFromPreviousEdge(Double prevOutput, Edge edge) {
        if (prevOutput >= maxinput) {
            maxinput = prevOutput;
            this.maxEdge = edge;
        }

    }
    @Override
    public void resetInputSum(){
        maxinput = -10000000000000000d;
        maxEdge = null;
    }


    @Override
    public void receiveOutputFromPreviousEdge(Double d){
        System.out.println("Ein POOlneuron kann nur output empfangen wenn die edge als objekt mitgeliefert wird");
        System.exit(1000);
    }

    @Override
    public void sendDeltaToEdge(){
        double smallDelta = 0;
        for(Edge edge: outgoingEdgeSet){
            smallDelta += edge.getWeight() * edge.getNextNeuron().getSmallDelta();
        }
            maxEdge.modWeight(smallDelta);
    }
}
