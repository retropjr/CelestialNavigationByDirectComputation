package nz.co.cportho.richard.celestialnavigationbydirectcomputation;

import java.util.Scanner;

public class BasicEphemerisData {
    private String year;
    private String month;
    private String day;
    private String hour;
    private String hourPlusOne;
    private boolean theNextDay = false;
    private String ghaString;
    private double gha;
    private String decString;
    private double dec;
    private String celestialBody;
    private double interpolationFactor;
    private double semiDiameter;



    public BasicEphemerisData(String utcOfSight, String body, double interpolationFactor, String gha0, String gha1,
                            String dec0, String dec1, String decHem, String sd) {
        this.year = extractYear(utcOfSight);
        this.month = extractMonth(utcOfSight);
        this.day = extractDay(utcOfSight);
        this.hour = extractHour(utcOfSight);
        if (this.hour.equals("23")){
            this.hourPlusOne = "00";
            this.theNextDay = true;
        } else {
            int hour = Integer.parseInt(this.hour);
            int hourPlusOne = hour += 1;
            this.hourPlusOne = String.valueOf(hourPlusOne);
        }
        this.celestialBody = body;
        this.interpolationFactor = interpolationFactor;

        double ghaA = Double.parseDouble(gha0.substring(0,3)) + (Double.parseDouble(gha0.substring(4,8))/60.0);
        double ghaB = Double.parseDouble(gha1.substring(0,3)) + (Double.parseDouble(gha1.substring(4,8))/60.0);
        if (ghaB < ghaA){
            ghaB += 360.0;
        }
        this.gha = ghaA + (interpolationFactor * (ghaB - ghaA));
        if(this.gha > 360){
            this.gha -= 360;
        }
        this.ghaString = String.valueOf(this.gha);

        double decA = Double.parseDouble(dec0.substring(0,2)) + (Double.parseDouble(dec0.substring(3,7))/60.0);
        double decB = Double.parseDouble(dec1.substring(0,2)) + (Double.parseDouble(dec1.substring(3,7))/60.0);
        if(decHem.equals("S")){
            decA *= -1;
            decB *= -1;
        }
        this.dec = decA + (interpolationFactor * (decB - decA));
        this.decString = String.valueOf(this.dec);

        this.semiDiameter = Double.parseDouble(sd)/60.0;

    }

    public BasicEphemerisData(String utcOfSight, String body, double interpolationFactor, boolean meridianAlt) {
        this.year = extractYear(utcOfSight);
        this.month = extractMonth(utcOfSight);
        this.day = extractDay(utcOfSight);
        this.hour = extractHour(utcOfSight);
        if (this.hour.equals("23")){
            this.hourPlusOne = "00";
            this.theNextDay = true;
        } else {
            int hour = Integer.parseInt(this.hour);
            int hourPlusOne = hour += 1;
            this.hourPlusOne = String.valueOf(hourPlusOne);
        }
        this.celestialBody = body;
        this.interpolationFactor = interpolationFactor;
        userInputEphemerisData(this.celestialBody, this.interpolationFactor, meridianAlt);
    }

    public BasicEphemerisData(){

    }

    public double getGha() {
        return gha;
    }

    public double getDec() {
        return dec;
    }

    public double getSemiDiameter() {
        return semiDiameter;
    }

    private String extractYear(String utc){
        return utc.substring(6,10);
    }

    private String extractMonth(String utc){
        return utc.substring(3, 5);
    }

    private String extractDay(String utc){
        return utc.substring(0,2);
    }

    private String extractHour(String utc){
        return utc.substring(11,13);
    }



    private void userInputEphemerisData(String celestialBody, double interpolationFactor, boolean meridianAltCalc){
        String dec1Str;
        String dec2Str;
        Scanner scanner = new Scanner(System.in);
        String decHemisphere;
        String sDStr;

        if(celestialBody.equals("Sun")){
            System.out.println("From Nautical Almanac " + this.year + " "
                    + this.month + " " + this.day + " " + this.celestialBody + " page...");

            System.out.println("Read and then enter hour " + this.hour + " DEC: ");
            dec1Str = scanner.nextLine();
            if (this.theNextDay){
                System.out.println("Read and then enter hour " + this.hourPlusOne + " (Next day) DEC: ");
            } else {
                System.out.println("Read and then enter hour " + this.hourPlusOne + " DEC: ");
            }
            dec2Str = scanner.nextLine();
            System.out.println("Is Declination N or S? Enter either N or S :");
            decHemisphere = scanner.nextLine();

            System.out.println("DEC 1 " + dec1Str + " DEC  2 " + dec2Str + " " + decHemisphere);
            double dec1 = Double.parseDouble(dec1Str.substring(0,2)) + (Double.parseDouble(dec1Str.substring(3,7))/60.0);
            double dec2 = Double.parseDouble(dec2Str.substring(0,2)) + (Double.parseDouble(dec2Str.substring(3,7))/60.0);
            if(decHemisphere.equals("S")){
                dec1 *= -1;
                dec2 *= -1;
            }
            this.dec = dec1 + (interpolationFactor * (dec2 - dec1));
            this.decString = String.valueOf(this.dec);
            System.out.println("DEC " + this.decString);

            System.out.println("From Nautical Almanac " + this.year + " "
                    + this.month + " " + this.day + " " + this.celestialBody + " page...");
            System.out.println("Read and then enter SD: ");
            sDStr = scanner.nextLine();
            System.out.println("Semi Diameter " + this.celestialBody + " equals : " + sDStr);

            this.semiDiameter = Double.parseDouble(sDStr)/60.0;

            System.out.println("SD " + this.semiDiameter);
        }
    }
}

