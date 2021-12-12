package nz.co.cportho.richard.celestialnavigationbydirectcomputation;

import datamodel.AlmanacData;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AlmanacController {

    @FXML
    private TextField tfGHA0;
    @FXML
    private TextField tfGHA1;
    @FXML
    private TextField tfDec0;
    @FXML
    private TextField tfDec1;
    @FXML
    private TextField tfDecHem;
    @FXML
    private TextField tfSD;
    @FXML
    private TextField tfMeridianPassage;

    double interpolationFactor;

    public AlmanacData getSunAlmanacData(double interpolationFactor) {
        this.interpolationFactor = interpolationFactor;
        String GHA0 = tfGHA0.getText();
        String GHA1 = tfGHA1.getText();
        String Dec0 = tfDec0.getText();
        String Dec1 = tfDec1.getText();
        String DecHem = tfDecHem.getText();
        String sd = tfSD.getText();
        String meridianPassage = tfMeridianPassage.getText();

        AlmanacData sunAlmanacData = new AlmanacData(GHA0, GHA1, Dec0, Dec1, DecHem, sd, meridianPassage, interpolationFactor);
        return sunAlmanacData;
    }

    public AlmanacData getLatitudeAlmanacData(double interpolationFactor) {
        this.interpolationFactor = interpolationFactor;
        String Dec0 = tfDec0.getText();
        String Dec1 = tfDec1.getText();
        String DecHem = tfDecHem.getText();
        String sd = tfSD.getText();
       // String meridianPassage = tfMeridianPassage.getText();

        AlmanacData latitudeAlmanacData = new AlmanacData(Dec0, Dec1, DecHem, sd, interpolationFactor);
        return latitudeAlmanacData;
    }

}
