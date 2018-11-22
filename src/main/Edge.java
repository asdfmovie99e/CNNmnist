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



}
