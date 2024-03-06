package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import tn.esprit.assets.Constants;
import tn.esprit.models.Reservation;
import tn.esprit.services.ReservationService;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.cell.PropertyValueFactory;


public class ReservationController implements Initializable {

    @FXML
    private TableView<Reservation> tableView;

    @FXML
    private TableColumn<Reservation, Integer> idColumn;

    @FXML
    private TableColumn<Reservation, Integer> clientIdColumn;

    @FXML
    private TableColumn<Reservation, Date> dateColumn;

    @FXML
    private TableColumn<Reservation, String> timeColumn;

    @FXML
    private TableColumn<Reservation, String> userNameColumn;

    @FXML
    private TableColumn<Reservation, String> phoneColumn;

    private int terrainId; // Holds the ID of the selected terrain

    private ReservationService reservationService;

    public void setTerrainId(int terrainId) {
        this.terrainId = terrainId;
        // After setting terrainId, load reservations for the terrain
        loadReservations();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reservationService = new ReservationService();
        initializeTable();
        loadReservations();
    }


    private void initializeTable() {
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            clientIdColumn.setCellValueFactory(new PropertyValueFactory<>("clientId"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateRes"));
            timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
            userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
            phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }



    private void loadReservations() {
        List<Reservation> reservations = reservationService.getReservationsByTerrainId(terrainId);
        tableView.getItems().addAll(reservations);
    }

    @FXML
    private void handleBackButtonClick(javafx.event.ActionEvent event) throws IOException {
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
