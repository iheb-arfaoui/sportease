package controllers;
import Utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Entites.Evenement;
import Entites.Categorie;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Date;
import java.util.ResourceBundle;
public class frontRcontroller {
    @FXML
    private TableColumn<Categorie, Date> dateCategorie_col;

    @FXML
    private TableColumn<Categorie, String> equipeGagnante_col;

    @FXML
    private TableView<Categorie> events_table;
    @FXML
    private TableColumn<Categorie, String> qrCodeColumn;
    @FXML
    private TableColumn<?, ?> id_col;

    @FXML
    private TextField keywordTextFld;

    @FXML
    private TableColumn<Categorie, Integer> montantCategorie_col;

    @FXML
    private TextField search_btn;
    @FXML
    private Button print_btn;
    @FXML
    private Button events_btn;
    @FXML
    private Button showEvents;

    @FXML
    private Button Add_btn;
    Connection mc;
    PreparedStatement ste;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        readAllEvents();

        showEvents();
    }
    public void ListCategorieController() throws SQLException {
    }
    @FXML
    private void showEvents() {
        ObservableList<Categorie> listM = getEvents();
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        equipeGagnante_col.setCellValueFactory(new PropertyValueFactory<>("equipeGagnante")); // Vérifiez le nom de la propriété
        montantCategorie_col.setCellValueFactory(new PropertyValueFactory<>("montantCategorie"));
        dateCategorie_col.setCellValueFactory(new PropertyValueFactory<>("dateCategorie"));

        events_table.setItems(listM);

    }

    private ObservableList<Categorie> getEvents() {
        ObservableList<Categorie> categories_list = FXCollections.observableArrayList();
        mc = DataSource.getInstance().getCon();
        String sql = "SELECT * from categorie";
        try {
            ste = mc.prepareStatement(sql);
            ResultSet rs = ste.executeQuery();
            System.out.println(rs.toString());
            while (rs.next()) {
                categories_list.add(new Categorie(rs.getInt("id"),  rs.getString("equipeGagnante"),  rs.getInt("montantCategorie"), rs.getDate("dateCategorie")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories_list;
    }




    private void readAllEvents() {
        ObservableList<Categorie> categoriesList = FXCollections.observableArrayList();
        mc = DataSource.getInstance().getCon();
        String sql = "SELECT * FROM Categorie";
        try {
            ste = mc.prepareStatement(sql);
            ResultSet rs;
            rs = ste.executeQuery();
            while (rs.next()) {
                Categorie categorie = new Categorie(
                        rs.getString("equipeGagnante"),
                        Integer.parseInt(rs.getString("montantCategorie")),
                        rs.getDate("dateCategorie"));

                categoriesList.add(categorie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @FXML
    private void getAddView(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/ajouterCategorie.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }


    public void voirevents(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/frontEV.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
