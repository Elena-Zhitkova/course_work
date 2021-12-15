package com.example.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Login {
    public Login() {

    }

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginButton;

    @FXML Button signupButton;

    @FXML
    private Label wrongLogin;


    public void UserLogin(ActionEvent event) throws IOException {
        checkLogin(1);
    }

    public void UserSignup(ActionEvent event) throws IOException {
        checkLogin(2);
    }

    private void checkLogin(int func) throws IOException {
        MainClient m = new MainClient();
        InputStream in = null;
        OutputStream os = null;

        if (username.getText().isEmpty() && password.getText().isEmpty()) {
            wrongLogin.setText("Введите логин и пароль!");
        } else {
            try {
                os = MainClient.socket.getOutputStream();
                in = MainClient.socket.getInputStream();
                var usernameRes = username.getText();
                var passwordRes = password.getText();
                System.out.println(usernameRes);
                System.out.println(passwordRes);
                var res = func + " " + usernameRes + " " + passwordRes + " ";
                os.write(res.getBytes());
                System.out.println(res.getBytes());
                System.out.println(MainClient.socket.isConnected());
                var bytes = new byte[1024];
                in.read(bytes);
                var check = new String(bytes, "UTF-8");
                var str1 = check.split(" ");
                if (str1[0].equals("1")) {
                    wrongLogin.setText("Успех!");
                    MainPage.access = 1;
                    m.changeScene("mainPage.fxml");
                } else if (str1[0].equals("2")) {
                    wrongLogin.setText("Что-то пошло не так, попробуйте еще раз.");
                }else if (str1[0].equals("3")) {
                    wrongLogin.setText("Успех!");
                    MainPage.access = 2;
                    m.changeScene("mainPage.fxml");
                }
                else {
                    wrongLogin.setText("Ошибка сервера.");
                }
                System.out.println(str1[0]);

            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}