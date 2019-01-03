package com.sklep.baza;

import reactor.util.annotation.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "kategorie")
public class Kategorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_kategorii")
    private int id_kategorii;

    @NonNull
    @Column(name = "nazwa", nullable=false)
    private String nazwa;

    public Kategorie(String nazwa) {
        this.nazwa = nazwa;
    }

    public Kategorie() {
    }

    public int getId_kategorii() {
        return id_kategorii;
    }

    public void setId_kategorii(int id_kategorii) {
        this.id_kategorii = id_kategorii;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    @Override
    public String toString() {
        return "Kategorie{" +
                "id_kategorii=" + id_kategorii +
                ", nazwa='" + nazwa + '\'' +
                '}';
    }
}