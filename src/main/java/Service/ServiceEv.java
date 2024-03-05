package Service;
import Entites.Evenement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Utils.DataSource;
import Entites.Historique;
import Entites.Categorie;

public class ServiceEv {
    Connection con ;

    public ServiceEv() throws SQLException {
        con = DataSource.getInstance().getCon();
    }




    public void ajouter(Evenement evenement) throws SQLException {
        // Assuming Categorie is already added in the database and has an ID
        String req = "INSERT INTO Evenement (nomEvent, lieu, dateEvent, duree, id_categorie , QrCode) VALUES (?, ?, ?, ?, ?,?)";
        PreparedStatement pre = con.prepareStatement(req);

        pre.setString(1, evenement.getNomEvent());
        pre.setString(2, evenement.getLieu());
        pre.setDate(3, new java.sql.Date(evenement.getDateEvent().getTime()));
        pre.setInt(4, evenement.getDuree());
        pre.setInt(5, evenement.getCategorie().getId()); // Set the foreign key to Categorie
        pre.setString(6, evenement.getQrCode());
        logHistory("Ajouter", "Added new event with name: " + evenement.getNomEvent());

        pre.executeUpdate();
    }


    public List<Evenement> readAll() throws SQLException {
        List<Evenement> list = new ArrayList<>();
        String req = "SELECT e.*, r.* FROM Evenement e LEFT JOIN Categorie r ON e.id_categorie = r.id";
        PreparedStatement statement = con.prepareStatement(req);
        ResultSet res = statement.executeQuery();
        while (res.next()) {
            Evenement evenement = new Evenement();
            evenement.setId(res.getInt("e.id"));
            evenement.setNomEvent(res.getString("e.nomEvent"));
            evenement.setLieu(res.getString("e.lieu"));
            evenement.setDateEvent(res.getDate("e.dateEvent"));
            evenement.setDuree(res.getInt("e.duree"));
            evenement.setQrCode(res.getString("e.QrCode"));
            // Create and set Categorie object if present
            int categorieId = res.getInt("r.id");
            if (!res.wasNull()) {
                Categorie categorie = new Categorie(
                        categorieId,
                        res.getString("r.equipeGagnante"),
                        res.getInt("r.montantCategorie"),
                        res.getDate("r.dateCategorie")
                );
                evenement.setCategorie(categorie);
            }

            list.add(evenement);
        }
        return list;
    }


    public Evenement findById(int id) throws SQLException {
        String req = "SELECT e.*, r.* FROM Evenement e LEFT JOIN Categorie r ON e.id_categorie = r.id WHERE e.id = ?";
        PreparedStatement statement = con.prepareStatement(req);
        statement.setInt(1, id);
        ResultSet res = statement.executeQuery();
        if (res.next()) {
            Evenement evenement = new Evenement();
            evenement.setId(res.getInt("e.id"));
            evenement.setNomEvent(res.getString("e.nomEvent"));
            evenement.setLieu(res.getString("e.lieu"));
            evenement.setDateEvent(res.getDate("e.dateEvent"));
            evenement.setDuree(res.getInt("e.duree"));
            evenement.setQrCode(res.getString("e.QrCode"));
            // Check if a Categorie record exists
            int categorieId = res.getInt("r.id");
            System.out.println(categorieId);
            if (!res.wasNull()) {
                Categorie categorie = new Categorie(
                        categorieId,
                        res.getString("r.equipeGagnante"),
                        res.getInt("r.montantCategorie"),
                        res.getDate("r.dateCategorie")

                );
                evenement.setCategorie(categorie);
            }

            return evenement;
        }
        return null;
    }



    public void update(Evenement evenement, int id) throws SQLException {
        // Verify the categorie ID exists
        if (evenement.getCategorie() != null) {
            String checkReq = "SELECT COUNT(*) FROM categorie WHERE id = ?";
            PreparedStatement checkStmt = con.prepareStatement(checkReq);
            checkStmt.setInt(1, evenement.getCategorie().getId());
            ResultSet rsCheck = checkStmt.executeQuery();
            if (rsCheck.next() && rsCheck.getInt(1) == 0) {
                // The categorie ID does not exist, handle this scenario appropriately
                throw new SQLException("Categorie ID does not exist: " + evenement.getCategorie().getId());
            }
        }

        // Proceed with the update
        String req = "UPDATE Evenement SET nomEvent=?, lieu=?, dateEvent=?, duree=?, id_categorie=?  WHERE id=?";
        PreparedStatement statement = con.prepareStatement(req);
        statement.setString(1, evenement.getNomEvent());
        statement.setString(2, evenement.getLieu());
        statement.setDate(3, new java.sql.Date(evenement.getDateEvent().getTime()));
        statement.setInt(4, evenement.getDuree());

        if (evenement.getCategorie() != null) {
            statement.setInt(5, evenement.getCategorie().getId());
        } else {
            statement.setNull(5, Types.INTEGER); // Set null if there is no Categorie
        }

        statement.setInt(6, id);

        logHistory("Update", "Updated event with ID: " + id);


        statement.executeUpdate();
    }



    public void delete(int id) throws SQLException {
        String req = "DELETE FROM Evenement WHERE id =" + id;
        PreparedStatement statement = con.prepareStatement(req);
        logHistory("Delete", "Deleted event with ID: " + id);

        statement.executeUpdate();


    }
    private void logHistory(String operationType, String description) throws SQLException {
        String req = "INSERT INTO historique (operation_type, description) VALUES (?, ?)";
        PreparedStatement pre = con.prepareStatement(req);

        pre.setString(1, operationType);
        pre.setString(2, description);
        pre.executeUpdate();
    }
    public List<Historique> fetchHistorique() throws SQLException {
        List<Historique> historiqueList = new ArrayList<>();
        String req = "SELECT * FROM historique";
        PreparedStatement pre = con.prepareStatement(req);
        ResultSet rs = pre.executeQuery();

        while (rs.next()) {
            Historique historique = new Historique(
                    rs.getInt("id"),
                    rs.getString("operation_type"),
                    rs.getString("description"),
                    rs.getTimestamp("operation_date") // Assuming the date is stored as TIMESTAMP in SQL
            );
            historiqueList.add(historique);
        }
System.out.println(historiqueList);
        return historiqueList;
    }

    public void delete(Evenement evenement) throws SQLException {


    }
}










