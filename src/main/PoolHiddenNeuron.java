package main;



public class PoolHiddenNeuron extends HiddenNeuron {

    double maxinput = 0;

            //Konstruktor
    PoolHiddenNeuron(int id){
        super(id);
    }

    public void receiveMaxOutputFromPreviousEdge(Double prevOutput) {
        if (prevOutput >= maxinput) {
            maxinput = prevOutput;
        }

    }
}
