package maalausprojektikirjanpito.domain;

import java.util.ArrayList;

public class Surface {
    String name;
    ArrayList<TreatmentCombination> treamentCombinations;

    /**
     * Create a new surface object.
     * @param name of the surface
     */
    public Surface(String name) {
        this.name = name;
        this.treamentCombinations = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList getTreamentCombinations() {
        return treamentCombinations;
    }
    
}
