package main;



public class PoolHiddenNeuron extends HiddenNeuron {

    double maxinput = Double.MIN_VALUE;

            //Konstruktor
    PoolHiddenNeuron(int id){
        super(id);
    }

    public void receiveMaxOutputFromPreviousEdge(Double prevOutput) {
        if (prevOutput >= maxinput) {
            maxinput = prevOutput;
        }

    }

    public void resetMaxInput(){
        maxinput = Double.MIN_VALUE;
    }
}
