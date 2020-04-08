package maalausprojektikirjanpito.dao;

import java.sql.*;
import java.util.*;
import maalausprojektikirjanpito.domain.PaintProject;
import maalausprojektikirjanpito.domain.User;


public class PaintProjectDao implements Dao<PaintProject, Integer> {
    String databaseURL;
    
    /**
     * Constructs a new UserDao-object.Checks the existence of a User table in the designated database and creates one if not present. SQLITE3 used creates the database if one does not exist.
     * @param databaseURL URL of the selected database as a String
     */
    public PaintProjectDao(String databaseURL) {
        this.databaseURL = databaseURL;
        
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
            PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS PaintProjects ("
                + "id INTEGER, "
                + "user_id INTEGER, "
                + "projectname VARCHAR(40), "
                + "PRIMARY KEY (id), "
                + "FOREIGN KEY (user_id) REFERENCES Users(id)"
                + ");");
            stmt.executeUpdate();

            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        
        Object users = this.list();
    }

    @Override
    public PaintProject create(PaintProject object) throws SQLException { 
        return null;
    }

    @Override
    public PaintProject read(Integer key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PaintProject update(PaintProject object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PaintProject> list() {
        ArrayList<PaintProject> projects = new ArrayList<>();
        
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM PaintProjects");
            
            ResultSet resultSet = stmt.executeQuery();
            
            while (resultSet.next()) {
                projects.add(new PaintProject(resultSet.getString("projectname")));
            }
            
            resultSet.close();
            stmt.close();
            connection.close();
            
            return projects;            
        } catch (SQLException e) {
            System.out.println("Error: " + e.toString());
        }
        return null;
    }
    
}
