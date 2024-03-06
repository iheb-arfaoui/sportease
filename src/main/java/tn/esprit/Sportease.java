package tn.esprit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Sportease extends Application {

    @Override
    public void start(Stage stage) throws IOException {
//        URL url = getClass().getResource("TerrainAdd.fxml");
        URL url = getClass().getResource("Auth.fxml");
        if (url == null) {
            System.out.println("Testing.fxml not found!");
        } else {
            System.out.println("URL: " + url);
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            stage.setMinWidth(1280);
            stage.setMinHeight(720);
            Image icon = new Image("logo.png");
            stage.getIcons().add(icon);
            stage.setTitle("Sportease");
            stage.setScene(scene);
            stage.show();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
