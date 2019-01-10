package com.sklep.gui.controllers;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Sesja implements Runnable {
    public static SessionFactory sessionFactory;

    public Sesja() {
    }

    @Override
    public void run() {
        nowaSesja();
    }

    private boolean nowaSesja() {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Nie udało się połączyć z bazą.");
            return false;
        }
        return true;
    }
}
