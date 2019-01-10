package com.sklep.baza;

import org.hibernate.annotations.Type;
import reactor.util.annotation.NonNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "urzytkownicy")
public class Urzytkownicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_urzytkownika")
    private int id_urzytkownika;

    @NonNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NonNull
    @Column(name = "login", nullable = false, unique = true, length = 30)
    private String login;

    @NonNull
    @Column(name = "haslo", nullable = false, length = 100)
    private String haslo;

    @NonNull
    @Column(name = "imie", nullable = false, length = 30)
    private String imie;

    @NonNull
    @Column(name = "nazwisko", nullable = false, length = 30)
    private String nazwisko;

    @NonNull
    @Column(name = "Admin", nullable = false)
    private boolean admin;

    @NonNull
    @Type(type = "date")
    @Column(name = "data_urodzenia", nullable = false)
    private Date data_urodzenia;

    @NonNull
    @Column(name = "nr_telefonu", nullable = false, length = 15)
    private String nr_telefonu;

    public Urzytkownicy(String email, String login, String haslo, String imie, String nazwisko, boolean admin, Date data_urodzenia, String nr_telefonu) {
        this.email = email;
        this.login = login;
        this.haslo = haslo;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.admin = admin;
        this.data_urodzenia = data_urodzenia;
        this.nr_telefonu = nr_telefonu;
    }

    public Urzytkownicy() {
    }

    public int getId_urzytkownika() {
        return id_urzytkownika;
    }

    public void setId_urzytkownika(int id_urzytkownika) {
        this.id_urzytkownika = id_urzytkownika;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Date getData_urodzenia() {
        return data_urodzenia;
    }

    public void setData_urodzenia(Date data_urodzenia) {
        this.data_urodzenia = data_urodzenia;
    }

    public String getNr_telefonu() {
        return nr_telefonu;
    }

    public void setNr_telefonu(String nr_telefonu) {
        this.nr_telefonu = nr_telefonu;
    }

    @Override
    public String toString() {
        return "Urzytkownicy{" +
                "id_urzytkownika=" + id_urzytkownika +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", haslo='" + haslo + '\'' +
                ", imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", admin=" + admin +
                ", data_urodzenia=" + data_urodzenia +
                ", nr_telefonu='" + nr_telefonu + '\'' +
                '}';
    }
}