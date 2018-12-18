package main;

import helper.MathHelper;

public class OutputNeuron extends Neuron{

    /**
     * Ein Neuron wird mit einer Identifikationsnummer initialisert, die eindeutig pro Schicht sein muss.
     * @param identNumber
     */
    public OutputNeuron(int identNumber) {
        super(identNumber);
    }

    @Override
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
     * @param targetWeight Das Zielgewicht, dass dieses OutputNeuron haben müsste (0 oder 1).
     */
    public void modWeight(Double targetWeight) {
        Double smallDelta = targetWeight - calculateOutput();
        lastSmallDelta = smallDelta;
        Double ableitung = MathHelper.getSigmoidApprox(lastInputValue) * (1 - MathHelper.getSigmoidApprox(lastInputValue));
        for(Edge edge: incomingEdges){
            edge.modWeight(smallDelta, ableitung);
        }
    }
}