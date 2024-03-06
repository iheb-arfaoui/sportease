package tn.esprit.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.assets.Constants;
import tn.esprit.models.Terrain;
import tn.esprit.services.TerrainService;
import java.util.UUID;


public class TerrainEditController implements Initializable {

    private int selectedTerrainId;
    private Terrain selectedTerrain;
    private TerrainService terrainCRUD;

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtCapacity;
    @FXML
    private ChoiceBox<String> txtsportType;
    @FXML
    private TextField txtrentPrice;
    @FXML
    private CheckBox txtDisponibility;
    @FXML
    private TextField txtaddress;
    @FXML
    private TextField txtupdatedat;
    @FXML
    private ImageView terrain_image;

    @FXML
    private Button btnUpload;

    private File imageFile;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        terrainCRUD = new TerrainService();
        txtsportType.getItems().addAll(Constants.categories);
    }

    public void setSelectedTerrainId(int terrainId) throws SQLException {
        this.selectedTerrainId = terrainId;
        this.selectedTerrain = terrainCRUD.display(terrainId);
        txtName.setText(selectedTerrain.getName());
        txtCapacity.setText(String.valueOf(selectedTerrain.getCapacity()));
        txtsportType.setValue(selectedTerrain.getSportType());
        txtrentPrice.setText(String.valueOf(selectedTerrain.getCapacity()));
        txtDisponibility.setSelected(selectedTerrain.isDisponibility());
        txtaddress.setText(selectedTerrain.getAddress());
    }

    public void handleSaveButtonClick(ActionEvent event) throws IOException {
        // Retrieve the Academy data from the fields
        int id = selectedTerrain.getId();
        String name = txtName.getText();
        int capacity = Integer.parseInt(txtCapacity.getText());
        String category = txtsportType.getValue();
        int rentPrice = Integer.parseInt(txtrentPrice.getText());
        boolean disponibility = txtDisponibility.isSelected();
        String address = txtaddress.getText();

        if (name.trim().isEmpty() || category == null || address.trim().isEmpty() || capacity == 0 || rentPrice == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Field");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();

        } else if (!name.matches("[a-zA-Z ]+") || !address.matches("[a-zA-Z ]+") ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Field Can Only Contain Letters");
            alert.setContentText("Please enter a valid name");
            alert.showAndWait();

        } else if (terrainCRUD.terrainExists(name)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Terrain Already Exists");
            alert.setHeaderText(null);
            alert.setContentText("A Terrain with the same name already exists");
            alert.showAndWait();
        } else {
            TerrainService terrainCRUD = new TerrainService();
            Terrain terrain = new Terrain();
            terrain.setId(id);
            terrain.setName(name);
            terrain.setCapacity(capacity);
            terrain.setSportType(category);
            terrain.setRentPrice(rentPrice);
            terrain.setDisponibility(disponibility);
            terrain.setAddress(address);

            // Update the image if a new one is selected
            if (imageFile != null) {
                String destPath = Constants.DEST_PATH;
                String imageName = generateUniqueName(imageFile); // Generate a unique name for the image
                File dest = new File(destPath + imageName); // Set the destination path for the image
                try {
                    Files.copy(imageFile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    terrain.setImageName(imageName);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                terrain.setImageName("nophoto.jpg");
            }

            terrainCRUD.updateEntity(terrain);

            // Close the window
            Stage stage = (Stage) txtName.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(URI.create(Constants.TerrainList).toURL());
            Parent terrainListParent = loader.load();
            Scene academyListScene = new Scene(terrainListParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(academyListScene);
            window.show();
        }
    }

    @FXML
    private void upload_imageAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG
                = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg
                = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG
                = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng
                = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        FileChooser.ExtensionFilter extFilterJPEG
                = new FileChooser.ExtensionFilter("JPEG files (*.JPEG)", "*.JPEG");
        FileChooser.ExtensionFilter extFilterjpeg
                = new FileChooser.ExtensionFilter("jpeg files (*.jpeg)", "*.jpeg");
        fileChooser.getExtensionFilters()
                .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng, extFilterJPEG, extFilterjpeg);
        //Show open file dialog
        imageFile = fileChooser.showOpenDialog(null);
        if (imageFile != null) {
            String image_uri = imageFile.toURI().toString();
            // Display the selected image in an ImageView
            Image image = new Image(image_uri);
            terrain_image.setImage(image);
        }
    }

    private String generateUniqueName(File imageFile) {
        String name = imageFile.getName();
        String extension = name.substring(name.lastIndexOf(".")).toLowerCase(); // Get the file extension
        String imageName = UUID.randomUUID().toString() + extension; // Generate a unique name with the same extension
        return imageName;
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
}
