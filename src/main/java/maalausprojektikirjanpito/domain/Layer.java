package maalausprojektikirjanpito.domain;

import java.util.ArrayList;

public class Layer {
    Integer layerId;
    String layerName;
    ArrayList<SurfaceTreatment> treatments = new ArrayList<>();
    String layerNote;
    
    /**
     * Create a new layer combination object.
     * @param layerName name given to this combination
     * @param layerNote attached to this combination
     */
    public Layer(String layerName, String layerNote) {
        this.layerName = layerName;
        this.layerNote = layerNote;
    }
    
    /**
     * Create a new layer object.
     * @param layerId Unique identifier of the layer as Integer.
     * @param layerName name given to this combination
     * @param layerNote layerNote attached to this combination
     */
    public Layer(Integer layerId, String layerName, String layerNote) {
        this.layerId = layerId;
        this.layerName = layerName;
        this.layerNote = layerNote;
    }

    public Integer getLayerId() {
        return layerId;
    }

    public void setLayerId(Integer layerId) {
        this.layerId = layerId;
    }

    public String getLayerName() {
        return layerName;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }

    public String getLayerNote() {
        return layerNote;
    }

    public void setLayerNote(String layerNote) {
        this.layerNote = layerNote;
    }

    public void setLayers(ArrayList<SurfaceTreatment> treatments) {
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
