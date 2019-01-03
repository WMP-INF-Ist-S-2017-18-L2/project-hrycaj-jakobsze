package com.sklep.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.sklep.gui.main.Funkcje;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DialogController implements Initializable {
    private double xOffSet = 0;
    private double yOffSet = 0;

    @FXML
    private Label dialogMainLabel;
    @FXML
    private Label dialogBottomLabel;
    @FXML
    private JFXButton dialogButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setDialogMainLabel("Konto zostało pomyślnie utworzone");
        setDialogBottomLabel("Możesz się teraz zalogować!");
        setDialogButton("Wróć do logowania");
    }

    @FXML
    private void onMousePressed(MouseEvent event) {
        xOffSet=event.getSceneX();
        yOffSet=event.getSceneY();
    }

    @FXML
    private void onMouseDragged(MouseEvent event) {
        dialogButton.getScene().getWindow().setX(event.getScreenX() - xOffSet);
        dialogButton.getScene().getWindow().setY(event.getScreenY() - yOffSet);
        dialogButton.getScene().getWindow().setOpacity(0.9f);
    }

    @FXML
    private void onMouseReleased(MouseEvent event) {
        dialogButton.getScene().getWindow().setOpacity(1.0f);
    }

    @FXML
    private void handleDialogButtonAction(ActionEvent event) {
        Funkcje.loadLogowanie();
        closeStage();
    }

    private void setDialogMainLabel(String tekst){
        dialogMainLabel.setText(tekst);
    }

    private void setDialogBottomLabel(String tekst){
        dialogBottomLabel.setText(tekst);
    }

    private void setDialogButton(String tekst){
        dialogButton.setText(tekst);
    }

    private void closeStage() {
        ((Stage) dialogButton.getScene().getWindow()).close();
    }

    @FXML
    private void handleKeyPressedAction(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            Funkcje.loadLogowanie();
            closeStage();
        }
    }
}
