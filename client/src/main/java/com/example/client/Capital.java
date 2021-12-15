package com.example.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.Inet4Address;

public class Capital {
    private int capitalId;
    private String capitalName;
    private double capitalPrice;
    private Integer usefullLife;

    public static ObservableList<Capital>  getCapital() throws IOException {
        var os = MainClient.socket.getOutputStream();
        var is = MainClient.socket.getInputStream();
        var res = 3 + " ";
        os.write(res.getBytes());
        var bytes = new byte[1024];
        is.read(bytes);
        var check = new String(bytes, "UTF-8");
        var str1 = check.split("\n");

        ObservableList<Capital> capital = FXCollections.observableArrayList();
        for (int i = 2; i < str1.length - 2; i += 3) {
            capital.add(new Capital(str1[i], Double.parseDouble(str1[i+1]), Integer.parseInt(str1[i+2])));
        }
        return capital;
    }

    public Capital() {}
    public Capital(String capitalName, double capitalPrice, Integer usefullLife) {
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
