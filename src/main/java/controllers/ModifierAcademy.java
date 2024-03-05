package controllers;
import Entites.Academy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Service.ServiceAcademy;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class ModifierAcademy {

    public static Academy academy;
    @FXML
    private TextField tf_name2;

    @FXML
    private TextField tf_cat2;

    @FXML
    private TextField tf_loca2;

    @FXML
    private TextField tf_create2;

    //private Academy academy;

    @FXML
    private Button btn_modif;


    public void initialize(){
        tf_name2.setText(academy.getName());
        tf_cat2.setText(academy.getCategory());
        tf_loca2.setText(academy.getLocalisation());
        tf_create2.setText(academy.getCreated_by());
    }

    @FXML
    void modifier(javafx.event.ActionEvent event) throws IOException {
        String name = tf_name2.getText();
        String category = tf_cat2.getText();
        String localisation = tf_loca2.getText();
        String created_by = tf_create2.getText();


        if (tf_name2.getText().isEmpty() || tf_cat2.getText().isEmpty() || tf_loca2.getText().isEmpty() || tf_create2.getText().isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs!");
            alert.showAndWait();
        } else {
            try {
                Academy a = new Academy();
                a.setName(tf_name2.getText());
                a.setCategory(tf_cat2.getText());
                a.setLocalisation(tf_loca2.getText());
                a.setCreated_by(tf_create2.getText());
                a.setId(academy.getId());

                ServiceAcademy sa = new ServiceAcademy();
                sa.updateEntity(a);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("Académie modifié avec succès");
                alert.showAndWait();

                // Vider les champs
                tf_name2.clear();
                tf_cat2.clear();
                tf_loca2.clear();
                tf_create2.clear();


            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur s'est produite lors de l'ajout de l'académie");
                alert.showAndWait();
                ex.printStackTrace();

            }

        }

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/ModifierAcademy.fxml"));
        Scene tabbleViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tabbleViewScene);
        window.show();
    }

}


