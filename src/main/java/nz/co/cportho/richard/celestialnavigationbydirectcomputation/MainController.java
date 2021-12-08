package nz.co.cportho.richard.celestialnavigationbydirectcomputation;

import datamodel.SunData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;

public class MainController {
    @FXML
    BorderPane mainPanel;


    @FXML
    public void showSunLOPCalculation() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Enter Sun data");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("sundialog.fxml"));
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
            SunController sunController = fxmlLoader.getController();
            SunData sunData = sunController.getSunData();

//            Sight sight = new Sight(sunData.getBody, limb, localDate, hour, minute, second, timeZone, clockError, sextantAltitude);
//            DRPosition drPosition = new DRPosition(latitude, latHemisphere, longitude, lonHemisphere);
//            BasicEphemerisData basicEphemerisData = new BasicEphemerisData(sight.getTimeOfSightUTC(), sight.getCelestialBody(),
//                        sight.getInterpolationFactor(), sunData.getGha0(), sunData.getGha1(), sunData.getDec0(), sunData.getDec1(), sunData.getDecHem(),
//                        sunData.getSd());
//            CalculatedAltitudeAndAzimuth calculatedAltitudeAndAzimuth = new CalculatedAltitudeAndAzimuth(basicEphemerisData.getGha(),
//                        basicEphemerisData.getDec(), drPosition.getLatitude(), drPosition.getLongitude());
//            HSextantToHObserved hSextantToHObserved = new HSextantToHObserved(eyeHeight, sight.getCelestialBody(), sight.getLimb(),
//                        indexError, temperature, pressure, basicEphemerisData.getSemiDiameter(),
//                        sight.getSextantAltitude());
//            PositionLine positionLine = new PositionLine(drPosition.getdRLatitude(), drPosition.getdRLongitude(),
//                        hSextantToHObserved.getObservedAltitude(), calculatedAltitudeAndAzimuth.getHC(),
//                        calculatedAltitudeAndAzimuth.getZ());
//            tfCalculationResult.setText(positionLine.getPlotString());
//            }
        }
    }




    @FXML
    public void calcMeridianPassage() {
        System.out.println("not implemented");
    }
//            String body = tfCelestialBody.getText();
//            String limb = String.valueOf(cbLimb.getValue());
//            String bearing = String.valueOf(cbBearing.getValue());
//            LocalDate localDate = dpLocalDate.getValue();
//            int hour = spHour.getValue();
//            int minute = spMinute.getValue();
//            int second = spSecond.getValue();
//            int timeZone = spTimeZone.getValue();
//            int clockError = spClockError.getValue();
//            String sextantAltitude = tfSextantAltitude.getText();
//            double eyeHeight = spEyeHeight.getValue();
//            double indexError = spIndexError.getValue();
//            double temperature = spTemperature.getValue();
//            double pressure = spPressure.getValue();
//            String latitude = tfLatitude.getText();
//            String latHemisphere = tfLatHemisphere.getText();
//            String longitude = tfLongitude.getText();
//            String lonHemisphere = tfLonHemisphere.getText();
//            String meridianPassage = tfMeridianPassage.getText();
//
//            Sight sight = new Sight(body, limb, localDate, meridianPassage, timeZone, sextantAltitude);
//            DRPosition drPosition = new DRPosition(longitude, lonHemisphere);
//            MeridianAltitude meridianAltitude = new MeridianAltitude(sight.getCelestialBody(), sight.getLimb(),
//                    sight.getTimeOfSightLocal(), sight.getTimeZone(), drPosition.getLongitude());
//
//            tfCalculationResult.setText("Meridian Passage " + body + " local time: " + meridianAltitude.getPassageLocal());
//        }



    @FXML
    public void handleExit(){
        Platform.exit();
    }

}