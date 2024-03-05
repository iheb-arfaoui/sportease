package Service;

import Entites.Academy;
import interfaces.IServiceAcademy;
import Utils.DataSource;
import org.bouncycastle.asn1.dvcs.Data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServiceAcademy implements IServiceAcademy<Academy> {


    public  void addEntity(Academy t) {
        String requete = "INSERT INTO academy (name, category,  localisation, created_by) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement pst = DataSource.getInstance().getCon().prepareStatement(requete);
            pst.setString(1, t.getName());
            pst.setString(2, t.getCategory());
            pst.setString(3, t.getLocalisation());
            pst.setString(4,t.getCreated_by());




            pst.executeUpdate();
            System.out.println("Academy added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    /*public void addEntity(String name, String category) {
        try {
            String requete = "INSERT INTO academy (name, category) VALUES (?,?)";
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, name);
            pst.setString(2, category);
            pst.executeUpdate();
            System.out.println("Academy added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }*/


//    public void addEntity2(Academy t) {
//        try {
//            String requete = "INSERT INTO academy (name,category) VALUES (?,?)";
//            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
//            pst.setString(1, t.getName());
//            pst.setString(2, t.getCategory());
//            pst.executeUpdate();
//            System.out.println("Success");
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }

    /*public void addEntity2() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter Academy name: ");
            String name = scanner.nextLine();
            System.out.println("Enter Academy category: ");
            String category = scanner.nextLine();

            String requete = "INSERT INTO academy (name, category) VALUES (?,?)";
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, name);
            pst.setString(2, category);
            pst.executeUpdate();
            System.out.println("Success");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }*/



    @Override
    public List<Academy> display() {
        List<Academy> myList = new ArrayList<>();
        try {
            String requete = "SELECT * FROM academy";
            Statement st = DataSource.getInstance().getCon().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()){
                Academy p = new Academy();
                p.setId(rs.getInt(1));
                p.setName(rs.getString("name"));
                p.setCategory(rs.getString("category"));
                p.setLocalisation(rs.getString("localisation"));
                p.setCreated_by(rs.getString("created_by"));

                myList.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    /*public void displayAllAcademies() {
        try {
            String query = "SELECT * FROM academy";
            Statement stmt = DataSource.getInstance().getCnx().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Category: " + rs.getString("category"));
                System.out.println("Image Name: " + rs.getString("image_name"));
                System.out.println("-----------------------------");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }*/

    @Override
    public void deleteEntity(Academy academy) throws SQLException {
        String req = "DELETE FROM academy where id=?";
        PreparedStatement pst = DataSource.getInstance().getCon().prepareStatement(req);
        pst.setInt(1, academy.getId());
        pst.executeUpdate();
    }




    public void updateEntity(Academy academy) {
        try {
            String requete = "UPDATE academy SET name=?, category=?,localisation =?, created_by =?  WHERE id=?";
            PreparedStatement pst = DataSource.getInstance().getCon().prepareStatement(requete);
            pst.setString(1, academy.getName());
            pst.setString(2, academy.getCategory());
            pst.setString(3, academy.getLocalisation());
            pst.setString(4, academy.getCreated_by());
            pst.setInt(5, academy.getId());

            pst.executeUpdate();
            /*int nb = pst.executeUpdate();
            if (nb > 0) {
                System.out.println("Academy with ID " + t.getId() + " updated successfully.");
            } else {
                System.out.println("No Academy found with ID " + t.getId() + ".");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }*/
        /*try {
            String requete = "UPDATE academy SET name=?, category=?,user_id =? WHERE id=?";
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, t.getName());
            pst.setString(2, t.getCategory());

            pst.setInt(3, t.getUser_id());
            int nb = pst.executeUpdate();
            if (nb > 0) {
                System.out.println("Academy with ID " + t.getId() + " updated successfully.");
            } else {
                System.out.println("No Academy found with ID " + t.getId() + ".");
            }*/
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }



    /*public void academyDetails(int id) {
        try {
            String requete = "SELECT * FROM academy WHERE id=?";
            PreparedStatement pst = DataSource.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                Academy p = new Academy();
                p.setId(rs.getInt(1));
                p.setName(rs.getString("name"));
                p.setCategory(rs.getString("category"));

                System.out.println("Academy details: " + p.toString());
            } else {
                System.out.println("No Academy found with ID " + id + ".");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }*/

    public boolean academyExists(String name) {
        try {
            String requete = "SELECT COUNT(*) FROM academy WHERE name=?";
            PreparedStatement pst = DataSource.getInstance().getCon().prepareStatement(requete);
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


    /*public Academy getEntity(int academyId) throws SQLException {
        String query = "SELECT * FROM academy WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Academy academy = null;

        try {
            connection = DataSource.getInstance().getCnx();
            statement = connection.prepareStatement(query);
            statement.setInt(1, academyId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String category = resultSet.getString("category");

                academy = new Academy(id, name, category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }

        return academy;
    }*/

}
