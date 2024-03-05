package interfaces;

import java.sql.SQLException;
import java.util.List;

public interface IServiceAcademy<T> {
    public void addEntity(T t);
    public void deleteEntity(T t) throws SQLException;
    public List<T> display();
    public  void updateEntity(T t);
    //public void addEntity2(T t);
    //public void academyDetails(int id);
}
