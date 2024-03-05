package Service;
import Entites.Categorie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Utils.DataSource;

public class ServiceR {

    Connection con ;

    public ServiceR() throws SQLException {
        con = DataSource.getInstance().getCon();
    }


    public void ajouter(Categorie categorie) throws SQLException {
        String req = "INSERT INTO categorie (equipeGagnante, montantCategorie, dateCategorie) VALUES (?, ?, ?)";
        PreparedStatement pre = con.prepareStatement(req);

        pre.setString(1, categorie.getEquipeGagnante());
        pre.setInt(2, categorie.getMontantCategorie());
        pre.setDate(3, new java.sql.Date(categorie.getDateCategorie().getTime())); // Utilisation de java.sql.Date pour les dates SQL
        pre.executeUpdate();
    }








    public void delete(Categorie categorie) throws SQLException {

    }


    public void delete(int id) throws SQLException {
        String req = "DELETE FROM Categorie WHERE id ="+id;
        PreparedStatement statement = con.prepareStatement(req);

        statement.executeUpdate();


    }




    public void update(Categorie categorie ,int id) throws SQLException {
        String req = "UPDATE categorie SET equipeGagnante=?, montantCategorie=?, dateCategorie=? WHERE id ="+id;
        PreparedStatement statement = con.prepareStatement(req);
        statement.setString(1, categorie.getEquipeGagnante());
        statement.setInt(2, categorie.getMontantCategorie());
        statement.setDate(3, (Date) categorie.getDateCategorie());

        statement.executeUpdate();
    }



    public Categorie findById(int id) throws SQLException {
        String req = "SELECT * FROM categorie WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(req);
        statement.setInt(1, id);
        ResultSet res = statement.executeQuery();
        if (res.next()) {

            Categorie categorie = new Categorie();
            categorie.setEquipeGagnante(res.getString("equipeGagnante"));
            categorie.setMontantCategorie(res.getInt("montantCategorie"));
            categorie.setDateCategorie(res.getDate("dateCategorie"));
            return categorie;
        }
            return null;
        }


        public List<Categorie> readAll () throws SQLException {
            List<Categorie> list = new ArrayList<>();
            String req = "SELECT * FROM categorie";
            PreparedStatement statement = con.prepareStatement(req);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                Categorie categorie = new Categorie();
                categorie.setId(res.getInt("id"));
                categorie.setEquipeGagnante(res.getString("equipeGagnante"));
                categorie.setMontantCategorie(res.getInt("montantCategorie"));
                categorie.setDateCategorie(res.getDate("dateCategorie"));
                list.add(categorie);
            }
            return list;
        }


}