package services;

import entities.Categorie;
import utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategorieService implements CRUD<Categorie> {

    public void add(Categorie categorie) throws SQLException {
        String query = "INSERT INTO `categorie`(`categorie_name`, `nombre_joueur`, `date`, `typeCategorie`, `gain`) VALUES (?,?,?,?,?)";
        Connection connect = DataSource.getInstance().getCnx();
        PreparedStatement preparedStatement = connect.prepareStatement(query);

        preparedStatement.setString(1, categorie.getCategorieName());
        preparedStatement.setString(2, categorie.getNombreJoueur());
        preparedStatement.setString(3, categorie.getDate());
        preparedStatement.setString(4, categorie.getTypeCategorie());
        preparedStatement.setFloat(5, categorie.getGain());
        preparedStatement.executeUpdate();
        System.out.println("Categorie added");
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM categorie WHERE categorie_id = ?";
        Connection connect = DataSource.getInstance().getCnx();
        PreparedStatement preparedStatement = connect.prepareStatement(query);

        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        System.out.println("Categorie deleted");
    }

    public List<Categorie> readAll() throws SQLException {
        List<Categorie> categories = new ArrayList<>();
        String query = "SELECT * FROM categorie";
        Connection connect = DataSource.getInstance().getCnx();
        PreparedStatement preparedStatement = connect.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Categorie categorie = new Categorie();
            categorie.setCategorieId(resultSet.getInt("categorie_id"));
            categorie.setCategorieName(resultSet.getString("categorie_name"));
            categorie.setNombreJoueur(resultSet.getString("nombre_joueur"));
            categorie.setDate(resultSet.getString("date"));
            categorie.setTypeCategorie(resultSet.getString("typeCategorie"));
            categorie.setGain(resultSet.getFloat("gain"));
            categories.add(categorie);
        }

        return categories;
    }

    public Categorie searchById(int categorieId) throws SQLException {
        String query = "SELECT * FROM categorie WHERE categorie_id = ?";
        Connection connect = DataSource.getInstance().getCnx();
        PreparedStatement preparedStatement = connect.prepareStatement(query);
        preparedStatement.setInt(1, categorieId);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            Categorie categorie = new Categorie();
            categorie.setCategorieId(resultSet.getInt("categorie_id"));
            categorie.setCategorieName(resultSet.getString("categorie_name"));
            categorie.setNombreJoueur(resultSet.getString("nombre_joueur"));
            categorie.setDate(resultSet.getString("date"));
            categorie.setTypeCategorie(resultSet.getString("typeCategorie"));
            categorie.setGain(resultSet.getFloat("gain"));
            return categorie;
        }

        return null;
    }

    public Categorie searchByName(String categorieName) throws SQLException {
        String query = "SELECT * FROM categorie WHERE categorie_name = ?";
        Connection connect = DataSource.getInstance().getCnx();
        PreparedStatement preparedStatement = connect.prepareStatement(query);
        preparedStatement.setString(1, categorieName);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            Categorie categorie = new Categorie();
            categorie.setCategorieId(resultSet.getInt("categorie_id"));
            categorie.setCategorieName(resultSet.getString("categorie_name"));
            categorie.setNombreJoueur(resultSet.getString("nombre_joueur"));
            categorie.setDate(resultSet.getString("date"));
            categorie.setTypeCategorie(resultSet.getString("typeCategorie"));
            categorie.setGain(resultSet.getFloat("gain"));
            return categorie;
        }

        return null;
    }


    public void update(Categorie categorie) throws SQLException {
        String query = "UPDATE `categorie` SET `categorie_name`=?,`nombre_joueur`=?,`date`=?,`typeCategorie`=?,`gain`=? WHERE `categorie_id`=?";
        Connection connect = DataSource.getInstance().getCnx();
        PreparedStatement preparedStatement = connect.prepareStatement(query);

        preparedStatement.setString(1, categorie.getCategorieName());
        preparedStatement.setString(2, categorie.getNombreJoueur());
        preparedStatement.setString(3, categorie.getDate());
        preparedStatement.setString(4, categorie.getTypeCategorie());
        preparedStatement.setFloat(5, categorie.getGain());
        preparedStatement.setInt(6, categorie.getCategorieId());
        preparedStatement.executeUpdate();
        System.out.println("Categorie updated");
    }
}
