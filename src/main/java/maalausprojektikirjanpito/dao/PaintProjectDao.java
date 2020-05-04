package maalausprojektikirjanpito.dao;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import maalausprojektikirjanpito.domain.PaintProject;


public class PaintProjectDao implements Dao<PaintProject, Integer> {
    String databaseURL;
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
    public void init() throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + this.databaseURL)) {
            PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS PaintProjects ("
                + "project_id INTEGER, user_id INTEGER, project_name VARCHAR(40), project_faction VARCHAR(40), project_category VARCHAR(40), project_completed BOOLEAN DEFAULT false, project_archived BOOLEAN DEFAULT false, project_intrash BOOLEAN DEFAULT false, "
                + "PRIMARY KEY (project_id), FOREIGN KEY (user_id) REFERENCES Users(id));");
            stmt.executeUpdate();
            stmt.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        this.projectsCache = (ArrayList<PaintProject>) this.list();
    }

    @Override
    public PaintProject create(PaintProject project) throws SQLException { 
        int id = -1;
        PaintProject createdProject = null;
        if (this.projectsCache.contains(project)) {
            return this.projectsCache.get(this.projectsCache.indexOf(project));
        } else {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL);
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO PaintProjects "
                + "(user_id, project_name, project_faction, project_category) "
                + "VALUES (?,?,?,?);",
                Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, project.getUserId());
            stmt.setString(2, project.getProjectName());
            stmt.setString(3, project.getProjectFaction());
            stmt.setString(4, project.getProjectCategory());
            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                createdProject = this.read(generatedKeys.getInt(1));
                this.projectsCache.add(createdProject);
            }
            generatedKeys.close();
            stmt.close();
            connection.close();
        }
        return createdProject;
    }

    @Override
    public PaintProject read(Integer projectId) throws SQLException {
        PaintProject project = null;
        List<Integer> projectIds = this.projectsCache.stream().map(p -> p.getProjectId()).collect(Collectors.toList());
        if (projectIds.contains(projectId)) {
            return this.projectsCache.get(projectIds.indexOf(projectId));
        } else {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM PaintProjects WHERE PaintProjects.project_id = (?);");
                stmt.setInt(1, projectId);
                ResultSet rs = stmt.executeQuery();
                project = new PaintProject(rs.getInt("project_id"), rs.getInt("user_id"), rs.getString("project_name"), rs.getString("project_faction"), rs.getString("project_category"), rs.getBoolean("project_completed"), rs.getBoolean("project_archived"), rs.getBoolean("project_intrash"));
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
    public PaintProject update(PaintProject project) throws SQLException {
        PaintProject updatedProject = null;
        if (this.projectsCache.contains(project)) {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
                PreparedStatement stmt = connection.prepareStatement("UPDATE PaintProjects "
                    + "SET user_id = ?, project_name = ?, project_faction, project_category = ?, project_completed = ?, project_archived = ?, project_intrash = ? "
                    + "WHERE project_id = ?;");
                stmt.setInt(1, project.getUserId());
                stmt.setString(2, project.getProjectName());
                stmt.setString(3, project.getProjectFaction());
                stmt.setString(4, project.getProjectCategory());
                stmt.setBoolean(5, project.getProjectCompleted());
                stmt.setBoolean(6, project.getProjectArchived());
                stmt.setBoolean(7, project.getProjectIntrash());
                stmt.setInt(8, project.getProjectId());
                stmt.executeUpdate(); 
                stmt.close(); 
                connection.close();
                updatedProject = this.read(project.getProjectId());
                this.projectsCache.set(this.projectsCache.indexOf(project), updatedProject);
            } catch (SQLException e) {
                System.out.println("Error: " + e);
            }
        }
        return updatedProject;
    }

    @Override
    public void delete(Integer projectId) throws SQLException {
        PaintProject projectToRemove = this.read(projectId);
        if (projectToRemove != null) {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM PaintProjects WHERE project_id = ?");
                
                stmt.setInt(1, projectId);
                
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
    public List<PaintProject> list() throws SQLException {
        ArrayList<PaintProject> projects = new ArrayList<>();
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL);
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM PaintProjects;");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            projects.add(new PaintProject(rs.getInt("project_id"), rs.getInt("user_id"), rs.getString("project_name"), rs.getString("project_faction"),
                    rs.getString("project_category"), rs.getBoolean("project_completed"), rs.getBoolean("project_archived"), rs.getBoolean("project_intrash")));
        }
        rs.close();
        stmt.close();
        connection.close();
        return projects;
    }
    
    /**
     * Get categories of the selected user's projects.
     * @param userId User_id of the selected user
     * @return List of categories of the users projects
     */
    public ArrayList<String> categoriesOfUser(Integer userId) {
        ArrayList<String> categories = new ArrayList<>();
        
        for (PaintProject p : this.projectsCache) {
            if (!categories.contains(p.getProjectCategory()) && p.getUserId().intValue() == userId) {
                categories.add(p.getProjectCategory());
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
        ArrayList<PaintProject> projectsInCategory = (ArrayList<PaintProject>) this.projectsCache.stream().filter(c -> c.getProjectCategory().equals(category)).collect(Collectors.toList());
        
        return projectsInCategory;
    }
}
