package nz.co.cportho.richard.celestialnavigationbydirectcomputation;


public class CalculatedAltitudeAndAzimuth {
    private double gHA;
    private double dec;
    private double lat;
    private double lon;
    private double hC;
    private double z;

    public CalculatedAltitudeAndAzimuth(double gHA, double dec, double lat, double lon) {
        this.gHA = gHA;
        this.dec = dec;
        this.lat = lat;
        this.lon = lon;
        calcHcAndZ();
    }


    public double getHC() {
        return hC;
    }

    public double getZ() {
        return z;
    }

    private void calcHcAndZ(){
        double k = (Math.PI) / 180;
        double lHA = (this.gHA + this.lon);
        if (lHA < 360){
            lHA = lHA + 360;
        } else if (lHA >= 360){
            lHA = lHA - 360;
        }
        System.out.println("LHA " + lHA);
        double s = Math.sin(this.dec * k);
        System.out.println(("S " + s));
        double c = (Math.cos((this.dec * k)) * (Math.cos(lHA * k)));
        System.out.println("C " + c);
        this.hC = Math.asin((s * Math.sin(this.lat * k)) + (c * Math.cos(this.lat * k))) / k;

        double x = ((s * Math.cos(this.lat * k)) - (c * Math.sin(this.lat * k))) / Math.cos(this.hC * k);
        if (x > 1) {
            x = 1;
        } else if (x < -1) {
            x = -1;
        }
        System.out.println("X " + x);

        double a = Math.acos(x);
        System.out.println("A " + a);

        if (lHA > 180) {
            this.z = a / k;
        } else {
            this.z = 360 - (a / k);
        }
    }
}
