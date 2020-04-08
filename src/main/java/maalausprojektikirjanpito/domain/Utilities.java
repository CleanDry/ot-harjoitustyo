package maalausprojektikirjanpito.domain;

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
}
