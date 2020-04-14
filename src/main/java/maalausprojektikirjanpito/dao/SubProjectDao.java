package maalausprojektikirjanpito.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import maalausprojektikirjanpito.domain.SubProject;


public class SubProjectDao implements Dao<SubProject, Integer> {
    String databaseURL;
    Integer project_id;
    ArrayList<SubProject> subProjectsCache;
    
    /**
     * Constructs a new SubProjectDao-object. Checks the existence of a SubProjects table in the designated database and creates one if not present. SQLITE3 used creates the database if one does not exist.
     * @param project_id Project_id of the project currently being managed
     * @param databaseURL URL of the selected database as a String
     */
    public SubProjectDao(Integer project_id, String databaseURL) {
        this.project_id = project_id;
        this.databaseURL = databaseURL;
        
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
            PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS SubProjects ("
                + "subproject_id INTEGER, "
                + "project_id INTEGER, "
                + "subproject_name VARCHAR(40), "
                + "subproject_completed BOOLEAN, "
                + "subproject_intrash BOOLEAN, "
                + "PRIMARY KEY (subproject_id), "
                + "FOREIGN KEY (project_id) REFERENCES Projects(project_id)"
                + ");");
            stmt.executeUpdate();

            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        
        this.subProjectsCache = (ArrayList<SubProject>) this.list();
    }


    @Override
    public SubProject create(SubProject subProject) throws SQLException {
        int id = -1;
        if (this.subProjectsCache.contains(subProject)) {
            return this.subProjectsCache.get(this.subProjectsCache.indexOf(subProject));
        } else {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO SubProjects "
                    + "(project_id, subproject_name, subproject_completed, subproject_intrash) "
                    + "VALUES (?,?,?,?,?);",
                    Statement.RETURN_GENERATED_KEYS);
                
                stmt.setInt(1, this.project_id);
                stmt.setString(2, subProject.getSubProject_name());
                stmt.setBoolean(4, false);
                stmt.setBoolean(5, false);
                
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
            subProject = this.read(id);
            this.subProjectsCache.add(subProject);
            return subProject;
        } else {
            return null;
        }
    }

    @Override
    public SubProject read(Integer subProject_id) {
        SubProject subProject = null;
        List<Integer> subProject_ids = this.subProjectsCache.stream().map(p -> p.getProject_id()).collect(Collectors.toList());
        if (subProject_ids.contains(subProject_id)) {
            return this.subProjectsCache.get(subProject_ids.indexOf(subProject_id));
        } else {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM SubProjects "
                        + "WHERE SubProjects.subproject_id = (?);");
                
                stmt.setInt(1, subProject_id);
                ResultSet rs = stmt.executeQuery();

                subProject = new SubProject(
                        rs.getInt("subproject_id"), 
                        rs.getInt("project_id"), 
                        rs.getString("subproject_name"),
                        rs.getBoolean("subproject_completed"),
                        rs.getBoolean("subproject_intrash")
                );

                rs.close();
                stmt.close();
                connection.close();           
            } catch (SQLException e) {
                System.out.println("Error: " + e.toString());
            }
        }
        
        return subProject;
    }

    @Override
    public SubProject update(SubProject subProject) {
        SubProject updatedSubProject = null;
        if (this.subProjectsCache.contains(subProject)) {
            System.out.println("SubProject found in cache!");
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
                PreparedStatement stmt = connection.prepareStatement("UPDATE SubProjects "
                    + "SET project_id = ?, subproject_name = ?, subproject_completed = ?, subproject_intrash = ? "
                    + "WHERE project_id = ?;");
                
                stmt.setInt(1, subProject.getProject_id());
                stmt.setString(2, subProject.getSubProject_name());
                stmt.setBoolean(3, subProject.isSubProject_completed());
                stmt.setBoolean(4, subProject.isSubProject_isInTrash());
                stmt.setInt(5, subProject.getProject_id());
                
                stmt.executeUpdate();
                
                stmt.close();
                connection.close();
                
                updatedSubProject = this.read(subProject.getProject_id());
                this.subProjectsCache.set(this.subProjectsCache.indexOf(subProject), updatedSubProject);
            } catch (SQLException e) {
                System.out.println("Error: " + e);
            }
        }
        return updatedSubProject;
    }

    @Override
    public void delete(Integer subProject_id) {
        SubProject subProjectToRemove = this.read(subProject_id);
        if (subProjectToRemove != null) {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM SubProjects WHERE subproject_id = ?");
                
                stmt.setInt(1, subProject_id);
                
                stmt.executeUpdate();
                
                stmt.close();
                connection.close();
                
                this.subProjectsCache.remove(subProjectToRemove);
            } catch (SQLException e) {
                System.out.println("Error: " + e);
            }
        }
    }

    @Override
    public List<SubProject> list() {
        ArrayList<SubProject> subProjects = new ArrayList<>();
        
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM SubProjects;");
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                subProjects.add(new SubProject(
                        rs.getInt("subproject_id"), 
                        rs.getInt("project_id"), 
                        rs.getString("subproject_name"),
                        rs.getBoolean("subproject_completed"),
                        rs.getBoolean("subproject_intrash")));
            }
            
            rs.close();
            stmt.close();
            connection.close();
            
            return subProjects;            
        } catch (SQLException e) {
            System.out.println("Error: " + e.toString());
        }
        return null;    
    }
    
}
