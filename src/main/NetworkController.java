package main;

import helper.DatabaseManager;
import helper.LearnObserver;
import helper.PictureCoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;


/**
 * Steuert das gesamte Neuronale Netzwerk.
 * @
 * @author Jens Krüger
 * @author Niklas Bruns
 * @author Marc Seibel
 * @version 1.0
 *
 */
public class NetworkController {

    private static int anzahlInputNeurons = 28 * 28;
    private static int anzahlHiddenOne = 784;
    private static int anzahlHiddenTwo = 105;
    private static int anzahlOutputNeuron = 10;
    public static double epsilon = 0.032d;
    private static final int ANZAHL_BILDER = 60000;
    private static ArrayList<InputNeuron> inputNeurons = new ArrayList<InputNeuron>();
    private static ArrayList<HiddenNeuron> hiddenNeuronsOne = new ArrayList<HiddenNeuron>();
    private static ArrayList<HiddenNeuron> hiddenNeuronsTwo = new ArrayList<HiddenNeuron>();
    private static ArrayList<OutputNeuron> outputNeurons = new ArrayList<OutputNeuron>();


    /**
     * Diese Methode steuert den Lernprozess.
     */
    public static void startLearning() {
        instantiateNeurons();
        instantiateEdges();
        for(int gesamtPictureNumber = 0; gesamtPictureNumber < ANZAHL_BILDER ; gesamtPictureNumber++){
            int pictureNumber = gesamtPictureNumber % ANZAHL_BILDER;
        sendForward(PictureCoder.get2DPictureArray(pictureNumber));
        LearnObserver.watchResults(PictureCoder.getLabel(pictureNumber), outputNeurons);
        doBackPropagation(PictureCoder.getLabel(pictureNumber));
        }
        saveCurrentNetwork();
    }

    /**
     * Diese Methode steuert den Lernprozess.
     * @param pixelArray Ein 2 Dimensionales array, in dem die einzelnen Pixel mit Werten zwischen 0 und 255 gespeichert sind
     */
    private static void sendForward(int[][] pixelArray) {
        resetAllNeurons();
        for(InputNeuron inputNeuron: inputNeurons){
            int x = inputNeuron.getIdentNumber() % 28;
            int y = (inputNeuron.getIdentNumber() - inputNeuron.getIdentNumber() % 28) / 28;
            int pixelValue = pixelArray[x][y];
            inputNeuron.receiveInput(pixelValue);
            inputNeuron.sendOutputToNextEdge();
        }
        for(HiddenNeuron hiddenNeuron : hiddenNeuronsOne){
            hiddenNeuron.sendOutputToNextEdge();
        }
        for(HiddenNeuron hiddenNeuron : hiddenNeuronsTwo){
            hiddenNeuron.sendOutputToNextEdge();
        }
        for(OutputNeuron outputNeuron: outputNeurons){
            outputNeuron.calculateOutput();
        }
    }

    /**
     * Setzt in allen Neuronen alle Werte zurück.
     */
    private static void resetAllNeurons() {
        for(HiddenNeuron hiddenNeuron : hiddenNeuronsOne){
            hiddenNeuron.resetInput();
        }
        for(HiddenNeuron hiddenNeuron : hiddenNeuronsTwo){
            hiddenNeuron.resetInput();
        }
        for(OutputNeuron outputNeuron: outputNeurons){
            outputNeuron.resetInput();
        }

    }

    /**
     * Erstellt die Objekte der Klasse Edge, die die Neuronen miteinander verbinden.
     */
    private static void instantiateEdges() {
        if(inputNeurons.get(0).outgoingEdges.size()> 0) return; // bricht hier ab falls es schon edges gibt
        HiddenNeuron[] hiddenNeuronsOneArray = hiddenNeuronsOne.toArray(new HiddenNeuron[hiddenNeuronsOne.size()]);

        for(InputNeuron inputNeuron: inputNeurons){
            for(int x = -1; x <= 1; x++){
                for(int y = -1; y <= 1; y++){
                    int targetNr = inputNeuron.getIdentNumber() + x + y * 28;
                    if(targetNr < 0 || targetNr >= 28 * 28 || (targetNr % 28 == 0 && x == 1) || (targetNr % 28 == 27 && x== -1)){
                        continue;
                    }
                    connectNeurons(inputNeuron, hiddenNeuronsOneArray[targetNr]);
                }
            }
        }

        for(HiddenNeuron hiddenNeuron1 : hiddenNeuronsOne){
            for(HiddenNeuron hiddenNeuron2: hiddenNeuronsTwo){
                connectNeurons(hiddenNeuron1, hiddenNeuron2);
            }
        }
        for(HiddenNeuron hiddenNeuron : hiddenNeuronsTwo){
            for(OutputNeuron outputNeuron: outputNeurons){
                connectNeurons(hiddenNeuron, outputNeuron);
            }
        }

    }

