package maalausprojektikirjanpito.dao;

import java.sql.*;
import java.util.*;
import maalausprojektikirjanpito.domain.User;

public class UserDao implements Dao<User, Integer> {

    public UserDao() {
        Object users = this.list();
        System.out.println("users: " + users);
        if (users == null) {
                try (Connection connection = DriverManager.getConnection("jdbc:sqlite:db/example.db")) {
                PreparedStatement stmt = connection.prepareStatement("CREATE TABLE Users ("
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
        }
    }
    

    @Override
    public User create(User user) {
        int id = -1;
        
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:db/example.db")) {
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
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:db/example.db")) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Users WHERE id = (?)");
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
            user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return user;
    }

    @Override
    public User update(User object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> list() {
        ArrayList<User> users = new ArrayList<>();
        
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:db/example.db")) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Users");
            
            ResultSet resultSet = stmt.executeQuery();
            
            while (resultSet.next()) {
                users.add(new User(resultSet.getString("username"), resultSet.getString("password")));
            }
            
            stmt.close();
            return users;            
        } catch (SQLException e) {
            System.out.println("Error: " + e.toString());
        }
        return null;
    }
    
}
