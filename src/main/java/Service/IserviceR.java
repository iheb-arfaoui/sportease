package Service;
import Entites.Categorie;
import java.sql.SQLException;
import java.util.List;
public interface IserviceR {




        void ajouter( Categorie categorie) throws SQLException;

        void delete(Categorie categorie) throws SQLException;

    void delete(int id) throws SQLException;

    void update(Categorie categorie,int id) throws SQLException;

        Categorie findById(int id) throws SQLException;

     List<Categorie> readAll() throws  SQLException;
}

