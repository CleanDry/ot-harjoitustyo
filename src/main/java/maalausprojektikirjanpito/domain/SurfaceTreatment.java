package maalausprojektikirjanpito.domain;

import javafx.scene.paint.Paint;

public class SurfaceTreatment {
    Integer treatmentId;
    String treatmentName;
    String treatmentType;
    String treatmentManufacturer;
    Paint treatmentColour;

    /**
     * Create a new SurfaceTreatment object.
     * @param name of the surface treatment
     * @param type of the surface treatment
     * @param manufacturer of of the surface treatment
     * @param colour of the surface treatment
     */
    public SurfaceTreatment(String name, String type, String manufacturer, Paint colour) {
        this.treatmentName = name;
        this.treatmentType = type;
        this.treatmentManufacturer = manufacturer;
        this.treatmentColour = colour;
    }
    
    /**
     * Create a new SurfaceTreatment object.
     * @param treatmentId of the surface treatment
     * @param name of the surface treatment
     * @param type of the surface treatment
     * @param manufacturer of the surface treatment
     * @param colour of the surface treatment
     */
    public SurfaceTreatment(Integer treatmentId, String name, String type, String manufacturer, Paint colour) {
        this.treatmentId = treatmentId;
        this.treatmentName = name;
        this.treatmentType = type;
        this.treatmentManufacturer = manufacturer;
        this.treatmentColour = colour;
    }

    public Integer getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(Integer treatmentId) {
        this.treatmentId = treatmentId;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }

    public String getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(String treatmentType) {
        this.treatmentType = treatmentType;
    }

    public String getTreatmentManufacturer() {
        return treatmentManufacturer;
    }

    public void setTreatmentManufacturer(String treatmentManufacturer) {
        this.treatmentManufacturer = treatmentManufacturer;
    }

    public Paint getTreatmentColour() {
        return treatmentColour;
    }

    public void setTreatmentColour(Paint treatmentColour) {
        this.treatmentColour = treatmentColour;
    }
}
