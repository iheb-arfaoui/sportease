package tn.esprit.interfaces;

import tn.esprit.models.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ResService {
    // Method to add a new reservation
    void addReservation(Reservation reservation);

    void updateReservation(Reservation reservation);

    // Method to delete a reservation
    void deleteReservation(int reservationId);

    List<Reservation> getAllReservations();
    // Method to retrieve a reservation by its ID
    Reservation getReservationById(int reservationId);

    List<Reservation> getReservationsByTerrainId(int terrainId);
    List<LocalTime> getBookedTimesForTerrain(int terrainId);

    List<LocalTime> getBookedTimesForTerrainForSingleDay(int terrainId, LocalDate date);

    // Method to retrieve reservations by client ID
    List<Reservation> getReservationsByClientId(int clientId);

    // Additional methods as needed...
}

