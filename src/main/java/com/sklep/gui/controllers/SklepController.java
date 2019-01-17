package com.sklep.gui.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import com.sklep.collections.Produkty_i_Zdjęcia;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.sklep.gui.controllers.LogowanieController.zalogowany;

public class SklepController implements Initializable {

    public static ObservableList<Produkty_i_Zdjęcia> listaProdoktowWKoszyku;
    private double xOffSet = 0;
    private double yOffSet = 0;
    private boolean isMaximized = false;
    @FXML
    private JFXButton searchButton;
    @FXML
    private JFXTextField searchBar;
    @FXML
    private JFXComboBox kategorieBar;
    @FXML
    private AnchorPane sklepRootAnchorPane;
    @FXML
    private FontAwesomeIconView gearIcon;
    public static List<Produkty_i_Zdjęcia> produktyArrayList = new ArrayList<>();
    @FXML
    private JFXListView<Produkty_i_Zdjęcia> list;
    @FXML
    private JFXListView<String> kategorieList;
    @FXML
    private JFXSpinner spinner;
    @FXML
    public JFXBadge badge1;
    private double oldX = 0, oldY = 0;
    @FXML
    private FontAwesomeIconView closeKoszykMark;
    @FXML
    private JFXHamburger hamburger;
    private JFXSnackbar snackbar;
    @FXML
    private JFXListView<Produkty_i_Zdjęcia> listKoszyk;
    @FXML
    private Text zalogowanyUrzytkownik;
    @FXML
    private AnchorPane nothingFound;
    @FXML
    private GridPane hamburgerMenu;

    public static void dodajDoKoszyka(Produkty_i_Zdjęcia item) {
        produktyArrayList.add(item);
        listaProdoktowWKoszyku = FXCollections.observableArrayList(produktyArrayList);
    }

    public static void usunZKoszyka(Produkty_i_Zdjęcia item) {
        produktyArrayList.remove(item);
        listaProdoktowWKoszyku = FXCollections.observableArrayList(produktyArrayList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        snackbar = new JFXSnackbar(sklepRootAnchorPane);
        snackbar.setPrefWidth(300);

        HamburgerBasicCloseTransition transition = new HamburgerBasicCloseTransition(hamburger);
        transition.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            transition.setRate(transition.getRate() * -1);
            transition.play();

            if (transition.getRate() == -1) {
                hamburgerMenu.visibleProperty().setValue(false);
            } else {
                hamburgerMenu.visibleProperty().setValue(true);
            }

        });

        spinner.visibleProperty().setValue(true);
        Thread t = new Thread(() -> {
            //Get Session
            Session session = Sesja.sessionFactory.openSession();

            try {
                TypedQuery<String> query = session.createQuery("select k.nazwa from Kategorie k", String.class);
                List<String> kategorie = query.getResultList();

                kategorieBar.getItems().add("Wszystkie kategorie");
                kategorieBar.getItems().addAll(kategorie);

                ObservableList<String> listaKategorii = FXCollections.observableArrayList(kategorie);

                kategorieList.setItems(listaKategorii);

                Platform.runLater(() -> kategorieBar.getSelectionModel().selectFirst());

                TypedQuery<Produkty_i_Zdjęcia> start;
                start = session.createQuery("select new com.sklep.collections.Produkty_i_Zdjęcia(p.id_produktu, p.nazwa, p.cena, z.sciezka_miniatury) from Produkty p, Zdjecia_Produktow z where p.id_produktu = z.id_produktu.id_produktu", Produkty_i_Zdjęcia.class);
                List<Produkty_i_Zdjęcia> produkty = start.getResultList();

                Platform.runLater(() -> wyswietl(produkty));


            } catch (Exception e) {
                System.out.println("Wystąpił wyjątek. " + e.getMessage());
                e.printStackTrace();
            } finally {
                if (!Sesja.sessionFactory.isClosed()) {
                    session.close();
                }
                Platform.runLater(() -> spinner.visibleProperty().setValue(false));
            }
        });
        t.start();

/*        sklepRootAnchorPane.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            transition.setRate(-1);
            transition.play();
            hamburgerMenu.visibleProperty().setValue(false);
        });*/
        sklepRootAnchorPane.addEventFilter(MouseEvent.ANY, mouseEvent -> {
            if (!listKoszyk.isVisible()) {
                try {
                    badge1.setText(String.valueOf(listaProdoktowWKoszyku.size()));
                    //listKoszyk.visibleProperty().setValue(true);
                } catch (NullPointerException e) {
                }
            } else {
                try {
                    badge1.setText(String.valueOf(listaProdoktowWKoszyku.size()));
                    listKoszyk.setItems(listaProdoktowWKoszyku);
                    listKoszyk.setCellFactory(ListView -> new KoszykCell());
                    badge1.setText(String.valueOf(listaProdoktowWKoszyku.size()));

                } catch (NullPointerException e) {}
            }
        });

