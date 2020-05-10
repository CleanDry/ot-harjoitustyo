package maalausprojektikirjanpito.domain;

import java.util.ArrayList;
import java.util.HashSet;

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
    
    /**
     * Returns the set of factions of a list of PaintProjects.
     * @param projects PaintProjects to go through
     * @return HashSet of factions as Strings
     */
    public static HashSet<String> factionsAsStrings(ArrayList<PaintProject> projects) {
        HashSet<String> factions = new HashSet<>();
        projects.stream().forEach(p -> factions.add(p.getProjectFaction()));
        return factions;
    }
    
    /**
     * Returns the set of categories of a list of PaintProjects.
     * @param projects PaintProjects to go through
     * @return HashSet of categories as Strings
     */
    public static HashSet<String> categoriesAsStrings(ArrayList<PaintProject> projects) {
        HashSet<String> categories = new HashSet<>();
        projects.stream().forEach(p -> categories.add(p.getProjectCategory()));
        return categories;
    }
}
