package maalausprojektikirjanpito.dao;

import java.sql.*;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import maalausprojektikirjanpito.domain.Layer;
import maalausprojektikirjanpito.domain.Surface;


public class SurfaceDao implements Dao<Surface, Integer> {
    String databaseURL;
    LayerDao layerDao;
    ArrayList<Surface> surfacesCache;
    
    /**
     * Constructs a new SurfaceDao-object.Checks the existence of a SurfaceDao table in the designated database and creates one if not present. SQLITE3 used creates the database if one does not exist.
     * @param databaseURL URL of the selected database as a String
     * @param layerDao injected for the object
     */
    public SurfaceDao(String databaseURL, LayerDao layerDao) {
        this.databaseURL = databaseURL;
        this.layerDao = layerDao;
        this.surfacesCache = (ArrayList<Surface>) this.list();
    }
    
    @Override
    public ArrayList<Surface> getCache() {
        return this.surfacesCache;
    }
    
    @Override
    public void init() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
            PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Surfaces ("
                + "surface_id INTEGER, subproject_id INTEGER, surface_name VARCHAR(40), surface_intrash BOOLEAN, "
                + "PRIMARY KEY (surface_id), FOREIGN KEY (subproject_id) REFERENCES Subprojects(subproject_id));");
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
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL);
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Surfaces "
                + "(subproject_id, surface_name, surface_intrash) "
                + "VALUES (?,?,?);",
                Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, surface.getSubprojectId());
            stmt.setString(2, surface.getSurfaceName());
            stmt.setBoolean(3, false);
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
            surface = this.read(id);
            this.surfacesCache.add(surface);
            return surface;
        } else {
            return null;
        }
    }

    @Override
    public Surface read(Integer surfaceId) {
        Surface surface = null;
        List<Integer> surfaceIds = this.surfacesCache.stream().map(p -> p.getSurfaceId()).collect(Collectors.toList());
        if (surfaceIds.contains(surfaceId)) {
            return this.surfacesCache.get(surfaceIds.indexOf(surfaceId));
        } else {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Surfaces WHERE Surfaces.surface_id = (?);");
                stmt.setInt(1, surfaceId);
                ResultSet rs = stmt.executeQuery();
                surface = new Surface(rs.getInt("surface_id"), rs.getInt("subproject_id"), rs.getString("surface_name"), rs.getBoolean("surface_intrash"));
                rs.close();
                stmt.close();
                surface.setLayers(this.layerDao.layersOfSurface(surface.getSurfaceId(), connection));
                connection.close();           
            } catch (SQLException e) {
                System.out.println("Error: " + e.toString());
            }
        }
        return surface;
    }

    @Override
    public Surface update(Surface surface) throws SQLException {
        Surface updatedSurface = null;
        if (this.surfacesCache.contains(surface)) {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL);
            PreparedStatement stmt = connection.prepareStatement("UPDATE Surfaces "
                + "SET subproject_id = ?, surface_name = ?, surface_intrash = ? "
                + "WHERE surface_id = ?;");
            stmt.setInt(1, surface.getSubprojectId());
            stmt.setString(2, surface.getSurfaceName());
            stmt.setBoolean(3, surface.getIsInTrash());
            stmt.setInt(4, surface.getSurfaceId());
            stmt.executeUpdate();
            stmt.close();
            connection.close();
            updatedSurface = this.read(surface.getSurfaceId());
            this.surfacesCache.set(this.surfacesCache.indexOf(surface), updatedSurface);
        }
        return updatedSurface;
    }

    @Override
    public void delete(Integer surfaceId) {
        Surface surfacetoRemove = this.read(surfaceId);
        if (surfacetoRemove != null) {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM Surfaces WHERE surface_id = ?");
                stmt.setInt(1, surfaceId);
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
                surfaces.add(new Surface(rs.getInt("surface_id"), rs.getInt("subproject_id"), rs.getString("surface_name"), rs.getBoolean("surface_intrash")));
            }
            rs.close();
            stmt.close();
            for (Surface surface : surfaces) {
                surface.setLayers(this.layerDao.layersOfSurface(surface.getSurfaceId(), connection));
            }
            connection.close();          
        } catch (SQLException e) {
            System.out.println("Error: " + e.toString());
        }
        return surfaces;    
    }
    
    public ArrayList<Surface> listOfSubprojectSurfaces(Integer subprojectId) {
        ArrayList<Surface> surfacesToReturn = (ArrayList<Surface>) this.getCache().stream().filter(s -> s.getSubprojectId().equals(subprojectId)).collect(Collectors.toList());
        return surfacesToReturn;
    }
    
    public boolean addLayerToSurface(Integer surfaceId, Integer layerId) {
        Surface surface = this.read(surfaceId);
        Layer layer = this.layerDao.read(layerId);
        int surfaceIndex = this.surfacesCache.indexOf(surface);
        if (!this.surfacesCache.get(surfaceIndex).getLayers().contains(layer)) {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO SurfaceLayers (surface_id, layer_id) VALUES (?,?);");
                stmt.setInt(1, surfaceId);
                stmt.setInt(2, layerId);
                stmt.executeUpdate();
                stmt.close();
                connection.close();
                this.surfacesCache.get(surfaceIndex).getLayers().add(this.layerDao.create(layer));
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(SurfaceDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    public boolean removeLayerFromSurface(Integer surfaceId, Integer layerId) {
        Surface surface = this.read(surfaceId);
        Layer layer = this.layerDao.read(layerId);
        int surfaceIndex = this.surfacesCache.indexOf(surface);
        if (this.surfacesCache.get(surfaceIndex).getLayers().contains(layer)) {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM SurfaceLayers WHERE surface_id = ? AND layer_id = ?;");
                stmt.setInt(1, surfaceId);
                stmt.setInt(2, layerId);
                stmt.executeUpdate();
                stmt.close();
                connection.close();
                this.surfacesCache.get(surfaceIndex).getLayers().remove(layer);
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(SurfaceDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
}
