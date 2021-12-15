package com.example.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

public class Users {
    private String login;
    private String password;
    private String  access;
    private String status;

    public Users() {}
    public Users(String login, String password, String access, String status) {

        this.login = login;
        this.password = password;
        this.access = access;
        this.status = status;
    }
    public static ObservableList<Users> getUsers() throws IOException {
        var os = MainClient.socket.getOutputStream();
        var is = MainClient.socket.getInputStream();
        var res = 8 + " ";
        os.write(res.getBytes());
        var bytes = new byte[1024];
        is.read(bytes);
        var check = new String(bytes, "UTF-8");
        var str1 = check.split("\n");

        ObservableList<Users> users = FXCollections.observableArrayList();
        for (int i = 2; i < str1.length -1; i += 4) {
            users.add(new Users(str1[i], str1[i+1], str1[i+2], str1[i+3]));
        }
        return users;
    }

    public String getAccess() {
        return access;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Users {" +  ", login='" + login + ", password='" + password
                + '\'' + ", access='" + access + ' ' + '}';
    }
}
