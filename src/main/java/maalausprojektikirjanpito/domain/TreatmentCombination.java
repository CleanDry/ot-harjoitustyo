package maalausprojektikirjanpito.domain;

import java.util.ArrayList;

public class TreatmentCombination {
    Integer combinationId;
    String combinationName;
    ArrayList<SurfaceTreatment> treatments = new ArrayList<>();
    String note;
    
    /**
     * Create a new treatment combination object.
     * @param combinationName name given to this combination
     * @param note attached to this combination
     */
    public TreatmentCombination(String combinationName, String note) {
        this.combinationName = combinationName;
        this.note = note;
    }
    
    /**
     * Create a new treatment combination object.
     * @param treatmentCombinationId Unique identifier of the treatmentCombination as Integer.
     * @param combinationName name given to this combination
     * @param note note attached to this combination
     */
    public TreatmentCombination(Integer treatmentCombinationId, String combinationName, String note) {
        this.combinationId = treatmentCombinationId;
        this.combinationName = combinationName;
        this.note = note;
    }

    public Integer getCombinationId() {
        return combinationId;
    }

    public void setTreatmentCombinationId(Integer treatmentCombinationId) {
        this.combinationId = treatmentCombinationId;
    }

    public String getCombinationName() {
        return combinationName;
    }

    public void setCombinationName(String combinationName) {
        this.combinationName = combinationName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setTreatments(ArrayList<SurfaceTreatment> treatments) {
        this.treatments = treatments;
    }

    public ArrayList<SurfaceTreatment> getTreatments() {
        return treatments;
    }
    
    /**
     * Add treatments to the combination.
     * @param treatment to be added
     * @return true if successful, false otherwise
     */
    public boolean addTreatment(SurfaceTreatment treatment) {
        if (this.treatments.size() < 5) {
            this.treatments.add(treatment);
            return true;
        }
        return false;
    }
    
    /**
     * Push given treatment up in the queue
     * @param treatment
     * @return true if successful, false otherwise
     */
    public boolean pushTreatmentUp(SurfaceTreatment treatment) {
        Integer currentIndex = this.treatments.indexOf(treatment);
        if (currentIndex >= 1) {
            this.treatments.set(currentIndex-1, treatment);
            return true;
        }
        return false;
    }
    
    /**
     * Push given treatment down in the queue
     * @param treatment
     * @return true if successful, false otherwise
     */
    public boolean pushTreatmentDown(SurfaceTreatment treatment) {
        Integer currentIndex = this.treatments.indexOf(treatment);
        if (currentIndex < this.treatments.size()-1) {
            this.treatments.set(currentIndex+1, treatment);
            return true;
        }
        return false;
    }
}
