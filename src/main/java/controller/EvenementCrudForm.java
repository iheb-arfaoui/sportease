package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

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

public class EvenementCrudForm {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField addressField;

    @FXML
    private TextField eventAdressField;

    @FXML
    private DatePicker dateField;

    @FXML
    private ComboBox<String> categorieCombo;

    @FXML
    private TextField nomField;

    @FXML
    private ComboBox<String> statusCombo;
    private Evenement selectedEvenement;


    @FXML
    void openCategorie(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CategorieCrud.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) statusCombo.getScene().getWindow();
            stage.setTitle("SporTease");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void goBack(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EvenementCrud.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) statusCombo.getScene().getWindow();
            stage.setTitle("SporTease");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void cancel(ActionEvent event) {
        goBack();
    }

    @FXML
    void submit(ActionEvent event) {
        EvenementService evenementService = new EvenementService();
        CategorieService categorieService = new CategorieService();

        String nomDuClient = nomField.getText();
        String address = addressField.getText();
        String date = String.valueOf(dateField.getValue());
        String status = statusCombo.getValue();
        String eventAdress = eventAdressField.getText();
        String categorie = categorieCombo.getValue();

        if (isValidInput(nomDuClient, address, date, status, eventAdress, categorie)) {
            try {
                Categorie categorieSearch = categorieService.searchByName(categorie);

                if (selectedEvenement == null) {
                    Evenement evenement = new Evenement(nomDuClient, address, date, status, eventAdress, categorieSearch.getCategorieId());
                    evenementService.add(evenement);
                } else {
                    Evenement evenement = new Evenement(selectedEvenement.getEvenementId(), nomDuClient, address, date, status, eventAdress, categorieSearch.getCategorieId());
                    evenementService.update(evenement);
                }

                goBack();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            showAlert("Error", "Missing input", "Please fill in all the fields.");
        }
    }

    private boolean isValidInput(String... inputs) {
        for (String input : inputs) {
            if (input == null || input.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    @FXML
    void initialize() {
        assert addressField != null : "fx:id=\"addressField\" was not injected: check your FXML file 'EvenementCrudForm.fxml'.";
        assert eventAdressField != null : "fx:id=\"eventAdressField\" was not injected: check your FXML file 'EvenementCrudForm.fxml'.";
        assert dateField != null : "fx:id=\"dateField\" was not injected: check your FXML file 'EvenementCrudForm.fxml'.";
        assert categorieCombo != null : "fx:id=\"EvenementCombo\" was not injected: check your FXML file 'EvenementCrudForm.fxml'.";
        assert nomField != null : "fx:id=\"nomField\" was not injected: check your FXML file 'EvenementCrudForm.fxml'.";
        assert statusCombo != null : "fx:id=\"statusCombo\" was not injected: check your FXML file 'EvenementCrudForm.fxml'.";

        CategorieService categorieService = new CategorieService();
        try {
            List<Categorie> allCategories = categorieService.readAll();
            for (Categorie categorie : allCategories) {
                categorieCombo.getItems().add(categorie.getCategorieName());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setEvenement(Evenement selected) {
        selectedEvenement = selected;

        nomField.setText(selectedEvenement.getEventName());
        addressField.setText(selectedEvenement.getEventAbr());
        eventAdressField.setText(String.valueOf(selectedEvenement.getEventAdress()));
        statusCombo.setValue(selectedEvenement.getStatus());
        dateField.setValue(LocalDate.parse(selectedEvenement.getEventDate()));

        CategorieService categorieService = new CategorieService();
        try {
            Categorie categorie = categorieService.searchById(selectedEvenement.getCategorieId());
            categorieCombo.setValue(categorie.getCategorieName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
