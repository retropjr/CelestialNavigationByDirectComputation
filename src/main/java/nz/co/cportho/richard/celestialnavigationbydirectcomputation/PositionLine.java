package nz.co.cportho.richard.celestialnavigationbydirectcomputation;

public class PositionLine {
    private String dRLat;
    private String dRLon;
    private double observedAlt;
    private double calculatedAlt;
    private double azimuth;
    private String plotString;

    public PositionLine(String dRLat, String dRLon, double observedAlt, double calculatedAlt, double azimuth) {
        this.dRLat = dRLat;
        this.dRLon = dRLon;
        this.observedAlt = observedAlt;
        this.calculatedAlt = calculatedAlt;
        this.azimuth = azimuth;
        this.plotString = Plot();
    }

    public String getPlotString() {
        return plotString;
    }

    private String Plot(){
        double p = this.observedAlt - this.calculatedAlt;
        String direction = null;

        if(p > 0) {
            direction = "Towards";
        } else if (p <= 0){
            direction = "Away";
        }

        double nm = p * -60.0;
        azimuth = Math.round(azimuth);

        if (azimuth < 100) {
            return "Plot: 0" + Double.toString(azimuth) + "T / " + Math.round((p * 100.00)) / 100.00 + "nm / " + direction;
        }
        else if (azimuth >= 100){
            return "Plot: " + Double.toString(azimuth) + "T / " + Math.round((p * 100.00)) / 100.00 + "nm / " + direction;
        }
        return "No plot calculated";
    }

}
