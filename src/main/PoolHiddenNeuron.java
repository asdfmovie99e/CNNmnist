package main;



public class PoolHiddenNeuron extends HiddenNeuron {

    double maxinput = Double.MIN_VALUE;
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

    public void resetMaxInput(){
        maxinput = Double.MIN_VALUE;
    }


    @Override
    public void receiveOutputFromPreviousEdge(Double d){
        System.out.println("Ein POOlneuron kann nur output empfangen wenn die edge als objekt mitgeliefert wird");
        System.exit(1000);
    }
}
