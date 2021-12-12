package nz.co.cportho.richard.celestialnavigationbydirectcomputation;

import java.util.Calendar;



public class SunriseSunset {
private double fractionalYear;
private boolean isLeapYear;
private int dayOfYear;
private String year;
private int monthNumber;
private String day;
private String timeZone;
private double latitude;
private double longitude;
private double eqnOfTime;
private double declination;
private double timeOffset;
private double trueSolarTime;
private double hourAngle;
private double sunrise;
private double sunnoon;
private double sunset;
private String sunriseString;
private String sunnoonString;
private String sunsetString;


public SunriseSunset(String day, String month, String year, String timeZone, double latitude, double longitude) {
    this.year = year;
    this.monthNumber = calcMonthNumber(month);
    this.day = day;
    this.timeZone = timeZone;
    this.latitude = latitude;
    this.longitude = longitude;
    this.isLeapYear = isLeapYear(Integer.valueOf(this.year));
    this.dayOfYear = dayOfYear(Integer.valueOf(this.year), monthNumber, Integer.valueOf(this.day));
    this.fractionalYear = fractionalYear(this.dayOfYear, this.isLeapYear);
    this.eqnOfTime = equationOfTime(this.fractionalYear);
    this.declination = calcDeclination(this.fractionalYear);
    this.timeOffset = calcTimeOffset(this.eqnOfTime, this.longitude, this.timeZone);
    this.trueSolarTime = calcTrueSolarTime(this.longitude, timeZone);
    this.hourAngle = calcHourAngle(this.trueSolarTime, this.latitude, this.declination);
    this.sunrise = calcSunrise(this.hourAngle, this.timeOffset);
    this.sunnoon = calcSunNoon(this.timeOffset);
    this.sunset = calcSunset(this.hourAngle, this.timeOffset);
    this.sunriseString = timeString(this.sunrise);
    this.sunnoonString = timeString(this.sunnoon);
    this.sunsetString = timeString(this.sunset);
    }

    public String getYear() {
        return year;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public String getDay() {
        return day;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public String getSunriseString() {
        return sunriseString;
    }

    public String getSunnoonString() {
        return sunnoonString;
    }

    public String getSunsetString() {
        return sunsetString;
    }


    public static int calcMonthNumber(String month){
        int m  = Integer.valueOf(month) - 1;

        return m;
    }

    public static boolean isLeapYear(int year){
    if (year < 1 || year > 9999) {
        return false;
    }
    if (year % 4 != 0) {
        return false;
    }
    if (year % 100 == 0) {
        if (year % 400 == 0) {
            return true;
        }
        return false;
    }
    return true;
    }



    public static int dayOfYear(int year, int month, int day){
    Calendar c = Calendar.getInstance();

    // Jan=0, Feb=1, Mar=2 ... //
    c.set(year, month, day);
    int doy = c.get(Calendar.DAY_OF_YEAR);

    return doy;
    }

    public static double fractionalYear(int dayOfYear, boolean isLeapYear){
    double fraction;
    int hour = 0;
    if (!isLeapYear){
        fraction = ((2 * Math.PI) / 365) * (dayOfYear - 1 + ( (hour - 12) / 24));
    } else {
        fraction = ((2 * Math.PI) / 366) * (dayOfYear - 1 + ((hour - 12)/ 24));
    }
    return fraction;
    }

    public static double equationOfTime(double fractionalYear){
    double eqnTime = 229.18 * (0.000075 + (0.001868 * Math.cos(fractionalYear)) -  (0.032077 * Math.sin(fractionalYear)) -
            (0.014615 * Math.cos(2 * fractionalYear)) - (0.040849 * Math.sin(2 * fractionalYear)));
    return eqnTime;
    }

    public static double calcDeclination(double fractionalYear){
    double dec = 0.006918 - (0.399912 * Math.cos(fractionalYear)) +  (0.070257 * Math.sin(fractionalYear)) -
            (0.006758 * Math.cos(2 * fractionalYear)) + (0.000907 * Math.sin(2 * fractionalYear)) -
            (0.002697 * Math.cos(3 * fractionalYear)) + (0.00148 * Math.sin(3 * fractionalYear));
    return dec;
    }

    public static double calcTimeOffset(double eqnOfTime, double longitude, String timeZone){
    double tZ = Double.parseDouble(timeZone);
    double offset = ((longitude / 15) * 60) + eqnOfTime - (60 * tZ);
    return offset;
    }

    public static double calcTrueSolarTime(double longitude, String timeZone){
    double tZ = Double.parseDouble(timeZone);
    double tst =  (12 * 60) + (4 * longitude)  - (tZ);
    return tst;
    }



    public static double calcHourAngle(double trueSolarTime, double latitude, double declination){

    double k = Math.PI / 180;
    double a = Math.cos(90.833 * k);
    double b = Math.cos(latitude * k);
    double c = Math.cos(declination);
    double d = Math.tan(latitude * k);
    double e = Math.tan(declination);
    double f = (a /(b * c)) - (d * e);

    double ha =  (Math.acos(f) / k);

    return ha;
    }



    public static double calcSunrise(double ha, double timeOffset){
    double sr = 720 - timeOffset - ((ha * 60) / 15);
    return sr;
    }

    private static double calcSunNoon(double timeOffset){
    double sn = 720 - timeOffset;
    return sn;
    }

    public static double calcSunset(double ha, double timeOffset){
    double ss = 720 - timeOffset + ((ha * 60) / 15);
    return ss;
    }

    public static String timeString(double time){
    double timeInHours = time / 60.0;
    Integer hour = (int) timeInHours;
    String hourString;
    Integer minute = (int) ((timeInHours - hour) * 60.0);
    String minuteString;

    if(hour < 10){
        hourString = "0" + hour.toString();
    } else {
        hourString = hour.toString();
    }
    if (minute < 10) {
        minuteString = "0" + minute.toString();
    } else  {
        minuteString = minute.toString();
    }
    String stringTime = (hourString + minuteString);

    return stringTime;
    }

}
