package main;

import helper.MathHelper;

public class HiddenNeuron extends Neuron {

    /**
     * Ein Neuron wird mit einer Identifikationsnummer initialisert, die eindeutig pro Schicht sein muss.
     * @param identNumber
     */
    public HiddenNeuron(int identNumber) {
        super(identNumber);
    }

    @Override
    /**
     * @{inheritDod}
     */
    public void resetInput() {
        lastInputValue = 0;
    }

    @Override
    /**
     * Empfängt Werte von der eingehenden Kante und summiert diese.
     * @param input Der eingehende Wert.
     */
    public void receiveInput(double input) {
        lastInputValue += input;
    }

    @Override
    /**
     * @{inheritDod}
     */
    public double calculateOutput() {
        lastOutputValue = MathHelper.getSigmoidApprox(lastInputValue);
        return lastOutputValue;
    }

    /**
     * Berechnet alle Werte, die die eingehenden Kanten brauchen um per Backpropagation ihr Gewicht zu verändern.
     * Ruft dann die entsprechenden Methoden in den eingehenden Kanten auf.
     */
    public void modWeight() {
        double smallDelta = 0;
       for(Edge edge: outgoingEdges){
           smallDelta += edge.getCurrentWeight() * edge.getNextNeuron().getLastSmallDelta();
       }



        lastSmallDelta = smallDelta;
        Double ableitung = MathHelper.getSigmoidApprox(lastInputValue) * (1 - MathHelper.getSigmoidApprox(lastInputValue));
        for (Edge edge : incomingEdges) {
            edge.modWeight(smallDelta, ableitung);
        }
    }
}
