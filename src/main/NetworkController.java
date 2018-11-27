package main;

public class NetworkController {
    //das netz ist so gedacht, dass die erste schicht eine normle input schicht ist.
    // die zweite ist eine convolutional schicht und die 3. maxpooling
    // die 4. wieder conv dann wieder maxpool und an 6. stelle output
    private static final int ANZAHL_INPUT_NEURONEN = 748;
    private static final int ANZAHL_HIDDEN_NEURONEN_ONE = 784; //conv mit 3x3 filter
    private static final int ANZAHL_HIDDEN_NEURONEN_TWO = 196; // max pool 2x2 wird zu 1
    private static final int ANZAHL_HIDDEN_NEURONEN_THREE = 196; //conv mit 3x3 filter
    private static final int ANZAHL_HIDDEN_NEURONEN_FOUR = 49; // max pool 2x2 wird zu 1
    private static final int ANZAHL_HIDDEN_NEURONEN_FIVE = 50;// voll vermaschte hidden schicht
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
        createSecondEdgeLayer();
        createThirdLayer();
    }

    private static void sendForward(){

    }

    private static void createFirstEdgeLayer(){
        Edge currentEdge = null;
        int neuronOneRoot = (int) Math.pow(ANZAHL_HIDDEN_NEURONEN_ONE, 0.5d);
        for(int i = 0; i < ANZAHL_HIDDEN_NEURONEN_ONE; i++){
            for(int i1 = -1; i1<= 1; i1++){
                for(int i2 = -1; i2 <= 1; i2++){
                    int outgoingIdent = i + i1 - (neuronOneRoot * i2);
                    if(0 <= outgoingIdent && outgoingIdent <= ANZAHL_INPUT_NEURONEN) {
                        currentEdge = new Edge(edgeCounter, inputNeurons[outgoingIdent], hiddenNeuronsOne[i]);
                        inputNeurons[outgoingIdent].addOutgoingEdge(currentEdge);
                        hiddenNeuronsOne[i].addIncomingEdge(currentEdge);
                        edgeCounter++;
                    }
                }
            }
        }
    }

    private static void createSecondEdgeLayer(){
        Edge currentEdge = null;
        int neuronTwoRoot = (int) Math.pow(ANZAHL_HIDDEN_NEURONEN_TWO, 0.5d);
        for(int i = 0; i < ANZAHL_HIDDEN_NEURONEN_TWO; i++){
            for(int i1 = 0; i1 <= 1; i++){
                for(int i2 = 0; i2 <= 1; i++){
                    int outgoingIdent = i1 +i2 * neuronTwoRoot + (i % (neuronTwoRoot/2)) * 2 + (i - (i % (neuronTwoRoot/2))) * 4;//formel nicht anpacken. werde ich nie wieder verstehen
                    if(0 <= outgoingIdent && outgoingIdent <= ANZAHL_HIDDEN_NEURONEN_ONE){
                        currentEdge = new Edge(edgeCounter, hiddenNeuronsOne[outgoingIdent], hiddenNeuronsTwo[i]);
                        hiddenNeuronsOne[outgoingIdent].addOutgoingEdge(currentEdge);
                        hiddenNeuronsTwo[i].addIncomingEdge(currentEdge);
                        edgeCounter++;
                    }
                }
            }
        }
    }

    private static void createThirdLayer(){
        Edge currentEdge = null;
        int neuronThreeRoot = (int) Math.pow(ANZAHL_HIDDEN_NEURONEN_THREE, 0.5d);
        for(int i = 0; i < ANZAHL_HIDDEN_NEURONEN_THREE; i++){
            for(int i1 = -1; i1<= 1; i1++){
                for(int i2 = -1; i2 <= 1; i2++){
                    int outgoingIdent = i + i1 - (neuronThreeRoot * i2);
                    if(0 <= outgoingIdent && outgoingIdent <= ANZAHL_HIDDEN_NEURONEN_TWO) {
                        currentEdge = new Edge(edgeCounter, hiddenNeuronsTwo[outgoingIdent], hiddenNeuronsThree[i]);
                        hiddenNeuronsTwo[outgoingIdent].addOutgoingEdge(currentEdge);
                        hiddenNeuronsThree[i].addIncomingEdge(currentEdge);
                        edgeCounter++;
                    }
                }
            }
        }

    }

}
