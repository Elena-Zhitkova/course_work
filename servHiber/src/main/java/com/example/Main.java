package com.example;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.metadata.ClassMetadata;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("сервер запущен....\nожидаем подключение....");
            ServerSocket serverSocket = new ServerSocket(4004);

            while(true) {
                Socket clientAccepted = serverSocket.accept();
                System.out.println("подключение выполнено....");
                (new ServerClientThread(clientAccepted)).run();
            }
        } catch (IOException var4) {
            var4.printStackTrace();
        }
    }
}