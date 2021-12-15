package com.example.client;

import javafx.application.Application;
import javafx.collections.ObservableArray;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class MainClient extends Application {

    private static Stage stg;
    public static Socket socket = null;

    @Override
    public void start(Stage stage) throws IOException {
        stg = stage;
        var a = getClass().getResource("hello-view.fxml");
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Система учета амортизации основных средств предприятия");
        stage.setScene(scene);
        stage.show();
    }
    public void changeScene(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainClient.class.getResource(fxml));
        Parent pane = fxmlLoader.load();
        stg.getScene().setRoot(pane);
        stg.sizeToScene();
    }

    public static void main(String[] args) {
        try {
            socket = new Socket("localhost", 4004);
            System.out.println(socket.isConnected());
        } catch (IOException e) {
            System.out.println(e);
        }
        launch(args);
    }



}