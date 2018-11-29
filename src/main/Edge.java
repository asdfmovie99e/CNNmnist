package main;


public class Edge {
    int id;
    double weight;
    Neuron previousNeuron;
    Neuron nextNeuron;

    Edge(int id,Neuron previousNeuron, Neuron nextNeuron)
    {   this.id=id;
        this.weight=Math.random();
        this.previousNeuron=previousNeuron;
        this.nextNeuron=nextNeuron;

    }



    public void giveToNextNeuron()
    {  double newInputValue; //Dieser Wert wird an das nächste Neuron übergeben.

        newInputValue= previousNeuron.getOutputvalue() * weight;
        if(nextNeuron instanceof PoolHiddenNeuron){
            ((PoolHiddenNeuron)nextNeuron).receiveOutputFromPreviousEdge(newInputValue, this);
        } else {
            nextNeuron.receiveOutputFromPreviousEdge(newInputValue);
        }
    }


    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
