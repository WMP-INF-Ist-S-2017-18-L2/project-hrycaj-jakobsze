package com.sklep.collections;

public class Produkty_i_Zdjęcia {

    private String nazwa;
    private double cena;
    private String sciezka_miniatury;

    public Produkty_i_Zdjęcia(String nazwa, double cena, String sciezka_miniatury) {
        this.nazwa = nazwa;
        this.cena = cena;
        this.sciezka_miniatury = sciezka_miniatury;
    }

    public Produkty_i_Zdjęcia() {
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
