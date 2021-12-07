package nz.co.cportho.richard.celestialnavigationbydirectcomputation;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Sight {
        private String celestialBody;
        private String limb;
        private String timeOfSightLocal;
        private String timeZone;
        private String timeOfSightUTC;
        private String stringSextantAltitude;
        private double interpolationFactor;

        public Sight(String body, String limb, LocalDate localDate, int hour, int minute, int second,
                     int timeZone, int clockError, String sextantAltitude) {
                this.celestialBody = body;
                this.limb = limb;
                this.timeOfSightLocal = createLocalTime(localDate,hour,minute,second);
                this.timeOfSightLocal = handleClockError(timeOfSightLocal, clockError);
                this.timeOfSightUTC = calcTimeOfSightUTC(timeOfSightLocal, timeZone);
                this.stringSextantAltitude = sextantAltitude;
                this.interpolationFactor = calcInterpolationFactor(minute, second);
        }



        public Sight(String body, String limb, LocalDate localDate, String merPass, int timeZone, String sextantAltitude) {
                this.celestialBody = body;
                this.limb = limb;
                this.celestialBody = body;
                this.limb = limb;
                int hour = Integer.parseInt(merPass.substring(0,2));
                int minute = Integer.parseInt(merPass.substring(3,5));
                int second = 0;
                this.timeOfSightLocal = createLocalTime(localDate, hour,minute,second);
                this.timeZone = String.valueOf(timeZone);
                this.stringSextantAltitude = sextantAltitude;
                this.interpolationFactor = calcInterpolationFactor(minute,second);
        }

        public String getTimeOfSightUTC() {
                return timeOfSightUTC;
        }

        public String getTimeOfSightLocal(){
                return timeOfSightLocal;
        }

        public String getSextantAltitude() {
                return stringSextantAltitude;
        }

        public double getInterpolationFactor() {
                return interpolationFactor;
        }

        public String getCelestialBody() {
                return celestialBody;
        }

        public String getLimb() {
                return limb;
        }

        public String getTimeZone(){
                return timeZone;
        }

        private String createLocalTime(LocalDate localDate, int hour, int minute, int second){
                String correctedDateString = "";
                String localDateString = localDate.toString();
                correctedDateString = localDateString.substring(8,10) + " " + localDateString.substring(5,7) + " " +
                        localDateString.substring(0,4) + " ";
                if (hour < 10){
                        correctedDateString = correctedDateString + "0" + String.valueOf(hour) + ":";
                } else {
                        correctedDateString = correctedDateString + String.valueOf(hour) + ":";
                }
                if (minute < 10){
                        correctedDateString = correctedDateString + "0" + String.valueOf(minute) + ":";
                } else {
                        correctedDateString = correctedDateString + String.valueOf(minute) + ":";
                }
                if (second < 10){
                        correctedDateString = correctedDateString + "0" + String.valueOf(second);
                } else {
                        correctedDateString = correctedDateString + String.valueOf(second);
                }

                return correctedDateString;
        }


        private String handleClockError(String timeOfSight, int clockError){

                Date local;
                long errorInMilliSec = clockError  * 1000;

                SimpleDateFormat dateTimeFormat = new SimpleDateFormat( "dd MM yyyy HH:mm:ss");
                try {
                        local = dateTimeFormat.parse(timeOfSight);
                        Date corrected = new Date(local.getTime() - errorInMilliSec);
                        return dateTimeFormat.format(corrected);
                } catch (Exception e) {
                        return "dd MM yyyy HH:mm:ss";
                }
        }

        private String calcTimeOfSightUTC(String timeOfSight, int timeZone){
                Date local;
                long errorInMilliSec = timeZone * 60 * 60 * -1000;

                SimpleDateFormat dateTimeFormat = new SimpleDateFormat( "dd MM yyyy HH:mm:ss");
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
}

