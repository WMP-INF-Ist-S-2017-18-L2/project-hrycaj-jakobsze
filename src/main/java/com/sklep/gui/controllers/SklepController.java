package com.sklep.gui.controllers;

import com.jfoenix.controls.*;
import com.sklep.baza.Produkty;
import com.sklep.baza.Zdjecia_Produktow;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
    private JFXListView<Produkty> list;
    @FXML
    private JFXListView<String> kategorieList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Get Session
        Session session = Sesja.sessionFactory.openSession();

        try {
            TypedQuery<String> query = session.createQuery("select k.nazwa from Kategorie k", String.class);
            List<String> kategorie = query.getResultList();

            kategorieBar.getItems().add("Wszystkie kategorie");
            kategorieBar.getItems().addAll(kategorie);

            ObservableList<String> listaKategorii = FXCollections.observableArrayList(kategorie);

            kategorieList.setItems(listaKategorii);

            kategorieBar.getSelectionModel().selectFirst();
        } catch (Exception e) {
            System.out.println("Wystąpił wyjątek. " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (!Sesja.sessionFactory.isClosed()) {
                session.close();
            }
        }
    }

    @FXML
    private void onMousePressed(MouseEvent event) {
        if(isMaximized == false){
            xOffSet=event.getSceneX();
            yOffSet=event.getSceneY();
        }
    }

    @FXML
    private void onMouseDragged(MouseEvent event) {
        if(isMaximized == false) {
            searchButton.getScene().getWindow().setX(event.getScreenX() - xOffSet);
            searchButton.getScene().getWindow().setY(event.getScreenY() - yOffSet);
            searchButton.getScene().getWindow().setOpacity(0.9f);
        }
    }

    @FXML
    private void onMouseReleased(MouseEvent event) {
        if(isMaximized == false) {
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

    private double oldX=0, oldY=0;
    @FXML
    private void handleMaximizeMark(MouseEvent event) {
        if(isMaximized == false){
            searchButton.getScene().getWindow().setHeight(Screen.getPrimary().getVisualBounds().getHeight());
            searchButton.getScene().getWindow().setWidth(Screen.getPrimary().getVisualBounds().getWidth());

            oldX=searchButton.getScene().getWindow().getX();
            oldY=searchButton.getScene().getWindow().getY();

            searchButton.getScene().getWindow().setX(0);
            searchButton.getScene().getWindow().setY(0);

//            AnchorPane.setRightAnchor(searchBar, 712.0);
//            AnchorPane.setRightAnchor(kategorieBar, 483.0);
//            AnchorPane.setRightAnchor(searchButton, 368.0);
//            AnchorPane.setRightAnchor(chartIcon, 155.0);
//            AnchorPane.setRightAnchor(gearIcon, 65.0);
//            AnchorPane.setRightAnchor(list, 377.0);
//
//            searchBar.setPrefSize(850, 50);
//            list.setPrefSize(1625, 1083);

           // ((Stage) szukajButton.getScene().getWindow()).setMaximized(true);
            isMaximized=true;
        }else{
            searchButton.getScene().getWindow().setWidth(1280);
            searchButton.getScene().getWindow().setHeight(720);

            searchButton.getScene().getWindow().setX(oldX);
            searchButton.getScene().getWindow().setY(oldY);

//            AnchorPane.setRightAnchor(searchBar, 562.0);
//            AnchorPane.setRightAnchor(kategorieBar, 333.0);
//            AnchorPane.setRightAnchor(searchButton, 218.0);
//            AnchorPane.setRightAnchor(chartIcon, 115.0);
//            AnchorPane.setRightAnchor(gearIcon, 35.0);
//            AnchorPane.setRightAnchor(list, 227.0);

//            searchBar.setPrefSize(500, 50);
//            list.setPrefSize(825, 550);

            //((Stage) szukajButton.getScene().getWindow()).setMaximized(false);
            isMaximized=false;
        }

    }

    @FXML
    private void handleKeyPressedAction(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            szukaj();
        }
    }

    @FXML
    public void searchButtonAction(ActionEvent actionEvent) {
        szukaj();
    }

    private void szukaj(){
        //Get Session
        Session session = Sesja.sessionFactory.openSession();

        try {
            TypedQuery<Produkty> query;
            if(!searchBar.getText().equals("")){
                if(kategorieBar.getSelectionModel().getSelectedIndex()==0){
                    query = session.createQuery("select p from Produkty p where LOWER(p.nazwa) like :tekst", Produkty.class);
                    query.setParameter("tekst", "%" + searchBar.getText().toLowerCase() + "%");
                }else{
                    query = session.createQuery("select p from Produkty p inner join p.items i where i.nazwa LIKE :nazwa_kat and LOWER(p.nazwa) like :tekst", Produkty.class);
                    query.setParameter("nazwa_kat", kategorieBar.getSelectionModel().getSelectedItem());
                    query.setParameter("tekst", "%" + searchBar.getText().toLowerCase() + "%");
                }

                List<Produkty> produkty = query.getResultList();

                TypedQuery<Zdjecia_Produktow> query2 = session.createQuery("select z from Zdjecia_Produktow z join z.id_produktu p", Zdjecia_Produktow.class);
                List<Zdjecia_Produktow> zdjecia_produktow = query2.getResultList();

                ObservableList<Produkty> listaProdoktow = FXCollections.observableArrayList(produkty);

                list.setCellFactory(new Callback<ListView<Produkty>, ListCell<Produkty>>() {
                    @Override
                    public ListCell<Produkty> call(ListView<Produkty> arg0) {
                        JFXListCell<Produkty> cell = new JFXListCell<Produkty>(){
                            @Override
                            protected void updateItem(Produkty item, boolean empty){

                                super.updateItem(item, empty);
                                if(item!=null){
                                    Image img = new Image("https://i.ebayimg.com/thumbs/images/m/m9GKb2oXLGnonAVN2KgNVjQ/s-l225.jpg");

                                    ImageView imageView = new ImageView(img);

                                    imageView.setFitHeight(155);
                                    imageView.setFitWidth(155);

                                    setGraphic(imageView);
                                    setText(item.getNazwa());
                                }
                            }
                        };
                        return cell;
                    }
                });
                list.setItems(listaProdoktow);
            }
        } catch (Exception e) {
            System.out.println("Wystąpił wyjątek. " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (!Sesja.sessionFactory.isClosed()) {
                session.close();
            }
        }
    }

    @FXML
    public void kategorieListHandleMouseClicked(MouseEvent actionEvent) {
        //Get Session
        Session session = Sesja.sessionFactory.openSession();

        TypedQuery<Produkty> query = session.createQuery("select p from Produkty p inner join p.items i where i.nazwa LIKE :nazwa ", Produkty.class);
        query.setParameter("nazwa", kategorieList.getSelectionModel().getSelectedItem());
        List<Produkty> produkty = query.getResultList();

        ObservableList<Produkty> listaProdoktow = FXCollections.observableArrayList(produkty);

        list.setCellFactory(new Callback<ListView<Produkty>, ListCell<Produkty>>() {
            @Override
            public ListCell<Produkty> call(ListView<Produkty> arg0) {
                JFXListCell<Produkty> cell = new JFXListCell<Produkty>(){
                    @Override
                    protected void updateItem(Produkty item, boolean empty){

                        super.updateItem(item, empty);
                        if(item!=null){
                            Image img = new Image("https://i.ebayimg.com/thumbs/images/m/m9GKb2oXLGnonAVN2KgNVjQ/s-l225.jpg");

                            ImageView imageView = new ImageView(img);

                            imageView.setFitHeight(155);
                            imageView.setFitWidth(155);

                            setGraphic(imageView);
                            setText(item.getNazwa());
                        }
                    }
                };
                return cell;
            }
        });
        list.setItems(listaProdoktow);






    }


//    private void closeStage() {
//        ((Stage) login.getScene().getWindow()).close();
//    }

}
