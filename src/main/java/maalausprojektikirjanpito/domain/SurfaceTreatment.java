package maalausprojektikirjanpito.domain;

public class SurfaceTreatment {
    String name;
    String manufacturer;
    String color;
    int identifier;

    /**
     * Create a new SurfaceTreatment object.
     * @param name of the surface treatment
     * @param manufacturer of the surface treatment
     * @param color of the surface treatment
     */
    public SurfaceTreatment(String name, String manufacturer, String color) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.color = color;
    }
    
    /**
     * Create a new SurfaceTreatment object.
     * @param name of the surface treatment
     * @param manufacturer of the surface treatment
     * @param color of the surface treatment
     * @param identifier of the surface treatment
     */
    public SurfaceTreatment(String name, String manufacturer, String color, int identifier) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.color = color;
        this.identifier = identifier;
    }
    
    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getColor() {
        return color;
    }

    public int getIdentifier() {
        return identifier;
    }
}
