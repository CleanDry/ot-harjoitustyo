package maalausprojektikirjanpito.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import maalausprojektikirjanpito.domain.SubProject;
import maalausprojektikirjanpito.domain.Surface;


public class SubProjectDao implements Dao<SubProject, Integer> {
    String databaseURL;
    SurfaceDao surfaceDao;
    ArrayList<SubProject> subProjectsCache;
    
    /**
     * Constructs a new SubProjectDao-object.Checks the existence of a SubProjects table in the designated database and creates one if not present. SQLITE3 used creates the database if one does not exist.
     * @param databaseURL URL of the selected database as a String
     * @param surfaceDao SurfaceDao-object injected
     */
    public SubProjectDao(String databaseURL, SurfaceDao surfaceDao) {
        this.databaseURL = databaseURL;
        this.surfaceDao = surfaceDao;
    }

    @Override
    public ArrayList<SubProject> getCache() {
        return subProjectsCache;
    }
    
    @Override
    public void init() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
            PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS SubProjects ("
                + "subproject_id INTEGER, project_id INTEGER, subproject_name VARCHAR(40), subproject_completed BOOLEAN DEFAULT false, subproject_intrash BOOLEAN DEFAULT false, "
                + "PRIMARY KEY (subproject_id), FOREIGN KEY (project_id) REFERENCES Projects(project_id));");
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
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL);
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO SubProjects "
                + "(project_id, subproject_name) "
                + "VALUES (?,?);",
                Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, subProject.getProjectId());
            stmt.setString(2, subProject.getSubProjectName());
            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }
            generatedKeys.close();
            stmt.close();
            connection.close();
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
    public SubProject read(Integer subProjectId) {
        SubProject subProject = null;
        List<Integer> subProjectIds = this.subProjectsCache.stream().map(p -> p.getProjectId()).collect(Collectors.toList());
        if (subProjectIds.contains(subProjectId)) {
            return this.subProjectsCache.get(subProjectIds.indexOf(subProjectId));
        } else {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM SubProjects "
                        + "WHERE SubProjects.subproject_id = (?);");
                stmt.setInt(1, subProjectId);
                ResultSet rs = stmt.executeQuery();
                subProject = new SubProject(rs.getInt("subproject_id"), rs.getInt("project_id"), rs.getString("subproject_name"), rs.getBoolean("subproject_completed"), rs.getBoolean("subproject_intrash"));
                rs.close();
                stmt.close();
                connection.close();
                subProject.setSurfaces(this.surfaceDao.listOfSubprojectSurfaces(subProject.getSubProjectId()));
            } catch (SQLException e) {
                System.out.println("Error: " + e.toString());
            }
        }
        return subProject;
    }

    @Override
    public SubProject update(SubProject subProject) throws SQLException {
        SubProject updatedSubProject = null;
        if (this.subProjectsCache.contains(subProject)) {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL);
            PreparedStatement stmt = connection.prepareStatement("UPDATE SubProjects "
                + "SET project_id = ?, subproject_name = ?, subproject_completed = ?, subproject_intrash = ? "
                + "WHERE subproject_id = ?;");
            stmt.setInt(1, subProject.getProjectId());
            stmt.setString(2, subProject.getSubProjectName());
            stmt.setBoolean(3, subProject.isSubProjectCompleted());
            stmt.setBoolean(4, subProject.isSubProjectInTrash());
            stmt.setInt(5, subProject.getSubProjectId());
            stmt.executeUpdate();
            stmt.close();
            connection.close();
            updatedSubProject = this.read(subProject.getProjectId());
            this.subProjectsCache.set(this.subProjectsCache.indexOf(subProject), updatedSubProject);
        }
        return updatedSubProject;
    }

    @Override
    public void delete(Integer subProjectId) {
        SubProject subProjectToRemove = this.read(subProjectId);
        if (subProjectToRemove != null) {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM SubProjects WHERE subproject_id = ?");
                stmt.setInt(1, subProjectId);
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
                subProjects.add(new SubProject(rs.getInt("subproject_id"), rs.getInt("project_id"), rs.getString("subproject_name"), rs.getBoolean("subproject_completed"), rs.getBoolean("subproject_intrash")));
            }
            rs.close();
            stmt.close();
            connection.close();
            for (SubProject subproject : subProjects) {
                subproject.setSurfaces(this.surfaceDao.listOfSubprojectSurfaces(subproject.getSubProjectId()));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.toString());
        }
        return subProjects;    
    }
    
    public boolean createNewSurface(Surface surface) {
        try {
            Surface newSurface = this.surfaceDao.create(surface);
            this.subProjectsCache.stream().filter((sb) -> (sb.getSubProjectId().equals(newSurface.getSubprojectId()))).forEachOrdered((sb) -> {
                sb.getSurfaces().add(surface);
            });
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(SubProjectDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean updateSurface(Surface surface) {
        try {
            Surface updatedSurface = this.surfaceDao.update(surface);
            this.subProjectsCache.stream().filter((sb) -> (sb.getSubProjectId().equals(updatedSurface.getSubprojectId()))).forEachOrdered((sb) -> {
                sb.getSurfaces().stream().filter((s) -> (s.getSurfaceId().equals(updatedSurface.getSurfaceId()))).forEachOrdered((s) -> s = updatedSurface);
            });
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(SubProjectDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public void deleteSurface(Surface surface) {
        this.surfaceDao.delete(surface.getSurfaceId());
        this.subProjectsCache.stream().filter((sb) -> (sb.getSubProjectId().equals(surface.getSubprojectId()))).forEachOrdered((sb) -> {
            sb.getSurfaces().remove(surface);
        });
    }
}
