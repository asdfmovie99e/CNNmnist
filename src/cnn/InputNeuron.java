package cnn;

/**
 * Ein Neuron, welches keine Vorgängerneuron hat und direkt von der NetworkController Klasse angesprochen wird.
 * @author Jens Krüger
 * @author Niklas Bruns
 * @author Marc Seibel
 * @version 1.0
 *
 */
public class InputNeuron extends Neuron {

    /**
     * Ein Neuron wird mit einer Identifikationsnummer initialisert, die eindeutig pro Schicht sein muss.
     * @param identNumber
     */
    public InputNeuron(int identNumber) {
        super(identNumber);
    }


    @Override
    /**
     * @{inheritDod}
     */
    public void resetInput()  {
       //nicht nötig
    }

    @Override
    /**
     * @{inheritDod}
     */
    public void receiveInput(double input) {
        this.lastInputValue = input;
    }

    @Override
    /**
     * @{inheritDod}
     */
    public double calculateOutput() {
        lastOutputValue =  ((Double)lastInputValue) / 255d;
        return lastOutputValue;
    }
}
