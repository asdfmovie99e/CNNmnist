package gui;



import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class WeightData {

    private final SimpleIntegerProperty nr;
    private final SimpleDoubleProperty succesrate;

    public WeightData (Integer nr, Double succesrate)
    {
     this.nr = new SimpleIntegerProperty(nr);
     this.succesrate = new SimpleDoubleProperty(succesrate);
    }



}
