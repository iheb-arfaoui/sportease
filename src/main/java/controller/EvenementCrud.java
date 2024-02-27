package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entities.Evenement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.EvenementService;
import entities.Evenement;
import entities.Categorie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.EvenementService;
import services.CategorieService;

public class EvenementCrud {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<?, ?> addressCo;

    @FXML
    private TableColumn<?, ?> eventAdressCo;
    @FXML
    private TableColumn<?, ?> categorieCo;

    @FXML
    private TableColumn<?, ?> dateCo;

    @FXML
    private Button modifierButton;

    @FXML
    private TableColumn<?, ?> nomCo;

    @FXML
    private TableColumn<?, ?> statusCo;

    @FXML
    private Button supprimerButton;

    @FXML
    private TableView<Evenement> table;
    @FXML
    private Evenement selectedEvenement;
    private Object categorieCombo;
    @FXML
    private ComboBox<String> statusCombo;


    @FXML
    void openCategorie(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CategorieCrud.fxml"));
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

    void afficherTable(){
        nomCo.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        addressCo.setCellValueFactory(new PropertyValueFactory<>("eventAbr"));
        dateCo.setCellValueFactory(new PropertyValueFactory<>("eventDate"));
        statusCo.setCellValueFactory(new PropertyValueFactory<>("status"));
        eventAdressCo.setCellValueFactory(new PropertyValueFactory<>("eventAdress"));
        categorieCo.setCellValueFactory(new PropertyValueFactory<>("categorieId"));

        EvenementService EvenementService = new EvenementService();
        try {

            ObservableList<Evenement> EvenementList = FXCollections.observableArrayList(EvenementService.readAll());
            table.setItems(EvenementList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void addEvenement(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EvenementCrudForm.fxml"));
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
    void modifierEvenement(ActionEvent event) {
        if(selectedEvenement != null)
        {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EvenementCrudForm.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load());
                EvenementCrudForm EvenementCrudForm = fxmlLoader.getController();

                EvenementCrudForm.setEvenement(selectedEvenement);
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
    void supprimerEvenement(ActionEvent event) {
        if(selectedEvenement != null)
        {
            EvenementService EvenementService = new EvenementService();
            try {
                EvenementService.delete(selectedEvenement.getEvenementId());
                afficherTable();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void initialize() {
        assert addressCo != null : "fx:id=\"addressCo\" was not injected: check your FXML file 'EvenementCrud.fxml'.";
        assert eventAdressCo != null : "fx:id=\"eventAdressCo\" was not injected: check your FXML file 'EvenementCrud.fxml'.";
        assert dateCo != null : "fx:id=\"dateCo\" was not injected: check your FXML file 'EvenementCrud.fxml'.";
        assert modifierButton != null : "fx:id=\"modifierButton\" was not injected: check your FXML file 'EvenementCrud.fxml'.";
        assert nomCo != null : "fx:id=\"nomCo\" was not injected: check your FXML file 'EvenementCrud.fxml'.";
        assert statusCo != null : "fx:id=\"statusCo\" was not injected: check your FXML file 'EvenementCrud.fxml'.";
        assert supprimerButton != null : "fx:id=\"supprimerButton\" was not injected: check your FXML file 'EvenementCrud.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'EvenementCrud.fxml'.";
        afficherTable();

        table.setOnMouseClicked((event)->handleRowClick());
        supprimerButton.setDisable(true);
        modifierButton.setDisable(true);
    }

    private void handleRowClick() {
        selectedEvenement = table.getSelectionModel().getSelectedItem();
        supprimerButton.setDisable(false);
        modifierButton.setDisable(false);

    }
}
