package maalausprojektikirjanpito.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionManager {
    private static Connection connection = null;
    
    public static Connection getDbConnection(String databaseUrl) throws SQLException {
        if (ConnectionManager.connection == null) {
            ConnectionManager.connection = DriverManager.getConnection("jdbc:sqlite:" + databaseUrl);
        } else {
            ConnectionManager.connection.close();
            ConnectionManager.connection = DriverManager.getConnection("jdbc:sqlite:" + databaseUrl);
        }
        return ConnectionManager.connection;
    }
}
