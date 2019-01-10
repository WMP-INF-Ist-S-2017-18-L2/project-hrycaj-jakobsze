package com.sklep.baza;

import reactor.util.annotation.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "zamowienia_produkty")
public class Zamowienia_Produkty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_zamowienia_produkty")
    private int id_zamowienia_produkty;

    @ManyToOne
    @JoinColumn(name = "id_zamowienia", nullable = false)
    private Zamowienia id_zamowienia;

    @ManyToOne
    @JoinColumn(name = "id_produktu", nullable = false)
    private Produkty id_produktu;

    @NonNull
    @Column(name = "ilosc", nullable = false)
    private int ilosc;

    @NonNull
    @Column(name = "cena_calkowita", nullable = false, columnDefinition = "Decimal(13,2)")
    private double cena_calkowita;

    public Zamowienia_Produkty(Zamowienia id_zamowienia, Produkty id_produktu, int ilosc, double cena_calkowita) {
        this.id_zamowienia = id_zamowienia;
        this.id_produktu = id_produktu;
        this.ilosc = ilosc;
        this.cena_calkowita = cena_calkowita;
    }

    public Zamowienia_Produkty() {
    }

    public int getId_zamowienia_produkty() {
        return id_zamowienia_produkty;
    }

    public void setId_zamowienia_produkty(int id_zamowienia_produkty) {
        this.id_zamowienia_produkty = id_zamowienia_produkty;
    }

    public Zamowienia getId_zamowienia() {
        return id_zamowienia;
    }

    public void setId_zamowienia(Zamowienia id_zamowienia) {
        this.id_zamowienia = id_zamowienia;
    }

    public Produkty getId_produktu() {
        return id_produktu;
    }

    public void setId_produktu(Produkty id_produktu) {
        this.id_produktu = id_produktu;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public double getCena_calkowita() {
        return cena_calkowita;
    }

    public void setCena_calkowita(double cena_calkowita) {
        this.cena_calkowita = cena_calkowita;
    }

    @Override
    public String toString() {
        return "Zamowienia_Produkty{" +
                "id_zamowienia_produkty=" + id_zamowienia_produkty +
                ", id_zamowienia=" + id_zamowienia +
                ", id_produktu=" + id_produktu +
                ", ilosc=" + ilosc +
                ", cena_calkowita=" + cena_calkowita +
                '}';
    }
}
