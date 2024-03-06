package tn.esprit.controllers;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tn.esprit.assets.Constants;
import tn.esprit.models.Terrain;
import tn.esprit.services.TerrainService;

public class TerrainDetailsController implements Initializable {

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
    private Button btnEdit;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnRes;
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
    public void handleEditButtonClick(ActionEvent event) throws IOException, SQLException {
        String idText = txtId.getText();
        if (idText.isEmpty()) {
            // Display an error message to the user
            Alert alert = new Alert(AlertType.ERROR, "Please enter an ID");
            alert.showAndWait();
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            // Display an error message to the user
            Alert alert = new Alert(AlertType.ERROR, "Invalid ID: " + idText);
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(URI.create(Constants.TerrainEdit).toURL());
        Parent terrainEditParent = loader.load();
        // Get the controller of the AcademyEdit.fxml file
        TerrainEditController controller = loader.getController();

        // Set the ID of the selected academy
        controller.setSelectedTerrainId(id);

        Stage stage = (Stage) txtId.getScene().getWindow();
        Scene scene = new Scene(terrainEditParent);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void handleDeleteButtonClick(ActionEvent event) throws IOException {
        int terrainId = Integer.parseInt(txtId.getText());

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this field?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            terrainCRUD.deleteEntity(terrainId);

            Alert alertDeleted = new Alert(AlertType.INFORMATION);
            alertDeleted.setTitle("Field Deleted");
            alertDeleted.setHeaderText(null);
            alertDeleted.setContentText("Field has been deleted successfully.");
            alertDeleted.showAndWait();

            Stage stage = (Stage) btnDelete.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(URI.create(Constants.TerrainList).toURL());
            Parent terrainListParent = loader.load();
            Scene scene = new Scene(terrainListParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            window.show();
        }
    }


    @FXML
    private void handleBackButtonClick(javafx.event.ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(URI.create(Constants.TerrainList).toURL());
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
            // Load the FXMLLoader
            FXMLLoader loader = new FXMLLoader(URI.create(Constants.Reservations).toURL());
            // Load the FXML file and get the root node
            Parent terrainListParent = loader.load();
            // Get the controller associated with the loaded FXML
            ReservationController reservationController = loader.getController();
            // Pass the terrain ID to the ReservationController
            reservationController.setTerrainId(terrainId);;
            // Create a new scene with the root node
            Scene scene = new Scene(terrainListParent);
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

