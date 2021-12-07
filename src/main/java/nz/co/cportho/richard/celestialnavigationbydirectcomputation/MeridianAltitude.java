package nz.co.cportho.richard.celestialnavigationbydirectcomputation;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MeridianAltitude {
    private String celestialBody;
    private String limb;
    private String passageLMT;
    private String localTimeZone;
    private String passageUTC;
    private String passageLocal;
    private double drLongitude;
    private long arcToTime;



    public MeridianAltitude(String celestialBody, String limb, String passageLMT, String localTimeZone, double drLongitude) {
        this.celestialBody = celestialBody;
        this.limb = limb;
        this.passageLMT = passageLMT;
        this.localTimeZone = localTimeZone;
        this.drLongitude = drLongitude;
        this.arcToTime = ArcToTime(this.drLongitude) ;
        this.passageUTC = calcPassageUTC(this.passageLMT, this.arcToTime);
        this.passageLocal = calcPassageLocal(this.passageUTC, this.localTimeZone);

    }

    public String getPassageUTC() {
        return passageUTC;
    }

    public String getPassageLocal() {
        return passageLocal;
    }

    public long getArcToTime() {
        return arcToTime;
    }

    private long ArcToTime(double drLongitude){
        long arc = (long) ((drLongitude / 15) * 60 * 60 * 1000);
        return  arc;
    }

    private String calcPassageUTC(String passageLMT, double arcToTime) {
        Date lmt;

        java.text.SimpleDateFormat dateTimeFormat = new SimpleDateFormat( "dd MM yyyy HH:mm");
        try {
            lmt = dateTimeFormat.parse(passageLMT);
            Date utc = new Date((long) (lmt.getTime() - arcToTime));
            return dateTimeFormat.format(utc);
        } catch (Exception e) {
            return "dd MM yyyy HH:mm";
        }
    }

    private String calcPassageLocal(String passageUTC, String timeZone) {
        Date utc;
        long hoursInMilliSec = Long.parseLong(timeZone) * 60 * 60 * 1000;

        java.text.SimpleDateFormat dateTimeFormat = new SimpleDateFormat( "dd MM yyyy HH:mm");
        try {
            utc = dateTimeFormat.parse(passageUTC);
            Date local = new Date(utc.getTime() + hoursInMilliSec);
            return dateTimeFormat.format(local);
        } catch (Exception e) {
            return "dd MM yyyy HH:mm";
        }
    }

    public String calcLatitude(String bodyBearing, double HObserved, double declination){
        String stringLatitude;
        double latitude;

        double bodyMeridianZenithDistance = 90.0 - HObserved;
        System.out.println(bodyMeridianZenithDistance);
        String bodyMeridianZenithName;

        if (bodyBearing.equals("North")){
            bodyMeridianZenithName = "South";
        } else
        {
            bodyMeridianZenithName = "North";
        }
        System.out.println(bodyMeridianZenithName);

        String decName;
        if (declination <= 0) {
            decName = "South";
        } else {
            decName = "North";
        }
        System.out.println(declination);
        System.out.println(decName);


        if(decName.equals(bodyMeridianZenithName)){
            latitude = bodyMeridianZenithDistance + (Math.abs(declination));
        } else  {
            latitude = bodyMeridianZenithDistance - (Math.abs(declination));
        }


        double latDeg = Math.floor(latitude);
        double latMin = (latitude - latDeg) * 60.0;

        String stringLatDeg = String.format("%.0f", latDeg);
        String stringLatMin = String.format("%.1f", latMin);

        String stringLatHem;
        if (bodyMeridianZenithDistance > declination){
            stringLatHem = bodyMeridianZenithName.substring(0,1);
        } else {
            stringLatHem = decName.substring(0,1);
        }


        stringLatitude = stringLatDeg + " " + stringLatMin + " " + stringLatHem;

        return stringLatitude;
    }


}
