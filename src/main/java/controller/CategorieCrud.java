package controller;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entities.Categorie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.CategorieService;

public class CategorieCrud {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button supprimerButton;

    @FXML
    private Button modifierButton;
    @FXML
    private TableColumn<?, ?> dateCo;

    @FXML
    private TableColumn<?, ?> nomCo;

    @FXML
    private TableColumn<?, ?> numCo;

    @FXML
    private TableColumn<?, ?> GainCo;

    @FXML
    private TableView<Categorie> table;

    @FXML
    private TableColumn<?, ?> typeCo;
    private Categorie selectedCategorie;




    void afficherTable(){
        nomCo.setCellValueFactory(new PropertyValueFactory<>("categorieName"));
        numCo.setCellValueFactory(new PropertyValueFactory<>("nombreJoueur"));
        dateCo.setCellValueFactory(new PropertyValueFactory<>("date"));
        typeCo.setCellValueFactory(new PropertyValueFactory<>("typeCategorie"));
        GainCo.setCellValueFactory(new PropertyValueFactory<>("gain"));

        CategorieService categorieService = new CategorieService();
        try {

            ObservableList<Categorie> categorieList = FXCollections.observableArrayList(categorieService.readAll());
            table.setItems(categorieList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void addCategorie(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CategorieCrudForm.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) table.getScene().getWindow();
            stage.setTitle("SporTease");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void modifierCategorie(ActionEvent event) {
        if (selectedCategorie != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CategorieCrudForm.fxml"));
            try {
                Parent root = fxmlLoader.load();
                CategorieCrudForm categorieCrudForm = fxmlLoader.getController();
                categorieCrudForm.setCategorie(selectedCategorie);

                Scene scene = new Scene(root);
                Stage stage = (Stage) table.getScene().getWindow();
                stage.setTitle("SporTease");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void supprimerCategorie(ActionEvent event) {
        if(selectedCategorie != null)
        {
            CategorieService categorieService = new CategorieService();
            try {
                categorieService.delete(selectedCategorie.getCategorieId());
                afficherTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void initialize() {
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'CategorieCrud.fxml'.";
        afficherTable();

        table.setOnMouseClicked((event)->handleRowClick());
        supprimerButton.setDisable(true);
        modifierButton.setDisable(true);
    }

    private void handleRowClick() {
        selectedCategorie = table.getSelectionModel().getSelectedItem();
        supprimerButton.setDisable(false);
        modifierButton.setDisable(false);

    }

    public void openEvenement(javafx.scene.input.MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EvenementCrud.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) table.getScene().getWindow();
            stage.setTitle("SporTease");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
