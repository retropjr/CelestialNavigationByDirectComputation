package datamodel;

public class SunAlmanacData {

    private String ghaString;
    private double gha;
    private String decString;
    private double dec;
    private double semiDiameter;
    private String meridianPassage;


    public SunAlmanacData(String gha0, String gha1,String dec0, String dec1, String decHem, String sd, String meridianPassage,
                    double interpolationFactor) {

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
        this.meridianPassage = meridianPassage;

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


}