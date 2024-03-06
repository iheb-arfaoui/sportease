package tn.esprit.services;

import java.sql.*;

import tn.esprit.models.Reservation;
import tn.esprit.interfaces.ResService;
import tn.esprit.utils.DataBase;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationService implements ResService {
    private Connection cnx;
    private static final List<LocalTime> bookedTimes = new ArrayList<>();

    // Sample method to populate booked times for demonstration purposes
//    static {
//        // Assume some booked times for the current day
//        bookedTimes.add(LocalTime.of(6, 0));
//        bookedTimes.add(LocalTime.of(14, 0));
//        bookedTimes.add(LocalTime.of(15, 30));
//    }

    public ReservationService() {
        cnx = DataBase.getInstance().getCnx();
    }

    @Override
    public void addReservation(Reservation reservation) {
        try {
            String query = "INSERT INTO reservation (terrain_id, client_id, dateRes, Time, userName, phone) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, reservation.getTerrainId());
            pst.setInt(2, reservation.getClientId());
            pst.setDate(3, reservation.getDateRes());
            pst.setTime(4, Time.valueOf(reservation.getTime()));
            pst.setString(5, reservation.getUserName());
            pst.setString(6, reservation.getPhone());
            pst.executeUpdate();
            System.out.println("Reservation created successfully.");
        } catch (SQLException ex) {
            System.out.println("Error creating reservation: " + ex.getMessage());
        }
    }

    // Other methods for updating, deleting, and querying reservations can be added here
    @Override
    public void updateReservation(Reservation reservation) {
        try {
            String query = "UPDATE reservation SET terrain_id = ?, client_id = ?, dateRes = ?, Time = ?, userName = ?, phone = ? WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, reservation.getTerrainId());
            pst.setInt(2, reservation.getClientId());
            pst.setDate(3, reservation.getDateRes());
            pst.setTime(4, Time.valueOf(reservation.getTime()));
            pst.setString(5, reservation.getUserName());
            pst.setString(6, reservation.getPhone());
            pst.setInt(7, reservation.getId());
            pst.executeUpdate();
            System.out.println("Reservation updated successfully.");
        } catch (SQLException ex) {
            System.out.println("Error updating reservation: " + ex.getMessage());
        }
    }

    @Override
    public void deleteReservation(int reservationId) {
        try {
            String query = "DELETE FROM reservation WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, reservationId);
            pst.executeUpdate();
            System.out.println("Reservation deleted successfully.");
        } catch (SQLException ex) {
            System.out.println("Error deleting reservation: " + ex.getMessage());
        }
    }

    @Override
    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        try {
            String query = "SELECT * FROM reservation";
            PreparedStatement pst = cnx.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int terrainId = rs.getInt("terrain_id");
                int clientId = rs.getInt("client_id");
                Date dateRes = rs.getDate("dateRes");
                Time sqlTime = rs.getTime("Time");
                LocalTime time = sqlTime.toLocalTime();
                String userName = rs.getString("userName");
                String phone = rs.getString("phone");
                Reservation reservation = new Reservation(id, terrainId, clientId, dateRes, time, userName, phone);
                reservations.add(reservation);
            }
        } catch (SQLException ex) {
            System.out.println("Error retrieving reservations: " + ex.getMessage());
        }
        return reservations;
    }

    @Override
    public Reservation getReservationById(int reservationId) {
        Reservation reservation = null;
        try {
            String query = "SELECT * FROM reservation WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, reservationId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                int terrainId = rs.getInt("terrain_id");
                int clientId = rs.getInt("client_id");
                Date dateRes = rs.getDate("dateRes");
                Time sqlTime = rs.getTime("Time");
                LocalTime time = sqlTime.toLocalTime();
                String userName = rs.getString("userName");
                String phone = rs.getString("phone");
                reservation = new Reservation(id, terrainId, clientId, dateRes, time, userName, phone);
            }
        } catch (SQLException ex) {
            System.out.println("Error retrieving reservation by ID: " + ex.getMessage());
        }
        return reservation;
    }

    @Override
    public List<Reservation> getReservationsByTerrainId(int terrainId) {
        List<Reservation> reservations = new ArrayList<>();
        try {
            String query = "SELECT * FROM reservation WHERE terrain_id = ?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, terrainId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int clientId = rs.getInt("client_id");
                Date dateRes = rs.getDate("dateRes");
                Time sqlTime = rs.getTime("Time");
                LocalTime time = sqlTime.toLocalTime();
                String userName = rs.getString("userName");
                String phone = rs.getString("phone");
                Reservation reservation = new Reservation(id, terrainId, clientId, dateRes, time, userName, phone);
                reservations.add(reservation);
            }
        } catch (SQLException ex) {
            System.out.println("Error retrieving reservations by terrain ID: " + ex.getMessage());
        }
        return reservations;
    }
    @Override
    public List<Reservation> getReservationsByClientId(int clientId) {
        List<Reservation> reservations = new ArrayList<>();
        try {
            String query = "SELECT * FROM reservation WHERE client_id = ?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, clientId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int terrainId = rs.getInt("terrain_id");
                Date dateRes = rs.getDate("dateRes");
                Time sqlTime = rs.getTime("Time");
                LocalTime time = sqlTime.toLocalTime();
                String userName = rs.getString("userName");
                String phone = rs.getString("phone");
                Reservation reservation = new Reservation(id, terrainId, clientId, dateRes, time, userName, phone);
                reservations.add(reservation);
            }
        } catch (SQLException ex) {
            System.out.println("Error retrieving reservations by client ID: " + ex.getMessage());
        }
        return reservations;
    }
    @Override
    public List<LocalTime> getBookedTimesForTerrain(int terrainId) {
        return null;
    }

    @Override
    public List<LocalTime> getBookedTimesForTerrainForSingleDay(int terrainId, LocalDate date) {
        List<LocalTime> bookedTimes = new ArrayList<>();
        try {
            // Query to select reservations for the given terrain on the specified date
            String query = "SELECT Time FROM reservation WHERE terrain_id = ? AND dateRes = ?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setInt(1, terrainId);
            pst.setDate(2, Date.valueOf(date)); // Convert LocalDate to SQL Date
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Time sqlTime = rs.getTime("Time");
                LocalTime time = sqlTime.toLocalTime();
                bookedTimes.add(time);
            }
        } catch (SQLException ex) {
            System.out.println("Error retrieving booked times for terrain ID: " + ex.getMessage());
        }
        return bookedTimes;
    }
}

