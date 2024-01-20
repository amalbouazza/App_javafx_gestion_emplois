module com.project.app_javafx_gestion_emplois {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;
    requires java.sql;


    opens com.project.app_javafx_gestion_emplois to javafx.fxml;
    exports com.project.app_javafx_gestion_emplois;
}