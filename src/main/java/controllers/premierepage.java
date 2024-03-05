package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class premierepage {



    @FXML
    private Button tf_Aca;

    @FXML
    private Button tf_Coa;

    @FXML
    void naviguerversacademie(ActionEvent event) { try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherAcademy.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) tf_Aca.getScene().getWindow();
        stage.setScene(new Scene(root));
    } catch (IOException e) {
        e.printStackTrace();
    }

    }

    @FXML
    void naviguerverscoach(ActionEvent event) { try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCoach.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) tf_Coa.getScene().getWindow();
        stage.setScene(new Scene(root));
    } catch (IOException e) {
        e.printStackTrace();
    }

    }

}
