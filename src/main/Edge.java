package main;

public class Edge {
    double weight;
    int beforeNeuronid;
    int behindNeuronid;

    Edge(double weight, int beforeNeuronid, int behindNeuronid)
    {
        this.weight=weight;
        this.beforeNeuronid=beforeNeuronid;
        this.behindNeuronid=behindNeuronid;

    }



    public void giveToNextNeuron()
    {  double newinputvalue; //Dieser Wert wird an das nächste Neuron übergeben.

        newinputvalue= Neuron.beforeNeuronid.getOutputvalue() * weight;
        Neuron.behindNeuronid.setInputvalue(newinputvalue);
    }




}
