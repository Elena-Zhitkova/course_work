package com.example;

public class Amortization {
    private int amortizationId;
    private double monthPerAmort;
    private double yearPerAmort;
    private double monthCostAmort;
    private double yearCostAmort;
    private int capital;
    
    public Amortization() {}
    public Amortization(int amortizationId, double monthPerAmort, double yearPerAmort,
                        double monthCostAmort, double yearCostAmort, int capital) {
        this.amortizationId = amortizationId;
        this.monthCostAmort = monthCostAmort;
        this.monthPerAmort = monthPerAmort;
        this.yearCostAmort = yearCostAmort;
        this.yearPerAmort = yearPerAmort;
        this.capital = capital;
    }

    public double getMonthCostAmort() {
        return monthCostAmort;
    }

    public int getAmortizationId() {
        return amortizationId;
    }

    public double getMonthPerAmort() {
        return monthPerAmort;
    }

    public double getYearCostAmort() {
        return yearCostAmort;
    }

    public double getYearPerAmort() {
        return yearPerAmort;
    }

    public int getCapital() {
        return capital;
    }

    public void setAmortizationId(int amortizationId) {
        this.amortizationId = amortizationId;
    }

    public void setCapital(int capital) {
        this.capital = capital;
    }

    public void setMonthCostAmort(double monthCostAmort) {
        this.monthCostAmort = monthCostAmort;
    }

    public void setMonthPerAmort(double monthPerAmort) {
        this.monthPerAmort = monthPerAmort;
    }

    public void setYearCostAmort(double yearCostAmort) {
        this.yearCostAmort = yearCostAmort;
    }

    public void setYearPerAmort(double yearPerAmort) {
        this.yearPerAmort = yearPerAmort;
    }

    @Override
    public String toString() {
        return "Amortization {" + "amortizationId=" + amortizationId + ", monthPerAmort='" +
                monthPerAmort + '\'' + ", yearCostAmort='" + yearCostAmort + '\'' + ", " +
                "yearPerAmort=" + yearPerAmort + ' ' + ", monthCostAmort=" + monthCostAmort
                + ", capital='" + capital + ' ' + '}';
    }
}
