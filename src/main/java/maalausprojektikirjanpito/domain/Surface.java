package maalausprojektikirjanpito.domain;

import java.util.ArrayList;

public class Surface {
    Integer surfaceId;
    Integer subprojectId;
    String surfaceName;
    Boolean isInTrash;
    ArrayList<TreatmentCombination> treamentCombinations = new ArrayList<>();

    /**
     * Create a new surface object.
     * @param subprojectId of the surface as Integer
     * @param surfaceName of the surface as String
     */
    public Surface(Integer subprojectId, String surfaceName) {
        this.subprojectId = subprojectId;
        this.surfaceName = surfaceName;
        this.isInTrash = false;
    }
    
    /**
     * Create a new surface object with surface_id.
     * @param surfaceId of the surface.
     * @param subprojectId of the surface.
     * @param surfaceName of the surface.
     * @param isInTrash Boolean.
     */
    public Surface(Integer surfaceId, Integer subprojectId, String surfaceName, boolean isInTrash) {
        this.surfaceId = surfaceId;
        this.subprojectId = subprojectId;
        this.surfaceName = surfaceName;
        this.isInTrash = isInTrash;
    }

    public Integer getSurfaceId() {
        return surfaceId;
    }

    public void setSurfaceId(Integer surfaceId) {
        this.surfaceId = surfaceId;
    }

    public Integer getSubprojectId() {
        return subprojectId;
    }

    public void setSubprojectId(Integer subprojectId) {
        this.subprojectId = subprojectId;
    }

    public String getSurfaceName() {
        return surfaceName;
    }

    public void setSurfaceName(String surfaceName) {
        this.surfaceName = surfaceName;
    }

    public Boolean getIsInTrash() {
        return isInTrash;
    }

    public void setIsInTrash(Boolean inTrash) {
        this.isInTrash = inTrash;
    }

    public ArrayList<TreatmentCombination> getTreamentCombinations() {
        return treamentCombinations;
    }

    public void setTreamentCombinations(ArrayList<TreatmentCombination> treamentCombinations) {
        this.treamentCombinations = treamentCombinations;
    }

    
    
    
    
}
