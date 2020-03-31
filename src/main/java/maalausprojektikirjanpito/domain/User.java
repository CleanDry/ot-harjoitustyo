package maalausprojektikirjanpito.domain;

import java.util.ArrayList;


public class User {
    private String username;
    private String password;
    private ArrayList<ProjectCategory> projectCategories;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.projectCategories = new ArrayList<>();
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

    public ArrayList getProjectCategories() {
        return projectCategories;
    }

    @Override
    public String toString() {
        return "User{" + "username=" + username + ", password=" + password + '}';
    }
}
