package main;

import java.util.HashSet;

/**
 * Abstrakte Oberklasse aller Neuronen.
 * @author Jens Krüger
 * @author Niklas Bruns
 * @author Marc Seibel
 * @version 1.0
 *
 */
public abstract class Neuron {
    double lastInputValue = 0;
    double lastOutputValue = 0;
    double lastSmallDelta;
    HashSet<Edge> incomingEdges = new HashSet<Edge>();
    HashSet<Edge> outgoingEdges = new HashSet<Edge>();
    int identNumber;

    /**
     * Ein Neuron wird mit einer Identifikationsnummer initialisert, die eindeutig pro Schicht sein muss.
     * @param identNumber
     */
    public Neuron(int identNumber){
        this.identNumber = identNumber;
    }

    public int getIdentNumber() {
        return identNumber;
    }

    public double getLastInputValue() {
        return lastInputValue;
    }

    public double getLastOutputValue() {
        return lastOutputValue;
    }

    /**
     * Setzt den letzten Input zurück.
     */
    public abstract void resetInput();

    /**
     * Empängt einen Wert von der vorherigen Kante oder von NetworkController
     * @param input Der eingegebene Wert.
     */
    public abstract void receiveInput(double input);

    /**
     * Berechnet den Output-wert aus dem Input-Wert
     * @return Der errechnete Output-wert
     */
    public abstract double calculateOutput();

    public double getLastSmallDelta(){
        return lastSmallDelta;
    }

    /**
     * Berechnet den Output-wert und schicht diesen an alle nächsten Kanten.
     */
    public void sendOutputToNextEdge(){
        calculateOutput();
        for(Edge outgoingEdge: outgoingEdges){
            outgoingEdge.receiveInput(lastOutputValue);
        }
    }

    /**
     * Fügt eine neue ausgehende Kante hinzu.
     * @param edge Die hinzuzufügende Kante.
     */
    public void addOutgoingEdge(Edge edge){
        outgoingEdges.add(edge);
    }

    /**
     * Fügt eine neue eingehende Kante hinzu.
     * @param edge Die hinzuzufügende Kante.
     */
    public void addIncomingEdge(Edge edge){
        incomingEdges.add(edge);
    }


    public HashSet<Edge> getIncomingEdges() {
        return incomingEdges;
    }

    public HashSet<Edge> getOutgoingEdges() {
        return outgoingEdges;
    }
}
