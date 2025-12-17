package entity;

public class Candle {
    private int id;
    private String candle;
    private double cost;
    private int idProfile;

    public Candle() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCandle() {
        return candle;
    }

    public void setCandle(String candle) {
        this.candle = candle;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(int idProfile) {
        this.idProfile = idProfile;
    }
}

