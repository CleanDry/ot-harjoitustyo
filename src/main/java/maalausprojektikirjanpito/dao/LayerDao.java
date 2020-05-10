package maalausprojektikirjanpito.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import maalausprojektikirjanpito.domain.Layer;
import maalausprojektikirjanpito.domain.SurfaceTreatment;

public class LayerDao implements Dao<Layer, Integer> {
    String databaseURL;
    SurfaceTreatmentDao surfaceTreatmentDao;
    ArrayList<Layer> layerCache;
    
    /**
     * Constructs a new Layer-object.
     * @param databaseURL URL of the selected database as a String
     * @param surfaceTreatmentDao injected to this object
     */
    public LayerDao(String databaseURL, SurfaceTreatmentDao surfaceTreatmentDao) {
        this.databaseURL = databaseURL;
        this.surfaceTreatmentDao = surfaceTreatmentDao;
        this.layerCache = (ArrayList<Layer>) this.list();
    }
    
    @Override
    public ArrayList<Layer> getCache() {
        return this.layerCache;
    }
    
    @Override
    public void init() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
            PreparedStatement stmt1 = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Layers ("
                + "layer_id INTEGER PRIMARY KEY, layer_name VARCHAR(40), layer_note VARCHAR(60));");
            stmt1.executeUpdate();
            stmt1.close();
            PreparedStatement stmt2 = connection.prepareStatement("CREATE TABLE IF NOT EXISTS SurfaceLayers ("
                + "surface_id INTEGER, layer_id INTEGER);");
            stmt2.executeUpdate();
            stmt2.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        this.layerCache = (ArrayList<Layer>) this.list();
    }


    @Override
    public Layer create(Layer layer) throws SQLException {
        int id = -1;
        if (this.layerCache.contains(layer)) {
            return this.layerCache.get(this.layerCache.indexOf(layer));
        } else {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL);
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Layers "
                + "(layer_name, layer_note) "
                + "VALUES (?,?);",
                Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, layer.getLayerName());
            stmt.setString(2, layer.getLayerNote());
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
            layer = this.read(id);
            this.layerCache.add(layer);
            return layer;
        } else {
            return null;
        }
    }

    @Override
    public Layer read(Integer layerId) {
        Layer layer = null;
        List<Integer> layerIds = this.layerCache.stream().map(p -> p.getLayerId()).collect(Collectors.toList());
        if (layerIds.contains(layerId)) {
            return this.layerCache.get(layerIds.indexOf(layerId));
        } else {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Layers WHERE Layers.layer_id = ?;");
                stmt.setInt(1, layerId);
                ResultSet rs = stmt.executeQuery();
                layer = new Layer(rs.getInt("layer_id"), rs.getString("layer_name"), rs.getString("layer_note"));
                rs.close();
                stmt.close();
                layer.setTreatments(this.surfaceTreatmentDao.treatmentsOfLayer(layerId, connection));
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error: " + e.toString());
            }
        }
        return layer;
    }

    @Override
    public Layer update(Layer layer) throws SQLException {
        Layer updatedLayer = null;
        if (this.layerCache.contains(layer)) {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL);
            PreparedStatement stmt = connection.prepareStatement("UPDATE TreatmentCombinations "
                + "SET layer_name = ?, layer_note = ? "
                + "WHERE layer_id = ?;");
            stmt.setString(1, layer.getLayerName());
            stmt.setString(2, layer.getLayerNote());
            stmt.setInt(3, layer.getLayerId());
            stmt.executeUpdate();
            stmt.close();
            connection.close();
            updatedLayer = this.read(layer.getLayerId());
            this.layerCache.set(this.layerCache.indexOf(layer), updatedLayer);
        }
        return updatedLayer;
    }

    @Override
    public void delete(Integer layerId) {
        Layer layerToRemove = this.read(layerId);
        if (layerToRemove != null) {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM Layers WHERE layer_id = ?");
                stmt.setInt(1, layerId);
                stmt.executeUpdate();
                stmt.close();
                connection.close();
                this.layerCache.remove(layerToRemove);
                // implement removing connections in SurfaceLayers!
            } catch (SQLException e) {
                System.out.println("Error: " + e);
            }
        }
    }

    @Override
    public List<Layer> list() {
        ArrayList<Layer> layers = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Layers;");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                layers.add(new Layer(rs.getInt("layer_id"), rs.getString("layer_name"), rs.getString("layer_note")));
            }
            rs.close();
            stmt.close();
            for (Layer layer : layers) {
                layer.setTreatments(this.surfaceTreatmentDao.treatmentsOfLayer(layer.getLayerId(), connection));
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.toString());
        }
        return layers;    
    }
    
    public ArrayList<Layer> layersOfSurface(Integer surfaceId, Connection connection) throws SQLException {
        ArrayList<Layer> layersToReturn = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement("SELECT Layers.layer_id, Layers.layer_name, Layers.layer_note "
                + "FROM Surfaces, SurfaceLayers, Layers "
                + "WHERE Surfaces.surface_id = ? AND Surfaces.surface_id = SurfaceLayers.surface_id AND SurfaceLayers.layer_id = Layers.layer_id;");
        stmt.setInt(1, surfaceId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            layersToReturn.add(new Layer(rs.getInt("layer_id"), rs.getString("layer_name"), rs.getString("layer_note")));
        }
        rs.close();
        stmt.close();
        for (Layer layer : layersToReturn) {
            layer.setTreatments(this.surfaceTreatmentDao.treatmentsOfLayer(layer.getLayerId(), connection));
        }
        return layersToReturn;
    }
    
    public boolean addTreatmentToLayer(Integer layerId, Integer surfaceTreatmentId) {
        Layer layer = this.read(layerId);
        SurfaceTreatment surfaceTreatment = this.surfaceTreatmentDao.read(surfaceTreatmentId);
        int layerIndex = this.layerCache.indexOf(layer);
        if (!this.layerCache.get(layerIndex).getTreatments().contains(surfaceTreatment)) {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO LayerTreatments (layer_id, treatment_id) VALUES (?,?);");
                stmt.setInt(1, layerId);
                stmt.setInt(2, surfaceTreatmentId);
                stmt.executeUpdate();
                stmt.close();
                connection.close();
                this.layerCache.get(layerIndex).getTreatments().add(surfaceTreatment);
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(SurfaceDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    public boolean removeTreatmentFromLayer(Integer layerId, Integer surfaceTreatmentId) {
        Layer layer = this.read(layerId);
        SurfaceTreatment surfaceTreatment = this.surfaceTreatmentDao.read(surfaceTreatmentId);
        int layerIndex = this.layerCache.indexOf(layer);
        if (this.layerCache.get(layerIndex).getTreatments().contains(surfaceTreatment)) {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM SurfaceLayers WHERE layer_id = ? AND treatment_id = ?;");
                stmt.setInt(1, layerId);
                stmt.setInt(2, surfaceTreatmentId);
                stmt.executeUpdate();
                stmt.close();
                connection.close();
                this.layerCache.get(layerIndex).getTreatments().remove(surfaceTreatment);
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(SurfaceDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
}
