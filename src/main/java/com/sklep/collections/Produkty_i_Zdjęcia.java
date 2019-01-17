package com.sklep.collections;

public class Produkty_i_Zdjęcia {

    private int id_produktu;
    private String nazwa;
    private double cena;
    private String sciezka_miniatury;

    public Produkty_i_Zdjęcia(int id_produktu, String nazwa, double cena, String sciezka_miniatury) {
        this.id_produktu = id_produktu;
        this.nazwa = nazwa;
        this.cena = cena;
        this.sciezka_miniatury = sciezka_miniatury;
    }

    public Produkty_i_Zdjęcia() {
    }

    public int getId_produktu() {
        return id_produktu;
    }

    public void setId_produktu(int id_produktu) {
        this.id_produktu = id_produktu;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public String getSciezka_miniatury() {
        return sciezka_miniatury;
    }

    public void setSciezka_miniatury(String sciezka_miniatury) {
        this.sciezka_miniatury = sciezka_miniatury;
    }

    @Override
    public String toString() {
        return "Produkty{" +
                "nazwa='" + nazwa + '\'' +
                ", cena=" + cena +
                ", sciezka='" + sciezka_miniatury + '\'' +
                '}';
    }
}
