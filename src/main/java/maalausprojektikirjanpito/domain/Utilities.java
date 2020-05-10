package maalausprojektikirjanpito.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Utilities {

    /**
     * Constructs a ne Utilities-object.
     */
    public Utilities() {
    }
    
    /**
     * Checks a String's length to be within given bounds.
     * @param string String to be checked.
     * @param minLength Minimum allowed length.
     * @param maxLength Maximum allowed length.
     * @return True if within bounds, false otherwise.
     */
    public static boolean stringLengthCheck(String string, int minLength, int maxLength) {

        return string.length() >= minLength && string.length() <= maxLength;
    }
    
    public static HashSet<String> factionsAsStrings(ArrayList<PaintProject> projects) {
        HashSet<String> factions = new HashSet<>();
        projects.stream().forEach(p -> factions.add(p.getProjectFaction()));
        return factions;
    }
    
    public static HashSet<String> categoriesAsStrings(ArrayList<PaintProject> projects) {
        HashSet<String> categories = new HashSet<>();
        projects.stream().forEach(p -> categories.add(p.getProjectCategory()));
        return categories;
    }
}