        zalogowanyUrzytkownik.setText(zalogowany.getImie() + " " + zalogowany.getNazwisko());
    }

    @FXML
    private void onMousePressed(MouseEvent event) {
        if (isMaximized == false) {
            xOffSet = event.getSceneX();
            yOffSet = event.getSceneY();
        }
    }

    @FXML
    private void onMouseDragged(MouseEvent event) {
        if (isMaximized == false) {
            searchButton.getScene().getWindow().setX(event.getScreenX() - xOffSet);
            searchButton.getScene().getWindow().setY(event.getScreenY() - yOffSet);
            searchButton.getScene().getWindow().setOpacity(0.9f);
        }
    }

    @FXML
    private void onMouseReleased(MouseEvent event) {
        if (isMaximized == false) {
            searchButton.getScene().getWindow().setOpacity(1.0f);
        }
    }

    @FXML
    private void handleCloseMark(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void handleMinimizeMark(MouseEvent event) {
        ((Stage) searchButton.getScene().getWindow()).setIconified(true);
    }

    @FXML
    private void handleMaximizeMark(MouseEvent event) {
        if (isMaximized == false) {
            oldX = searchButton.getScene().getWindow().getX();
            oldY = searchButton.getScene().getWindow().getY();

            searchButton.getScene().getWindow().setX(0);
            searchButton.getScene().getWindow().setY(0);

            searchButton.getScene().getWindow().setHeight(Screen.getPrimary().getVisualBounds().getHeight());
            searchButton.getScene().getWindow().setWidth(Screen.getPrimary().getVisualBounds().getWidth());

            isMaximized = true;
        } else {
            searchButton.getScene().getWindow().setX(oldX);
            searchButton.getScene().getWindow().setY(oldY);

            searchButton.getScene().getWindow().setWidth(1280);
            searchButton.getScene().getWindow().setHeight(720);

            isMaximized = false;
        }
    }

    @FXML
    private void handleKeyPressedAction(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            szukaj();
        }
    }

    @FXML
    private void searchButtonAction(ActionEvent actionEvent) {
        szukaj();
    }

    private void szukaj() {
        list.getItems().clear();
        spinner.visibleProperty().setValue(true);
        Thread t = new Thread(() -> {
            //Get Session
            Session session = Sesja.sessionFactory.openSession();
            try {
                TypedQuery<Produkty_i_Zdjęcia> query;
                if (!searchBar.getText().equals("")) {
                    if (kategorieBar.getSelectionModel().getSelectedIndex() == 0) {
                        query = session.createQuery("select new com.sklep.collections.Produkty_i_Zdjęcia(p.id_produktu, p.nazwa, p.cena, z.sciezka_miniatury) from Produkty p, Zdjecia_Produktow z where LOWER(p.nazwa) like :tekst and p.id_produktu = z.id_produktu.id_produktu", Produkty_i_Zdjęcia.class);
                        query.setParameter("tekst", "%" + searchBar.getText().toLowerCase() + "%");
                    } else {
                        query = session.createQuery("select new com.sklep.collections.Produkty_i_Zdjęcia(p.id_produktu, p.nazwa, p.cena, z.sciezka_miniatury) from Produkty p, Zdjecia_Produktow z inner join p.items i where i.nazwa LIKE :nazwa_kat and LOWER(p.nazwa) like :tekst and p.id_produktu = z.id_produktu.id_produktu", Produkty_i_Zdjęcia.class);
                        query.setParameter("nazwa_kat", kategorieBar.getSelectionModel().getSelectedItem());
                        query.setParameter("tekst", "%" + searchBar.getText().toLowerCase() + "%");
                    }
                    List<Produkty_i_Zdjęcia> produkty = query.getResultList();

                    Platform.runLater(() -> wyswietl(produkty));
                }

            } catch (Exception e) {
                System.out.println("Wystąpił wyjątek. " + e.getMessage());
                e.printStackTrace();
            } finally {
                if (!Sesja.sessionFactory.isClosed()) {
                    session.close();
                }
                Platform.runLater(() -> spinner.visibleProperty().setValue(false));
            }
        });
        t.start();
    }

    private void wyswietl(List<Produkty_i_Zdjęcia> produkty) {

        ObservableList<Produkty_i_Zdjęcia> listaProdoktow = FXCollections.observableArrayList(produkty);

        list.setItems(listaProdoktow);
        list.setCellFactory(ListView -> new ListCell());

        if (list.getItems().isEmpty()) {
            nothingFound.visibleProperty().setValue(true);
        } else {
            nothingFound.visibleProperty().setValue(false);
        }
    }

    @FXML
    public void kategorieListHandleMouseClicked(MouseEvent actionEvent) {

        list.getItems().clear();
        spinner.visibleProperty().setValue(true);

        Thread t = new Thread(() -> {
            //Get Session
            Session session = Sesja.sessionFactory.openSession();
            try {
                TypedQuery<Produkty_i_Zdjęcia> query = session.createQuery("select new com.sklep.collections.Produkty_i_Zdjęcia(p.id_produktu, p.nazwa, p.cena, z.sciezka_miniatury) from Produkty p, Zdjecia_Produktow z inner join p.items i where i.nazwa like :nazwa and p.id_produktu = z.id_produktu.id_produktu", Produkty_i_Zdjęcia.class);
                query.setParameter("nazwa", kategorieList.getSelectionModel().getSelectedItem());

                List<Produkty_i_Zdjęcia> produkty = query.getResultList();

                Platform.runLater(() -> wyswietl(produkty));

            } catch (Exception e) {
                System.out.println("Wystąpił wyjątek. " + e.getMessage());
                e.printStackTrace();
            } finally {
                if (!Sesja.sessionFactory.isClosed()) {
                    session.close();
                }
                Platform.runLater(() -> spinner.visibleProperty().setValue(false));
            }
        });
        t.start();
    }

    @FXML
    void handleKoszykAction(MouseEvent event) {

        listKoszyk.setItems(listaProdoktowWKoszyku);
        listKoszyk.setCellFactory(ListView -> new KoszykCell());

        listKoszyk.visibleProperty().setValue(true);
        closeKoszykMark.visibleProperty().setValue(true);
    }

    @FXML
    private void list(MouseEvent event) {


    }

    @FXML
    void closeKoszykMarkClicked(MouseEvent event) {
        listKoszyk.visibleProperty().setValue(false);
        closeKoszykMark.visibleProperty().setValue(false);
    }

    @FXML
    void zamknijAplikacje(MouseEvent event) {
        System.exit(0);
    }
}
