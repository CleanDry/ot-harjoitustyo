package maalausprojektikirjanpito.domain;

import java.util.*;


public class User {
    private Integer identifier;
    private String username;
    private String password;
    private HashMap<String, ArrayList<PaintProject>> projectCategories;

    /**
     * Create a new User object.
     * @param username selected username as a String
     * @param password selected password as a String
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.projectCategories = new HashMap<>();
    }
    
    /**
     * Create a new User object with database id.
     * @param id the object id in the attached database as Integer
     * @param username selected username as a String
     * @param password selected password as a String
     */
    public User(Integer id, String username, String password) {
        this.identifier = id;
        this.username = username;
        this.password = password;
        this.projectCategories = new HashMap<>();
    }

    public Integer getId() {
        return identifier;
    }

    public void setId(Integer identifier) {
        this.identifier = identifier;
    }
    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashMap getProjectCategories() {
        return projectCategories;
    }

    @Override
    public String toString() {
        return "User{" + "Id=" + identifier + ", username=" + username + ", password=" + password + '}';
    }
}
