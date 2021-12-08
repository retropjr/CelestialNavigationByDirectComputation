package nz.co.cportho.richard.celestialnavigationbydirectcomputation;

import datamodel.SunData;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class SunController {
    @FXML
    ChoiceBox<FXCollections> cbBody;
    @FXML
    ChoiceBox<FXCollections> cbLimb;
    @FXML
    ChoiceBox<FXCollections> cbBearing;
    @FXML
    DatePicker dpLocalDate;
    @FXML
    Spinner<Integer> spHour;
    @FXML
    Spinner<Integer> spMinute;
    @FXML
    Spinner<Integer> spSecond;
    @FXML
    Spinner<Integer> spTimeZone;
    @FXML
    Spinner<Integer> spClockError;
    @FXML
    TextField tfSextantAltitude;
    @FXML
    Spinner<Double> spEyeHeight;
    @FXML
    Spinner<Double> spIndexError;
    @FXML
    Spinner<Double> spTemperature;
    @FXML
    Spinner<Double> spPressure;
    @FXML
    TextField tfLatitude;
    @FXML
    TextField tfLatHemisphere;
    @FXML
    TextField tfLongitude;
    @FXML
    TextField tfLonHemisphere;
    @FXML
    TextField tfMeridianPassage;
    @FXML
    TextField tfUTCSight;
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
    TextField tfCalculationResult;


    public SunData getSunData() {
        String body = String.valueOf(cbBody.getValue());
        String limb = String.valueOf(cbLimb.getValue());
        String bearing = String.valueOf(cbBearing.getValue());
        LocalDate localDate = dpLocalDate.getValue();
        int hour = spHour.getValue();
        int minute = spMinute.getValue();
        int second = spSecond.getValue();
        int timeZone = spTimeZone.getValue();
        int clockError = spClockError.getValue();
        String sextantAltitude = tfSextantAltitude.getText();
        double eyeHeight = spEyeHeight.getValue();
        double indexError = spIndexError.getValue();
        double temperature = spTemperature.getValue();
        double pressure = spPressure.getValue();
        String latitude = tfLatitude.getText();
        String latHemisphere = tfLatHemisphere.getText();
        String longitude = tfLongitude.getText();
        String lonHemisphere = tfLonHemisphere.getText();
        String GHA0 = tfGHA0.getText();
        String GHA1 = tfGHA1.getText();
        String Dec0 = tfDec0.getText();
        String Dec1 = tfDec1.getText();
        String DecHem = tfDecHem.getText();
        String SD = tfSD.getText();

        SunData sunData = new SunData(body);
        return sunData;
    }
}
