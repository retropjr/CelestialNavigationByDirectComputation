package nz.co.cportho.richard.celestialnavigationbydirectcomputation;

import datamodel.DRPosition;
import datamodel.SunAlmanacData;
import datamodel.SunSight;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;

public class MainController {

    @FXML
    BorderPane mainPanel;
    @FXML
    TextField tfResult;

    SunSight sunSight;
    DRPosition drPosition;
    SunAlmanacData sunAlmanacData;

    @FXML
    public void showSunLOPCalculation() {
        //** Open a dialog to get sun sight data from user and create the SunSight instance **
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Enter Sun data");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("sunSight.fxml"));
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
            sunSight = sunController.getSunSightData();

            tfResult.setText(sunSight.getTimeOfSightUTC());
        }

        //** Open a dialog to get DR position from user and create the DRPosition instance **
        dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Enter DR Position at " +  sunSight.getTimeOfSightUTC() + " UTC.");
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("drPosition.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog.");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result2 = dialog.showAndWait();

        if (result2.isPresent() && result2.get() == ButtonType.OK) {
            DRController DRController = fxmlLoader.getController();
            drPosition = DRController.getDRPositionData();
        }

        //** Open a dialog to get Nautical Almanac data from user and create the SunAlmanacData instance **
        dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Enter Nautical Almanac data for Sun at " +  sunSight.getTimeOfSightUTC() + " UTC.");
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("sunAlmanacData.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog.");
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result3 = dialog.showAndWait();

        if (result3.isPresent() && result3.get() == ButtonType.OK) {
            SunAlmanacController sunAlmanacController = fxmlLoader.getController();
            sunAlmanacData = sunAlmanacController.getSunAlmanacData(sunSight.getInterpolationFactor());
        }

        CalculatedAltitudeAndAzimuth calculatedAltitudeAndAzimuth = new CalculatedAltitudeAndAzimuth(sunAlmanacData.getGha(),
                sunAlmanacData.getDec(), drPosition.getLatitude(), drPosition.getLongitude());
        HSextantToHObserved hSextantToHObserved = new HSextantToHObserved(sunSight.getEyeHeight(), sunSight.getBody(),
                   sunSight.getLimb(), sunSight.getIndexError(), sunSight.getTemperature(), sunSight.getPressure(),
                   sunAlmanacData.getSemiDiameter(), sunSight.getSextantAltitude());
        PositionLine positionLine = new PositionLine(drPosition.getdRLatitude(), drPosition.getdRLongitude(),
                    hSextantToHObserved.getObservedAltitude(), calculatedAltitudeAndAzimuth.getHC(),
                    calculatedAltitudeAndAzimuth.getZ());

        tfResult.setText("Plot: " + positionLine.getPlotString());

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