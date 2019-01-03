package com.sklep.gui.main;

import com.sklep.gui.controllers.Sesja;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/logowanie.fxml"));

        //JFXDecorator decorator = new JFXDecorator(stage, root);
        Scene scene = new Scene(root);

        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);

        stage.setScene(scene);
        stage.show();
        stage.setTitle("Sklep logowanie");

        new Sesja();

        //LibraryAssistantUtil.setStageIcon(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}