    /**
     * Erstellt die Neuronen schicht für Schicht.
     */
    private static void instantiateNeurons() {
        if(inputNeurons.size() > 0) return; // falls die neuronen schon existerien  passiert hier nichts
        for(int i = 0; i < anzahlInputNeurons; i++){
            InputNeuron inputNeuron = new InputNeuron(i);
            inputNeurons.add(inputNeuron);
        }
        for(int i = 0; i < anzahlHiddenOne; i++){
            HiddenNeuron hiddenNeuron = new HiddenNeuron(i);
            hiddenNeuronsOne.add(hiddenNeuron);
        }
        for(int i = 0; i < anzahlHiddenTwo; i++){
            HiddenNeuron hiddenNeuron = new HiddenNeuron(i);
            hiddenNeuronsTwo.add(hiddenNeuron);
        }
        for(int i = 0; i < anzahlOutputNeuron; i++){
            OutputNeuron outputNeuron = new OutputNeuron(i);
            outputNeurons.add(outputNeuron);
        }
    }

    /**
     * Verbindet 2 Neuronen durch eine Edge miteinander. Diese Edge wird hier neu erstellt.
     * @param previousNeuron Das Neuron, von dem die Verbindung ausgeht.
     * @param nextNeuron Das Neuron, an welches die Verbindung gerichtet ist.
     * @return Die in dieser Methode erstellte Edge
     */
    private static Edge connectNeurons(Neuron previousNeuron, Neuron nextNeuron){
       Edge edge = new Edge(previousNeuron,nextNeuron);
       previousNeuron.addOutgoingEdge(edge);
       nextNeuron.addIncomingEdge(edge);
       return edge;
    }

    /**
     * Korrigiert die Gewichte der Edges auf Basis der erratenen ZAhl und der Zahl die richtig gewesen wäre.
     * @param label Die Zahl, die richtig gewesen wäre.
     */
    private static void doBackPropagation(int label){
        //es werden immer funktionen in den neuronen hinter den edges aufgerufen die angepasst werden
        for(OutputNeuron outputNeuron: outputNeurons){
            if(label == outputNeuron.getIdentNumber()) {
                outputNeuron.modWeight(1d);
            } else{
                outputNeuron.modWeight(0d);
            }
        }
        for(HiddenNeuron hiddenNeuron: hiddenNeuronsTwo) {
            hiddenNeuron.modWeight();
        }
        for(HiddenNeuron hiddenNeuron: hiddenNeuronsOne){
            hiddenNeuron.modWeight();

        }
    }

    /**
     * Speichert den momentanen Zustand des Netzwerkes in der Datenbank ab.
     */
    private static void saveCurrentNetwork(){
        //Freie Save Nummer finden
        Integer[] occupiedSaveNumbers = DatabaseManager.getAllSaveNumbers();
        Integer saveNr = 0;
        while(Arrays.asList(occupiedSaveNumbers).contains(saveNr)){
            saveNr++;
        }
        //in den Haupttable Schreiben
        DatabaseManager.addMainTableEntry(saveNr, anzahlInputNeurons, anzahlHiddenOne, anzahlHiddenTwo,
                                            anzahlOutputNeuron,LearnObserver.getSuccesRate());
        Integer edgeNumber = 0;
        for(InputNeuron inputNeuron:inputNeurons){
            HashSet<Edge> outgoingEdges = inputNeuron.getOutgoingEdges();
            for(Edge edge: outgoingEdges){
                DatabaseManager.addEdge(saveNr, 0,edgeNumber,edge.getPreviousNeuron().getIdentNumber(), edge.getNextNeuron().getIdentNumber(), edge.getCurrentWeight());
                edgeNumber++;
            }
        }
        edgeNumber = 0;
        for(HiddenNeuron hiddenNeuronOne: hiddenNeuronsOne){
            HashSet<Edge> outgoingEdges = hiddenNeuronOne.getOutgoingEdges();
            for(Edge edge: outgoingEdges){
                DatabaseManager.addEdge(saveNr,1,edgeNumber,edge.getPreviousNeuron().getIdentNumber(), edge.getNextNeuron().getIdentNumber(), edge.getCurrentWeight());
                edgeNumber++;
            }
        }
        edgeNumber = 0;
        for(HiddenNeuron hiddenNeuronTwo: hiddenNeuronsTwo){
            HashSet<Edge> outgoingEdges = hiddenNeuronTwo.getOutgoingEdges();
            for(Edge edge: outgoingEdges){
                DatabaseManager.addEdge(saveNr,2,edgeNumber,edge.getPreviousNeuron().getIdentNumber(), edge.getNextNeuron().getIdentNumber(), edge.getCurrentWeight());
                edgeNumber++;
            }
        }


    DatabaseManager.flushSaveCommand();
    }

