package com.sklep.gui.main;

import com.sklep.gui.controllers.Sesja;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Test extends Application {

    public static Thread nowaSesja;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/logowanie.fxml"));

        Scene scene = new Scene(root);

        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);

        stage.setScene(scene);
        stage.show();
        stage.setTitle("Sklep logowanie");

        Thread t = new Thread(() -> {
            Sesja sesja = new Sesja();
            nowaSesja = new Thread(sesja);
            nowaSesja.start();
        });
        t.start();
    }
}