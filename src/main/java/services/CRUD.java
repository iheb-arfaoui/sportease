package services;

import entities.Evenement;

import java.sql.SQLException;
import java.util.List;

public interface CRUD<T> {
    void add(T entity) throws SQLException;
    void delete(int id) throws SQLException;
    List<T> readAll() throws SQLException;
    void update(T entity) throws SQLException;
}
