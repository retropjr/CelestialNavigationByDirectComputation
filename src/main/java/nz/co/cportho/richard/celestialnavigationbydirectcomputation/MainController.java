package nz.co.cportho.richard.celestialnavigationbydirectcomputation;

import datamodel.AlmanacData;
import datamodel.DRPosition;
import datamodel.Sight;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;

public class MainController {

    @FXML
    BorderPane mainPanel;
    @FXML
    TextArea taResult;

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

        taResult.setText("Plot: " + positionLine.getPlotString());

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

        taResult.setText("Meridian Passage " + sight.getBody() + " local time: " + meridianAltitude.getPassageLocal());

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

        taResult.setText("Latitude: " + meridianAltitude.calcLatitude(sight.getBearing(), hSextantToHObserved.getObservedAltitude(),
                almanacData.getDec()));
    }

    @FXML
    public void calcSunriseSunset(){

        //** Open a dialog to get sight data from user and create the Sight instance **
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Enter local date and timezone.");
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
        dialog.setTitle("Enter position for time of sunrise and sunset calculation.");
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

        SunriseSunset sunriseSunset = new SunriseSunset(sight.getDayLocal(), sight.getMonthLocal(), sight.getYearLocal(), sight.getTimeZoneLocal(),
                drPosition.getLatitude(), drPosition.getLongitude());

        taResult.setText("Sunrise: " + sunriseSunset.getSunriseString() + " " + "Sunset: " + sunriseSunset.getSunsetString());
    }

    @FXML
    public void starFinderSetUp(){

        //** Open a dialog to get sight data from user and create the Sight instance **
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Enter local date and timezone.");
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
        dialog.setTitle("Enter position for time of sunrise and sunset calculation.");
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

        SunriseSunset sunriseSunset = new SunriseSunset(sight.getDayLocal(), sight.getMonthLocal(), sight.getYearLocal(), sight.getTimeZoneLocal(),
                drPosition.getLatitude(), drPosition.getLongitude());
        StarFinderSetUp starFinderSetUp = new StarFinderSetUp(drPosition.getLongitude(), sight.getDayLocal(),
                sight.getMonthLocal(), sight.getYearLocal(), sunriseSunset.getSunriseString(), sunriseSunset.getSunsetString(),
                sight.getTimeZoneLocal(), sight.getIsMorning());

        //** Open a dialog to get Nautical Almanac data from user and create the AlmanacData instance **
        dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        if(sight.getIsMorning()) {
            dialog.setTitle("From Nautical Almanac enter GHA Aries " + starFinderSetUp.getTimeOfSightUTCMorning() + " UTC.");
        } else {
            dialog.setTitle("From Nautical Almanac enter GHA Aries " + starFinderSetUp.getTimeOfSightUTCEvening() + " UTC.");
        }
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
            almanacData = almanacController.getAriesAlmanacData(sight.getInterpolationFactor());
        }

        LHAAries lhaAries = new LHAAries(drPosition.getLongitude(), almanacData.getGha());

        if(sight.getIsMorning()) {
            taResult.setText("Time of  star sight local: " + starFinderSetUp.getTimeOfSightLocalMorning() + " " +
                    "LHA Aries at time of sight local: " + lhaAries.getLHAAriesString());
        } else {
            taResult.setText("Time of star sight local: " + starFinderSetUp.getTimeOfSightLocalEvening() + " " +
                    "LHA Aries at time of sight local: " + lhaAries.getLHAAriesString());
        }
    }

    @FXML
    public void handleExit(){
        Platform.exit();
    }

}