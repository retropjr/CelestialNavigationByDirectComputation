package nz.co.cportho.richard.celestialnavigationbydirectcomputation;

public class HSextantToHObserved {
    private double eyeHeight;
    private String celestialBody;
    private String limb;
    private double indexError;
    private double temperature;
    private double pressure;
    private double semiDiameter;
    private double sextantAltitude;
    private double observedAltitude;

    public HSextantToHObserved(double h, String body, String limb, double error, double temp, double press, double semiDiameter,
                               String sextantAltitude){
        this.eyeHeight = h;
        this.celestialBody = body;
        this.limb = limb;
        this.indexError = error;
        this.temperature = temp;
        this.pressure = press;
        if (limb.equals("Upper")){
            this.semiDiameter = semiDiameter * -1;
        } else if (limb.equals("Lower")){
            this.semiDiameter = semiDiameter;
        }
        this.sextantAltitude = Double.parseDouble(sextantAltitude.substring(0,2)) + Double.parseDouble(sextantAltitude.substring(3,7)) / 60.0;

        calcObservedAltitude();
    }

    public double getObservedAltitude(){
        return observedAltitude;
    }

    private void calcObservedAltitude() {
        double k = Math.PI / 180;
        double dip;
        double apparentAltitude;
        double r;
        double f;
        double refraction;
        double parallaxInAltitude;

        dip = 0.0293 * Math.sqrt(this.eyeHeight);
        apparentAltitude = this.sextantAltitude + this.indexError - dip;
        r = 0.0167 / Math.tan((apparentAltitude + (7.32 / (apparentAltitude + 4.32))) * k);
        f = (0.28 * this.pressure) / (this.temperature + 273);
        refraction = r * f;

        if (this.celestialBody.equals("Sun")) {
            parallaxInAltitude = 0.0024 * Math.cos(apparentAltitude * k);
            this.observedAltitude = apparentAltitude - refraction + parallaxInAltitude + this.semiDiameter;
        }
    }
}


