package com.sklep.gui.controllers;

import com.jfoenix.controls.*;
import com.sklep.baza.Adresy;
import com.sklep.baza.Kraje;
import com.sklep.baza.Urzytkownicy;
import com.sklep.gui.main.Funkcje;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.commons.text.WordUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static com.sklep.gui.main.Funkcje.hex;
import static com.sklep.gui.main.Test.nowaSesja;

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
    private JFXButton dialogButton;
    @FXML
    private Label dialogMainLabel;
    @FXML
    private Label dialogBottomLabel;
    @FXML
    private StackPane stackPane;
    @FXML
    private JFXSpinner spiner;

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
        ((Stage) imie.getScene().getWindow()).close();
        Funkcje.loadLogowanie();
    }

    private Date getDataUr() {
        if (data_ur.getValue() != null) {
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

    private boolean sprawdzDlugosc(JFXTextField field, int minRozmiar) {
        if (field.getText().length() < minRozmiar) {
            field.getStyleClass().removeAll("correct");
            field.getStyleClass().add("wrong");
            return false;
        } else {
            field.getStyleClass().removeAll("wrong");
            field.getStyleClass().add("correct");
            return true;
        }
    }

    private boolean sprawdzImie() {
        if (!sprawdzDlugosc(imie, 2)) {
            imie.setTooltip(new Tooltip("Imie musi być dłuższe od 2 znaków"));
            return false;
        } else {
            imie.setTooltip(null);
            return true;
        }
    }

    private boolean sprawdzNazwisko() {
        if (!sprawdzDlugosc(nazwisko, 2)) {
            nazwisko.setTooltip(new Tooltip("Nazwisko musi być dłuższe od 2 znaków"));
            return false;
        } else {
            nazwisko.setTooltip(null);
            return true;
        }
    }

    private boolean sprawdzLogin() {
        Session session = Sesja.sessionFactory.openSession();

        if (!sprawdzDlugosc(login, 3)) {
            login.setTooltip(new Tooltip("Login musi być dłuższy od 3 znaków"));
            return false;
        } else {
            try {
                login.getStyleClass().removeAll("correct");
                login.getStyleClass().add("wrong");
                login.setTooltip(new Tooltip("Login jest już zajęty"));
                TypedQuery<Urzytkownicy> query = session.createQuery("select u from Urzytkownicy u where LOWER(u.login) = :login1", Urzytkownicy.class);
                query.setParameter("login1", login.getText().toLowerCase());
                query.getSingleResult();

                return false;
            } catch (NoResultException e) {
                login.getStyleClass().removeAll("wrong");
                login.getStyleClass().add("correct");
                login.setTooltip(null);
                return true;
            }
        }
    }

    private boolean sprawdzPHaslo() {
        if (!p_haslo.getText().equals(haslo.getText()) || p_haslo.getText().length() == 0) {
            p_haslo.getStyleClass().removeAll("correct");
            p_haslo.getStyleClass().removeAll("wrong");
            p_haslo.getStyleClass().add("wrong");
            haslo.getStyleClass().removeAll("correct");
            haslo.getStyleClass().removeAll("wrong");
            haslo.getStyleClass().add("wrong");
            haslo.setTooltip(new Tooltip("Hasła różnią się od siebie"));
            p_haslo.setTooltip(new Tooltip("Hasła różnią się od siebie"));
            return false;
        } else {
            p_haslo.getStyleClass().removeAll("wrong");
            p_haslo.getStyleClass().add("correct");
            haslo.getStyleClass().removeAll("wrong");
            haslo.getStyleClass().add("correct");
            p_haslo.setTooltip(null);
            return true;
        }
    }

    private boolean sprawdzHaslo() {
        if (haslo.getText().length() < 6) {
            haslo.getStyleClass().removeAll("correct");
            haslo.getStyleClass().add("wrong");
            haslo.setTooltip(new Tooltip("Hasło musi być dłuższe od 6 znaków"));
            return false;
        } else {
            haslo.getStyleClass().removeAll("wrong");
            haslo.getStyleClass().add("correct");
            //haslo.setTooltip(null);
            return true;
        }
    }

    private boolean sprawdzMiasto() {
        if (!sprawdzDlugosc(miasto, 3)) {
            miasto.setTooltip(new Tooltip("Podana nazwa jest za krótka"));
            return false;
        } else {
            miasto.setTooltip(null);
            return true;
        }
    }

    private boolean sprawdzNrTelefonu() {
        if (!sprawdzDlugosc(nr_telefonu, 9)) {
            nr_telefonu.setTooltip(new Tooltip("Podany numer telefonu jest zbyt krótki"));
            return false;
        } else {
            nr_telefonu.setTooltip(null);
            return true;
        }
    }

    private boolean sprawdzEmail() {
        Session session = Sesja.sessionFactory.openSession();
        if (!sprawdzDlugosc(email, 5)) {
            email.getStyleClass().removeAll("correct");
            email.getStyleClass().add("wrong");
            email.setTooltip(new Tooltip("Wpisany email jest nieprawidłowy"));
            return false;
        } else {
            try {
                email.getStyleClass().removeAll("correct");
                email.getStyleClass().add("wrong");
                email.setTooltip(new Tooltip("Konto z takim emailem już istnieje"));
                TypedQuery<Urzytkownicy> query = session.createQuery("select u from Urzytkownicy u where LOWER(u.email) = :email", Urzytkownicy.class);
                query.setParameter("email", email.getText().toLowerCase());
                query.getSingleResult();

                return false;
            } catch (NoResultException e) {
                String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                        "[a-zA-Z0-9_+&*-]+)*@" +
                        "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                        "A-Z]{2,7}$";

                Pattern pat = Pattern.compile(emailRegex);

                if (pat.matcher(email.getText()).matches()) {
                    email.getStyleClass().removeAll("wrong");
                    email.getStyleClass().add("correct");
                    email.setTooltip(null);
                    return true;
                } else {
                    email.getStyleClass().removeAll("correct");
                    email.getStyleClass().add("wrong");
                    email.setTooltip(new Tooltip("Wpisany email jest nieprawidłowy"));
                    return false;
                }
            }
        }

    }

    private boolean sprawdzDate() {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime thirteenYearsAgo = now.minusYears(13);

        if (getDataUr() != null) {
            if (getDataUr().toInstant().isBefore(thirteenYearsAgo.toInstant())) {
                data_ur.getStyleClass().removeAll("wrong");
                data_ur.getStyleClass().add("correct");
                data_ur.setTooltip(null);
                return true;
            } else {
                data_ur.getStyleClass().removeAll("correct");
                data_ur.getStyleClass().add("wrong");
                data_ur.setTooltip(new Tooltip("Musisz mieć więcej niż 13 lat aby się zarejestrować"));
                return false;
            }
        } else {
            data_ur.getStyleClass().removeAll("correct");
            data_ur.getStyleClass().add("wrong");
            data_ur.setTooltip(new Tooltip("Musisz mieć więcej niż 13 lat aby się zarejestrować"));
            return false;
        }
    }

    private boolean sprawdzKodPocztowy() {
        if (sprawdzDlugosc(kod_pocztowy, 6)) {
            String kodPocztowyRegex = "^\\d{2}-\\d{3}$";

            Pattern pat = Pattern.compile(kodPocztowyRegex);

            if (pat.matcher(kod_pocztowy.getText()).matches()) {
                kod_pocztowy.getStyleClass().removeAll("wrong");
                kod_pocztowy.getStyleClass().add("correct");
                kod_pocztowy.setTooltip(null);
                return true;
            } else {
                kod_pocztowy.getStyleClass().removeAll("correct");
                kod_pocztowy.getStyleClass().add("wrong");
                kod_pocztowy.setTooltip(new Tooltip("Kod pocztowy jest nieprawidłowy: XX-XXX"));
                return false;
            }
        } else {
            kod_pocztowy.getStyleClass().removeAll("correct");
            kod_pocztowy.getStyleClass().add("wrong");
            kod_pocztowy.setTooltip(new Tooltip("Kod pocztowy jest nieprawidłowy: XX-XXX"));
            return false;
        }
    }

    private boolean sprawdzUlica() {
        if (!sprawdzDlugosc(ulica, 3)) {
            ulica.setTooltip(new Tooltip("Wpisana nazwa jest zbyt krótka"));
            return false;
        } else {
            ulica.setTooltip(null);
            return true;
        }
    }

    private boolean sprawdzNrDomu() {
        if (!sprawdzDlugosc(nr_domu, 1)) {
            nr_domu.setTooltip(new Tooltip("Pole nie może być puste"));
            return false;
        } else {
            nr_domu.setTooltip(null);
            return true;
        }
    }

    private void zarejestruj() {
        Thread t = new Thread(() -> {
            try {

                BoxBlur blur = new BoxBlur(3, 3, 3);

                rejestacjaRootAnchorPane.setEffect(blur);
                rejestacjaRootAnchorPane.disableProperty().setValue(true);
                spiner.visibleProperty().setValue(true);

                while (nowaSesja.isAlive()) {
                    Thread.sleep(500);
                }

                //Get Session
                Session session = Sesja.sessionFactory.openSession();
                Transaction tx;

                sprawdzImie();
                sprawdzNazwisko();
                sprawdzLogin();
                sprawdzPHaslo();
                sprawdzHaslo();
                sprawdzEmail();
                sprawdzNrTelefonu();
                sprawdzDate();
                sprawdzMiasto();
                sprawdzKodPocztowy();
                sprawdzUlica();
                sprawdzNrDomu();

                try {
                    if (sprawdzImie() && sprawdzNazwisko() && sprawdzLogin() && sprawdzPHaslo() && sprawdzHaslo() && sprawdzEmail() && sprawdzNrTelefonu() && sprawdzDate() && sprawdzMiasto() && sprawdzKodPocztowy() && sprawdzUlica() && sprawdzNrDomu()) {

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


                        Platform.runLater(() -> {
                            rejestacjaRootAnchorPane.setEffect(blur);
                            rejestacjaRootAnchorPane.disableProperty().setValue(true);

                            //dialogMainLabel.setText("Rejestracja przebiegła pomyślnie");
                            //dialogBottomLabel.setText("Możesz się teraz zalogować.");
                            rejestracjaDialogAnchorPane.visibleProperty().setValue(true);
                        });

                    } else {
                        Platform.runLater(() -> {
                            rejestacjaRootAnchorPane.setEffect(null);

                            rejestacjaRootAnchorPane.disableProperty().setValue(false);
                            spiner.visibleProperty().setValue(false);
                        });
                    }
                } catch (Exception e) {
                    System.out.println("Wystąpił wyjątek. " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    if (!Sesja.sessionFactory.isClosed()) {
                        session.close();
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
                e.printStackTrace();
            }
        });
        t.start();

        rejestacjaRootAnchorPane.requestFocus();
    }

//    private void showDialog() {
//        BoxBlur blur = new BoxBlur(3, 3, 3);
//
//        rejestracjaDialogAnchorPane.visibleProperty().setValue(true);
//
//        rejestacjaRootAnchorPane.setEffect(blur);
//        rejestacjaRootAnchorPane.disableProperty().setValue(true);
//    }

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

    @FXML
    private void kodPocztowyHandleKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            zarejestruj();
        }

        kod_pocztowy.textProperty().addListener((observable, oldValue, newValue) -> {
            if (kod_pocztowy.getText().length() > 6) {
                String s = kod_pocztowy.getText().substring(0, 6);
                kod_pocztowy.setText(s);
            } else if (!newValue.matches("\\d*|-")) {
                if (kod_pocztowy.getText().isBlank()) {
                    kod_pocztowy.setText(newValue.replaceAll("[^\\d*]", ""));
                } else if (kod_pocztowy.getText().endsWith("--")) {
                    kod_pocztowy.setText(newValue.replace("--", "-"));
                } else {
                    kod_pocztowy.setText(newValue.replaceAll("[^\\d|-]", ""));
                }
            }
        });
    }

    private void sprawdzImieINazwisko(JFXTextField pole) {
        pole.textProperty().addListener((observable, oldValue, newValue) -> {
            if (pole.getText().length() > 30) {
                String s = pole.getText().substring(0, 30);
                pole.setText(s);
            } else {
                if (!newValue.matches("A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ*")) {
                    pole.setText(newValue.replaceAll("[^A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]", ""));
                }

                if (!pole.getText().isBlank()) {
                    String cap = pole.getText().substring(0, 1).toUpperCase() + pole.getText().substring(1).toLowerCase();
                    pole.setText(cap);
                }
            }
        });
    }

    @FXML
    private void loginHandleKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            zarejestruj();
        }

        login.textProperty().addListener((observable, oldValue, newValue) -> {
            if (login.getText().length() > 30) {
                String s = login.getText().substring(0, 30);
                login.setText(s);
            } else {
                if (!newValue.matches("a-zA-Z_0-9-zżźćńółęąśŻŹĆĄŚĘŁÓŃ*")) {
                    login.setText(newValue.replaceAll("[^a-zA-Z_0-9-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]", ""));
                }
            }
        });
    }

    @FXML
    private void imieHandleKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            zarejestruj();
        }
        sprawdzImieINazwisko(imie);
    }

    @FXML
    private void nazwiskoHandleKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            zarejestruj();
        }
        sprawdzImieINazwisko(nazwisko);
    }

    @FXML
    private void hasloHandleKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            zarejestruj();
        }

        haslo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (haslo.getText().length() > 100) {
                String s = haslo.getText().substring(0, 100);
                haslo.setText(s);
            }
        });
    }

    @FXML
    private void numerTelefonuHandleKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            zarejestruj();
        }

        nr_telefonu.textProperty().addListener((observable, oldValue, newValue) -> {
            if (nr_telefonu.getText().length() > 9) {
                String s = nr_telefonu.getText().substring(0, 9);
                nr_telefonu.setText(s);
            } else {
                if (!newValue.matches("\\d*")) {
                    nr_telefonu.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    @FXML
    private void emailHandleKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            zarejestruj();
        }

        email.textProperty().addListener((observable, oldValue, newValue) -> {
            if (email.getText().length() > 255) {
                String s = email.getText().substring(0, 255);
                email.setText(s);
            } else {
                email.setText(newValue.replaceAll("[^a-zA-Z0-9_+&*@.-]", ""));
            }
        });
    }

    @FXML
    private void miastoHandleKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            zarejestruj();
        }

        miasto.textProperty().addListener((observable, oldValue, newValue) -> {
            if (miasto.getText().length() > 100) {
                String s = miasto.getText().substring(0, 100);
                miasto.setText(s);
            } else {
                if (miasto.getText().length() <= 1) {
                    miasto.setText(newValue.replaceAll("[^a-zA-ZżźćńółęąśŻŹĆĄŚĘŁÓŃ]", ""));
                } else if (miasto.getText().endsWith("  ")) {
                    miasto.setText(newValue.replace("  ", " "));
                } else if (miasto.getText().endsWith(" -")) {
                    miasto.setText(newValue.replace(" -", " "));
                } else if (miasto.getText().endsWith("--")) {
                    miasto.setText(newValue.replace("--", "-"));
                } else if (miasto.getText().endsWith("- ")) {
                    miasto.setText(newValue.replace("- ", "-"));
                } else {
                    miasto.setText(newValue.replaceAll("[^a-zA-Z żźćńółęąśŻŹĆĄŚĘŁÓŃ-]", ""));
                }
                miasto.setText(WordUtils.capitalizeFully(miasto.getText(), ' ', '-'));
            }
        });
    }

    @FXML
    private void ulicaHandleKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            zarejestruj();
        }

        ulica.textProperty().addListener((observable, oldValue, newValue) -> {
            if (ulica.getText().length() > 100) {
                String s = ulica.getText().substring(0, 100);
                ulica.setText(s);
            } else {
                if (ulica.getText().length() <= 1) {
                    ulica.setText(newValue.replaceAll("[^a-zA-Z0-9żźćńółęąśŻŹĆĄŚĘŁÓŃ]", ""));
                } else if (ulica.getText().endsWith("  ")) {
                    ulica.setText(newValue.replace("  ", " "));
                } else {
                    ulica.setText(newValue.replaceAll("[^a-zA-Z0-9żźćńółęąśŻŹĆĄŚĘŁÓŃ -]", ""));
                }
                ulica.setText(WordUtils.capitalizeFully(ulica.getText(), ' '));
            }
        });
    }

    @FXML
    private void numerDomuHandleKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            zarejestruj();
        }

        nr_domu.textProperty().addListener((observable, oldValue, newValue) -> {
            if (nr_domu.getText().length() > 5) {
                String s = nr_domu.getText().substring(0, 5);
                nr_domu.setText(s);
            } else {
                nr_domu.setText(newValue.replaceAll("[^a-zA-Z0-9/-]", ""));
            }
        });
    }

    @FXML
    private void dataUrodzeniaHandleKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            zarejestruj();
        }
    }
}
