package com.sklep.baza;

import reactor.util.annotation.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "kraje")
public class Kraje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_kraju")
    private int id_kraju;

    @NonNull
    @Column(name = "nazwa", nullable=false)
    private String nazwa;

    public Kraje(String nazwa) {
        this.nazwa = nazwa;
    }

    public Kraje() {
    }

    public int getId_kraju() {
        return id_kraju;
    }

    public void setId_kraju(int id_kraju) {
        this.id_kraju = id_kraju;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    @Override
    public String toString() {
        return "Kraje{" +
                "id_kraju=" + id_kraju +
                ", nazwa='" + nazwa + '\'' +
                '}';
    }
}
