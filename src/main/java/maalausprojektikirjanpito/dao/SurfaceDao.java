package maalausprojektikirjanpito.dao;

import java.sql.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import maalausprojektikirjanpito.domain.Surface;


public class SurfaceDao implements Dao<Surface, Integer> {
    String databaseURL;
    Integer subproject_id;
    ArrayList<Surface> surfacesCache;
    
    /**
     * Constructs a new SurfaceDao-object. Checks the existence of a SurfaceDao table in the designated database and creates one if not present. SQLITE3 used creates the database if one does not exist.
     * @param subproject_id Sub project_id of the project currently being managed
     * @param databaseURL URL of the selected database as a String
     */
    public SurfaceDao(Integer subproject_id, String databaseURL) {
        this.subproject_id = subproject_id;
        this.databaseURL = databaseURL;
        
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
            PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Surfaces ("
                + "surface_id INTEGER, "
                + "subproject_id INTEGER, "
                + "surface_name VARCHAR(40), "
                + "surface_intrash BOOLEAN, "
                + "PRIMARY KEY (surface_id), "
                + "FOREIGN KEY (subproject_id) REFERENCES Subprojects(subproject_id)"
                + ");");
            stmt.executeUpdate();

            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        
        this.surfacesCache = (ArrayList<Surface>) this.list();
    }


    @Override
    public Surface create(Surface surface) throws SQLException {
        int id = -1;
        if (this.surfacesCache.contains(surface)) {
            return this.surfacesCache.get(this.surfacesCache.indexOf(surface));
        } else {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO Surfaces "
                    + "(subproject_id, surface_name, surface_intrash) "
                    + "VALUES (?,?,?);",
                    Statement.RETURN_GENERATED_KEYS);
                
                stmt.setInt(1, this.subproject_id);
                stmt.setString(2, surface.getSurface_name());
                stmt.setBoolean(3, false);
                
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
            surface = this.read(id);
            this.surfacesCache.add(surface);
            return surface;
        } else {
            return null;
        }
    }

    @Override
    public Surface read(Integer surface_id) {
        Surface surface = null;
        List<Integer> surface_ids = this.surfacesCache.stream().map(p -> p.getSurface_id()).collect(Collectors.toList());
        if (surface_ids.contains(surface_id)) {
            return this.surfacesCache.get(surface_ids.indexOf(surface_id));
        } else {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Surfaces "
                        + "WHERE Surfaces.surface_id = (?);");
                
                stmt.setInt(1, surface_id);
                ResultSet rs = stmt.executeQuery();

                surface = new Surface(
                        rs.getInt("surface_id"), 
                        rs.getInt("subproject_id"), 
                        rs.getString("surface_name"),
                        rs.getBoolean("surface_intrash")
                );

                rs.close();
                stmt.close();
                connection.close();           
            } catch (SQLException e) {
                System.out.println("Error: " + e.toString());
            }
        }
        
        return surface;
    }

    @Override
    public Surface update(Surface surface) {
        Surface updatedSurface = null;
        if (this.surfacesCache.contains(surface)) {
            System.out.println("Surface found in cache!");
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
                PreparedStatement stmt = connection.prepareStatement("UPDATE Surfaces "
                    + "SET subproject_id = ?, surface_name = ?, surface_intrash = ? "
                    + "WHERE surface_id = ?;");
                
                stmt.setInt(1, surface.getSubproject_id());
                stmt.setString(2, surface.getSurface_name());
                stmt.setBoolean(3, surface.getInTrash());
                stmt.setInt(4, surface.getSurface_id());
                
                stmt.executeUpdate();
                
                stmt.close();
                connection.close();
                
                updatedSurface = this.read(surface.getSurface_id());
                this.surfacesCache.set(this.surfacesCache.indexOf(surface), updatedSurface);
            } catch (SQLException e) {
                System.out.println("Error: " + e);
            }
        }
        return updatedSurface;
    }

    @Override
    public void delete(Integer surface_id) {
        Surface surfacetoRemove = this.read(surface_id);
        if (surfacetoRemove != null) {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM Surfaces WHERE surface_id = ?");
                
                stmt.setInt(1, surface_id);
                
                stmt.executeUpdate();
                
                stmt.close();
                connection.close();
                
                this.surfacesCache.remove(surfacetoRemove);
            } catch (SQLException e) {
                System.out.println("Error: " + e);
            }
        }
    }

    @Override
    public List<Surface> list() {
        ArrayList<Surface> surfaces = new ArrayList<>();
        
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Surfaces;");
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                surfaces.add(new Surface(
                        rs.getInt("surface_id"), 
                        rs.getInt("subproject_id"), 
                        rs.getString("surface_name"),
                        rs.getBoolean("surface_intrash")));
            }
            
            rs.close();
            stmt.close();
            connection.close();
            
            return surfaces;            
        } catch (SQLException e) {
            System.out.println("Error: " + e.toString());
        }
        return null;    
    }
}
