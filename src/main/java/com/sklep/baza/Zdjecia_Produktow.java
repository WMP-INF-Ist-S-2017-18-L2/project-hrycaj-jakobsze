package com.sklep.baza;

import javax.persistence.*;

@Entity
@Table(name = "zdjecia_produktow")
public class Zdjecia_Produktow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_zdjecia")
    private int id_zdjecia;

    @ManyToOne
    @JoinColumn(name="id_produktu", nullable=false)
    private Produkty id_produktu;

    @Column(name = "sciezka")
    private String sciezka;

    @Column(name = "sciezka_miniatury")
    private String sciezka_miniatury;

    @Column(name = "alt")
    private String alt;


    public Zdjecia_Produktow(Produkty id_produktu, String sciezka, String sciezka_miniatury, String alt) {
        this.id_produktu = id_produktu;
        this.sciezka = sciezka;
        this.sciezka_miniatury = sciezka_miniatury;
        this.alt = alt;
    }

    public Zdjecia_Produktow() {
    }

    public int getId_zdjecia() {
        return id_zdjecia;
    }

    public void setId_zdjecia(int id_zdjecia) {
        this.id_zdjecia = id_zdjecia;
    }

    public Produkty getId_produktu() {
        return id_produktu;
    }

    public void setId_produktu(Produkty id_produktu) {
        this.id_produktu = id_produktu;
    }

    public String getSciezka() {
        return sciezka;
    }

    public void setSciezka(String sciezka) {
        this.sciezka = sciezka;
    }

    public String getSciezka_miniatury() {
        return sciezka_miniatury;
    }

    public void setSciezka_miniatury(String sciezka_miniatury) {
        this.sciezka_miniatury = sciezka_miniatury;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    @Override
    public String toString() {
        return "Zdjecia_Produktow{" +
                "id_zdjecia=" + id_zdjecia +
                ", id_produktu=" + id_produktu +
                ", sciezka='" + sciezka + '\'' +
                ", sciezka_miniatury='" + sciezka_miniatury + '\'' +
                ", alt='" + alt + '\'' +
                '}';
    }
}

