package com.project.app_javafx_gestion_emplois;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RequetesController implements Initializable{
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;


        @FXML
        private Button btnAff;

        @FXML
        private Button btnrech;

        @FXML
        private Button btnsupp;

        @FXML
        private ListView<String> classe;

        @FXML
        private TextField idco;

        @FXML
        private ListView<String> idclasse;

        @FXML
        private TextField matiere;
    @FXML
    private TableColumn<Enseignant_cours, String> cls;

    @FXML
    private TableColumn<Enseignant_cours, String> contEns;

    @FXML
    private TableColumn<Enseignant_cours, String> heure;

    @FXML
    private TableColumn<Enseignant_cours, Integer> id;
    @FXML
    private TableColumn<Enseignant_cours, String> jour;

    @FXML
    private TableColumn<Enseignant_cours, String> mat;
    @FXML
    private TableColumn<Enseignant_cours, String> nomEns;


    @FXML
        private TableView<Enseignant_cours> table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showenseignant_cours();

        ObservableList<String> classes = FXCollections.observableArrayList();
        classes.add("1ère année");
        classes.add("2ème année");
        classes.add("3ème année");
        classes.add("4ème année");
        classe.setItems(classes);

        ObservableList<String> classe = FXCollections.observableArrayList();
        classe.add("1ère année");
        classe.add("2ème année");
        classe.add("3ème année");
        classe.add("4ème année");


        idclasse.setItems(classe);

    }
        @FXML
        void affEmploi(ActionEvent event) {
            String classe = idclasse.getSelectionModel().getSelectedItem();

            // Search for the class matching the specified criteria in the database
            String sql1 = "select * from enseignant_cours where classe like ?";
            con = DBConnexion.getCon();

            try {
                st = con.prepareStatement(sql1);
                st.setString(1,"%"+classe+"%");

                rs = st.executeQuery();

                ObservableList<Enseignant_cours> list = FXCollections.observableArrayList();

                while (rs.next()) {
                    Enseignant_cours enseignant_cours = new Enseignant_cours();
                    enseignant_cours.setIdc(rs.getInt("idc"));
                    enseignant_cours.setClasse(rs.getString("classe"));
                    enseignant_cours.setMatiere(rs.getString("matiere"));
                    enseignant_cours.setJour(rs.getString("jour"));
                    enseignant_cours.setHeure(rs.getString("heure"));
                    enseignant_cours.setNom(rs.getString("nom"));
                    enseignant_cours.setContact(rs.getString("contact"));
                    list.add(enseignant_cours);

                }

                if (list.isEmpty()) {
                    // Afficher un message d'alerte si la recherche ne renvoie aucun résultat
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Aucun résultat");
                    alert.setHeaderText(null);
                    alert.setContentText("Aucun emploi du temps trouvé pour la classe spécifiée.");
                    alert.showAndWait();
                } else {
                    table.setItems(list);
                }


            } catch (SQLException e) {
                throw new RuntimeException(e);


            }
        }

        public ObservableList<Enseignant_cours> getEnseignant_cours() {
        ObservableList<Enseignant_cours> enseignant_cours = FXCollections.observableArrayList();
        String query = "select * from enseignant_cours ";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {
                Enseignant_cours ec = new Enseignant_cours(); // Créez un nouvel objet Enseignant_cours
                ec.setIdc(rs.getInt("idc"));
                ec.setClasse(rs.getString("classe"));
                ec.setJour(rs.getString("jour"));
                ec.setHeure(rs.getString("heure"));
                ec.setMatiere(rs.getString("matiere"));
                ec.setNom(rs.getString("nom"));
                ec.setContact(rs.getString("contact"));
                enseignant_cours.add(ec);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return enseignant_cours;
    }

    public void showenseignant_cours() {
        ObservableList<Enseignant_cours> list = getEnseignant_cours();
        table.setItems(list);
        id.setCellValueFactory(new PropertyValueFactory<Enseignant_cours, Integer>("idc"));
        cls.setCellValueFactory(new PropertyValueFactory<Enseignant_cours, String>("classe"));
        mat.setCellValueFactory(new PropertyValueFactory<Enseignant_cours, String>("matiere"));
        jour.setCellValueFactory(new PropertyValueFactory<Enseignant_cours, String>("jour"));
        heure.setCellValueFactory(new PropertyValueFactory<Enseignant_cours, String>("heure"));
        nomEns.setCellValueFactory(new PropertyValueFactory<Enseignant_cours, String>("nom"));
        contEns.setCellValueFactory(new PropertyValueFactory<Enseignant_cours, String>("contact"));
    }
        @FXML
       void searchClasse(ActionEvent event) {
                String classes = classe.getSelectionModel().getSelectedItem();
                String matieres = matiere.getText();

                // Search for the class matching the specified criteria in the database
                String sql = "select * from enseignant_cours where classe like ? and matiere like ?";
                con = DBConnexion.getCon();

                try {
                    st = con.prepareStatement(sql);
                    st.setString(1,"%"+classes+"%");
                    st.setString(2,"%"+matieres+"%");

                    rs = st.executeQuery();

                    ObservableList<Enseignant_cours> list = FXCollections.observableArrayList();

                    while (rs.next()) {
                        Enseignant_cours enseignant_cours = new Enseignant_cours();
                        enseignant_cours.setIdc(rs.getInt("idc"));
                        enseignant_cours.setClasse(rs.getString("classe"));
                        enseignant_cours.setMatiere(rs.getString("matiere"));
                        enseignant_cours.setJour(rs.getString("jour"));
                        enseignant_cours.setHeure(rs.getString("heure"));
                        enseignant_cours.setNom(rs.getString("nom"));
                        enseignant_cours.setContact(rs.getString("contact"));
                        list.add(enseignant_cours);

                    }
                    if (list.isEmpty()) {
                        // Afficher un message d'alerte si la recherche ne renvoie aucun résultat
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Aucun résultat");
                        alert.setHeaderText(null);
                        alert.setContentText("Aucun enseignant trouvé pour la classe et la matière spécifiées.");
                        alert.showAndWait();
                    } else {
                        table.setItems(list);
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);


                }
            }



        @FXML
        void suppCours(ActionEvent event) {
            String IDC = idco.getText();

            // Check if the course ID is empty
            if (IDC.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Attention");
                alert.setHeaderText(null);
                alert.setContentText("Le champ `IDC` est vide.");
                alert.showAndWait();
                return;
            }

            // Check if the course exists
            String sql = "select * from cours where idc = ?";
            con = DBConnexion.getCon();
            try {
                st = con.prepareStatement(sql);
                st.setString(1, IDC);
                rs = st.executeQuery();

                if (!rs.next()) {
                    // The course does not exist
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Erreur");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Le cours spécifié n'existe pas.");
                    errorAlert.showAndWait();
                    return;
                }

                // Delete the course
                String delete = "delete from cours where idc = ?";
                st = con.prepareStatement(delete);
                st.setString(1, IDC);

                int rowsAffected = st.executeUpdate();

                if (rowsAffected > 0) {
                    // The course was deleted successfully
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Succès");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Le cours a été supprimé avec succès.");
                    successAlert.showAndWait();
                } else {
                    // An error occurred while deleting the course
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Erreur");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Une erreur est survenue lors de la suppression du cours.");
                    errorAlert.showAndWait();
                }

                showenseignant_cours();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

}
