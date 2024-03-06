package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.services.TerrainService;
import tn.esprit.models.Terrain;
import tn.esprit.assets.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;
//import java.awt.event.ActionEvent;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class TerrainAddController implements Initializable {

    // Inject the terrain service using Spring or other dependency injection frameworks
    private final TerrainService terrainService;

    public TerrainAddController() {
        // Initialize terrainService here
        this.terrainService = new TerrainService(); // or initialize it with any appropriate value
    }

    public TerrainAddController(TerrainService terrainService) {
        this.terrainService = terrainService;
    }

    // Inject the necessary UI components from your FXML file
    @FXML
    private Button btnUpload;

    @FXML
    private TextField nameField;

    @FXML
    private Spinner<Integer> capacityField;

    @FXML
    private ChoiceBox<String> sportTypeField;

    @FXML
    private Spinner<Integer> rentPriceField;

    @FXML
    private CheckBox disponibilityCheckBox;

    @FXML
    private TextField addressField;

    @FXML
    private WebView mapWebView;

    @FXML
    private ImageView terrainImage;
    private File imageFile;
    private int id_owner;

    public void setData(int id_owner) {
        this.id_owner = 1;
    }

    // Initialize method
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SpinnerValueFactory<Integer> valueFactory_c = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 9999999, 1, 1);// (min,max,startvalue,incrementValue)
        SpinnerValueFactory<Integer> valueFactory_rp = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 9999999, 1, 1);// (min,max,startvalue,incrementValue)
        capacityField.setValueFactory(valueFactory_c);
        rentPriceField.setValueFactory(valueFactory_rp);
        sportTypeField.getItems().addAll(Constants.categories);
//        WebEngine webEngine = mapWebView.getEngine();
//        webEngine.load(getClass().getResource("/Maps.html").toExternalForm());
    }

    // Define a method to handle adding a new terrain entity
    @FXML
    private void handleAddTerrain() {
        // Retrieve input data from UI components
        String name = nameField.getText();
        int capacity = capacityField.getValue();
        String sportType = sportTypeField.getValue();
        int rentPrice = rentPriceField.getValue();
        boolean disponibility = disponibilityCheckBox.isSelected();
        String address = addressField.getText();
        LocalDateTime updatedAt = LocalDateTime.now();
        if (name.trim().isEmpty() || sportType == null || address.trim().isEmpty() || capacity == 0 || rentPrice == 0) {
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

        } else if (terrainService.terrainExists(name)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Terrain Already Exists");
            alert.setHeaderText(null);
            alert.setContentText("A Terrain with the same name already exists");
            alert.showAndWait();
        } else {

            if (imageFile != null) {
                //
                String destPath = Constants.DEST_PATH;
                //                String destPath = "C:/Users/ramib/Desktop/";
                String imageName = generateUniqueName(imageFile); // Generate a unique name for the image
                File dest = new File(destPath + imageName); // Set the destination path for the image
                try {
                    Files.copy(imageFile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    Terrain newTerrain = new Terrain(id_owner, name, capacity, sportType, rentPrice, disponibility, address, imageName, updatedAt);
                    newTerrain.setImageName(imageName);
                    terrainService.addEntity(newTerrain);
                    System.out.println("Terrain added successfully");
                    redirect();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                String imageName = "nophoto.png";
                URL defaultImageUrl = getClass().getResource(Constants.Icons + imageName);
                if (defaultImageUrl != null) {
                    File defaultImageFile = new File(defaultImageUrl.getPath());
                    //                    String destPath = "C:/Users/ramib/Desktop/Study/Pidev/Java/Projects/Uploads/";
                    String destPath = Constants.DEST_PATH;
                    File dest = new File(destPath + imageName);
                    try {
                        Files.copy(defaultImageFile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        Terrain newTerrain = new Terrain(id_owner, name, capacity, sportType, rentPrice, disponibility, address, imageName, updatedAt);
                        terrainService.addEntity(newTerrain);
                        System.out.println("Terrain added successfully");
                        redirect();
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                } else {
                    System.out.println("Unable to find default image");
                }
            }
        }
    }

    @FXML
    private void upload_imageAction(javafx.event.ActionEvent event) {
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
            terrainImage.setImage(image);
        }
    }

    private String generateUniqueName(File imageFile) {
        String name = imageFile.getName();
        String extension = name.substring(name.lastIndexOf(".")).toLowerCase(); // Get the file extension
        String imageName = UUID.randomUUID().toString() + extension; // Generate a unique name with the same extension
        return imageName;
    }

    private void redirect() throws IOException {
        FXMLLoader loader = new FXMLLoader(URI.create(Constants.TerrainList).toURL());
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private static final Set<String> BAD_WORDS = new HashSet<>();



    static {
            try {
                File file = new File(Constants.Badwords);
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    BAD_WORDS.add(line.trim().toLowerCase());
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static boolean containsBadWords(String text) {
        String[] words = text.split("\\s+");
        for (String word : words) {
            if (BAD_WORDS.contains(word.toLowerCase())) {
                return true;
            }
        }
        return false;
    }


        @FXML
    private void handleBackButtonClick(ActionEvent event) throws IOException {
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
