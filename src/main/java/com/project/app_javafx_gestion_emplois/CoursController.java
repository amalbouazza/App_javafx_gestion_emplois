package com.project.app_javafx_gestion_emplois;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CoursController implements Initializable {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;

    @FXML
    private Button btnsave;

    @FXML
    private ListView<String> classe;

    @FXML
    private TableColumn<Cours, String> colEns;

    @FXML
    private TableColumn<Cours, String> colclasse;

    @FXML
    private TableColumn<Cours, String> colh;

    @FXML
    private TableColumn<Cours, String> colj;

    @FXML
    private TableColumn<Cours, String> colmat;

    @FXML
    private ListView<String> heure;

    @FXML
    private TextField id_ens;

    @FXML
    private ListView<String> jour;

    @FXML
    private TextField mat;

   /* @FXML
    private AnchorPane table;*/
   @FXML
   private TableView<Cours> table;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> classes = FXCollections.observableArrayList();
        classes.add("1ère année");
        classes.add("2ème année");
        classes.add("3ème année");
        classes.add("4ème année");

        classe.setItems(classes);

        ObservableList<String> heures = FXCollections.observableArrayList();
        heures.add("8h-10h");
        heures.add("10h-12h");
        heures.add("14h-16h");
        heures.add("16h-18h");
        heure.setItems(heures);

        ObservableList<String> jours = FXCollections.observableArrayList();
        jours.add("Lundi");
        jours.add("Mardi");
        jours.add("Mercredi");
        jours.add("Jeudi");
        jours.add("Vendredi");
        jours.add("Samedi");
        jour.setItems(jours);

        /*ObservableList<String> id_ens = FXCollections.observableArrayList();
        id_ens.add("Ens1");
        id_ens.add("Ens2");
        id_ens.add("Ens3");
        id_ens.add("Ens4");
        id_ens.add("Ens5");
        id_ens.add("Ens6");*/

        //id_ens.setItems(id_ens);

        showcours();
    }

    public ObservableList<Cours> getCours() {
        ObservableList<Cours> cours = FXCollections.observableArrayList();
        String query = "select * from cours";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {
                Cours c = new Cours(); // Créez un nouvel objet cours
                c.setIdc(rs.getInt("idc"));
                c.setClasse(rs.getString("classe"));
                c.setJour(rs.getString("jour"));
                c.setHeure(rs.getString("heure"));
                c.setMatiere(rs.getString("matiere"));
                c.setId_ens(rs.getString("id_ens"));
                cours.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cours;
    }

    public void showcours() {
        ObservableList<Cours> cours = getCours();
        colclasse.setCellValueFactory(new PropertyValueFactory<>("classe"));
        colmat.setCellValueFactory(new PropertyValueFactory<>("matiere"));
        colj.setCellValueFactory(new PropertyValueFactory<>("jour"));
        colh.setCellValueFactory(new PropertyValueFactory<>("heure"));
        colEns.setCellValueFactory(new PropertyValueFactory<>("id_ens"));
        table.setItems(cours);
    }

    @FXML
    void creatCours(ActionEvent event) {
        String classe = this.classe.getSelectionModel().getSelectedItem();
        String matiere = this.mat.getText();
        String jour = this.jour.getSelectionModel().getSelectedItem();
        String heure = this.heure.getSelectionModel().getSelectedItem();
        String id_ens = this.id_ens.getText();



        String query = "insert into cours (classe, matiere, jour, heure, id_ens) values (?, ?, ?, ?, ?)";

        try {
            con = DBConnexion.getCon();
            st = con.prepareStatement(query);
            st.setString(1, classe);
            st.setString(2, matiere);
            st.setString(3, jour);
            st.setString(4, heure);
            st.setString(5, id_ens);
            st.executeUpdate();
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Succès");
            successAlert.setHeaderText("Le cours a été créé avec succès");
            successAlert.showAndWait();
        } catch (SQLException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText("Une erreur est survenue lors de la création du cours");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
            throw new RuntimeException(e);
        }

        showcours();
    }
}

