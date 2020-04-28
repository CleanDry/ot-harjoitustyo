package maalausprojektikirjanpito.ui;

import java.util.ArrayList;
import java.util.HashSet;
import javafx.scene.control.TreeItem;
import maalausprojektikirjanpito.domain.PaintProject;


public class TreeViewHelper {
    private ArrayList<PaintProject> projects = new ArrayList<>();

    
    
    
    
    public ArrayList<TreeItem> getProjectsAsTreeItems(ArrayList<PaintProject> projects) {
        ArrayList<TreeItem> projectsAsTreeItems = new ArrayList<>();
        
        
        
        HashSet<String> categories = new HashSet<>();
        projects.stream().forEach(p -> {categories.add(p.getProjectName());});
        
        ArrayList<TreeItem> categoriesAsTreeItems = new ArrayList<>();
        categories.stream().forEach(c -> {categoriesAsTreeItems.add(new TreeItem(c));});
        
        TreeItem categoriesAsATreeItem = new TreeItem("Active Projects");
        categoriesAsATreeItem.getChildren().addAll(categoriesAsTreeItems);
        projectsAsTreeItems.add(categoriesAsATreeItem);
        
        return projectsAsTreeItems;
    }
    
    public ArrayList<PaintProject> getProjects() {
        return projects;
    }

    public void setProjects(ArrayList<PaintProject> projects) {
        this.projects = projects;
    }
}