    /**
     * Lädt einen alten Zustand des Netzwerkes aus der Datenbank.
     * @param saveNr Die Nummber des Sepciherstandes, der geladen werden soll.
     */
    public static void loadDataFromDb (Integer saveNr)
    {
       Object[] obArray = DatabaseManager.getMainTableEntry(saveNr);
        inputNeurons = new ArrayList<InputNeuron>();
        hiddenNeuronsOne = new ArrayList<HiddenNeuron>();
        hiddenNeuronsTwo = new ArrayList<HiddenNeuron>();
        outputNeurons = new ArrayList<OutputNeuron>();
       anzahlInputNeurons = (Integer) obArray[2];
       anzahlHiddenOne = (Integer) obArray[3];
       anzahlHiddenTwo = (Integer) obArray[4];
       anzahlOutputNeuron = (Integer) obArray[8];
        for(int i = 0; i < anzahlInputNeurons; i++){
            InputNeuron inputNeuron = new InputNeuron(i);
            inputNeurons.add(inputNeuron);
        }
        for(int i = 0; i < anzahlHiddenOne; i++){
            HiddenNeuron hiddenNeuron = new HiddenNeuron(i);
            hiddenNeuronsOne.add(hiddenNeuron);
        }
        for(int i = 0; i < anzahlHiddenTwo; i++){
            HiddenNeuron hiddenNeuron = new HiddenNeuron(i);
            hiddenNeuronsTwo.add(hiddenNeuron);
        }
        for(int i = 0; i < anzahlOutputNeuron; i++){
            OutputNeuron outputNeuron = new OutputNeuron(i);
            outputNeurons.add(outputNeuron);
        }
        Integer layerZeroEdgeCount = DatabaseManager.getEgeCountInLayer(saveNr, 0);
        Integer layerOneEdgeCount = DatabaseManager.getEgeCountInLayer(saveNr, 1);
        Integer layerTwoEdgeCount = DatabaseManager.getEgeCountInLayer(saveNr, 2);

        for(int edgeNumber = 0; edgeNumber < layerZeroEdgeCount; edgeNumber++){
            Object[] edgeValues = DatabaseManager.getEdgeEntry(saveNr,0, edgeNumber);
            Neuron preNeuron = inputNeurons.get((Integer) edgeValues[0]);
            Neuron nextNeuron = hiddenNeuronsOne.get((Integer) edgeValues[1]);
            Double weight = (Double) edgeValues[2];
            Edge edge = connectNeurons(preNeuron,nextNeuron);
            edge.setNewWeight(weight);
        }

        for(int edgeNumber = 0; edgeNumber < layerOneEdgeCount; edgeNumber++){
            Object[] edgeValues = DatabaseManager.getEdgeEntry(saveNr,1, edgeNumber);
            Neuron preNeuron = hiddenNeuronsOne.get((Integer) edgeValues[0]);
            Neuron nextNeuron = hiddenNeuronsTwo.get((Integer) edgeValues[1]);
            Double weight = (Double) edgeValues[2];
            Edge edge = connectNeurons(preNeuron,nextNeuron);
            edge.setNewWeight(weight);
        }

        for(int edgeNumber = 0; edgeNumber < layerTwoEdgeCount; edgeNumber++){
            Object[] edgeValues = DatabaseManager.getEdgeEntry(saveNr,2, edgeNumber);
            Neuron preNeuron = hiddenNeuronsTwo.get((Integer) edgeValues[0]);
            Neuron nextNeuron = outputNeurons.get((Integer) edgeValues[1]);
            Double weight = (Double) edgeValues[2];
            Edge edge = connectNeurons(preNeuron,nextNeuron);
            edge.setNewWeight(weight);
        }


    }

    /**
     * Sendet das in der GUI gezeichnete Bild durch das Netzwerk.
     * @param pixelArray Ein 2 Dimensionales array, in dem die einzelnen Pixel mit Werten zwischen 0 und 255 gespeichert sind
     * @return Ein Array in dem die ergebniswerte zu den einzelnen Zahlen stehen. z.B. resultArray[4] == 0.8 bedeutet, dass die gezeichnete Zahl sehr wahrscheinlich als 4 erkannt wurde
     */
    public static double[] sendDrawnImageToNeurons(int[][] pixelArray){
        resetAllNeurons();
        sendForward(pixelArray);
        double[] resultArray = new double[10];
        OutputNeuron biggestNeuron = null;
        for(int i = 0 ; i < 10; i++){
            resultArray[i] = outputNeurons.get(i).getLastOutputValue();
            if(biggestNeuron == null || outputNeurons.get(i).getLastOutputValue() > biggestNeuron.getLastOutputValue()){
                biggestNeuron = outputNeurons.get(i);
            }
        }

        return resultArray;
    }
}
