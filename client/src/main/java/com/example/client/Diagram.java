package com.example.client;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Diagram implements Initializable {
    @FXML
    private PieChart pieChart;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<PieChart.Data> pieChartData =
                    FXCollections.observableArrayList();
            var ams = Amortization.getAmortization();
            for(var item: ams)
            {
                pieChartData.add(new PieChart.Data(item.getCapitalName(), item.getYearCostAmort()));
            }

            pieChart.getData().addAll(pieChartData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void okAction(ActionEvent event) throws IOException {

        MainClient m = new MainClient();
        m.changeScene("mainPage.fxml");
    }
}
