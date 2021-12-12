package nz.co.cportho.richard.celestialnavigationbydirectcomputation;

import datamodel.AlmanacData;
import datamodel.DRPosition;
import datamodel.Sight;
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

    Sight sight;
    DRPosition drPosition;
    AlmanacData almanacData;

    @FXML
    public void showSunLOPCalculation() {
        //** Open a dialog to get sun sight data from user and create the SunSight instance **
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Enter Sun data");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("sight.fxml"));
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
            SightController sunController = fxmlLoader.getController();
            sight = sunController.getSightData();
        } else if (result.get() == ButtonType.CANCEL){
            return;
        }

        //** Open a dialog to get DR position from user and create the DRPosition instance **
        dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Enter DR Position at " +  sight.getTimeOfSightUTC() + " UTC.");
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
        dialog.setTitle("Enter Nautical Almanac data for Sun at " +  sight.getTimeOfSightUTC() + " UTC.");
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("almanacData.fxml"));
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
            AlmanacController sunAlmanacController = fxmlLoader.getController();
            almanacData = sunAlmanacController.getSunAlmanacData(sight.getInterpolationFactor());
        }

        CalculatedAltitudeAndAzimuth calculatedAltitudeAndAzimuth = new CalculatedAltitudeAndAzimuth(almanacData.getGha(),
                almanacData.getDec(), drPosition.getLatitude(), drPosition.getLongitude());
        HSextantToHObserved hSextantToHObserved = new HSextantToHObserved(sight.getEyeHeight(), sight.getBody(),
                   sight.getLimb(), sight.getIndexError(), sight.getTemperature(), sight.getPressure(),
                   almanacData.getSemiDiameter(), sight.getSextantAltitude());
        PositionLine positionLine = new PositionLine(drPosition.getDRLatitude(), drPosition.getDRLongitude(),
                    hSextantToHObserved.getObservedAltitude(), calculatedAltitudeAndAzimuth.getHC(),
                    calculatedAltitudeAndAzimuth.getZ());

        tfResult.setText("Plot: " + positionLine.getPlotString());

    }



    @FXML
    public void calcMeridianPassage() {
        //** Open a dialog to get appropriate sight data from user and create the Sight instance **
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Enter Sight data. Set local time to meridian passage of body.");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("sight.fxml"));
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
            SightController sunController = fxmlLoader.getController();
            sight = sunController.getSightData();

        } else if (result.get() == ButtonType.CANCEL){
            return;
        }

        //** Open a dialog to get DR Longitude from user and create the DRPosition instance **
        dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Enter DR Longitude at " +  sight.getTimeOfSightLocal() + " Local.");
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
            drPosition = DRController.getLongitudeData();
        }

        MeridianAltitude meridianAltitude = new MeridianAltitude(sight.getBody(), sight.getLimb(),
                sight.getTimeOfSightLocal(), sight.getTimeZoneLocal(), drPosition.getLongitude());

        tfResult.setText("Meridian Passage " + sight.getBody() + " local time: " + meridianAltitude.getPassageLocal());

    }

    @FXML
    public void calcLatitude(){

        //** Open a dialog to get sight data from user and create the Sight instance **
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Enter sight data. Set local time to meridian passage of body.");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("sight.fxml"));
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
            SightController sunController = fxmlLoader.getController();
            sight = sunController.getSightData();
        } else if (result.get() == ButtonType.CANCEL){
            return;
        }

        //** Open a dialog to get DR longitude from user and create the DRPosition instance **
        dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Enter DR longitude at " +  sight.getTimeOfSightLocal() + " Local.");
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
            drPosition = DRController.getLongitudeData();
        }

        //** Open a dialog to get Nautical Almanac data from user and create the AlmanacData instance **
        dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("From Nautical Almanac enter declination and SD for body at " +  sight.getTimeOfSightUTC() + " UTC.");
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("almanacData.fxml"));
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
            AlmanacController almanacController = fxmlLoader.getController();
            almanacData = almanacController.getLatitudeAlmanacData(sight.getInterpolationFactor());
        }

        MeridianAltitude meridianAltitude = new MeridianAltitude(sight.getBody(), sight.getLimb(),
                sight.getTimeOfSightLocal(), sight.getTimeZoneLocal(), drPosition.getLongitude());
        HSextantToHObserved hSextantToHObserved = new HSextantToHObserved(sight.getEyeHeight(), sight.getBody(), sight.getLimb(),
                sight.getIndexError(), sight.getTemperature(), sight.getPressure(), almanacData.getSemiDiameter(), sight.getSextantAltitude());

        tfResult.setText("Latitude: " + meridianAltitude.calcLatitude(sight.getBearing(), hSextantToHObserved.getObservedAltitude(),
                almanacData.getDec()));
    }

    @FXML
    public void handleExit(){
        Platform.exit();
    }

}