package maalausprojektikirjanpito.domain;

import java.sql.SQLException;
import java.util.*;
import maalausprojektikirjanpito.dao.UserDao;


public class ManagerService {
    private User loggedIn;
    private UserDao userDao;
    private HashMap<String, User> users = new HashMap<>();

    public ManagerService() {
        // 1st DB ref!
        userDao = new UserDao();
        for (User u : userDao.list()) {
            users.put(u.getUsername(), u);
        }
    }
    
    /**
     * 
     * @param username User's username
     * @param password User's password
     * @return returns false if username already taken, username.length is less than 3 or password.length is less than 8, otherwise true
     */
    
    public boolean createUser (String username, String password) {
        // Check if the given pair meets the criteria
        if (username.length() < 3 || password.length() < 8) {
            System.out.println("Username has to be at least 3 and password 8 characters");
            return false;
        } else if (users.containsKey(username)) {
            System.out.println("Username taken.");
            return false;
        }
        
        System.out.println("User created successfully!");
        User user = new User(username, password);
        userDao.create(user);
        users.put(username,user);
        return true;
    }
    
    /**
     * 
     * @param username  User's username
     * @param password  User's password
     * @return false if either field is empty or no matching pair of username and password found, true if found
     */
    
    public boolean login (String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Either field was empty!");
            return false;
        } else if (users.containsKey(username) && this.users.get(username).getPassword().equals(password)) {
            loggedIn = new User(username, password);
            System.out.println("Successfully logged in " + username + " " + password + "!");
            return true;
        }
        System.out.println("Login failed for some other reason.");
        return false;
    }

    public User getLoggedIn() {
        if (loggedIn == null) {
            System.out.println("Nobody logged in!");
            return new User("","");
        }
        System.out.println("Logged in: " + loggedIn.getUsername());
        return loggedIn;
    }
    
    public void logout() {
        System.out.println("Logging out");
        loggedIn = null;  
    }
}
