package tn.org.myhomeapp;

public class Terminal {

    private int maxTemp;
    private int maxHumidity;
    private String id;

    public Terminal(){}

    public Terminal(String id, int maxTemp, int maxHumidity) {
        this.maxTemp = maxTemp;
        this.maxHumidity = maxHumidity;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(int maxTemp) {
        this.maxTemp = maxTemp;
    }

    public int getMaxHumidity() {
        return maxHumidity;
    }

    public void setMaxHumidity(int maxHumidity) {
        this.maxHumidity = maxHumidity;
    }
}
