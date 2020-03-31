package maalausprojektikirjanpito.domain;

import java.util.HashMap;

public class TreatmentCombination {
    HashMap<SurfaceTreatment, Integer> treatments = new HashMap<>();

    public TreatmentCombination() {
    }

    public HashMap getTreatments() {
        return treatments;
    }

    public void addTreatment(SurfaceTreatment treatment) {
        this.treatments.put(treatment, 0);
    }
    
    public void addTreatment(SurfaceTreatment treatment, int ratio) {
        this.treatments.put(treatment, ratio);
    }
    
    public void setTreatmentRatio(SurfaceTreatment treatment, int ratio) {
        this.treatments.put(treatment, ratio);
    }
}
