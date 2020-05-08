package maalausprojektikirjanpito.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.paint.Color;
import maalausprojektikirjanpito.domain.SurfaceTreatment;

public class SurfaceTreatmentDao implements Dao<SurfaceTreatment, Integer> {
    String databaseURL;
    ArrayList<SurfaceTreatment> treatmentCache;
    
    /**
     * Constructs a new SurfaceTreatment-object.
     * @param databaseURL URL of the selected database as a String
     */
    public SurfaceTreatmentDao(String databaseURL) {
        this.databaseURL = databaseURL;
        this.treatmentCache = (ArrayList<SurfaceTreatment>) this.list();
    }
    
    @Override
    public ArrayList<SurfaceTreatment> getCache() {
        return this.treatmentCache;
    }
    
    @Override
    public void init() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
            PreparedStatement stmt1 = connection.prepareStatement("CREATE TABLE IF NOT EXISTS SurfaceTreatments ("
                + "treatment_id INTEGER PRIMARY KEY, treatment_name VARCHAR(40), treatment_type VARCHAR(40), treatment_manufacturer VARCHAR(40), treatment_colour VARCHAR(40));");
            stmt1.executeUpdate();
            stmt1.close();
            PreparedStatement stmt2 = connection.prepareStatement("CREATE TABLE IF NOT EXISTS LayerTreatments ("
                + "layer_id INTEGER, treatment_id INTEGER);");
            stmt2.executeUpdate();
            stmt2.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        this.treatmentCache = (ArrayList<SurfaceTreatment>) this.list();
    }


    @Override
    public SurfaceTreatment create(SurfaceTreatment treatment) throws SQLException {
        int id = -1;
        if (this.treatmentCache.contains(treatment)) {
            return this.treatmentCache.get(this.treatmentCache.indexOf(treatment));
        } else {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL);
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO SurfaceTreatments "
                + "(treatment_name, treatment_type, treatment_manufacturer, treatment_colour) "
                + "VALUES (?,?,?,?);",
                Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, treatment.getTreatmentName());
            stmt.setString(2, treatment.getTreatmentType());
            stmt.setString(3, treatment.getTreatmentManufacturer());
            stmt.setString(4, treatment.getTreatmentColour().toString());
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
            treatment = this.read(id);
            this.treatmentCache.add(treatment);
            return treatment;
        } else {
            return null;
        }
    }

    @Override
    public SurfaceTreatment read(Integer treatmentId) {
        SurfaceTreatment treatment = null;
        List<Integer> treatmentIds = this.treatmentCache.stream().map(t -> t.getTreatmentId()).collect(Collectors.toList());
        if (treatmentIds.contains(treatmentId)) {
            return this.treatmentCache.get(treatmentIds.indexOf(treatmentId));
        } else {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM SurfaceTreatments WHERE SurfaceTreatments.treatment_id = ?;");
                stmt.setInt(1, treatmentId);
                ResultSet rs = stmt.executeQuery();
                treatment = new SurfaceTreatment(rs.getInt("treatment_id"), rs.getString("treatment_name"), rs.getString("treatment_type"), rs.getString("treatment_manufacturer"), Color.valueOf(rs.getString("treatment_colour")));
                rs.close();
                stmt.close();
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error: " + e.toString());
            }
        }
        return treatment;
    }

    @Override
    public SurfaceTreatment update(SurfaceTreatment treatment) throws SQLException {
        SurfaceTreatment updatedTreatment = null;
        if (this.treatmentCache.contains(treatment)) {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL);
            PreparedStatement stmt = connection.prepareStatement("UPDATE SurfaceTreatments "
                + "SET treatment = ?, treatment_type = ?, treatment_manufacturer = ?, treatment_colour = ? "
                + "WHERE layer_id = ?;");
            stmt.setString(1, treatment.getTreatmentName());
            stmt.setString(2, treatment.getTreatmentType());
            stmt.setString(3, treatment.getTreatmentManufacturer());
            stmt.setString(4, treatment.getTreatmentColour().toString());
            stmt.setInt(5, treatment.getTreatmentId());
            stmt.executeUpdate();
            stmt.close();
            connection.close();
            updatedTreatment = this.read(treatment.getTreatmentId());
            this.treatmentCache.set(this.treatmentCache.indexOf(treatment), updatedTreatment);
        }
        return updatedTreatment;
    }

    @Override
    public void delete(Integer treatmentId) {
        SurfaceTreatment treatmentToRemove = this.read(treatmentId);
        if (treatmentToRemove != null) {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM SurfaceTreatments WHERE treatment_id = ?");
                stmt.setInt(1, treatmentId);
                stmt.executeUpdate();
                stmt.close();
                connection.close();
                this.treatmentCache.remove(treatmentToRemove);
            } catch (SQLException e) {
                System.out.println("Error: " + e);
            }
        }
    }

    @Override
    public List<SurfaceTreatment> list() {
        ArrayList<SurfaceTreatment> treatments = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM SurfaceTreatments;");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                treatments.add(new SurfaceTreatment(rs.getInt("treatment_id"), rs.getString("treatment_name"), rs.getString("treatment_type"), rs.getString("treatment_manufacturer"), Color.valueOf(rs.getString("treatment_colour"))));
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.toString());
        }
        return treatments;    
        }
    
    public ArrayList<SurfaceTreatment> treatmentsOfLayer(Integer layerId, Connection connection) throws SQLException {
        ArrayList<SurfaceTreatment> treatmentsToReturn = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement("SELECT SurfaceTreatments.treatment_id, SurfaceTreatments.treatment_name, SurfaceTreatments.treatment_type, SurfaceTreatments.treatment_manufacturer, SurfaceTreatments.treatment_colour "
                + "FROM Layers, LayerTreatments, SurfaceTreatments "
                + "WHERE Layers.layerId = ? AND Layers.layerId = LayerTreatments.layerId AND LayerTreatments.treatment_id = SurfaceTreatments.treatment_id;");
        stmt.setInt(1, layerId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            treatmentsToReturn.add(new SurfaceTreatment(rs.getInt("treatment_id"), rs.getString("treatment_name"), rs.getString("treatment_type"), rs.getString("treatment_manufacturer"), Color.valueOf(rs.getString("treatment_colour"))));
        }
        rs.close();
        stmt.close();
        return treatmentsToReturn;
    }
}
