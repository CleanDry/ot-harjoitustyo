package maalausprojektikirjanpito.dao;

import java.sql.*;
import java.util.*;
import maalausprojektikirjanpito.domain.User;

public class UserDao implements Dao<User, Integer> {
    String databaseURL;
    HashMap<String, User> usersCache = new HashMap<>();

    /**
     * Constructs a new UserDao-object. Checks the existence of a Users table in the designated database and creates one if not present. SQLITE3 used creates the database if one does not exist.
     * @param url URL of the selected database as a String
     */
    public UserDao(String url) {
        databaseURL = url;
    }
    

    @Override
    public HashMap<String, User> getCache() {
        return usersCache;
    }
    
   @Override
    public void init() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + this.databaseURL)) {
            PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Users ("
                + "id INTEGER, "
                + "username VARCHAR(20), "
                + "password VARCHAR(20), "
                + "PRIMARY KEY (id)"
                + ");");
            stmt.executeUpdate();

            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        
        this.list().forEach((u) -> {
            usersCache.put(u.getUsername().toLowerCase(), u);
        });
    }

    @Override
    public User create(User user) {
        int id = -1;
        
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Users "
                    + "(username, password) "
                    + " VALUES (?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            
            stmt.executeUpdate();
            
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }
            
            generatedKeys.close();
            stmt.close();
            connection.close();
            
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        
        if (id >= 0) {
            return this.read(id);
        } else {
            return null;
        }
    }

    @Override
    public User read(Integer key) {
        User user = null;
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Users WHERE id = (?)");
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
            user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
            
            rs.close();
            stmt.close();
            connection.close();
            
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return user;
    }

    @Override
    public User update(User user) {
        User updatedUser = null;
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
            PreparedStatement stmt = connection.prepareStatement("UPDATE Users "
                    + "SET username  = ?, password = ? "
                    + "WHERE id = " + user.getId());
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.executeUpdate();
            
            updatedUser = this.read(user.getId());
            
            stmt.close();
            connection.close();
            
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return updatedUser;
    }

    @Override
    public void delete(Integer key) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM Users "
                    + "WHERE id = " + key);
            stmt.executeUpdate();
            
            stmt.close();
            connection.close();
            
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }

    @Override
    public List<User> list() {
        ArrayList<User> users = new ArrayList<>();
        
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Users");
            
            ResultSet resultSet = stmt.executeQuery();
            
            while (resultSet.next()) {
                users.add(new User(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password")));
            }
            
            resultSet.close();
            stmt.close();
            connection.close();           
        } catch (SQLException e) {
            System.out.println("Error: " + e.toString());
        }
        return users;
    }
    
}
