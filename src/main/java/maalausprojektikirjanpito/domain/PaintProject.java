package maalausprojektikirjanpito.domain;

import java.util.ArrayList;
import java.util.Objects;

public class PaintProject {
    Integer project_id;
    Integer user_id;
    String project_name;
    String project_category;
    Boolean project_completed;
    Boolean project_archived;
    Boolean project_intrash;
    ArrayList<SubProject> subprojects = new ArrayList<>();

    /**
     * Create a new PaintProject-object.
     * @param user_id User_id of the user whose projects are being managed as an Integer
     * @param project_name Project's name as a String
     * @param project_category Project's category as a String
     */
    public PaintProject(Integer user_id, String project_name, String project_category) {
        this.user_id = user_id;
        this.project_name = project_name;
        this.project_category = project_category;
        this.project_completed = false;
        this.project_archived = false;
        this.project_intrash = false;
    }

    /**
     * Create a new PaintProject-object.
     * @param project_id Project_id of the project as an Integer
     * @param user_id User_id of the user whose projects are being managed as an Integer
     * @param project_name Project's name as a String
     * @param project_category Project's category as a String
     */
   public PaintProject(Integer project_id, Integer user_id, String project_name, String project_category, Boolean project_completed, Boolean project_archived, Boolean project_intrash) {
        this.project_id = project_id;
        this.user_id = user_id;
        this.project_name = project_name;
        this.project_category = project_category;
        this.project_completed = project_completed;
        this.project_archived = project_archived;
        this.project_intrash = project_intrash;
    }

    public Integer getProject_id() {
        return project_id;
    }

    public void setProject_id(Integer project_id) {
        this.project_id = project_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getProject_category() {
        return project_category;
    }

    public void setProject_category(String project_category) {
        this.project_category = project_category;
    }

    public Boolean getProject_completed() {
        return project_completed;
    }

    public void setProject_completed(Boolean project_completed) {
        this.project_completed = project_completed;
    }

    public Boolean getProject_archived() {
        return project_archived;
    }

    public void setProject_archived(Boolean project_archived) {
        this.project_archived = project_archived;
    }

    public Boolean getProject_intrash() {
        return project_intrash;
    }

    public void setProject_intrash(Boolean project_intrash) {
        this.project_intrash = project_intrash;
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
            return Objects.equals(this.project_id, project.project_id) || this.project_name.equals(project.project_name);
        } else {
            return false;
        }    
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.project_id);
        hash = 59 * hash + Objects.hashCode(this.project_name);
        return hash;
    }
    
    
}
