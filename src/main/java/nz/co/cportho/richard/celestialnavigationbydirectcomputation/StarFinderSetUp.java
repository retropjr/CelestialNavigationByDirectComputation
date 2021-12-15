package nz.co.cportho.richard.celestialnavigationbydirectcomputation;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StarFinderSetUp {

    private Double drLongitude;
    private String day;
    private String month;
    private String year;
    private String sunrise;
    private String sunset;
    private String timeOfSightLocalMorning;
    private String timeOfSightLocalEvening;
    private String timeOfSightUTCMorning;
    private String timeOfSightUTCEvening;
    private String timeZone;
    private boolean isMorning;
    final String OFFSET = "15";


    public StarFinderSetUp(Double drLongitude, String day, String month, String year, String sunrise, String sunset,
                           String timeZone, boolean isMorning) {
        this.drLongitude = drLongitude;
        this.day = day;
        this.month = month;
        this.year = year;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.timeZone = timeZone;
        this.isMorning = isMorning;

        if(isMorning) {
            this.timeOfSightLocalMorning = calcTimeOfSightLocalMorning(this.day, this.month, this.year, this.sunrise, this.OFFSET);
            this.timeOfSightUTCMorning = calcTimeOfSightUTC(this.timeOfSightLocalMorning, this.timeZone);

        } else {
            this.timeOfSightLocalEvening = calcTimeOfSightLocalEvening(this.day, this.month, this.year, this.sunset, this.OFFSET);
            this.timeOfSightUTCEvening = calcTimeOfSightUTC(this.timeOfSightLocalEvening, this.timeZone);
        }
    }


    public String getTimeOfSightLocalMorning() {
        return timeOfSightLocalMorning;
    }

    public String getTimeOfSightLocalEvening(){
        return timeOfSightLocalEvening;
    }

    public String getTimeOfSightUTCMorning() {
        return timeOfSightUTCMorning;
    }

    public String getTimeOfSightUTCEvening(){
        return timeOfSightUTCEvening;
    }


    private String calcTimeOfSightLocalMorning(String day, String month, String year, String sunrise, String offset) {
        String hourString;
        String minuteString;
        String timeOfSightLocalMorning;

        int totalMinutes = (Integer.parseInt(sunrise.substring(0, 2)) * 60) + Integer.parseInt(sunrise.substring(2, 4)) -
                Integer.parseInt(offset);
        int hour = totalMinutes / 60;
        int minute = totalMinutes % 60;
        if(hour < 10){
            hourString = "0" + hour;
        } else {
            hourString = String.valueOf(hour);
        }
        if (minute < 10) {
            minuteString = "0" + minute;
        } else  {
            minuteString = String.valueOf(minute);
        }
        timeOfSightLocalMorning = day + " " + month + " " +  year + " " + (hourString + minuteString);

    return timeOfSightLocalMorning;
    }

    private String calcTimeOfSightLocalEvening(String day, String month, String year, String sunset, String offset) {
        String hourString;
        String minuteString;
        String timeOfSightLocalEvening;

        int totalMinutes = (Integer.parseInt(sunset.substring(0, 2)) * 60) + Integer.parseInt(sunset.substring(2, 4)) +
                Integer.parseInt(offset);
        int hour = totalMinutes / 60;
        int minute = totalMinutes % 60;
        if(hour < 10){
            hourString = "0" + hour;
        } else {
            hourString = String.valueOf(hour);
        }
        if (minute < 10) {
            minuteString = "0" + minute;
        } else  {
            minuteString = String.valueOf(minute);
        }
        timeOfSightLocalEvening = day + " " + month + " " +  year + " " + (hourString + minuteString);

        return timeOfSightLocalEvening;
    }

    private String calcTimeOfSightUTC(String timeOfSightLocal, String timeZone) {
        String day = timeOfSightLocal.substring(0, 2);
        String month = timeOfSightLocal.substring(3, 5);
        String year = timeOfSightLocal.substring(6, 10);
        String hour = timeOfSightLocal.substring(11, 13);
        String minute = timeOfSightLocal.substring(13, 15);

        String localString = day + " " + month + " " + year + " " + hour + minute;
        String UTCString;

        long hoursInMilliSec = Long.parseLong(timeZone) * 60 * 60 * 1000;

        Date local;
        java.text.SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd MM yyyy HHmm");
        try {
            local = dateTimeFormat.parse(localString);
            Date utc = new Date(local.getTime() - hoursInMilliSec);
            UTCString = dateTimeFormat.format(utc);
        } catch (Exception e) {
            return "dd MM yyyy HHmm";
        }

        return UTCString;
    }



}
