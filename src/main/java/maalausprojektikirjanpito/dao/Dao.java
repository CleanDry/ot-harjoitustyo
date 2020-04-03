package maalausprojektikirjanpito.dao;

import java.sql.*;
import java.util.*;

public interface Dao<T, K> {
    T create(T object) throws SQLException;
    T read(K key) throws SQLException;
    T update(T object) throws SQLException;
    void delete(K key) throws SQLException;
    List<T> list() throws SQLException;
}
