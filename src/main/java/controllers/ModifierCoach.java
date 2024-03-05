package controllers;

import entities.Academy;
import entities.Coach;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceAcademy;
import services.serviceCoach;

import java.io.IOException;

public class ModifierCoach {

    public static Coach coach;

    @FXML
    private TextField tf_nameC;

    @FXML
    private TextField tf_mail;

    @FXML
    private TextField tf_phone;

    @FXML
    private TextField tf_aca;

    @FXML
    private Button btn_modiff;


    public void initialize(){
        tf_nameC.setText(coach.getName());
        tf_mail.setText(coach.getEmail());
        tf_phone.setText(coach.getPhone());
        tf_aca.setText(coach.getAcademyName());

    }

    @FXML
    void update(ActionEvent event) throws IOException {


        String name = tf_nameC.getText();
        String email = tf_mail.getText();
        String phone = tf_phone.getText();
        String academy_name = tf_aca.getText();


        if (tf_nameC.getText().isEmpty() || tf_mail.getText().isEmpty() || tf_phone.getText().isEmpty() || tf_aca.getText().isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs!");
            alert.showAndWait();
        } else {
            try {
                Coach c = new Coach();
                c.setName(tf_nameC.getText());
                c.setEmail(tf_mail.getText());
                c.setPhone(tf_phone.getText());
                c.setAcademyName(tf_aca.getText());
                c.setId(coach.getId());

                serviceCoach sc = new serviceCoach();
                sc.updateEntity2(c);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("Coach modifié avec succès");
                alert.showAndWait();

                tf_nameC.clear();
                tf_mail.clear();
                tf_phone.clear();
                tf_aca.clear();


            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur s'est produite lors de l'ajout du coach");
                alert.showAndWait();
                ex.printStackTrace();

            }

        }

        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/ModifierCoach.fxml"));
        Scene tabbleViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tabbleViewScene);
        window.show();
    }

    }



