package nz.co.cportho.richard.celestialnavigationbydirectcomputation;

import datamodel.Sight;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class SightController {
    @FXML
    private ChoiceBox<FXCollections> cbBody;
    @FXML
    private ChoiceBox<FXCollections> cbLimb;
    @FXML
    private ChoiceBox<FXCollections> cbBearing;
    @FXML
    private DatePicker dpLocalDate;
    @FXML
    private Spinner<Integer> spHour;
    @FXML
    private Spinner<Integer> spMinute;
    @FXML
    private Spinner<Integer> spSecond;
    @FXML
    private Spinner<Integer> spTimeZone;
    @FXML
    private Spinner<Integer> spClockError;
    @FXML
    private TextField tfSextantAltitude;
    @FXML
    private Spinner<Double> spEyeHeight;
    @FXML
    private Spinner<Double> spIndexError;
    @FXML
    private Spinner<Double> spTemperature;
    @FXML
    private Spinner<Double> spPressure;


    public Sight getSightData() {
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


            Sight sightData = new Sight(body, limb, bearing, localDate, hour, minute, second, timeZone,
                    clockError, sextantAltitude, eyeHeight, indexError, temperature, pressure);

            return sightData;
    }

}
