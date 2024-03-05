
package controllers;

import Service.ServiceR;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import Entites.Categorie;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;

import static java.lang.Integer.parseInt;

public class AjouterCategorieController {

    @FXML
    private AnchorPane id;

    @FXML
    private DatePicker txtdatecategorie;

    @FXML
    private TextField txtequipeGagnante;

    @FXML
    private TextField txtmontantCategorie;

    private  final ServiceR ser = new ServiceR();

    public AjouterCategorieController() throws SQLException {
    }




    @FXML
    void ajouterCategorie(ActionEvent event) throws ParseException {


        String equipeGagnante = txtequipeGagnante.getText();

        int montantCategorie = parseInt(txtmontantCategorie.getText());

        LocalDate selectedDate = txtdatecategorie.getValue();
//controle saisie de equipegagnante
        if (equipeGagnante.isEmpty()) {
            // Afficher un message d'erreur si le champ est vide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir une équipe gagnante.");
            alert.show();
            return; // Arrêter l'exécution de la méthode si le champ est vide
        }
        // Vérifier si le champ equipeGagnante contient uniquement des lettres
        if (!equipeGagnante.matches("^[a-zA-Z]+$")) {
            // Afficher un message d'erreur si le champ ne contient pas uniquement des lettres
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le champ 'Équipe Gagnante' doit contenir uniquement des lettres.");
            alert.show();
            return; // Arrêter l'exécution de la méthode si le champ ne contient pas uniquement des lettres
        }

        if (montantCategorie <= 0) {
            // Afficher un message d'erreur si le montant de la récompense est inférieur ou égal à zéro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le montant de la récompense doit être supérieur à zéro.");
            alert.show();
            return; // Arrêter l'exécution de la méthode si le montant de la récompense est inférieur ou égal à zéro
        }
        // Vérification que la date est sélectionnée
        if (selectedDate != null) {
            Date dateCategorie = Date.valueOf(selectedDate);
            // Création de l'objet Arbitre avec les valeurs récupérées
            Categorie categorie = new Categorie( equipeGagnante, montantCategorie,  dateCategorie);

            try {
                // Appel de la méthode ajouter() de votre service avec l'arbitre en paramètre
                ser.ajouter(categorie);
                System.out.println("Categorie ajouté avec succès!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("La categorie a été ajouté avec succès !");
                alert.showAndWait();
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout d'une categorie: " + e.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur s'est produite lors de l'ajout d'une categorie: " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            // Affichage d'un message d'erreur si aucune date n'est sélectionnée
            System.out.println("Veuillez sélectionner une date d'embauche.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une date d'embauche.");
            alert.showAndWait();
        }
    }

}
