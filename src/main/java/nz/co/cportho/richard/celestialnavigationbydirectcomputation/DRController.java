package nz.co.cportho.richard.celestialnavigationbydirectcomputation;


import datamodel.DRPosition;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class DRController {
    @FXML
    private TextField tfLatitude;
    @FXML
    private TextField tfLatHemisphere;
    @FXML
    private TextField tfLongitude;
    @FXML
    private TextField tfLonHemisphere;



    public DRPosition getDRPositionData() {

        String latitude = tfLatitude.getText();
        String latHemisphere = tfLatHemisphere.getText();
        String longitude = tfLongitude.getText();
        String lonHemisphere = tfLonHemisphere.getText();


        DRPosition DRPositionData = new DRPosition( latitude, latHemisphere, longitude, lonHemisphere);
        return DRPositionData;
    }

    public DRPosition getLongitudeData(){
        String longitude = tfLongitude.getText();
        String lonHemisphere = tfLonHemisphere.getText();

        DRPosition longitudeData = new DRPosition(longitude, lonHemisphere);
        return longitudeData;
    }

}
