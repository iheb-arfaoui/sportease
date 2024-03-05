package controllers;
import Entites.Academy;
import Entites.Coach;
import javafx.event.ActionEvent;
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
import Service.serviceCoach;

import java.io.IOException;
import java.util.Optional;



public class CardC {

private Coach coach;
    @FXML
    private Label label_name;

    @FXML
    private Label label_mail;

    @FXML
    private Label label_phone;

    @FXML
    private Label label_acad;

    public void setData(Coach coach){

        this.coach=coach;

        label_name.setText(coach.getName());
        label_mail.setText(coach.getEmail());
        label_phone.setText(coach.getPhone());
        label_acad.setText(coach.getAcademyName());
    }

    @FXML
    void supprimer(ActionEvent event) throws IOException {
        serviceCoach service = new serviceCoach();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment supprimer ce coach ?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            service.deleteEntity(coach);

            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/AfficherCoach.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }
    }

    @FXML
    void updateCoach(ActionEvent event) throws IOException {

        ModifierCoach.coach=coach;
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/ModifierCoach.fxml"));
        Scene tabbleViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tabbleViewScene);
        window.show();
    }

    @FXML
    void naviguerVersAjouter(ActionEvent event) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterCoach.fxml"));
            label_phone.getScene().setRoot(root);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}


