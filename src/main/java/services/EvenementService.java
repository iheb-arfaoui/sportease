package services;

import entities.Evenement;
import utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EvenementService implements CRUD<Evenement> {

    public void add(Evenement evenement) throws SQLException
    {
        String query = "INSERT INTO evenement (event_name, event_abr, event_date, `categorieId`, `status`, `eventAdress`) VALUES (?, ?, ?, ?, ?, ?)";
        Connection connect = DataSource.getInstance().getCnx();
        PreparedStatement preparedStatement = connect.prepareStatement(query);
        preparedStatement.setString(1, evenement.getEventName());
        preparedStatement.setString(2, evenement.getEventAbr());
        preparedStatement.setString(3, evenement.getEventDate());
        preparedStatement.setInt(4, evenement.getCategorieId());
        preparedStatement.setString(5, evenement.getStatus());
        preparedStatement.setString(6, evenement.getEventAdress());
        preparedStatement.executeUpdate();
        System.out.println("Evenement added");
    }
    public void delete(int id) throws SQLException
    {
        String query = "DELETE FROM evenement WHERE event_id = ?";
        Connection connect = DataSource.getInstance().getCnx();
        PreparedStatement preparedStatement = connect.prepareStatement(query);

        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        System.out.println("Evenement deleted");
    }
    public List<Evenement> readAll()  throws SQLException
    {
        List<Evenement> evenements = new ArrayList<>();
        String query = "SELECT * FROM evenement";
        Connection connect = DataSource.getInstance().getCnx();
        PreparedStatement preparedStatement = connect.prepareStatement(query);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Evenement evenement = new Evenement();
            evenement.setEvenementId(resultSet.getInt("event_id"));
            evenement.setEventName(resultSet.getString("event_name"));
            evenement.setEventAbr(resultSet.getString("event_abr"));
            evenement.setEventDate(resultSet.getString("event_date"));
            evenement.setCategorieId(resultSet.getInt("categorieId"));
            evenement.setStatus(resultSet.getString("status"));
            evenement.setEventAdress(resultSet.getString("eventAdress"));
            evenements.add(evenement);
        }

        return evenements;
    }
    public void update(Evenement evenement) throws SQLException
    {
        String query = "UPDATE evenement SET event_name = ?, event_abr = ?, event_date = ? , categorieId = ?, status = ? , eventAdress = ? WHERE event_id = ?";
        Connection connect = DataSource.getInstance().getCnx();
        PreparedStatement preparedStatement = connect.prepareStatement(query);

        preparedStatement.setString(1, evenement.getEventName());
        preparedStatement.setString(2, evenement.getEventAbr());
        preparedStatement.setString(3, evenement.getEventDate());
        preparedStatement.setInt(4, evenement.getCategorieId());
        preparedStatement.setString(5, evenement.getStatus());
        preparedStatement.setString(6, evenement.getEventAdress());
        preparedStatement.setInt(7, evenement.getEvenementId());
        preparedStatement.executeUpdate();
        System.out.println("Evenement updated");
    }

}
