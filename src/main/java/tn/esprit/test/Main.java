/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.test;

//import com.jfoenix.controls.JFXSnackbar;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;



/**
 *
 * @author WALID
 */
public class Main extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("../gui/terrain/Home.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("Terrain_view_client.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("../gui/academy/AcademyList.fxml"));
        Scene scene = new Scene(root);
        Image icon = new Image("/tn/esprit/assets/icon.png");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setTitle("Sportease");
        stage.setResizable(true);
        stage.show();
    }
    
}
