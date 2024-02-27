package controller;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import entities.Categorie;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.CategorieService;

public class Home {

    @FXML
    private ResourceBundle resources;
    @FXML
    private VBox childContainer;
    @FXML
    private URL location;

    @FXML
    private Pagination pagination;
    @FXML
    public AnchorPane rootPane;


    @FXML
    void initialize() {
        assert pagination != null : "fx:id=\"pagination\" was not injected: check your FXML file 'home.fxml'.";

        try {
            CategorieService categorieService = new CategorieService();
            List<Categorie> categorieList = categorieService.readAll();

            int itemsPerPage = 3;
            int pageCount = (categorieList.size() + itemsPerPage - 1) / itemsPerPage;

            pagination.setPageCount(pageCount);
            pagination.setPageFactory(pageIndex -> createCategoriePage(categorieList, pageIndex, itemsPerPage));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private VBox createCategoriePage(List<Categorie> categorieList, int pageIndex, int itemsPerPage) {
        VBox pageBox = new VBox(10);
        pageBox.setPadding(new Insets(10));

        int start = pageIndex * itemsPerPage;
        int end = Math.min(start + itemsPerPage, categorieList.size());

        for (int i = start; i < end; i += 3) {
            HBox cardRow = new HBox(10);

            for (int j = i; j < Math.min(i + 3, end); j++) {
                Categorie categorie = categorieList.get(j);
                VBox categorieCard = createCategorieCard(categorie);
                cardRow.getChildren().add(categorieCard);
            }

            pageBox.getChildren().add(cardRow);
        }

        return pageBox;
    }

    private VBox createCategorieCard(Categorie categorie) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: white; -fx-border-color: black;");
        card.setPadding(new Insets(10));

        ImageView imageView = new ImageView(new Image("images/logosport.png"));
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        Label nameLabel = new Label("Name: " + categorie.getCategorieName());
        Label nombreLabel = new Label("Nombre: " + categorie.getNombreJoueur());
        Label dateLabel = new Label("Date: " + categorie.getDate());
        Label typeLabel = new Label("Type of Vehicle: " + categorie.getTypeCategorie());
        Label gainLabel = new Label("Gain: " + categorie.getGain() + "DT");

        card.getChildren().addAll(imageView, nameLabel, nombreLabel, dateLabel, typeLabel, gainLabel);

        return card;
    }

    public void openCategorie(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CategorieCrud.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("SporTease");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void openEvenement(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EvenementCrud.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("SporTease");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }




}