package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.assets.Constants;
import tn.esprit.models.Reservation;
import tn.esprit.services.ReservationService;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;


public class BookingController implements Initializable {

    @FXML
    private TextField userNameTextField;

    @FXML
    private TextField phoneTextField;

    private ReservationService reservationService;
    private int terrainId;
    private int clientId;
    private LocalDate selectedDate;
    private LocalTime selectedTime;



    public void setTerrainId(int terrainId) {
        this.terrainId = terrainId;
    }

    public void setSelectedDate(LocalDate selectedDate) {
        this.selectedDate = selectedDate;
    }

    public void setSelectedTime(LocalTime selectedTime) {
        this.selectedTime = selectedTime;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reservationService = new ReservationService();
    }

    @FXML
    private void confirmBooking(ActionEvent event) throws IOException {
        String userName = userNameTextField.getText();
        String phone = phoneTextField.getText();
        this.clientId = 1;
        if (userName.trim().isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Field");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();

        } else if (!userName.matches("[a-zA-Z ]+") ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Field Can Only Contain Letters");
            alert.setContentText("Please enter a valid name");
            alert.showAndWait();
        } else if (!phone.matches("\\d{8}")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Phone Number");
            alert.setHeaderText(null);
            alert.setContentText("Phone number must be exactly 8 digits");
            alert.showAndWait();
        } else {


            // Create a reservation object
            Reservation reservation = new Reservation();
            reservation.setTerrainId(terrainId);
            reservation.setClientId(clientId);
            // You may need to set the client ID here if available in your application
            reservation.setDateRes(Date.valueOf(selectedDate));
            reservation.setTime(selectedTime);
            reservation.setUserName(userName);
            reservation.setPhone(phone);

            // Add the reservation
            reservationService.addReservation(reservation);
            redirect();
        }
    }

    private void redirect() throws IOException {
        FXMLLoader loader = new FXMLLoader(URI.create(Constants.TerrainListClient).toURL());
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) userNameTextField.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleBackButtonClick(javafx.event.ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(URI.create(Constants.TerrainListClient).toURL());
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
