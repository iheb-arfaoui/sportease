package controllers;

import entities.Academy;
import entities.Coach;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.ServiceAcademy;
import services.serviceCoach;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AfficherCoach implements Initializable {

    @FXML
    private VBox vbox2;

    @FXML
    private TextField rechercher;

    @FXML
    private Button btnn;

    @FXML
    private Button btn_excelC;


    @FXML
    private ScrollPane scroll2;

    @FXML
    private GridPane grid2;

    @FXML
    private Button btn_tri;

    @FXML

    private Button tf_retour;

    private List<Coach> coaches;
    int rows=3;
    int columns=0;



    public void resetRowsColumns()
    {
        rows=3;
        rows=0;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serviceCoach sc=new serviceCoach();
        coaches=sc.display();
        try {

            for (Coach c :coaches) {
                System.out.println(c);
                FXMLLoader fxmlLoader = new FXMLLoader ();
                fxmlLoader.setLocation(getClass().getResource("/CardCoach.fxml"));
                AnchorPane cardBox = fxmlLoader.load ();
                cardBox.setOpacity(10); // Définit un espacement de 10 pixels entre les éléments enfants du HBox

                CardC card = fxmlLoader.getController();
                card.setData(c);
                if(columns==1)
                {
                    columns=0;
                    rows++;
                }
                grid2.add(cardBox,columns++,rows);
                GridPane.setMargin(cardBox,new Insets(10));
            }

        } catch (IOException e) {
            e.printStackTrace ();
        }

        rechercher.textProperty().addListener((observable, oldValue, newValue) -> {
            rechercherCoach(newValue.trim());
        });
        rechercherCoach("");
    }

    @FXML
    void naviguezVersAjouter(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCoach.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnn.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void afficherCoaches(List<Coach> coaches) {
        grid2.getChildren().clear(); // Effacer les éléments précédents

        //resetRowsColumns(); // Réinitialiser les lignes et colonnes
        try {
            for (Coach c : coaches) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardCoach.fxml"));
                AnchorPane cardBox = fxmlLoader.load();
                cardBox.setOpacity(10); // Définit un espacement de 10 pixels entre les éléments enfants du HBox

                CardC cardC = fxmlLoader.getController();
                cardC.setData(c);
                if (columns == 2) {
                    columns = 0;
                    rows++;
                }
                grid2.add(cardBox, columns++, rows);
                GridPane.setMargin(cardBox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void rechercherCoach(String motCle) {
                /*String motCle = recherche.getText().toLowerCase().trim();
                List<Club> clubsFiltres = clubs.stream()
                        .filter(club -> club.getNomClub().toLowerCase().contains(motCle))
                        .collect(Collectors.toList());

                afficherClubs(clubsFiltres);*/
        rechercher.getText().toLowerCase().trim();
        serviceCoach service = new serviceCoach();
        List<Coach> coaches = service.display();

        if (!motCle.isEmpty()) {
            coaches = coaches.stream()
                    .filter(p -> p.getName().toLowerCase().contains(motCle.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Mettre à jour l'affichage des clubs filtrés dans la grille
        afficherCoaches(coaches);

    }

    @FXML
    void trier(ActionEvent event) {
        List<Coach> coachTries = coaches.stream()
                .sorted(Comparator.comparing(Coach::getName))
                .collect(Collectors.toList());

        afficherCoaches(coachTries);
    }

    @FXML
    void excelExport(ActionEvent event) throws IOException {
        String filePathC = "coaches.xlsx";

        // Appeler la méthode d'exportation Excel avec la liste complète des clubs et le chemin du fichier Excel
        ExcelExport.exportToExcel(coaches, filePathC);

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
            desktop.open(new File(filePathC));


        }
    }
    @FXML
    void naviguerverspremierpage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/premierepage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) tf_retour.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }



