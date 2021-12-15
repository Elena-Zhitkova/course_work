package com.example.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

public class Amortization {
    private double monthPerAmort;
    private double yearPerAmort;
    private double monthCostAmort;
    private double yearCostAmort;
    private String capitalName;

    public static ObservableList<Amortization> getAmortization() throws IOException {
        var os = MainClient.socket.getOutputStream();
        var is = MainClient.socket.getInputStream();
        var res = 7 + " ";
        os.write(res.getBytes());
        var bytes = new byte[1024];
        is.read(bytes);
        var check = new String(bytes, "UTF-8");
        var str1 = check.split("\n");

        ObservableList<Amortization> amortizations = FXCollections.observableArrayList();
        for (int i = 2; i < str1.length -1; i += 5) {
            amortizations.add(new Amortization(Double.parseDouble(str1[i]),
                    Double.parseDouble(str1[i+1]), Double.parseDouble(str1[i+2]),
                    Double.parseDouble(str1[i+3]), str1[i + 4]));
        }
        return amortizations;
    }

    public Amortization() {}
    public Amortization( double monthPerAmort, double yearPerAmort,
                        double monthCostAmort, double yearCostAmort, String capital) {
        this.monthCostAmort = monthCostAmort;
        this.monthPerAmort = monthPerAmort;
        this.yearCostAmort = yearCostAmort;
        this.yearPerAmort = yearPerAmort;
        this.capitalName = capital;
    }

    public String getCapitalName() {
        return capitalName;
    }

    public void setCapitalName(String capitalName) {
        this.capitalName = capitalName;
    }

    public double getMonthCostAmort() {
        return monthCostAmort;
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

    public String getCapital() {
        return capitalName;
    }

    public void setCapital(String capital) {
        this.capitalName = capital;
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
}
