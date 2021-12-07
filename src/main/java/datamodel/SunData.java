package datamodel;

public class SunData {
    private String gha0;
    private String gha1;
    private String dec0;
    private String dec1;
    private String decHem;
    private String sd;

    public SunData(String gha0, String gha1, String dec0, String dec1, String decHem, String sd) {
        this.gha0 = gha0;
        this.gha1 = gha1;
        this.dec0 = dec0;
        this.dec1 = dec0;
        this.decHem = decHem;
        this.sd = sd;
    }

    public String getGha0() {
        return gha0;
    }

    public String getGha1() {
        return gha1;
    }

    public String getDec0() {
        return dec0;
    }

    public String getDec1() {
        return dec1;
    }

    public String getDecHem() {
        return decHem;
    }

    public String getSd() {
        return sd;
    }
}
