package main;

public class NetoworkController {
    //das netz ist so gedacht, dass die erste schicht eine normle input schicht ist.
    // die zweite ist eine convolutional schicht und die 3. maxpooling
    // die 4. wieder conv dann wieder maxpool und an 6. stelle output
    private static final int ANZAHL_INPUT_NEURONEN = 748;
    private static final int ANZAHL_HIDDEN_NEURONEN_ONE = 784;
    private static final int ANZAHL_HIDDEN_NEURONEN_TWO = 1;
    private static final int ANZAHL_HIDDEN_NEURONEN_THREE = 1;
    private static final int ANZAHL_HIDDEN_NEURONEN_FOUR = 1;
    private static final int ANZAHL_HIDDEN_NEURONEN_FIVE = 1;// eigentlich variabel. muss mal mit rumgespielt werden
    private static final int ANZAHL_OUTPUT_NEURONEN = 10;
    // es wird von einer 4x4 convolution schicht und einer 2x2 max pooling schicht ausgegangen
    private static final int ANZAHL_EGDES =    123456789; // muss noch manuel eingegeben werden
    private static final int ANZAHL_BILDER = 59000;
    private static final int PIXEL_X = 28;
    private static final int PIXEL_Y = 28;
    private static InputNeuron[] inputNeurons = new InputNeuron[ANZAHL_INPUT_NEURONEN];
    private static HiddenNeuron[] hiddenNeuronsOne= new HiddenNeuron[ANZAHL_HIDDEN_NEURONEN_ONE];
    private static HiddenNeuron[] hiddenNeuronsTwo= new HiddenNeuron[ANZAHL_HIDDEN_NEURONEN_TWO];
    private static HiddenNeuron[] hiddenNeuronsThree= new HiddenNeuron[ANZAHL_HIDDEN_NEURONEN_THREE];
    private static HiddenNeuron[] hiddenNeuronsFour= new HiddenNeuron[ANZAHL_HIDDEN_NEURONEN_FOUR];
    private static HiddenNeuron[] hiddenNeuronsFive= new HiddenNeuron[ANZAHL_HIDDEN_NEURONEN_FIVE];
    private static OutputNeuron[] outputNeurons = new OutputNeuron[ANZAHL_OUTPUT_NEURONEN];
    private static int edgeCounter = 0;

    public static void startLearning(){
        createNeuronNetwork();
        sendForward();
    }

    private static void createNeuronNetwork(){
        for(int i = 0; i < ANZAHL_INPUT_NEURONEN; i++){
            int xCoord = i % 28;
            int yCoord = (i - xCoord) / 28;
            inputNeurons[i]  = new InputNeuron(i);
        }
        for(int i = 0; i < ANZAHL_HIDDEN_NEURONEN_ONE; i++){
            hiddenNeuronsOne[i] = new HiddenNeuron(i);
        }
        for(int i = 0; i < ANZAHL_HIDDEN_NEURONEN_TWO; i++){
            hiddenNeuronsTwo[i] = new PoolHiddenNeuron(i);
        }
        for(int i = 0; i < ANZAHL_HIDDEN_NEURONEN_THREE; i++){
            hiddenNeuronsThree[i] = new HiddenNeuron(i);
        }
        for(int i = 0; i < ANZAHL_HIDDEN_NEURONEN_FOUR; i++){
            hiddenNeuronsFour[i] = new PoolHiddenNeuron(i);
        }
        for(int i = 0; i < ANZAHL_HIDDEN_NEURONEN_FIVE; i++){
            hiddenNeuronsFive[i] = new HiddenNeuron(i);
        }
        for(int i = 0; i < ANZAHL_OUTPUT_NEURONEN; i++){
            outputNeurons[i] = new OutputNeuron(i);
        }
        createFirstEdgeLayer();
    }

    private static void sendForward(){

    }

    private static void createFirstEdgeLayer(){
        Edge currentEdge = null;
        for(int i = 0; i < ANZAHL_HIDDEN_NEURONEN_ONE; i++){
            int xCoord = i % 28;
            int yCoord = (i - xCoord) / 28;
            for(int i1 = -1; i1<= 1; i1++){
                for(int i2 = -1; i2 <= 1; i2++){
                    int inputIdent = i + i1 - (28 * i2);
                    if(0 <= inputIdent && inputIdent <= ANZAHL_INPUT_NEURONEN)
                    currentEdge = new Edge(edgeCounter,inputNeurons[inputIdent],hiddenNeuronsOne[i]);
                    inputNeurons[i].addOutgoingEdge(i,currentEdge);
                    hiddenNeuronsOne[i].addIncomingEdge(i,currentEdge);
                    edgeCounter++;
                }
            }
        }
    }
}
