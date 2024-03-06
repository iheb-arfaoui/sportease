package tn.esprit.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.esprit.assets.Constants;
import tn.esprit.services.ReservationService;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.DatePicker;


public class ReservationClient implements Initializable {
    @FXML
    private ImageView backIcon;

    @FXML
    private HBox timeSlotsContainer; // Container for time slot buttons

    @FXML
    private DatePicker datePicker; // DatePicker for selecting dates

    private int terrainId; // Holds the ID of the selected terrain

    private LocalDate selectedDate;
    private LocalTime selectedTime;// Holds the selected date

    private ReservationService reservationService;

    // Constructor to receive the terrain ID
    public void setTerrainId(int terrainId) {
        this.terrainId = terrainId;
        initialize();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Terrain ID set to: " + terrainId);
    }
    private void initialize() {
        reservationService = new ReservationService();
        selectedDate = LocalDate.now(); // Set default selected date to today
        datePicker.setValue(selectedDate); // Set default value of DatePicker to today
        displayTimeSlots();

        // Listen for changes to the selected date
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            selectedDate = newValue;
            updateDisplayTimeSlots();
        });
    }

    private void displayTimeSlots() {
        updateDisplayTimeSlots();
    }

    private void updateDisplayTimeSlots() {
        // Clear existing time slots
        timeSlotsContainer.getChildren().clear();

        // Fetch booked times for the selected terrain for the selected date
        List<LocalTime> bookedTimes = reservationService.getBookedTimesForTerrainForSingleDay(terrainId, selectedDate);
        System.out.println("Booked times for terrain ID " + terrainId + " on " + selectedDate + ": " + bookedTimes);

        // Define time slots for the selected date
        LocalTime startTime = LocalTime.of(6, 0);
        LocalTime endTime = LocalTime.of(22, 0);
        LocalTime step = LocalTime.of(1, 30);

        // Generate buttons for each time slot
        while (startTime.isBefore(endTime)) {
            final LocalTime slotTime = startTime;

            Button timeSlotButton = new Button(startTime.toString());
            timeSlotButton.setOnAction(event -> handleBooking(slotTime));

            // Disable button if time slot is booked
            if (bookedTimes.contains(startTime)) {
                timeSlotButton.setDisable(true);
            }

            // Add button to the container
            timeSlotsContainer.getChildren().add(timeSlotButton);

            // Increment time by step
            startTime = startTime.plusHours(step.getHour()).plusMinutes(step.getMinute());
        }
    }

    private void handleBooking(LocalTime selectedTime) {
        this.selectedTime = selectedTime;
        try {
            // Load the booking process FXML file
            FXMLLoader loader = new FXMLLoader(URI.create(Constants.Booking).toURL());
            Parent bookingParent = loader.load();

            // Get the controller
            BookingController bookingController = loader.getController();

            // Pass selected date and time to the booking controller
            bookingController.setTerrainId(terrainId);
            bookingController.setSelectedDate(selectedDate);
            bookingController.setSelectedTime(selectedTime);

            // Show the booking scene
            Scene bookingScene = new Scene(bookingParent);
            Stage stage = (Stage) timeSlotsContainer.getScene().getWindow();
            stage.setScene(bookingScene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Error loading booking process: " + ex.getMessage());
        }
    }

    @FXML
    private void handleBackButtonClick(ActionEvent event) throws IOException {
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
