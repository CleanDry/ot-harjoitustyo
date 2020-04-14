package maalausprojektikirjanpito.domain;

import java.util.ArrayList;
import java.util.Objects;

public class SubProject {
    Integer subProject_id;
    Integer project_id;
    String subProject_name;
    boolean subProject_completed;
    boolean subProject_isInTrash;
    ArrayList<Surface> surfaces = new ArrayList<>();


    /**
     * Create a new PaintProject-object.
     * @param project_id Project_id of the project that the sub project belongs to as an Integer
     * @param subProject_name Sub project's name as a String
     */
    public SubProject(Integer project_id, String subProject_name) {
        this.project_id = project_id;
        this.subProject_name = subProject_name;
        this.subProject_completed = false;
        this.subProject_isInTrash = false;
    }

    /**
     * Create a new PaintProject-object.
     * @param subProject_id subProject_id of the sub project as an Integer
     * @param project_id Project_id of the project that the sub project belongs to as an Integer
     * @param subProject_name Sub project's name as a String
     * @param subProject_completed True if sub project is marked as completed, false otherwise
     * @param subProject_isInTrash True if sub project is marked as being in trash, false otherwise
     */
   public SubProject(Integer subProject_id, Integer project_id, String subProject_name, Boolean subProject_completed, Boolean subProject_isInTrash) {
        this.subProject_id = subProject_id;
        this.project_id = project_id;
        this.subProject_name = subProject_name;
        this.subProject_completed = subProject_completed;
        this.subProject_isInTrash = subProject_isInTrash;
    }

    public Integer getSubProject_id() {
        return subProject_id;
    }

    public void setSubProject_id(Integer subProject_id) {
        this.subProject_id = subProject_id;
    }

    public Integer getProject_id() {
        return project_id;
    }

    public void setProject_id(Integer project_id) {
        this.project_id = project_id;
    }

    public String getSubProject_name() {
        return subProject_name;
    }

    public void setSubProject_name(String subProject_name) {
        this.subProject_name = subProject_name;
    }

    public boolean isSubProject_completed() {
        return subProject_completed;
    }

    public void setSubProject_completed(boolean subProject_completed) {
        this.subProject_completed = subProject_completed;
    }

    public boolean isSubProject_isInTrash() {
        return subProject_isInTrash;
    }

    public void setSubProject_isInTrash(boolean subProject_isInTrash) {
        this.subProject_isInTrash = subProject_isInTrash;
    }

    public ArrayList<Surface> getSurfaces() {
        return surfaces;
    }

    public void setSurfaces(ArrayList<Surface> surfaces) {
        this.surfaces = surfaces;
    }
   
    /**
     * Sub projects are considered equal if either their subProject_id or subProject_name match.
     * @param obj object that is compared
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj.getClass().equals(this.getClass())) {
            SubProject subProject = (SubProject) obj;
            return Objects.equals(this.subProject_id, subProject.project_id) || this.subProject_name.equals(subProject.subProject_name);
        } else {
            return false;
        }    
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.subProject_id);
        hash = 59 * hash + Objects.hashCode(this.subProject_name);
        return hash;
    }
}
