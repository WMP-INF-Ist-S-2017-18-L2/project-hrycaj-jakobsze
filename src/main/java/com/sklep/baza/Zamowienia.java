package com.sklep.baza;

import reactor.util.annotation.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "zamowienia")
public class Zamowienia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_zamowienia")
    private int id_zamowienia;

    @NonNull
    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "id_urzytkownika", nullable = false)
    private Urzytkownicy id_urzytkownika;

    public Zamowienia(String status, Urzytkownicy id_urzytkownika) {
        this.status = status;
        this.id_urzytkownika = id_urzytkownika;
    }

    public Zamowienia() {
    }

    public int getId_zamowienia() {
        return id_zamowienia;
    }

    public void setId_zamowienia(int id_zamowienia) {
        this.id_zamowienia = id_zamowienia;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Urzytkownicy getId_urzytkownika() {
        return id_urzytkownika;
    }

    public void setId_urzytkownika(Urzytkownicy id_urzytkownika) {
        this.id_urzytkownika = id_urzytkownika;
    }

    @Override
    public String toString() {
        return "Zamowienia{" +
                "id_zamowienia=" + id_zamowienia +
                ", status='" + status + '\'' +
                ", id_urzytkownika=" + id_urzytkownika +
                '}';
    }
}
