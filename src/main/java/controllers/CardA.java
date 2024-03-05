package controllers;
import Entites.Academy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import Service.ServiceAcademy;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.EventObject;
import java.util.Optional;

public class CardA{

    private Academy academy;
    @FXML
    private Label label_nom;

    @FXML
    private Label label_cat;

    @FXML
    private Label label_loca;


    @FXML
    private Label label_create;
    @FXML
    private Button btn_delete;



    public void setData(Academy academy){

        this.academy=academy;

        label_nom.setText(academy.getName());
        label_cat.setText(academy.getCategory());
        label_loca.setText(academy.getLocalisation());
        label_create.setText(academy.getCreated_by());
    }

    @FXML
    void delete(javafx.event.ActionEvent event) throws IOException, SQLException {
        ServiceAcademy service = new ServiceAcademy();

        // Confirmation de la suppression
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment supprimer cette académie ?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            service.deleteEntity(academy);

            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/AfficherAcademy.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }
    }



    @FXML
    void update(javafx.event.ActionEvent event) throws IOException {

        ModifierAcademy.academy=academy;
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/ModifierAcademy.fxml"));
        Scene tabbleViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tabbleViewScene);
        window.show();
    }

    @FXML
    void naviguerVersAjouterC(javafx.event.ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterAcademy.fxml"));
            label_loca.getScene().setRoot(root);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    }






