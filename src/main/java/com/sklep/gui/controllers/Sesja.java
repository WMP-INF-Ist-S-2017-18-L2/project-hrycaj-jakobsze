package com.sklep.gui.controllers;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Sesja {
    public static SessionFactory sessionFactory;

    public Sesja() {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

}
