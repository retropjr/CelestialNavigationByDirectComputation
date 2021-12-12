package datamodel;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Sight {

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
    private String timeOfSightLocal;
    private String timeOfSightUTC;
    private double interpolationFactor;


    public Sight(String body, String limb, String bearing, LocalDate localDate, int hour, int minute, int second,
                 int timeZone, int clockError, String sextantAltitude, double eyeHeight, double indexError,
                 double temperature, double pressure) {
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
        this.timeOfSightLocal = createLocalTime(localDate, hour, minute, second);
        this.timeOfSightLocal = handleClockError(timeOfSightLocal, clockError);
        this.timeOfSightUTC = calcTimeOfSightUTC(timeOfSightLocal, timeZone);
        this.interpolationFactor = calcInterpolationFactor(minute, second);
    }

    private String createLocalTime(LocalDate localDate, int hour, int minute, int second) {
        String correctedDateString = "";
        String localDateString = localDate.toString();
        correctedDateString = localDateString.substring(8, 10) + " " + localDateString.substring(5, 7) + " " +
                localDateString.substring(0, 4) + " ";
        if (hour < 10) {
            correctedDateString = correctedDateString + "0" + String.valueOf(hour) + ":";
        } else {
            correctedDateString = correctedDateString + String.valueOf(hour) + ":";
        }
        if (minute < 10) {
            correctedDateString = correctedDateString + "0" + String.valueOf(minute) + ":";
        } else {
            correctedDateString = correctedDateString + String.valueOf(minute) + ":";
        }
        if (second < 10) {
            correctedDateString = correctedDateString + "0" + String.valueOf(second);
        } else {
            correctedDateString = correctedDateString + String.valueOf(second);
        }

        return correctedDateString;
    }


    private String handleClockError(String timeOfSight, int clockError) {

        Date local;
        long errorInMilliSec = clockError * 1000;

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
        try {
            local = dateTimeFormat.parse(timeOfSight);
            Date corrected = new Date(local.getTime() - errorInMilliSec);
            return dateTimeFormat.format(corrected);
        } catch (Exception e) {
            return "dd MM yyyy HH:mm:ss";
        }
    }

    private String calcTimeOfSightUTC(String timeOfSight, int timeZone) {
        Date local;
        long errorInMilliSec = timeZone * 60 * 60 * -1000;

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
        try {
            local = dateTimeFormat.parse(timeOfSight);
            Date corrected = new Date(local.getTime() + errorInMilliSec);
            return dateTimeFormat.format(corrected);
        } catch (Exception e) {
            return "dd MM yyyy HH:mm:ss";
        }
    }

    private double calcInterpolationFactor(int minute, int second) {
        if (second != 0) {
            return ((minute / 60.0) + (second / 3600.0));
        } else {
            return minute / 60.0;
        }
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

    public String getTimeOfSightUTC() {
        return timeOfSightUTC;
    }

    public String getTimeOfSightLocal(){
        return timeOfSightLocal;
    }

    public String getTimeZoneLocal(){
        return String.valueOf(this.timeZone);
    }

    public double getInterpolationFactor() {
        return interpolationFactor;
    }
}

