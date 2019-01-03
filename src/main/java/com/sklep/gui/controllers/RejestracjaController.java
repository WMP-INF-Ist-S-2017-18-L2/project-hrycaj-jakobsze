package com.sklep.gui.controllers;

import com.jfoenix.controls.*;
import com.sklep.baza.Adresy;
import com.sklep.baza.Kraje;
import com.sklep.baza.Urzytkownicy;
import com.sklep.gui.main.Funkcje;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.TypedQuery;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import static com.sklep.gui.main.Funkcje.hex;

public class RejestracjaController implements Initializable {

    private double xOffSet = 0;
    private double yOffSet = 0;

    @FXML
    private AnchorPane rejestacjaRootAnchorPane;
    @FXML
    private AnchorPane rejestracjaDialogAnchorPane;
    @FXML
    private JFXTextField imie;
    @FXML
    private JFXTextField nazwisko;
    @FXML
    private JFXTextField login;
    @FXML
    private JFXPasswordField haslo;
    @FXML
    private JFXPasswordField p_haslo;
    @FXML
    private JFXTextField nr_telefonu;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXDatePicker data_ur;
    @FXML
    private JFXComboBox kraj;
    @FXML
    private JFXTextField miasto;
    @FXML
    private JFXTextField kod_pocztowy;
    @FXML
    private JFXTextField ulica;
    @FXML
    private JFXTextField nr_domu;
    @FXML
    private JFXButton rejestracjaAnulujButton;
    @FXML
    private JFXButton dialogButton;
    @FXML
    private Label dialogMainLabel;
    @FXML
    private Label dialogBottomLabel;
    @FXML
    private StackPane stackPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void onMousePressed(MouseEvent event) {
        xOffSet=event.getSceneX();
        yOffSet=event.getSceneY();
    }

    @FXML
    private void onMouseDragged(MouseEvent event) {
        imie.getScene().getWindow().setX(event.getScreenX() - xOffSet);
        imie.getScene().getWindow().setY(event.getScreenY() - yOffSet);
        imie.getScene().getWindow().setOpacity(0.9f);
    }

    @FXML
    private void onMouseReleased(MouseEvent event) {
        login.getScene().getWindow().setOpacity(1.0f);
    }

    @FXML
    private void handleAnulujButtonAction(ActionEvent event) {
        closeStage();
    }

    @FXML
    private void handleCloseMark(MouseEvent event) {
        closeStage();
    }

    @FXML
    private void handleMinimizeMark(MouseEvent event) {
        ((Stage) login.getScene().getWindow()).setIconified(true);
    }

    private void closeStage() {
        ((Stage) rejestracjaAnulujButton.getScene().getWindow()).close();
        Funkcje.loadLogowanie();
    }

    @FXML
    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            zarejestruj();
        }
    }

    private Date getDataUr(){
        if(data_ur.getValue()!=null){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            LocalDate dateInString = data_ur.getValue();

            Date date = new Date();

            try {

                date = formatter.parse(String.valueOf(dateInString));

            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE, 1);
            date = c.getTime();

            return date;
        } else {
            return null;
        }

    }

    private void zarejestruj(){
        //Get Session
        Session session = Sesja.sessionFactory.openSession();
        Transaction tx = null;

        try {
            if (!email.getText().equals("") && !login.getText().equals("") && !haslo.getText().equals("") && p_haslo.getText().equals(haslo.getText()) && !imie.getText().equals("") && !nazwisko.getText().equals("") && getDataUr()!=null && !nr_telefonu.getText().equals("")){

                Urzytkownicy nowy_urzytkownik = new Urzytkownicy(email.getText(), login.getText(), hex(haslo.getText()), imie.getText(), nazwisko.getText(), false, getDataUr(), nr_telefonu.getText());

                //start transaction
                tx = session.beginTransaction();
                //Save the Model objects
                session.save(nowy_urzytkownik);
                //Commit transaction
                tx.commit();

                String login1 = login.getText();
                TypedQuery<Urzytkownicy> query = session.createQuery("select u from Urzytkownicy u where LOWER(u.login) = :login1", Urzytkownicy.class);
                query.setParameter("login1", login1.toLowerCase());
                Urzytkownicy urzytkownik = query.getSingleResult();

                TypedQuery<Kraje> query2 = session.createQuery("select k from Kraje k where k.id_kraju = 1", Kraje.class);
                Kraje kraj = query2.getSingleResult();

                Adresy nowy_adres = new Adresy(ulica.getText(), nr_domu.getText(), kod_pocztowy.getText(), miasto.getText(), urzytkownik, kraj);

                //start transaction
                tx = session.beginTransaction();
                //Save the Model objects
                session.save(nowy_adres);
                //Commit transaction
                tx.commit();

                dialogMainLabel.setText("Rejestracja przebiegła pomyślnie");
                dialogBottomLabel.setText("Możesz się teraz zalogować.");
                showDialog();
            }else{
                dialogMainLabel.setText("Rejestracja nie powiodła się");
                dialogBottomLabel.setText("Spróbuj ponownie");
                showDialog();
            }

        } catch (Exception e) {
            System.out.println("Wystąpił wyjątek. " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (!Sesja.sessionFactory.isClosed()) {
                session.close();
            }
        }
    }

    private void showDialog(){
        BoxBlur blur = new BoxBlur(3,3,3);

        rejestracjaDialogAnchorPane.visibleProperty().setValue(true);

        rejestacjaRootAnchorPane.setEffect(blur);
        rejestacjaRootAnchorPane.disableProperty().setValue(true);
    }

    @FXML
    private void handleZarejestrujButtonAction(ActionEvent event) {
        zarejestruj();
    }

    @FXML
    private void handleDialogButtonAction(ActionEvent event) {
        closeStage();
    }

    @FXML
    private void handleKeyPressedAction(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            Funkcje.loadLogowanie();
            closeStage();
        }
    }
}
