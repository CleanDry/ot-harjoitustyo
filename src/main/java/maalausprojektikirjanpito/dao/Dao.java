package maalausprojektikirjanpito.dao;

import java.sql.*;
import java.util.*;

public interface Dao<T, K> {
    
    /** 
     * Interface for an object creation.
     * @param object to be created
     * @return created object
     * @throws SQLException
     */
    T create(T object) throws SQLException;
    
    /**
     * Interface for reading an object.
     * @param key integer to be read
     * @return object read, null if not found
     * @throws SQLException 
     */
    T read(K key) throws SQLException;
    
    /**
     * Interface for updating an object.
     * @param object to be updated
     * @return updated object, null if not found
     * @throws SQLException 
     */
    T update(T object) throws SQLException;
    
    /**
     * Interface for deleting an object.
     * @param key of the object to be deleted
     * @throws SQLException 
     */
    void delete(K key) throws SQLException;
    
    /**
     * Interface for returning a list of objects.
     * @return a list of objects in the database
     * @throws SQLException 
     */
    List<T> list() throws SQLException;
}
