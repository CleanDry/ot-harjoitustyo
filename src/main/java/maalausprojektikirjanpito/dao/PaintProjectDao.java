package maalausprojektikirjanpito.dao;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import maalausprojektikirjanpito.domain.PaintProject;


public class PaintProjectDao implements Dao<PaintProject, Integer> {
    String databaseURL;
    Integer user_id;
    ArrayList<PaintProject> projectsCache;
    
    /**
     * Constructs a new PaintProjectDao-object. Checks the existence of a PaintProjects table in the designated database and creates one if not present.SQLITE3 used creates the database if one does not exist.
     * @param databaseURL URL of the selected database as a String
     */
    public PaintProjectDao(String databaseURL) {
        this.databaseURL = databaseURL;
    }

    @Override
    public ArrayList<PaintProject> getCache() {
        return projectsCache;
    }
    
    @Override
    public void init() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + this.databaseURL)) {
            PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS PaintProjects ("
                + "project_id INTEGER, "
                + "user_id INTEGER, "
                + "project_name VARCHAR(40), "
                + "project_category VARCHAR(40), "
                + "project_completed BOOLEAN, "
                + "project_archived BOOLEAN, "
                + "project_intrash BOOLEAN, "
                + "PRIMARY KEY (project_id), "
                + "FOREIGN KEY (user_id) REFERENCES Users(id)"
                + ");");
            stmt.executeUpdate();

            stmt.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        
        this.projectsCache = (ArrayList<PaintProject>) this.list();
    }

    @Override
    public PaintProject create(PaintProject project) { 
        int id = -1;
        if (this.projectsCache.contains(project)) {
            return this.projectsCache.get(this.projectsCache.indexOf(project));
        } else {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO PaintProjects "
                    + "(user_id, project_name, project_category, project_completed, project_archived, project_intrash) "
                    + "VALUES (?,?,?,?,?,?);",
                    Statement.RETURN_GENERATED_KEYS);
                
                stmt.setInt(1, this.user_id);
                stmt.setString(2, project.getProject_name());
                stmt.setString(3, project.getProject_category());
                stmt.setBoolean(4, false);
                stmt.setBoolean(5, false);
                stmt.setBoolean(6, false);
                
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
        }
        
        if (id >= 0) {
            project = this.read(id);
            this.projectsCache.add(project);
            return project;
        } else {
            return null;
        }
    }

    @Override
    public PaintProject read(Integer project_id) {
        PaintProject project = null;
        List<Integer> project_ids = this.projectsCache.stream().map(p -> p.getProject_id()).collect(Collectors.toList());
        if (project_ids.contains(project_id)) {
            return this.projectsCache.get(project_ids.indexOf(project_id));
        } else {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM PaintProjects "
                        + "WHERE PaintProjects.project_id = (?);");
                
                stmt.setInt(1, project_id);
                ResultSet rs = stmt.executeQuery();

                project = new PaintProject(
                        rs.getInt("project_id"), 
                        rs.getInt("user_id"), 
                        rs.getString("project_name"), 
                        rs.getString("project_category"),
                        rs.getBoolean("project_completed"),
                        rs.getBoolean("project_archived"),
                        rs.getBoolean("project_intrash")
                );

                rs.close();
                stmt.close();
                connection.close();           
            } catch (SQLException e) {
                System.out.println("Error: " + e.toString());
            }
        }
        
        return project;
    }

    @Override
    public PaintProject update(PaintProject project) {
        PaintProject updatedProject = null;
        if (this.projectsCache.contains(project)) {
            System.out.println("Project found in cache!");
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
                PreparedStatement stmt = connection.prepareStatement("UPDATE PaintProjects "
                    + "SET user_id = ?, project_name = ?, project_category = ?, project_completed = ?, project_archived = ?, project_intrash = ? "
                    + "WHERE project_id = ?;");
                
                stmt.setInt(1, project.getUser_id());
                stmt.setString(2, project.getProject_name());
                stmt.setString(3, project.getProject_category());
                stmt.setBoolean(4, project.getProject_completed());
                stmt.setBoolean(5, project.getProject_archived());
                stmt.setBoolean(6, project.getProject_intrash());
                stmt.setInt(7, project.getProject_id());
                
                stmt.executeUpdate();
                
                stmt.close();
                connection.close();
                
                updatedProject = this.read(project.getProject_id());
                this.projectsCache.set(this.projectsCache.indexOf(project), updatedProject);
            } catch (SQLException e) {
                System.out.println("Error: " + e);
            }
        }
        return updatedProject;
    }

    @Override
    public void delete(Integer project_id) {
        PaintProject projectToRemove = this.read(project_id);
        if (projectToRemove != null) {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM PaintProjects WHERE project_id = ?");
                
                stmt.setInt(1, project_id);
                
                stmt.executeUpdate();
                
                stmt.close();
                connection.close();
                
                this.projectsCache.remove(projectToRemove);
            } catch (SQLException e) {
                System.out.println("Error: " + e);
            }
        }
    }

    @Override
    public List<PaintProject> list() {
        ArrayList<PaintProject> projects = new ArrayList<>();
        
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM PaintProjects;");
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                projects.add(new PaintProject(
                        rs.getInt("project_id"), 
                        rs.getInt("user_id"), 
                        rs.getString("project_name"), 
                        rs.getString("project_category"),
                        rs.getBoolean("project_completed"),
                        rs.getBoolean("project_archived"),
                        rs.getBoolean("project_intrash")));
            }
            
            rs.close();
            stmt.close();
            connection.close();          
        } catch (SQLException e) {
            System.out.println("Error: " + e.toString());
        }
        return projects;
    }
    
    public void setUser(Integer user_id) {
        this.user_id = user_id;
    }
    
    /**
     * Get categories of the selected user's projects.
     * @param user_id User_id of the selected user
     * @return List of categories of the users projects
     */
    public ArrayList<String> categoriesOfUser(Integer user_id) {
        ArrayList<String> categories = new ArrayList<>();
        
        for (PaintProject p : this.projectsCache) {
            if (!categories.contains(p.getProject_category())) {
                categories.add(p.getProject_category());
            }
        }
        
        return categories;
    }
    
    /**
     * Get projects in a particular category.
     * @param category Selected category as a String
     * @return ArrayList<PaintProject> of the projects in a category
     */
    public ArrayList<PaintProject> projectsInCategory(String category) {
        ArrayList<PaintProject> projectsInCategory = (ArrayList<PaintProject>) this.projectsCache.stream().filter(c -> c.getProject_category().equals(category)).collect(Collectors.toList());
        
        return projectsInCategory;
    }
}
