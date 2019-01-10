package com.sklep.gui.controllers;

import com.jfoenix.controls.*;
import com.sklep.collections.Produkty_i_Zdjęcia;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SklepController implements Initializable {


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
    @FXML
    private FontAwesomeIconView chartIcon;
    @FXML
    private JFXListView<Produkty_i_Zdjęcia> list;
    @FXML
    private JFXListView<String> kategorieList;
    @FXML
    private JFXSpinner spinner;

    private double oldX = 0, oldY = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
                start = session.createQuery("select new com.sklep.collections.Produkty_i_Zdjęcia(p.nazwa, p.cena, z.sciezka_miniatury) from Produkty p, Zdjecia_Produktow z where p.id_produktu = z.id_produktu.id_produktu", Produkty_i_Zdjęcia.class);
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
                        query = session.createQuery("select new com.sklep.collections.Produkty_i_Zdjęcia(p.nazwa, p.cena, z.sciezka_miniatury) from Produkty p, Zdjecia_Produktow z where LOWER(p.nazwa) like :tekst and p.id_produktu = z.id_produktu.id_produktu", Produkty_i_Zdjęcia.class);
                        query.setParameter("tekst", "%" + searchBar.getText().toLowerCase() + "%");
                    } else {
                        query = session.createQuery("select new com.sklep.collections.Produkty_i_Zdjęcia(p.nazwa, p.cena, z.sciezka_miniatury) from Produkty p, Zdjecia_Produktow z inner join p.items i where i.nazwa LIKE :nazwa_kat and LOWER(p.nazwa) like :tekst and p.id_produktu = z.id_produktu.id_produktu", Produkty_i_Zdjęcia.class);
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

        Thread t = new Thread(() -> {
            ObservableList<Produkty_i_Zdjęcia> listaProdoktow = FXCollections.observableArrayList(produkty);

            list.setCellFactory(new Callback<ListView<Produkty_i_Zdjęcia>, ListCell<Produkty_i_Zdjęcia>>() {
                @Override
                public ListCell<Produkty_i_Zdjęcia> call(ListView<Produkty_i_Zdjęcia> arg0) {
                    JFXListCell<Produkty_i_Zdjęcia> cell = new JFXListCell<Produkty_i_Zdjęcia>() {
                        @Override
                        protected void updateItem(Produkty_i_Zdjęcia item, boolean empty) {
                            Image img;
                            super.updateItem(item, empty);
                            if (item != null) {

                                if (!item.getSciezka_miniatury().isBlank()) {
                                    img = new Image(item.getSciezka_miniatury(), 155, 155, false, false);
                                } else {
                                    img = new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/480px-No_image_available.svg.png", 155, 155, false, false);
                                }

                                ImageView imageView = new ImageView(img);


                                setGraphic(imageView);

                                setText(item.getNazwa());
                            }
                        }
                    };
                    return cell;
                }
            });
            Platform.runLater(() -> list.setItems(listaProdoktow));

        });
        t.start();
    }

    @FXML
    public void kategorieListHandleMouseClicked(MouseEvent actionEvent) {

        list.getItems().clear();
        spinner.visibleProperty().setValue(true);

        Thread t = new Thread(() -> {
            //Get Session
            Session session = Sesja.sessionFactory.openSession();
            try {
                TypedQuery<Produkty_i_Zdjęcia> query = session.createQuery("select new com.sklep.collections.Produkty_i_Zdjęcia(p.nazwa, p.cena, z.sciezka_miniatury) from Produkty p, Zdjecia_Produktow z inner join p.items i where i.nazwa like :nazwa and p.id_produktu = z.id_produktu.id_produktu", Produkty_i_Zdjęcia.class);
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
}
