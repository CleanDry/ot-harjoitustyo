package maalausprojektikirjanpito.domain;

import java.util.ArrayList;

public class PaintingProject {
    String name;
    ArrayList<Subproject> subprojects;

    public PaintingProject(String name) {
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
