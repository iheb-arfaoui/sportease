package Service;

import Entites.Coach;
import interfaces.IServiceCoach;
import Utils.DataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class serviceCoach implements IServiceCoach<Coach> {
    @Override
    public void addEntity2(Coach t) {
        try {
            String qry = "INSERT INTO coach (name, email, phone, academy_name) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = DataSource.getInstance().getCon().prepareStatement(qry);
            pst.setString(1, t.getName());
            pst.setString(2, t.getEmail());
            pst.setString(3, t.getPhone());
            pst.setString(4, t.getAcademyName());

//            pst.setInt(4, t.getAcademy().getId()); // set academy id
            pst.executeUpdate();
            System.out.println("Coach added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public void deleteEntity(Coach coach) {
        try {
            String requete = "DELETE FROM coach WHERE id=?";
            PreparedStatement pst = DataSource.getInstance().getCon().prepareStatement(requete);
            pst.setInt(1, coach.getId());
            pst.executeUpdate();
            /*int nb = pst.executeUpdate();
            if (nb > 0) {
                System.out.println("Coach with ID " + id + " deleted successfully.");
            } else {
                System.out.println("No Coach found with ID " + id + ".");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }*/
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
        /*public boolean coachExists(String name) {
        try {
            String requete = "SELECT COUNT(*) FROM coach WHERE name=?";
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            return count > 0;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }*/

    public List<Coach> display() {
        List<Coach> myList = new ArrayList<>();
        try {
            String qry = "SELECT * FROM coach";
            Statement st = DataSource.getInstance().getCon().createStatement();
            ResultSet rs = st.executeQuery(qry);
            while (rs.next()){
                Coach p = new Coach();
                p.setId(rs.getInt(1));
                p.setName(rs.getString("name"));
                p.setAcademyName(rs.getString("academy_name"));
                p.setEmail(rs.getString("email"));
                p.setPhone(rs.getString("phone"));
                myList.add(p);
                System.out.println(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }
    /*public List<String> findCoachNamesByAcademyName(String academyName) {
        List<String> coachNames = new ArrayList<>();
        try {
            String query = "SELECT name FROM coach WHERE academy_name=?";
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(query);
            pst.setString(1, academyName);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                String coachName = resultSet.getString("name");
                coachNames.add(coachName);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return coachNames;
    }*/


    /*public void coachDetails(int id) {
        try {
            String requete = "SELECT * FROM coach WHERE id=?";
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                Coach p = new Coach();
                p.setId(rs.getInt(1));
                p.setName(rs.getString("name"));
                p.setEmail(rs.getString("email"));
                p.setPhone(rs.getString("telephone"));
                p.setAcademyName(rs.getString("academy_name"));
                System.out.println("Coach details: " + p.toString());
            } else {
                System.out.println("No Coach found with ID " + id + ".");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }*/
    public void updateEntity2(Coach coach) {
        try {
            String req = "UPDATE coach SET name=?, email=?, phone=?, academy_name=? WHERE id=?";
            PreparedStatement pst = DataSource.getInstance().getCon().prepareStatement(req);
            pst.setString(1, coach.getName());
            pst.setString(2, coach.getEmail());
            pst.setString(3, coach.getPhone());
            pst.setString(4, coach.getAcademyName());
            pst.setInt(5, coach.getId());
            pst.executeUpdate();

            /*int nb = pst.executeUpdate();
            if (nb > 0) {
                System.out.println("Coach with ID " + t.getId() + " updated successfully.");
            } else {
                System.out.println("No Coach found with ID " + t.getId() + ".");
            }*/
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }






}

