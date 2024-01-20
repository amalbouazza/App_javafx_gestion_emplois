package com.project.app_javafx_gestion_emplois;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    private Parent fxml;

    @FXML
    private AnchorPane root;


    /*@FXML
    private AnchorPane rootPane; // Ajoutez ceci si vous souhaitez utiliser l'élément de votre fichier FXML*/


    public void initialize(URL location, ResourceBundle resources) {


    }

    @FXML
    void Acceuil(MouseEvent event) {
        try {
            fxml = FXMLLoader.load(getClass().getResource("/fxml/image.fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException e) {
            // throw new RuntimeException(e);
            e.printStackTrace();
        }
    }


    @FXML
    void cours(MouseEvent event) {
        try {
            fxml = FXMLLoader.load(getClass().getResource("/fxml/cours.fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException e) {
           // throw new RuntimeException(e);
            e.printStackTrace();
        }
    }

    @FXML
    void enseignants(MouseEvent event) {
        try {
            fxml = FXMLLoader.load(getClass().getResource("/fxml/Enseignants.fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException e) {
           // throw new RuntimeException(e);
            e.printStackTrace();

        }

    }

    @FXML
    void requetes(MouseEvent event) {
        try {
            fxml = FXMLLoader.load(getClass().getResource("/fxml/Requetes.fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException e) {
            // throw new RuntimeException(e);
            e.printStackTrace();

        }

    }



}