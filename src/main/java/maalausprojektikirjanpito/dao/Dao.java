package maalausprojektikirjanpito.dao;

import java.sql.*;
import java.util.*;

public interface Dao<T, K> {
    
    /**
     * Method to access the Object's cache.
     * @return a suitable cache object, an empty object if not initialized.
     * @throws SQLException 
     */
    Object getCache() throws SQLException;
    
    /**
     * Method to initialize current Object.
     * @throws SQLException 
     */
    void init() throws SQLException;
    
    /** 
     * Method to create an object  of type T.
     * @param object to be created
     * @return created object
     * @throws SQLException
     */
    T create(T object) throws SQLException;
    
    /**
     * Method for reading an object of type T of key K.
     * @param key integer to be read
     * @return object read, null if not found
     * @throws SQLException 
     */
    T read(K key) throws SQLException;
    
    /**
     * Method for updating an object of type T.
     * @param object to be updated
     * @return updated object, null if not found
     * @throws SQLException 
     */
    T update(T object) throws SQLException;
    
    /**
     * Method for deleting an object of type T with the key K.
     * @param key of the object to be deleted
     * @throws SQLException 
     */
    void delete(K key) throws SQLException;
    
    /**
     * Method for returning a list of objects of type T.
     * @return a list of objects in the database
     * @throws SQLException 
     */
    List<T> list() throws SQLException;
}
