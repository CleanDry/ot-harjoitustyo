package maalausprojektikirjanpito.domain;

import java.util.ArrayList;
import java.util.Objects;

public class PaintProject {
    Integer projectId;
    Integer userId;
    String projectName;
    String projectCategory;
    Boolean projectCompleted;
    Boolean projectArchived;
    Boolean projectIntrash;
    ArrayList<SubProject> subprojects = new ArrayList<>();

    /**
     * Create a new PaintProject-object.
     * @param userId User_id of the user whose projects are being managed as an Integer
     * @param projectName Project's name as a String
     * @param projectCategory Project's category as a String
     */
    public PaintProject(Integer userId, String projectName, String projectCategory) {
        this.userId = userId;
        this.projectName = projectName;
        this.projectCategory = projectCategory;
        this.projectCompleted = false;
        this.projectArchived = false;
        this.projectIntrash = false;
    }

    /**
     * Create a new PaintProject-object.
     * @param projectId Project_id of the project as an Integer
     * @param userId User_id of the user whose projects are being managed as an Integer
     * @param projectName Project's name as a String
     * @param projectCategory Project's category as a String
     * @param projectCompleted Boolean
     * @param projectArchived Boolean
     * @param projectIntrash Boolean
     */
    public PaintProject(Integer projectId, Integer userId, String projectName, String projectCategory, Boolean projectCompleted, Boolean projectArchived, Boolean projectIntrash) {
        this.projectId = projectId;
        this.userId = userId;
        this.projectName = projectName;
        this.projectCategory = projectCategory;
        this.projectCompleted = projectCompleted;
        this.projectArchived = projectArchived;
        this.projectIntrash = projectIntrash;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectCategory() {
        return projectCategory;
    }

    public void setProjectCategory(String projectCategory) {
        this.projectCategory = projectCategory;
    }

    public Boolean getProjectCompleted() {
        return projectCompleted;
    }

    public void setProjectCompleted(Boolean projectCompleted) {
        this.projectCompleted = projectCompleted;
    }

    public Boolean getProjectArchived() {
        return projectArchived;
    }

    public void setProjectArchived(Boolean projectArchived) {
        this.projectArchived = projectArchived;
    }

    public Boolean getProjectIntrash() {
        return projectIntrash;
    }

    public void setProjectIntrash(Boolean projectIntrash) {
        this.projectIntrash = projectIntrash;
    }

    public ArrayList<SubProject> getSubprojects() {
        return subprojects;
    }

    public void setSubprojects(ArrayList<SubProject> subprojects) {
        this.subprojects = subprojects;
    }

    /**
     * Projects are considered equal if either their project_id or project_name match.
     * @param obj object that is compared
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj.getClass().equals(this.getClass())) {
            PaintProject project = (PaintProject) obj;
            return Objects.equals(this.projectId, project.projectId) || this.projectName.equals(project.projectName);
        } else {
            return false;
        }    
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.projectId);
        hash = 59 * hash + Objects.hashCode(this.projectName);
        return hash;
    }
    
    
}
