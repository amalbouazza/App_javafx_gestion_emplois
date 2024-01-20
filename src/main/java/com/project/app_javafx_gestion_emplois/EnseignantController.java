package com.project.app_javafx_gestion_emplois;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert;



import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EnseignantController implements Initializable {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;




    @FXML
    private Button bntupdate;

    @FXML
    private Button btnadd;



    @FXML
    private Button btnrech;

    @FXML
    private Button btnsupp;

    @FXML
    private TextField idEns;
    @FXML
    private TableColumn<Enseignant, String> colcont;

    @FXML
    private TableColumn<Enseignant, String> colid;

    @FXML
    private TableColumn<Enseignant, String> colnom;

    @FXML
    private TableView<Enseignant> table;
    String id = "";
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showEnseignant();



    }

    public ObservableList<Enseignant> getEnseignant(){
        ObservableList<Enseignant> enseignants = FXCollections.observableArrayList();
        String query = "select * from enseignants";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {
                Enseignant enseignant = new Enseignant(); // Créez un nouvel objet Enseignant
                enseignant.setId(rs.getString("id"));
                enseignant.setNom(rs.getString("nom"));
                enseignant.setContact(rs.getString("contact"));
                enseignants.add(enseignant);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return enseignants;
       }
    public void showEnseignant(){
        ObservableList<Enseignant> List = getEnseignant();
        table.setItems(List);
        colid.setCellValueFactory(new PropertyValueFactory<Enseignant,String>("id"));
        colnom.setCellValueFactory(new PropertyValueFactory<Enseignant,String>("nom"));
        colcont.setCellValueFactory(new PropertyValueFactory<Enseignant,String>("contact"));

    }

    @FXML
    private TextField txnom;

    @FXML
    private TextField txtel;

    @FXML
    void creatEnseignant(ActionEvent event) {
        String id =idEns.getText();
        String nom = txnom.getText();
        String contact = txtel.getText();


        if (nom != null && contact != null && id != null && !nom.isEmpty() && !contact.isEmpty() && !id.isEmpty()) {
            // Les champs nom et contact sont valides et non vides
            // Insérez les données dans la base de données
            String insert = "insert into enseignants(id ,nom, contact) values(?, ? , ?)";
            con = DBConnexion.getCon();
            try {
                st = con.prepareStatement(insert);
                st.setString(1,idEns.getText());
                st.setString(2,txnom.getText());
                st.setString(3,txtel.getText());

                st.executeUpdate();
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Succès");
                successAlert.setHeaderText("L'enseignant a été créé avec succès");
                successAlert.showAndWait();

                clear();
                showEnseignant();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Affichez un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Les champs ID, nom et contact ne peuvent pas être vides.");
            alert.showAndWait();
        }



    }

   // @FXML
   // void home(Stage stage) throws IOException{
// Code pour charger la page d'accueil
        //FXMLLoader loader =new FXMLLoader(getClass().getResource("fxml/HomePage.fxml"));
       // try {
           // Parent root =loader.load();
            //Scene scene = new Scene(root);
           // stage.setScene(scene);
           // stage.show();
       // } catch (IOException e) {
        //    throw new RuntimeException(e);
       // }

   // }


    @FXML
    void getData(MouseEvent event) {
        Enseignant enseignant = table.getSelectionModel().getSelectedItem();
        id = enseignant.getId();
        txnom.setText(enseignant.getNom());
        txtel.setText(enseignant.getContact());
        idEns.setText(enseignant.getId());
        idEns.setText(String.valueOf(id));
        idEns.setDisable(true);
        btnadd.setDisable(true);


    }
    @FXML

    void searchEnseignant(ActionEvent event) {
        String nom = txnom.getText();
        //int id = Integer.parseInt(idEns.getText());
        String query = "select * from enseignants where nom like ?";
        con = DBConnexion.getCon();
        try {
            st = con.prepareStatement(query);
            st.setString(1,"%"+nom+"%");

            rs = st.executeQuery();
            ObservableList<Enseignant> enseignants = FXCollections.observableArrayList();
            while (rs.next()) {
                Enseignant enseignant = new Enseignant();
                enseignant.setId(rs.getString("id"));
                enseignant.setNom(rs.getString("nom"));
                enseignant.setContact(rs.getString("contact"));
                enseignants.add(enseignant);
            }
            if (enseignants != null) {
                // Afficher un message de succès
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Succès");
                successAlert.setHeaderText("Recherche réussie");
                successAlert.setContentText("Nombre d'enseignants trouvés : " + enseignants.size());
                successAlert.showAndWait();
                table.setItems(enseignants);
            } else {
                // Afficher un message d'erreur
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aucun résultat");
                alert.setHeaderText(null);
                alert.setContentText("Aucun enseignant trouvé pour le nom spécifié.");
                alert.showAndWait();

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);


        }

    }

    @FXML
    void clearForm(ActionEvent event) {
        clear();

    }

    void clear(){
        showEnseignant();
        idEns.setDisable(false);
        txnom.setText(null);
        txtel.setText(null);
        idEns.setText(null);
        btnadd.setDisable(false);

    }
    @FXML
    void suppEnseignant(ActionEvent event) {
        String delete = "delete from enseignants where id = ?";
        con = DBConnexion.getCon();

        try {
            st = con.prepareStatement(delete);
            st.setString(1, idEns.getText());

            // Vérifier si l'ID enseignant existe
            String sql = "select * from enseignants where id = ?";
            st = con.prepareStatement(sql);
            st.setString(1, idEns.getText());
            rs = st.executeQuery();

            if (!rs.next()) {
                // L'ID enseignant n'existe pas
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("L'ID enseignant spécifié n'existe pas.");
                errorAlert.showAndWait();
                return;
            }

            // Supprimer l'enseignant
            st = con.prepareStatement(delete);
            st.setString(1, idEns.getText());
            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                // L'enseignant a été supprimé avec succès
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Succès");
                successAlert.setHeaderText(null);
                successAlert.setContentText("L'enseignant a été supprimé avec succès.");
                successAlert.showAndWait();
            } else {
                // Une erreur s'est produite lors de la suppression de l'enseignant
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Une erreur s'est produite lors de la suppression de l'enseignant.");
                errorAlert.showAndWait();
            }

            showEnseignant();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

   /* void suppEnseignant(ActionEvent event) {
        String delete ="delete from enseignants where id  =?";
        con = DBConnexion.getCon();
        try {
            st =con.prepareStatement(delete);
            st.setString(1, idEns.getText());
            st.executeUpdate();
            showEnseignant();
            clear();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }*/

    @FXML
    void updateEnseignant(ActionEvent event) {

        String nom = txnom.getText();
        String contact = txtel.getText();

        if (nom != null && contact != null && !nom.isEmpty() && !contact.isEmpty()) {
            String update = "update enseignants set nom = ?, contact = ? where id = ?";
            con = DBConnexion.getCon();

            try {
                st = con.prepareStatement(update);

                st.setString(1, txnom.getText());
                st.setString(2, txtel.getText());
                st.setString(3,idEns.getText());

                int rowsAffected = st.executeUpdate();

                if (rowsAffected > 0) {
                    // L'enseignant a été mis à jour avec succès
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Succès");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("L'enseignant a été mis à jour avec succès.");
                    successAlert.showAndWait();
                } else {
                    // Aucune ligne n'a été affectée
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Aucun résultat");
                    alert.setHeaderText(null);
                    alert.setContentText("Aucune ligne n'a été affectée.");
                    alert.showAndWait();
                }
                showEnseignant();
                clear();
            } catch (SQLException e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Une erreur s'est produite lors de la mise à jour de l'enseignant.");
                errorAlert.showAndWait();
            }
        } else {
            // Affichez un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Les champs nom et contact ne peuvent pas être vides.");
            alert.showAndWait();
        }
    }



    public void home(ActionEvent actionEvent) {
    }
}
