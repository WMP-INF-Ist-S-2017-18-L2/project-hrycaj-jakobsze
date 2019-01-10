package com.sklep.gui.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import com.sklep.baza.Urzytkownicy;
import com.sklep.gui.main.Funkcje;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.net.URL;
import java.util.ResourceBundle;

import static com.sklep.gui.main.Funkcje.hex;
import static com.sklep.gui.main.Test.nowaSesja;

public class LogowanieController implements Initializable {

    private double xOffSet = 0;
    private double yOffSet = 0;
    @FXML
    private JFXTextField login;
    @FXML
    private JFXPasswordField haslo;
    @FXML
    private JFXSpinner spiner;
    @FXML
    private AnchorPane childAnchorPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void onMousePressed(MouseEvent event) {
        xOffSet = event.getSceneX();
        yOffSet = event.getSceneY();
    }

    @FXML
    private void onMouseDragged(MouseEvent event) {
        login.getScene().getWindow().setX(event.getScreenX() - xOffSet);
        login.getScene().getWindow().setY(event.getScreenY() - yOffSet);
        login.getScene().getWindow().setOpacity(0.9f);
    }

    @FXML
    private void onMouseReleased(MouseEvent event) {
        login.getScene().getWindow().setOpacity(1.0f);
    }

    @FXML
    private void handleZalogujButtonAction(ActionEvent event) {
        zaloguj();
    }

    private void zaloguj() {

        Thread t = new Thread(() -> {
            try {
                childAnchorPane.disableProperty().setValue(true);
                spiner.visibleProperty().setValue(true);

                BoxBlur blur = new BoxBlur(3, 3, 3);
                childAnchorPane.setEffect(blur);

                while (nowaSesja.isAlive()) {
                    Thread.sleep(500);
                }
                //Get Session
                Session session = Sesja.sessionFactory.openSession();
                try {
                    String login1 = login.getText();
                    String haslo1 = hex(haslo.getText());

                    if (!login1.equals("") && !haslo.getText().equals("")) {
                        TypedQuery<Urzytkownicy> query = session.createQuery("select u from Urzytkownicy u where LOWER(u.login) = :login1", Urzytkownicy.class);
                        query.setParameter("login1", login1.toLowerCase());
                        Urzytkownicy urzytkownik = query.getSingleResult();

                        if (haslo1.equals(urzytkownik.getHaslo())) {
                            Platform.runLater(() -> {
                                closeStage();
                                Funkcje.loadSklepMain();
                            });
                        }
                    }
                } catch (NoResultException e) {
                    System.out.println("Brak urzytkownika o takim loginie");
                } catch (Exception e) {
                    System.out.println("Wystąpił wyjątek. " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    if (!Sesja.sessionFactory.isClosed()) {
                        session.close();
                        login.getStyleClass().add("wrong");
                        haslo.getStyleClass().add("wrong");
                    }
                }

                childAnchorPane.disableProperty().setValue(false);
                spiner.visibleProperty().setValue(false);
                childAnchorPane.setEffect(null);

            } catch (InterruptedException e) {
                System.out.println("Interrupted");
                e.printStackTrace();
            }
        });
        t.start();
    }

    @FXML
    private void handleAnulujButtonAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void handleCloseMark(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void handleMinimizeMark(MouseEvent event) {
        ((Stage) login.getScene().getWindow()).setIconified(true);
    }

    private void closeStage() {
        ((Stage) login.getScene().getWindow()).close();
    }

    @FXML
    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            zaloguj();
        }
    }

    @FXML
    private void handleZarejestrujButton(MouseEvent event) {
        closeStage();
        Funkcje.loadRejestracja();
    }
}
