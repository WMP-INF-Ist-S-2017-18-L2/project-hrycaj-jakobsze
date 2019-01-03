package com.sklep.gui.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Funkcje {

    public static void loadLogowanie() {
        try {
            Parent parent = FXMLLoader.load(Funkcje.class.getResource("/fxml/logowanie.fxml"));
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setTitle("Logowanie");
            stage.setScene(new Scene(parent));
            stage.show();

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public static void loadRejestracja() {
        try {
            Parent parent = FXMLLoader.load(Funkcje.class.getResource("/fxml/rejestracja.fxml"));
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setTitle("Rejestracja");
            stage.setScene(new Scene(parent));
            stage.show();

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public static void loadSklepMain() {
        try {
            Parent parent = FXMLLoader.load(Funkcje.class.getResource("/fxml/sklep.fxml"));
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setTitle("Sklep");
            stage.setScene(new Scene(parent));
            stage.show();

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    /*public static void loadDialog(){
        try {
            Parent parent = FXMLLoader.load(Funkcje.class.getResource("/fxml/dialog.fxml"));
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setTitle("Dialog");
            stage.setScene(new Scene(parent));
            stage.show();

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }*/

    public static String hex(String haslo) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        md.update(haslo.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();

        String hex = String.format("%064x", new BigInteger(1, digest));

        return hex;
    }

}
