package nz.co.cportho.richard.celestialnavigationbydirectcomputation;

public class LHAAries {
    private double GHAAries;
    private double LHAAries;
    private String LHAAriesString;

    public LHAAries(Double longitude, double gha) {
        this.GHAAries = gha;
        this.LHAAries = (GHAAries + longitude) % 360;
        this.LHAAriesString = String.valueOf(LHAAries);
    }

    public double getGhaAries(){
        return GHAAries;
    }
    public double getLHAAries() {
        return LHAAries;
    }

    public String getLHAAriesString() {
        return LHAAriesString;
    }
}
