package com.example;

public class Capital {
    private int capitalId;
    private String capitalName;
    private double capitalPrice;
    private int usefullLife;

    public Capital() {}
    public Capital(int capitalId, String capitalName, double capitalPrice, int usefullLife) {
        this.capitalId = capitalId;
        this.capitalName = capitalName;
        this.capitalPrice = capitalPrice;
        this.usefullLife = usefullLife;
    }

    public int getCapitalId() {
        return capitalId;
    }

    public double getCapitalPrice() {
        return capitalPrice;
    }

    public void setCapitalId(int capitalId) {
        this.capitalId = capitalId;
    }

    public int getUsefullLife() {
        return usefullLife;
    }

    public String getCapitalName() {
        return capitalName;
    }

    public void setCapitalName(String capitalName) {
        this.capitalName = capitalName;
    }

    public void setCapitalPrice(double capitalPrice) {
        this.capitalPrice = capitalPrice;
    }

    public void setUsefullLife(int usefullLife) {
        this.usefullLife = usefullLife;
    }
}
