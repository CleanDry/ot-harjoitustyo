package maalausprojektikirjanpito.domain;

import java.util.ArrayList;

public class ProjectCategory {
    String name;
    ArrayList<PaintingProject> projects;

    public ProjectCategory(String name) {
        this.name = name;
        this.projects = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList getProjects() {
        return projects;
    }
    
}
