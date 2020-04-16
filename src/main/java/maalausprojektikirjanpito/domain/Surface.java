package maalausprojektikirjanpito.domain;

import java.util.ArrayList;

public class Surface {
    Integer surface_id;
    Integer subproject_id;
    String surface_name;
    Boolean inTrash;
    ArrayList<TreatmentCombination> treamentCombinations = new ArrayList<>();

    /**
     * Create a new surface object.
     * @param subproject_id of the surface as Integer
     * @param surface_name of the surface as String
     */
    public Surface(Integer subproject_id, String surface_name) {
        this.subproject_id = subproject_id;
        this.surface_name = surface_name;
        this.inTrash = false;
    }
    
    /**
     * Create a new surface object with surface_id.
     * @param surface_id of the surface.
     * @param subproject_id of the surface.
     * @param surface_name of the surface.
     */
    public Surface(Integer surface_id, Integer subproject_id, String surface_name, boolean inTrash) {
        this.surface_id = surface_id;
        this.subproject_id = subproject_id;
        this.surface_name = surface_name;
        this.inTrash = inTrash;
    }

    public Integer getSurface_id() {
        return surface_id;
    }

    public void setSurface_id(Integer surface_id) {
        this.surface_id = surface_id;
    }

    public Integer getSubproject_id() {
        return subproject_id;
    }

    public void setSubproject_id(Integer subproject_id) {
        this.subproject_id = subproject_id;
    }

    public String getSurface_name() {
        return surface_name;
    }

    public void setSurface_name(String surface_name) {
        this.surface_name = surface_name;
    }

    public Boolean getInTrash() {
        return inTrash;
    }

    public void setInTrash(Boolean inTrash) {
        this.inTrash = inTrash;
    }

    public ArrayList<TreatmentCombination> getTreamentCombinations() {
        return treamentCombinations;
    }

    public void setTreamentCombinations(ArrayList<TreatmentCombination> treamentCombinations) {
        this.treamentCombinations = treamentCombinations;
    }
    
    
    
    
    
}
