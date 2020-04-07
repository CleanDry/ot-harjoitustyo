package maalausprojektikirjanpito.domain;

import java.util.HashMap;

public class TreatmentCombination {
    HashMap<SurfaceTreatment, Integer> treatments = new HashMap<>();

    /**
     * Create a new treatment combination object.
     */
    public TreatmentCombination() {
    }

    /**
     * Return treatments in this combination.
     * @return HashMap of the treatments with treatments as keys and integer values as ratios
     */
    public HashMap getTreatments() {
        return treatments;
    }

    /**
     * Add treatments to the combination without specifying the ratios.
     * @param treatment to be added
     */
    public void addTreatment(SurfaceTreatment treatment) {
        this.treatments.put(treatment, 0);
    }
    
    /**
     * Add treatments with ratio specified.
     * @param treatment to be added
     * @param ratio as an integer
     */
    public void addTreatment(SurfaceTreatment treatment, int ratio) {
        this.treatments.put(treatment, ratio);
    }
    
    /**
     * Set ratios for a treatment in the HashMap.
     * @param treatment to be adjusted
     * @param ratio to be given
     */
    public void setTreatmentRatio(SurfaceTreatment treatment, int ratio) {
        this.treatments.put(treatment, ratio);
    }
}
