package nz.co.cportho.richard.celestialnavigationbydirectcomputation;

import datamodel.SunData;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class MainController {
    @FXML
    BorderPane mainPanel;
    @FXML
    ChoiceBox<FXCollections> cbCelestialBody;
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
    TextField tfCalculationResult;
    @FXML
    TextField tfMeridianPassage;

    @FXML
    public void showGetEphemerisDataDialog(){
        String body = String.valueOf(cbCelestialBody.getValue());
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


        if (body.equals("Sun")) {
            Sight sight = new Sight(body, limb, localDate, hour, minute, second, timeZone, clockError, sextantAltitude);
            DRPosition drPosition = new DRPosition(latitude, latHemisphere, longitude, lonHemisphere);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(mainPanel.getScene().getWindow());
            dialog.setTitle("Enter Nautical Almanac Data");
            dialog.setHeaderText("UTC of sight: " + sight.getTimeOfSightUTC());
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("sunephemerisdatadialog.fxml"));
            try {
                dialog.getDialogPane().setContent(fxmlLoader.load());
            } catch (IOException e) {
                System.out.println("Couldn't load the dialog.");
                e.printStackTrace();
                return;
            }

            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                SunEphemerisController sunEphemerisController = fxmlLoader.getController();
                SunData sunData = sunEphemerisController.getSunData();

                BasicEphemerisData basicEphemerisData = new BasicEphemerisData(sight.getTimeOfSightUTC(), sight.getCelestialBody(),
                        sight.getInterpolationFactor(), sunData.getGha0(), sunData.getGha1(), sunData.getDec0(), sunData.getDec1(), sunData.getDecHem(),
                        sunData.getSd());

                CalculatedAltitudeAndAzimuth calculatedAltitudeAndAzimuth = new CalculatedAltitudeAndAzimuth(basicEphemerisData.getGha(),
                        basicEphemerisData.getDec(), drPosition.getLatitude(), drPosition.getLongitude());

                HSextantToHObserved hSextantToHObserved = new HSextantToHObserved(eyeHeight, sight.getCelestialBody(), sight.getLimb(),
                        indexError, temperature, pressure, basicEphemerisData.getSemiDiameter(),
                        sight.getSextantAltitude());

                PositionLine positionLine = new PositionLine(drPosition.getdRLatitude(), drPosition.getdRLongitude(),
                        hSextantToHObserved.getObservedAltitude(), calculatedAltitudeAndAzimuth.getHC(),
                        calculatedAltitudeAndAzimuth.getZ());

                tfCalculationResult.setText(positionLine.getPlotString());
            }
        } else {
            tfCalculationResult.setText("Haven't implemented that yet!");
        }
    }

    @FXML
    public void calcMeridianPassage(){
        String body = String.valueOf(cbCelestialBody.getValue());
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
        String meridianPassage = tfMeridianPassage.getText();

        Sight sight = new Sight(body, limb, localDate, meridianPassage, timeZone, sextantAltitude);
        DRPosition drPosition = new DRPosition(longitude, lonHemisphere);
        MeridianAltitude meridianAltitude = new MeridianAltitude(sight.getCelestialBody(), sight.getLimb(),
                sight.getTimeOfSightLocal(), sight.getTimeZone(), drPosition.getLongitude());

        tfCalculationResult.setText("Meridian Passage " + body + " local time: " + meridianAltitude.getPassageLocal());
    }

    @FXML
    public void handleExit(){
        Platform.exit();
    }

}