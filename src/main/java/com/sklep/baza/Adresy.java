package com.sklep.baza;


import reactor.util.annotation.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "adresy")
public class Adresy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_adresu")
    private int id_adresu;

    @NonNull
    @Column(name = "ulica", nullable=false)
    private String ulica;

    @NonNull
    @Column(name = "nr_domu", nullable=false, length = 5)
    private String nr_domu;

    @NonNull
    @Column(name = "kod_pocztowy", nullable=false, length = 10)
    private String kod_pocztowy;

    @NonNull
    @Column(name = "miasto", nullable=false , length = 100)
    private String miasto;

    @ManyToOne
    @JoinColumn(name="id_urzytkownika", nullable=false)
    private Urzytkownicy id_urzytkownika;

    @ManyToOne
    @JoinColumn(name="id_kraju", nullable=false)
    private Kraje id_kraju;

    public Adresy(String ulica, String nr_domu, String kod_pocztowy, String miasto, Urzytkownicy id_urzytkownika, Kraje id_kraju) {
        this.ulica = ulica;
        this.nr_domu = nr_domu;
        this.kod_pocztowy = kod_pocztowy;
        this.miasto = miasto;
        this.id_urzytkownika = id_urzytkownika;
        this.id_kraju = id_kraju;
    }

    public Adresy() {
    }

    public int getId_adresu() {
        return id_adresu;
    }

    public void setId_adresu(int id_adresu) {
        this.id_adresu = id_adresu;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getNr_domu() {
        return nr_domu;
    }

    public void setNr_domu(String nr_domu) {
        this.nr_domu = nr_domu;
    }

    public String getKod_pocztowy() {
        return kod_pocztowy;
    }

    public void setKod_pocztowy(String kod_pocztowy) {
        this.kod_pocztowy = kod_pocztowy;
    }

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public Urzytkownicy getId_urzytkownika() {
        return id_urzytkownika;
    }

    public void setId_urzytkownika(Urzytkownicy id_urzytkownika) {
        this.id_urzytkownika = id_urzytkownika;
    }

    public Kraje getId_kraju() {
        return id_kraju;
    }

    public void setId_kraju(Kraje id_kraju) {
        this.id_kraju = id_kraju;
    }

    @Override
    public String toString() {
        return "Adresy{" +
                "id_adresu=" + id_adresu +
                ", ulica='" + ulica + '\'' +
                ", nr_domu='" + nr_domu + '\'' +
                ", kod_pocztowy='" + kod_pocztowy + '\'' +
                ", miasto='" + miasto + '\'' +
                ", id_urzytkownika=" + id_urzytkownika +
                ", id_kraju=" + id_kraju +
                '}';
    }
}
