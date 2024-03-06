/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.interfaces.ITerrain_service;
import tn.esprit.models.Terrain;
import tn.esprit.utils.DataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author WALID
 */
public class TerrainService implements IService<Terrain>, ITerrain_service {
    private Connection cnx;

    public TerrainService() {
        cnx = DataBase.getInstance().getCnx();
    }

    @Override
    public void addEntity(Terrain t) {
        try {
            String rq = "INSERT INTO terrain ( owner_id, name, capacity, sportType, rentPrice, disponibility, address, imageName, updatedAt) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = cnx.prepareStatement(rq);
            pst.setInt(1, t.getOwner_id());
            pst.setString(2, t.getName());
            pst.setInt(3, t.getCapacity());
            pst.setString(4, t.getSportType());
            pst.setInt(5, t.getRentPrice());
            pst.setBoolean(6, t.isDisponibility());
            pst.setString(7, t.getAddress());
            pst.setString(8, t.getImageName());
            pst.setTimestamp(9, Timestamp.valueOf(t.getUpdatedAt()));
            pst.executeUpdate();
            System.out.println("Terrain has been added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void updateEntity(Terrain t) {
        try {
            String rq = "UPDATE terrain SET name = ?, capacity = ?, sportType = ?, rentPrice = ?, disponibility = ?, address = ?, imageName = ? WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(rq);
//            pst.setInt(1, t.getOwner_id());
            pst.setString(1, t.getName());
            pst.setInt(2, t.getCapacity());
            pst.setString(3, t.getSportType());
            pst.setInt(4, t.getRentPrice());
            pst.setBoolean(5, t.isDisponibility());
            pst.setString(6, t.getAddress());
            pst.setString(7, t.getImageName());
//            pst.setTimestamp(9, Timestamp.valueOf(t.getUpdatedAt()));
            pst.setInt(8, t.getId());
            pst.executeUpdate();
            System.out.println("Terrain has been updated");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void deleteEntity(int id) {
        try {
            String rq = "DELETE FROM terrain WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(rq);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Terrain has been deleted");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Terrain> displayEntity() {
        List<Terrain> myList = new ArrayList<>();

        try {
            String rq = "SELECT * FROM terrain";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(rq);
            while (rs.next()) {
                Terrain t = new Terrain();
                t.setId(rs.getInt("id"));
                t.setOwner_id(rs.getInt("owner_id"));
                t.setName(rs.getString("name"));
                t.setCapacity(rs.getInt("capacity"));
                t.setSportType(rs.getString("sportType"));
                t.setRentPrice(rs.getInt("rentPrice"));
                t.setDisponibility(rs.getBoolean("disponibility"));
                t.setAddress(rs.getString("address"));
                t.setImageName(rs.getString("imageName"));
                t.setUpdatedAt(rs.getTimestamp("updatedAt").toLocalDateTime());
                myList.add(t);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    @Override
    public Terrain display(int id) {
        Terrain t = new Terrain();
        try {
            String rq = "SELECT * FROM terrain WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(rq);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                t.setId(rs.getInt("id"));
                t.setOwner_id(rs.getInt("owner_id"));
                t.setName(rs.getString("name"));
                t.setCapacity(rs.getInt("capacity"));
                t.setSportType(rs.getString("sportType"));
                t.setRentPrice(rs.getInt("rentPrice"));
                t.setAddress(rs.getString("address"));
                t.setImageName(rs.getString("imageName"));
                t.setDisponibility(rs.getBoolean("disponibility"));
                t.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return t;
    }

    @Override
    public List<Terrain> myProprieties(int owner_id) {
        List<Terrain> myList = new ArrayList<>();

        try {
            String rq = "SELECT * FROM terrain WHERE owner_id = ?";
            PreparedStatement pst = cnx.prepareStatement(rq);
            pst.setInt(1, owner_id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Terrain t = new Terrain();
                t.setId(rs.getInt("id"));
                t.setOwner_id(rs.getInt("owner_id"));
                t.setName(rs.getString("name"));
                t.setCapacity(rs.getInt("capacity"));
                t.setSportType(rs.getString("sport_type"));
                t.setRentPrice(rs.getInt("rent_price"));
                t.setAddress(rs.getString("address"));
                t.setImageName(rs.getString("image_name"));
                t.setDisponibility(rs.getBoolean("disponibility"));
                t.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                myList.add(t);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    @Override
    public List<Terrain> filterTerrain(String location, String sport_type, String rent_price) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Terrain> searchTerrain(String search_term) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void add_autoCompleteWord(String word) {
        try {
            String rq = "INSERT INTO auto_complete (text) VALUES (?)";
            PreparedStatement pst = cnx.prepareStatement(rq);
            pst.setString(1, word);
            pst.executeUpdate();
            System.out.println("New word has been added.");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Set<String> get_autoCompleteWords() {
        Set<String> words = new HashSet<>();
        try {
            String rq = "SELECT * FROM auto_complete";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(rq);
            while (rs.next()) {
                words.add(rs.getString("text"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return words;
    }

    @Override
    public Terrain find_terrain(String name, String city, String country) {
        Terrain t =null;
        try {
            String rq = "SELECT * FROM terrain WHERE name = ? AND city = ? AND country = ?";
            PreparedStatement pst = cnx.prepareStatement(rq);
            pst.setString(1, name);
            pst.setString(2, city);
            pst.setString(3, country);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                t=new Terrain();
                t.setId(rs.getInt("id"));
                t.setOwner_id(rs.getInt("owner_id"));
                t.setName(rs.getString("name"));
                t.setCapacity(rs.getInt("capacity"));
                t.setSportType(rs.getString("sport_type"));
                t.setRentPrice(rs.getInt("rent_price"));
                t.setAddress(rs.getString("address"));
                t.setImageName(rs.getString("image_name"));
                t.setDisponibility(rs.getBoolean("disponibility"));
                t.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return t;
    }

    @Override
    public Terrain find_terrain_update(int id_terrain, String name, String city, String country) {
        Terrain t =null;
        try {
            String rq = "SELECT * FROM terrain WHERE name = ? AND city = ? AND country = ? AND id <> ?";
            PreparedStatement pst =cnx.prepareStatement(rq);
            pst.setString(1, name);
            pst.setString(2, city);
            pst.setString(3, country);
            pst.setInt(4, id_terrain);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                t=new Terrain();
                t.setId(rs.getInt("id"));
                t.setOwner_id(rs.getInt("owner_id"));
                t.setName(rs.getString("name"));
                t.setCapacity(rs.getInt("capacity"));
                t.setSportType(rs.getString("sport_type"));
                t.setRentPrice(rs.getInt("rent_price"));
                t.setAddress(rs.getString("address"));
                t.setImageName(rs.getString("image_name"));
                t.setDisponibility(rs.getBoolean("disponibility"));
                t.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return t;
    }

    public boolean terrainExists(String name) {
        try {
            String requete = "SELECT COUNT(*) FROM terrain WHERE name=?";
            PreparedStatement pst = DataBase.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            return count > 0;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
}


