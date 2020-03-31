package maalausprojektikirjanpito.domain;

import java.util.HashMap;


public class ManagerService {
    private User loggedIn;
    private HashMap<String, String> users = new HashMap<>();

    public ManagerService() {
    }
    
    /**
     * 
     * @param username User's username
     * @param password User's password
     * @return returns false if username already taken, otherwise true
     */
    
    public boolean createUser (String username, String password) {
        if (users.containsKey(username)) {
            System.out.println("Username taken.");
            return false;
        }
        System.out.println("User created successfully!");
        users.put(username, password);
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
        } else if (users.containsKey(username) && this.users.get(username).equals(password)) {
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
