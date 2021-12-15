package com.example;

public class Users {
    private int userId;
    private String login;
    private String password;
    private int access;
    private int status;

    public Users() {}
    public Users(int userId, String login, String password, int access, int status) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.access = access;
        this.status = status;
    }

    public int getAccess() {
        return access;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public int getUserId() {
        return userId;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Users {" + "userId=" + userId + ", login='" + login + ", password='" + password
                + '\'' + ", access='" + access + ' ' + '}';
    }
}
