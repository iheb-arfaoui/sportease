package controllers;

import Entites.Academy;
import Entites.Coach;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import Service.ServiceAcademy;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.scene.control.TextField;


public class AfficherAcademy implements Initializable {

    @FXML
    private VBox vbox;

    @FXML
    private ScrollPane scroll;

    @FXML
    private Button btn_addA;

    @FXML
    private TextField rechercherA;


    @FXML
    private Button btn_excel;



    @FXML
    private Button btn;


    @FXML
    private GridPane grid;

    @FXML
    private Button tf_ret;


    private List<Academy> academies;
    int rows=3;
    int columns=0;



    public void resetRowsColumns()
    {
        rows=3;
        rows=0;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ServiceAcademy sc=new ServiceAcademy();
        academies=sc.display();
        try {

            for (Academy a :academies) {
                System.out.println(a);
                FXMLLoader fxmlLoader = new FXMLLoader ();
                fxmlLoader.setLocation(getClass().getResource("/CardAcademy.fxml"));
                AnchorPane cardBox = fxmlLoader.load ();
                cardBox.setOpacity(10); // Définit un espacement de 10 pixels entre les éléments enfants du HBox

                CardA card = fxmlLoader.getController();
                card.setData(a);
                if(columns==1)
                {
                    columns=0;
                    rows++;
                }
                grid.add(cardBox,columns++,rows);
                GridPane.setMargin(cardBox,new Insets(10));
            }

        } catch (IOException e) {
            e.printStackTrace ();
        }

        rechercherA.textProperty().addListener((observable, oldValue, newValue) -> {
            rechercherAcademy(newValue.trim());
        });
        rechercherAcademy("");
    }

    @FXML
    void naviguerVersAjouter(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterAcademy.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btn_addA.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void afficherAcademies(List<Academy> academies) {
        grid.getChildren().clear(); // Effacer les éléments précédents

        //resetRowsColumns(); // Réinitialiser les lignes et colonnes
        try {
            for (Academy a : academies) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardAcademy.fxml"));
                AnchorPane cardBox = fxmlLoader.load();
                cardBox.setOpacity(10); // Définit un espacement de 10 pixels entre les éléments enfants du HBox

                CardA cardA = fxmlLoader.getController();
                cardA.setData(a);
                if (columns == 2) {
                    columns = 0;
                    rows++;
                }
                grid.add(cardBox, columns++, rows);
                GridPane.setMargin(cardBox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void rechercherAcademy(String motCle) {
                /*String motCle = recherche.getText().toLowerCase().trim();
                List<Club> clubsFiltres = clubs.stream()
                        .filter(club -> club.getNomClub().toLowerCase().contains(motCle))
                        .collect(Collectors.toList());

                afficherClubs(clubsFiltres);*/
        rechercherA.getText().toLowerCase().trim();
        ServiceAcademy service = new ServiceAcademy();
        List<Academy> academies = service.display();

        if (!motCle.isEmpty()) {
            academies = academies.stream()
                    .filter(p -> p.getName().toLowerCase().contains(motCle.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Mettre à jour l'affichage des clubs filtrés dans la grille
        afficherAcademies(academies);

    }

    @FXML
    void Trier(ActionEvent event) {
        List<Academy> academiesTries = academies.stream()
            .sorted(Comparator.comparing(Academy::getName))
            .collect(Collectors.toList());

        afficherAcademies(academiesTries);

    }

    @FXML
    void ExelExplor(ActionEvent event) throws IOException {
        String filePathA = "academies.xlsx";

        // Appeler la méthode d'exportation Excel avec la liste complète des clubs et le chemin du fichier Excel
        ExcelExport.exportToExcelA(academies, filePathA);

        // Afficher un message de succès à l'utilisateur
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Exportation Excel");
        alert.setHeaderText(null);
        alert.setContentText("Les coaches ont été exportés vers Excel avec succès !");
        alert.showAndWait();

        if (Desktop.isDesktopSupported()) {
            // Obtenir l'instance de Desktop
            Desktop desktop = Desktop.getDesktop();


            // Ouvrir le fichier avec l'application par défaut associée à son extension
            desktop.open(new File(filePathA));


        }

    }
    @FXML
    void naviguervers1page(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) tf_ret.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}

