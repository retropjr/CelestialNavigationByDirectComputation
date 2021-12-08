package datamodel;

import java.time.LocalDate;

public class SunData {

    private String body;
    private String limb;
    private String bearing;
    private LocalDate localDate;
    private int hour;
    private int minute;
    private int second;
    private int timeZone;
    private int clockError;
    private String sextantAltitude;
    private double eyeHeight;
    private double indexError;
    private double temperature;
    private double pressure;
    private String latitude;
    private String latHemisphere;
    private String longitude;
    private String lonHemisphere;
    private String GHA0;
    private String GHA1;
    private String Dec0;
    private String Dec1;
    private String DecHem;
    private String sd;

    public SunData(String body, String limb, String bearing, LocalDate localDate, int hour, int minute, int second,
                   int timeZone, int clockError, String sextantAltitude, double eyeHeight, double indexError,
                   double temperature, double pressure, String latitude, String latHemisphere,
                   String longitude, String lonHemisphere, String GHA0, String GHA1, String dec0, String dec1, String decHem, String sd) {
        this.body = body;
        this.limb = limb;
        this.bearing = bearing;
        this.localDate = localDate;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.timeZone = timeZone;
        this.clockError = clockError;
        this.sextantAltitude = sextantAltitude;
        this.eyeHeight = eyeHeight;
        this.indexError = indexError;
        this.temperature = temperature;
        this.pressure = pressure;
        this.latitude = latitude;
        this.latHemisphere = latHemisphere;
        this.longitude = longitude;
        this.lonHemisphere = lonHemisphere;
        this.GHA0 = GHA0;
        this.GHA1 = GHA1;
        Dec0 = dec0;
        Dec1 = dec1;
        DecHem = decHem;
        this.sd = sd;
    }

    public String getBody() {
        return body;
    }

    public String getLimb() {
        return limb;
    }

    public String getBearing() {
        return bearing;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public int getTimeZone() {
        return timeZone;
    }

    public int getClockError() {
        return clockError;
    }

    public String getSextantAltitude() {
        return sextantAltitude;
    }

    public double getEyeHeight() {
        return eyeHeight;
    }

    public double getIndexError() {
        return indexError;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLatHemisphere() {
        return latHemisphere;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLonHemisphere() {
        return lonHemisphere;
    }

    public String getGHA0() {
        return GHA0;
    }

    public String getGHA1() {
        return GHA1;
    }

    public String getDec0() {
        return Dec0;
    }

    public String getDec1() {
        return Dec1;
    }

    public String getDecHem() {
        return DecHem;
    }

    public String getSd() {
        return sd;
    }
}
