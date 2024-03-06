package tn.esprit.test;

import tn.esprit.models.Reservation;
import tn.esprit.services.ReservationService;
import tn.esprit.services.TerrainService;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        ReservationService reservationService = new ReservationService();
        TerrainService terrainService = new TerrainService();
//        Reservation reservation = new Reservation();


        int terrainId = 2; // Replace 1 with the actual terrain ID

// Call the method to retrieve reservations for the specified terrain ID
        LocalDate date = LocalDate.now(); // Use the current date for testing

        // Call the getBookedTimesForTerrainForSingleDay method
        List<LocalTime> bookedTimes = reservationService.getBookedTimesForTerrainForSingleDay(terrainId, date);

        // Print the booked times
        System.out.println("Booked times for terrain ID " + terrainId + " on " + date + ":");
        System.out.println("name existence : " + terrainService.terrainExists("gra"));
        for (LocalTime time : bookedTimes) {
            System.out.println(time);
// Display the retrieved reservations for verification
//        for (Reservation reservation : reservations) {
//            System.out.println(reservation); // Assuming you've overridden the toString() method in Reservation
//        }

        }
    }
}