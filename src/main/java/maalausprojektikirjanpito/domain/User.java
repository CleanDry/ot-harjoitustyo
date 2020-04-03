package maalausprojektikirjanpito.domain;

import java.util.*;


public class User {
    private Integer Id;
    private String username;
    private String password;
    private HashMap<String, ArrayList<PaintProject>> projectCategories;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.projectCategories = new HashMap<>();
    }
    
    public User(Integer id, String username, String password) {
        this.Id = id;
        this.username = username;
        this.password = password;
        this.projectCategories = new HashMap<>();
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
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
        return "User{" + "Id=" + Id + ", username=" + username + ", password=" + password + '}';
    }
}
