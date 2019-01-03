package com.sklep.baza;

import org.hibernate.annotations.Type;
import reactor.util.annotation.NonNull;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "produkty")
public class Produkty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produktu")
    private int id_produktu;

    @NonNull
    @Column(name = "nazwa", nullable=false)
    private String nazwa;

    @Type(type = "text")
    @Column(name = "opis")
    private String opis;

    @NonNull
    @Column(name = "cena", nullable=false, columnDefinition="Decimal(13,2)")
    private double cena;

    @NonNull
    @Column(name = "dostepna_ilosc", nullable=false)
    private int dostepna_ilosc;

    @ManyToMany(targetEntity = Kategorie.class, cascade = { CascadeType.ALL })
    @JoinTable(name = "kategorie_produkty",
            joinColumns = { @JoinColumn(name = "id_produktu") },
            inverseJoinColumns = { @JoinColumn(name = "id_kategorii") })
    private Set<Kategorie> items;

    public Produkty(String nazwa, String opis, double cena, int dostepna_ilosc, Set<Kategorie> items) {
        this.nazwa = nazwa;
        this.opis = opis;
        this.cena = cena;
        this.dostepna_ilosc = dostepna_ilosc;
        this.items = items;
    }

    public Produkty() {
    }

    public int getId_produktu() {
        return id_produktu;
    }

    public void setId_produktu(int id_zamowienia) {
        this.id_produktu = id_zamowienia;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public int getDostepna_ilosc() {
        return dostepna_ilosc;
    }

    public void setDostepna_ilosc(int dostepna_ilosc) {
        this.dostepna_ilosc = dostepna_ilosc;
    }

    public Set<Kategorie> getItems() {
        return items;
    }

    public void setItems(Set<Kategorie> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Produkty{" +
                "id_produktu=" + id_produktu +
                ", nazwa='" + nazwa + '\'' +
                ", opis='" + opis + '\'' +
                ", cena=" + cena +
                ", dostepna_ilosc=" + dostepna_ilosc +
                ", items=" + items +
                '}';
    }
}
