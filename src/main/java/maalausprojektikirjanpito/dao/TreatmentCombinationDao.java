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
import maalausprojektikirjanpito.domain.TreatmentCombination;

public class TreatmentCombinationDao implements Dao<TreatmentCombination, Integer> {
    String databaseURL;
    ArrayList<TreatmentCombination> treatmentCombinationCache;
    
    /**
     * Constructs a new TreatmentCombination-object.
     * @param databaseURL URL of the selected database as a String
     */
    public TreatmentCombinationDao(String databaseURL) {
        this.databaseURL = databaseURL;
        this.treatmentCombinationCache = (ArrayList<TreatmentCombination>) this.list();
    }
    
    @Override
    public ArrayList<TreatmentCombination> getCache() {
        return this.treatmentCombinationCache;
    }
    
    @Override
    public void init() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
            PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS TreatmentCombinations ("
                + "combination_id INTEGER, combination_name VARCHAR(40), combination_note VARCHAR(60)"
                + "PRIMARY KEY (combination_id);");
            stmt.executeUpdate();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        this.treatmentCombinationCache = (ArrayList<TreatmentCombination>) this.list();
    }


    @Override
    public TreatmentCombination create(TreatmentCombination combination) throws SQLException {
        int id = -1;
        if (this.treatmentCombinationCache.contains(combination)) {
            return this.treatmentCombinationCache.get(this.treatmentCombinationCache.indexOf(combination));
        } else {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL);
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO TreatmentCombinations "
                + "(combination_name, combination_note) "
                + "VALUES (?,?);",
                Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, combination.getCombinationName());
            stmt.setString(2, combination.getNote());
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
            combination = this.read(id);
            this.treatmentCombinationCache.add(combination);
            return combination;
        } else {
            return null;
        }
    }

    @Override
    public TreatmentCombination read(Integer combinationId) {
        TreatmentCombination combination = null;
        List<Integer> combinationIds = this.treatmentCombinationCache.stream().map(p -> p.getCombinationId()).collect(Collectors.toList());
        if (combinationIds.contains(combinationId)) {
            return this.treatmentCombinationCache.get(combinationIds.indexOf(combinationId));
        } else {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM TreatmentCombinations WHERE TreatmentCombinations.combination_id = (?);");
                stmt.setInt(1, combinationId);
                ResultSet rs = stmt.executeQuery();
                combination = new TreatmentCombination(rs.getInt("combination_id"), rs.getString("combination_name"), rs.getString("combination_note"));
                rs.close();
                stmt.close();
                connection.close();
                // implement adding treatments to combinations as ArrayLists!
            } catch (SQLException e) {
                System.out.println("Error: " + e.toString());
            }
        }
        return combination;
    }

    @Override
    public TreatmentCombination update(TreatmentCombination combination) throws SQLException {
        TreatmentCombination updatedCombination = null;
        if (this.treatmentCombinationCache.contains(combination)) {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL);
            PreparedStatement stmt = connection.prepareStatement("UPDATE TreatmentCombinations "
                + "SET combination_name = ?, combination_note = ? "
                + "WHERE combination_id = ?;");
            stmt.setString(1, combination.getCombinationName());
            stmt.setString(2, combination.getNote());
            stmt.setInt(3, combination.getCombinationId());
            stmt.executeUpdate();
            stmt.close();
            connection.close();
            updatedCombination = this.read(combination.getCombinationId());
            this.treatmentCombinationCache.set(this.treatmentCombinationCache.indexOf(combination), updatedCombination);
        }
        return updatedCombination;
    }

    @Override
    public void delete(Integer combinationId) {
        TreatmentCombination combinationToRemove = this.read(combinationId);
        if (combinationToRemove != null) {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM TreatmentCombinations WHERE combination_id = ?");
                stmt.setInt(1, combinationId);
                stmt.executeUpdate();
                stmt.close();
                connection.close();
                this.treatmentCombinationCache.remove(combinationToRemove);
            } catch (SQLException e) {
                System.out.println("Error: " + e);
            }
        }
    }

    @Override
    public List<TreatmentCombination> list() {
        ArrayList<TreatmentCombination> combinations = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseURL)) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM TreatmentCombinations;");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                combinations.add(new TreatmentCombination(rs.getInt("combination_id"), rs.getString("combination_name"), rs.getString("combination_note")));
            }
            rs.close();
            stmt.close();
            connection.close();
            // implement adding treatments to combinations as ArrayLists!
        } catch (SQLException e) {
            System.out.println("Error: " + e.toString());
        }
        return combinations;    
    }
}
