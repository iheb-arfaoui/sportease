package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tn.esprit.assets.Constants;
import tn.esprit.models.Terrain;
import tn.esprit.services.TerrainService;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TerrainDetailsClient implements Initializable {

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtCapacity;
    @FXML
    private TextField txtsportType;
    @FXML
    private TextField txtrentPrice;
    @FXML
    private CheckBox txtDisponibility;
    @FXML
    private TextField txtaddress;
    @FXML
    private TextField txtupdatedat;
    @FXML
    private Button btnBook;

    @FXML
    private ImageView img;

    private TerrainService terrainCRUD = new TerrainService();
    @FXML
    private ImageView backIcon;
    private int terrainId;



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtDisponibility.setDisable(true);
    }

    public void setTerrain(Terrain terrain) {
        String image_path_directory = "file:"+Constants.DEST_PATH;
        String full_path;
        txtId.setText(String.valueOf(terrain.getId()));
        txtName.setText(terrain.getName());
        txtCapacity.setText(String.valueOf(terrain.getCapacity()));
        txtsportType.setText(terrain.getSportType());
        txtrentPrice.setText(String.valueOf(terrain.getRentPrice()));
        txtDisponibility.setSelected(terrain.isDisponibility());
        txtaddress.setText(terrain.getAddress());
        txtupdatedat.setText(terrain.getUpdatedAt().toString());
        this.terrainId = terrain.getId();
        System.out.println(terrain.getImageName());
        if (terrain.getImageName() != null) {
            full_path = image_path_directory + terrain.getImageName();
            System.out.println(full_path);
            img.setImage(new Image(full_path));
        } else {
            System.out.println("No image");
        }
    }

    @FXML
    private void handleBackButtonClick(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(URI.create(Constants.TerrainListClient).toURL());
            Parent terrainListParent = loader.load();
            Scene scene = new Scene(terrainListParent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void handleBookButtonClick(ActionEvent event) throws IOException {
        try {
            // Load the FXMLLoader for the ReservationClient interface
            FXMLLoader loader = new FXMLLoader(URI.create(Constants.ReservationsClient).toURL());

            // Load the FXML file and get the root node
            Parent reservationClientParent = loader.load();

            // Get the controller associated with the loaded FXML
            ReservationClient reservationClientController = loader.getController();

            // Set the terrain ID in the controller
            reservationClientController.setTerrainId(terrainId);

            // Create a new scene with the root node
            Scene scene = new Scene(reservationClientParent);

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the scene to the stage
            stage.setScene(scene);

            // Show the stage
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


}

