package com.sklep.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import com.sklep.collections.Produkty_i_Zdjęcia;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import static com.sklep.gui.controllers.SklepController.produktyArrayList;
import static com.sklep.gui.controllers.SklepController.usunZKoszyka;

public class KoszykCell extends JFXListCell<Produkty_i_Zdjęcia> {

    private static boolean xVisible = false;
    private static boolean dodajDoKoszykaVisible = true;
    @FXML
    private Label cellNazwa;
    @FXML
    private Label cellCena;
    @FXML
    private ImageView cellImageView;
    @FXML
    private AnchorPane cellAnchor;
    @FXML
    private JFXButton deleteButton;
    private FXMLLoader mLLoader;
    private int index;
    private Image img;

    @Override
    protected void updateItem(Produkty_i_Zdjęcia item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null) {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/fxml/koszykcell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (!item.getSciezka_miniatury().isBlank()) {
                img = new Image(item.getSciezka_miniatury(), 155, 155, false, false);
            } else {
                img = new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/480px-No_image_available.svg.png", 155, 155, false, false);
            }


            cellNazwa.setText(item.getNazwa());
            cellCena.setText(item.getCena() + "zł");
            cellImageView.setImage(img);

            setText(null);
            setGraphic(cellAnchor);

            index = produktyArrayList.indexOf(item);

            deleteButton.setOnAction(event -> usunZKoszyka(item));

        }


    }


}
