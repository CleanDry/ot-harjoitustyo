package maalausprojektikirjanpito.domain;

import java.util.ArrayList;

public class Subproject {
    String name;
    boolean completed;
    boolean archived;
    boolean isInTrash;
    ArrayList<Surface> surfaces;

    public Subproject(String name) {
        this.name = name;
        this.completed = false;
        this.archived = false;
        this.isInTrash = false;
        this.surfaces = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public boolean isIsInTrash() {
        return isInTrash;
    }

    public void setIsInTrash(boolean isInTrash) {
        this.isInTrash = isInTrash;
    }

    public ArrayList getSurfaces() {
        return surfaces;
    }
    
}
