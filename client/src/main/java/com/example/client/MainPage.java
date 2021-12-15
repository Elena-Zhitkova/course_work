package com.example.client;


import javafx.beans.Observable;
import javafx.beans.value.ObservableListValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainPage implements Initializable {
    @FXML
    private Button logout;

    @FXML
    private TableView<Capital> capitalTableView;
    @FXML
    private TableView<Amortization> amortizationTableView;
    @FXML
    private TableView<Users> usersTableView;
    @FXML
    private TableColumn<Users, String> usersLogin;
    @FXML
    private TableColumn<Users, String> usersPassword;
    @FXML
    private TableColumn<Users, String> usersAccess;
    @FXML
    private TableColumn<Users, String> usersStatus;
    @FXML
    private TableColumn<Capital, String> capitalName;
    @FXML
    private TableColumn<Capital, Double> capitalPrice;
    @FXML
    private TableColumn<Capital, Integer> capitalUsefullLife;
    @FXML
    private TableColumn<Amortization, String> capitalNameAmort;
    @FXML
    private TableColumn<Amortization, Double> monthPerAmort;
    @FXML
    private TableColumn<Amortization, Double> yearPerAmort;
    @FXML
    private TableColumn<Amortization, Double> monthCostAmort;
    @FXML
    private TableColumn<Amortization, Double> yearCostAmort;
    @FXML
    private Button addCapitalButton;
    @FXML
    private Button deleteCapitalButton;
    @FXML
    private Button updateCapitalButton;
    @FXML
    private TextField nameCapitalTextField;
    @FXML
    private TextField priceCapitalTextField;
    @FXML
    private TextField usefullLifeCapitalTextField;
    @FXML
    private Label errorLabel;
    @FXML
    private Label successLabel;
    @FXML
    private Button chooseButton;
    @FXML
    private Button addUserButton;
    @FXML
    private TextField loginTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField accessTextField;
    @FXML
    private Label errorUsers;
    @FXML
    private Label successUsers;
    @FXML
    private TabPane generalTabPane;
    @FXML
    private Tab capitalTab;
    @FXML
    private Tab amortizationTab;
    @FXML
    private Tab usersTab;
    public static int access = 1;



//    InputStream in = null;
//    OutputStream os = null;


    public void doChart(ActionEvent event) throws IOException {
        MainClient m = new MainClient();
        m.changeScene("diagram.fxml");
    }


    public void changeStatus(ActionEvent event) throws IOException {
        var in = MainClient.socket.getInputStream();
        var os = MainClient.socket.getOutputStream();
        var table = usersTableView.getSelectionModel().getSelectedItem();
        var res = "11 " + "\n" + table.getLogin() + "\n" + table.getPassword() + "\n"
                + table.getStatus() + "\n";

        os.write(res.getBytes());
        var bytes = new byte[1024];
        in.read(bytes);
        var check = new String(bytes, "UTF-8");
        var str1 = check.split(" ");
        if (str1[0].equals("1")){
            errorLabel.setText(" ");
            successLabel.setText("Статус пользователя успешно изменен.");
        } else {
            successLabel.setText(" ");
            errorLabel.setText("Ошибка со стороны сервера.");
        }

        tableViewUsersWriter();
    }

    public void delUser(ActionEvent event) throws IOException {
        var in = MainClient.socket.getInputStream();
        var os = MainClient.socket.getOutputStream();
        var table = usersTableView.getSelectionModel().getSelectedItem();
        var res = "10 " + "\n" + table.getLogin() + "\n" + table.getPassword() + "\n";

        os.write(res.getBytes());
        var bytes = new byte[1024];
        in.read(bytes);
        var check = new String(bytes, "UTF-8");
        var str1 = check.split(" ");
        if (str1[0].equals("1")){
            errorLabel.setText(" ");
            successLabel.setText("Пользователь удален.");
        } else {
            successLabel.setText(" ");
            errorLabel.setText("Ошибка со стороны сервера.");
        }

        tableViewUsersWriter();
    }

    public void addUser(ActionEvent event) throws IOException {
        MainClient m = new MainClient();
        InputStream in = null;
        OutputStream os = null;

        if (loginTextField.getText().isEmpty() && passwordTextField.getText().isEmpty() &&
                accessTextField.getText().isEmpty()) {
            errorUsers.setText("Все поля должны быть заполнены!");
        } else {
            try {
                os = MainClient.socket.getOutputStream();
                in = MainClient.socket.getInputStream();
                var usernameRes = loginTextField.getText();
                var passwordRes = passwordTextField.getText();
                var accessRes = accessTextField.getText();
                System.out.println(usernameRes);
                System.out.println(passwordRes);
                System.out.println(accessRes);
                var res = 9 + " " + usernameRes + " " + passwordRes + " " + accessRes + " ";
                os.write(res.getBytes());
                System.out.println(res.getBytes());
                System.out.println(MainClient.socket.isConnected());
                var bytes = new byte[1024];
                in.read(bytes);
                var check = new String(bytes, "UTF-8");
                var str1 = check.split(" ");
                if (str1[0].equals("1")) {
                    successUsers.setText("Пользоваетль успешно добавлен.");
                    loginTextField.setText("");
                    passwordTextField.setText("");
                    accessTextField.setText("");
                } else if (str1[0].equals("2")) {
                    errorUsers.setText("Что-то пошло не так, попробуйте еще раз.");
                } else {
                    errorUsers.setText("Ошибка сервера.");
                }
                System.out.println(str1[0]);
                tableViewUsersWriter();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    public void userLogout(ActionEvent event) throws IOException {
        MainClient m = new MainClient();
        m.changeScene("hello-view.fxml");
    }

    public void tableViewWriter() {
        capitalName.setCellValueFactory(new PropertyValueFactory<Capital, String>("capitalName"));
        capitalPrice.setCellValueFactory(new PropertyValueFactory<Capital, Double>("capitalPrice"));
        capitalUsefullLife.setCellValueFactory(new PropertyValueFactory<Capital, Integer>("usefullLife"));

        try {
            capitalTableView.setItems(Capital.getCapital());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tableViewAmortizationWriter() {
        capitalNameAmort.setCellValueFactory(new PropertyValueFactory<Amortization, String>("capitalName"));
        yearPerAmort.setCellValueFactory(new PropertyValueFactory<Amortization, Double>("yearPerAmort"));
        yearCostAmort.setCellValueFactory(new PropertyValueFactory<Amortization, Double>("yearCostAmort"));
        monthPerAmort.setCellValueFactory(new PropertyValueFactory<Amortization, Double>("monthPerAmort"));
        monthCostAmort.setCellValueFactory(new PropertyValueFactory<Amortization, Double>("monthCostAmort"));

        try {
            amortizationTableView.setItems(Amortization.getAmortization());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tableViewUsersWriter() {
        usersLogin.setCellValueFactory(new PropertyValueFactory<Users, String>("login"));
        usersPassword.setCellValueFactory(new PropertyValueFactory<Users, String>("password"));
        usersAccess.setCellValueFactory(new PropertyValueFactory<Users, String>("access"));
        usersStatus.setCellValueFactory(new PropertyValueFactory<Users, String>("status"));
        try {
            usersTableView.setItems(Users.getUsers());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void createReport(ActionEvent event) throws IOException
    {
        String str = "Наименование\tГодовой процент\tГодовая цена\tМесячный процент\tМесячная цена";
        for(var item: Amortization.getAmortization())
        {
            str += "\n" + item.getCapitalName() + "\t" +item.getYearPerAmort() + "\t" + item.getYearCostAmort() + "\t" +
                    item.getMonthPerAmort() + "\t" + item.getMonthCostAmort();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter("report.xls"));
        writer.write(str+']');

        writer.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tableViewWriter();
        tableViewAmortizationWriter();
        tableViewUsersWriter();
        if(access == 2){
            generalTabPane.getTabs().get(0).setDisable(true);
            generalTabPane.getTabs().get(2).setDisable(true);
            generalTabPane.getSelectionModel().select(1);
        }


    }

    public void addCapital(ActionEvent event) throws IOException {
        InputStream in = null;
        OutputStream os = null;
        if (nameCapitalTextField.getText().isEmpty() || priceCapitalTextField.getText().isEmpty() ||
                usefullLifeCapitalTextField.getText().isEmpty()) {
            successLabel.setText(" ");
            errorLabel.setText("Заполните, пожалуйста, все поля.");
        } else {
            try {
                //отправляем
                os = MainClient.socket.getOutputStream();
                in = MainClient.socket.getInputStream();
                var name = nameCapitalTextField.getText();
                var usefullLife = usefullLifeCapitalTextField.getText();
                var price = priceCapitalTextField.getText();
                System.out.println(name + "\n" + usefullLife + "\n" + price + "\n");
                var res = 4 + " " + name + " " + usefullLife + " " + price + " ";
                os.write(res.getBytes());
                System.out.println(res.getBytes());
                System.out.println(MainClient.socket.isConnected());

                //получаем
                var bytes = new byte[1024];
                in.read(bytes);
                var check = new String(bytes, "UTF-8");
                var str1 = check.split(" ");
                if (str1[0].equals("1")) {
                    errorLabel.setText(" ");
                    successLabel.setText("Основной фонд успешно добавлен!");
                    tableViewWriter();
                    nameCapitalTextField.setText("");
                    priceCapitalTextField.setText("");
                    usefullLifeCapitalTextField.setText("");
                } else if (str1[0].equals("2")) {
                    errorLabel.setText(" ");
                    successLabel.setText("Ошибка со стороны сервера.");
                } else if (str1[0].equals("3")) {
                    errorLabel.setText(" ");
                    successLabel.setText("Данные не соотвествуют требуемому типа данных.");
                } else {
                    errorLabel.setText(" ");
                    successLabel.setText("Неизвестная ошибка со стороны сервера.");
                }
                System.out.println(str1[0]);
                tableViewWriter();
                tableViewAmortizationWriter();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    public void deleteCapital(ActionEvent event) throws IOException {
        var in = MainClient.socket.getInputStream();
        var os = MainClient.socket.getOutputStream();
        var table = capitalTableView.getSelectionModel().getSelectedItem();
        var res = "5 " + "\n" + table.getCapitalName() + "\n" + table.getCapitalPrice() + "\n"
                + table.getUsefullLife() + "\n";

        os.write(res.getBytes());
        var bytes = new byte[1024];
        in.read(bytes);
        var check = new String(bytes, "UTF-8");
        var str1 = check.split(" ");
        if (str1[0].equals("1")){
            errorLabel.setText(" ");
            successLabel.setText("Запись успешно удалена!");
        } else {
            successLabel.setText(" ");
            errorLabel.setText("Ошибка со стороны сервера.");
        }

        tableViewWriter();
        tableViewAmortizationWriter();
    }

    public void updateCapital(ActionEvent event) throws IOException {
        var in = MainClient.socket.getInputStream();
        var os = MainClient.socket.getOutputStream();

        var res =oldRes + nameCapitalTextField.getText() + "\n" + priceCapitalTextField.getText()
                + "\n" + usefullLifeCapitalTextField.getText() + "\n";
        os.write(res.getBytes());

        var bytes = new byte[1024];
        in.read(bytes);
        var check = new String(bytes, "UTF-8");
        var str1 = check.split(" ");
        if (str1[0].equals("1")){
            errorLabel.setText(" ");
            successLabel.setText("Запись успешно обновлена!");
            tableViewAmortizationWriter();
            tableViewWriter();
        } else {
            successLabel.setText(" ");
            errorLabel.setText("Ошибка со стороны сервера.");
        }
    }

    public String oldRes;
    public void choose(ActionEvent event) throws IOException {
        var table = capitalTableView.getSelectionModel().getSelectedItem();
        if (table != null) {
            nameCapitalTextField.setText(table.getCapitalName());
            priceCapitalTextField.setText(String.valueOf(table.getCapitalPrice()));
            usefullLifeCapitalTextField.setText(String.valueOf(table.getUsefullLife()));
            oldRes = "6 " + "\n" + nameCapitalTextField.getText() + "\n" + priceCapitalTextField.getText()
                    + "\n" + usefullLifeCapitalTextField.getText() + "\n";
            successLabel.setText(" ");
            errorLabel.setText(" ");
        } else {
            successLabel.setText(" ");
            errorLabel.setText("Выберите строку для изменения.");
        }
    }
}
