package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application{
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/premierepage.fxml"));
        //Scene scene = new Scene((new FXMLLoader(getClass().getResource("/AjouterClub.fxml"))).load());
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Liste des académies");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}

