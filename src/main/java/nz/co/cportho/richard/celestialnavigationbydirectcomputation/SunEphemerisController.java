package nz.co.cportho.richard.celestialnavigationbydirectcomputation;

import datamodel.SunData;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SunEphemerisController {


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

    public SunData getSunData(){
        String GHA0 = tfGHA0.getText();
        String GHA1 = tfGHA1.getText();
        String Dec0 = tfDec0.getText();
        String Dec1 = tfDec1.getText();
        String DecHem = tfDecHem.getText();
        String SD = tfSD.getText();

        SunData newSunData = new SunData(GHA0, GHA1, Dec0, Dec1, DecHem, SD);
        return newSunData;
    }


}
