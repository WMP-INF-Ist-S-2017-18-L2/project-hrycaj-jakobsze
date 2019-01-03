package com.sklep.metody;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {




       /* Set<Zamowienia> itemsSet = new HashSet<Zamowienia>();

        adresy u2 = new adresy("AD", "AM", "Adam", "Warlock", itemsSet);

        Zamowienia zam = new Zamowienia(u2, "Funkcje", 77);
        Zamowienia zam2 = new Zamowienia(u2, "lul", 997);
        Zamowienia zam3 = new Zamowienia(u2, "yeeeeeeezd", 666);
        Zamowienia zam4 = new Zamowienia(u2, "3", 11);
        Zamowienia zam5 = new Zamowienia(u2, "", 99.99);

        itemsSet.add(zam);
        itemsSet.add(zam2);
        itemsSet.add(zam3);
        itemsSet.add(zam4);
        itemsSet.add(zam5);*/

//        Produkty p = new Produkty("Coś", 99.88, 20);
//
//        Daty d = new Daty(30, 12, 2018);
//        Daty d2 = new Daty(10, 5, 2099);
//        Daty d3 = new Daty(7, 2, 2028);
//        Daty d4 = new Daty(31, 8, 2011);
//
//        Urzytkownicy u = new Urzytkownicy();
//
//
//
//        u = new Urzytkownicy("CJay", "Funkcje", "Adrian", "Hrycaj", itemsSet);
//        Urzytkownicy u2 = new Urzytkownicy("AD", "AM", "Adam", "Warlock", itemsSet);
//        Urzytkownicy u3 = new Urzytkownicy("Teścik", "lulul lolal", "Janusz", "Tracz", itemsSet);


        //-------------BAZA-----------------
/*
        SessionFactory sessionFactory = null;
        Session session;
        Transaction tx;

        try {
            //Get Session
            sessionFactory = HibernateAnnotationUtil.getSessionFactory();
            session = sessionFactory.getCurrentSession();
            System.out.println("Sesja utworzona");


            tx = session.beginTransaction();


            tx.commit();


        } catch (Exception e) {
            System.out.println("Wystąpił wyjątek. " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (!sessionFactory.isClosed()) {
                System.out.println("Zamykanie sesji");
                sessionFactory.close();
            }

        }
    }*/
    }
}

