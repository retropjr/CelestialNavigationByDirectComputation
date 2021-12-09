package datamodel;

public class DRPosition {

    private String dRLatitude;
    private String dRLatHemisphere;
    private String dRLongitude;
    private String dRLongHemisphere;
    private double latitude;
    private double longitude;

    public DRPosition(String dRLatitude, String dRLatHemisphere, String dRLongitude, String dRLongHemisphere) {
        this.dRLatitude = dRLatitude;
        this.dRLatHemisphere = dRLatHemisphere;
        this.dRLongitude = dRLongitude;
        this.dRLongHemisphere = dRLongHemisphere;
        this.latitude = latitude(this.dRLatitude, this.dRLatHemisphere);
        this.longitude = longitude(this.dRLongitude, this.dRLongHemisphere);
    }

    public DRPosition(String dRLongitude, String dRLongHemisphere){
        this.dRLongitude = dRLongitude;
        this.dRLongHemisphere = dRLongHemisphere;
        this.longitude = longitude(this.dRLongitude, this.dRLongHemisphere);
    }

    public String getdRLatitude() {
        return dRLatitude;
    }

    public String getdRLatHemisphere() {
        return dRLatHemisphere;
    }

    public String getdRLongitude() {
        return dRLongitude;
    }

    public String getdRLongHemisphere() {
        return dRLongHemisphere;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public double latitude(String dRLatitude, String dRLatHemisphere){

        double lat = Double.parseDouble(dRLatitude.substring(0,2)) + (Double.parseDouble(dRLatitude.substring(3,7))/60.0);
        if(dRLatHemisphere.equals("S")){
            lat *= -1;
            return lat;
        } else if (dRLatHemisphere.equals("N")){
            lat *= 1;
            return lat;
        }
        return lat;
    }

    public double longitude(String dRLongitude, String dRLonHemisphere){

        double lon = Double.parseDouble(dRLongitude.substring(0,3)) + (Double.parseDouble(dRLongitude.substring(4,8))/60.0);
        if(dRLonHemisphere.equals("W")){
            lon *= -1;
            return lon;
        } else if (dRLonHemisphere.equals("E")){
            lon *= 1;
            return lon;
        }
        return lon;
    }
}


