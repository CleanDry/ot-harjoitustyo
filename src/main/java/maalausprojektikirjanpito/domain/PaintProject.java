package maalausprojektikirjanpito.domain;

import java.util.ArrayList;

public class PaintProject {
    String name;
    ArrayList<SubProject> subprojects;
    
    /**
     * Create a new paint project object.
     * @param name of the project
     */
    public PaintProject(String name) {
        this.name = name;
        this.subprojects = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList getSubprojects() {
        return subprojects;
    }    
    
}
