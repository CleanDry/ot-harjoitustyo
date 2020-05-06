package maalausprojektikirjanpito.domain;

import java.util.ArrayList;
import java.util.Objects;

public class SubProject {
    Integer subProjectId;
    Integer projectId;
    String subProjectName;
    boolean subProjectCompleted;
    boolean subProjectInTrash;
    ArrayList<Surface> surfaces = new ArrayList<>();


    /**
     * Create a new PaintProject-object.
     * @param projectId Project_id of the project that the sub project belongs to as an Integer
     * @param subProjectName Sub project's name as a String
     */
    public SubProject(Integer projectId, String subProjectName) {
        this.projectId = projectId;
        this.subProjectName = subProjectName;
        this.subProjectCompleted = false;
        this.subProjectInTrash = false;
    }

    /**
     * Create a new PaintProject-object.
     * @param subProjectId subProject_id of the sub project as an Integer
     * @param projectId Project_id of the project that the sub project belongs to as an Integer
     * @param subProjectName Sub project's name as a String
     * @param subProjectCompleted True if sub project is marked as completed, false otherwise
     * @param subProjectInTrash True if sub project is marked as being in trash, false otherwise
     */
    public SubProject(Integer subProjectId, Integer projectId, String subProjectName, Boolean subProjectCompleted, Boolean subProjectInTrash) {
        this.subProjectId = subProjectId;
        this.projectId = projectId;
        this.subProjectName = subProjectName;
        this.subProjectCompleted = subProjectCompleted;
        this.subProjectInTrash = subProjectInTrash;
    }

    public Integer getSubProjectId() {
        return subProjectId;
    }

    public void setSubProjectId(Integer subProjectId) {
        this.subProjectId = subProjectId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getSubProjectName() {
        return subProjectName;
    }

    public void setSubProjectName(String subProjectName) {
        this.subProjectName = subProjectName;
    }

    public boolean isSubProjectCompleted() {
        return subProjectCompleted;
    }

    public void setSubProjectCompleted(boolean subProjectCompleted) {
        this.subProjectCompleted = subProjectCompleted;
    }

    public boolean isSubProjectInTrash() {
        return subProjectInTrash;
    }

    public void setSubProjectInTrash(boolean subProjectInTrash) {
        this.subProjectInTrash = subProjectInTrash;
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
            return Objects.equals(this.subProjectId, subProject.subProjectId) || this.subProjectName.equals(subProject.subProjectName);
        } else {
            return false;
        }    
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.subProjectId);
        hash = 59 * hash + Objects.hashCode(this.subProjectName);
        return hash;
    }
}
