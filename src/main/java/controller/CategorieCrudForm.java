package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

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
import services.CategorieService;

public class CategorieCrudForm {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker dateField;

    @FXML
    private TextField nomField;

    @FXML
    private TextField numField;

    @FXML
    private TextField gainField;

    @FXML
    private ComboBox<String> typeCombo;

    private Categorie selectedCategorie;


    @FXML
    void openEvenement(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EvenementCrudForm.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) typeCombo.getScene().getWindow();
            stage.setTitle("SporTease");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    void goBack(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CategorieCrud.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) typeCombo.getScene().getWindow();
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
        CategorieService categorieService = new CategorieService();

        String nom = nomField.getText();
        String nombreJou = numField.getText();
        String date = String.valueOf(dateField.getValue());
        String type = typeCombo.getValue();
        String gainText = gainField.getText();

        if (isValidInput(nom, nombreJou, date, type, gainText)) {
            try {
                Float gain = Float.parseFloat(gainText);

                if (selectedCategorie == null) {
                    Categorie categorie = new Categorie(nom, nombreJou, date, type, gain);
                    categorieService.add(categorie);
                } else {
                    Categorie categorie = new Categorie(selectedCategorie.getCategorieId(), nom, nombreJou, date, type, gain);
                    categorieService.update(categorie);
                }

                goBack();
            } catch (SQLException | NumberFormatException e) {
                showAlert("Error", "Invalid input for Salary", "Please enter a valid numeric value for Salary.");
            }
        } else {
            showAlert("Error", "Invalid Input", "Please fill in all the fields.");
        }
    }

    private boolean isValidInput(String nom, String nombreJou, String date, String type, String gainText) {
        return !nom.isEmpty() && !nombreJou.isEmpty() && date != null && type != null && !gainText.isEmpty();
    }

    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR   );
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void setCategorie(Categorie selected) {
        selectedCategorie = selected;

        nomField.setText(selectedCategorie.getCategorieName());
        numField.setText(selectedCategorie.getNombreJoueur());
        gainField.setText(String.valueOf(selectedCategorie.getGain()));
        typeCombo.setValue(selectedCategorie.getTypeCategorie());
        dateField.setValue(LocalDate.parse(selectedCategorie.getDate()));
    }
}
