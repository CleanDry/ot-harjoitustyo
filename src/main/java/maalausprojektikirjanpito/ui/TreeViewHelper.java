package maalausprojektikirjanpito.ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;
import javafx.scene.control.TreeItem;
import maalausprojektikirjanpito.domain.Layer;
import maalausprojektikirjanpito.domain.PaintProject;
import maalausprojektikirjanpito.domain.SubProject;
import static maalausprojektikirjanpito.domain.Utilities.categoriesAsStrings;
import static maalausprojektikirjanpito.domain.Utilities.factionsAsStrings;


public class TreeViewHelper {
    private UserInterface UI;

    public TreeViewHelper(UserInterface UI) {
        this.UI = UI;
    }
    
    public TreeItem getTreeViewItems(ArrayList<PaintProject> projects) {
        TreeItem newRootItem = new TreeItem("All Projects");
        TreeItem activeProjectsRoot = new TreeItem<>("Active Projects");
        activeProjectsRoot.getChildren().addAll(activeProjectsAsTreeItems(projects));
        activeProjectsRoot.setExpanded(true);
        TreeItem archivedProjectsRoot = new TreeItem<>("Archived Projects");
        archivedProjectsRoot.getChildren().addAll(archivedProjectsAsTreeItems(projects));
        newRootItem.getChildren().addAll(activeProjectsRoot, archivedProjectsRoot);
        return newRootItem;
    }
    
    public ArrayList<TreeItem> activeProjectsAsTreeItems(ArrayList<PaintProject> projects) {
        ArrayList<PaintProject> activeProjects = (ArrayList<PaintProject>) projects.stream().filter(p -> !p.getProjectArchived() && !p.getProjectIntrash()).collect(Collectors.toList());
        return categoriesAsTreeItems(activeProjects);
    }
    
    public ArrayList<TreeItem> archivedProjectsAsTreeItems(ArrayList<PaintProject> projects) {
        ArrayList<PaintProject> archivedProjects = (ArrayList<PaintProject>) projects.stream().filter(p -> p.getProjectArchived() && !p.getProjectIntrash()).collect(Collectors.toList());
        return categoriesAsTreeItems(archivedProjects);
    }
    
    public ArrayList<TreeItem> categoriesAsTreeItems(ArrayList<PaintProject> projects) {
        ArrayList<TreeItem> categoryTreeItems = new ArrayList<>();
        HashSet<String> categories = categoriesAsStrings(projects);
        for (String category : categories) {
            TreeItem categoryItem = new TreeItem(category);
            ArrayList<PaintProject> projectsOfCategory = (ArrayList<PaintProject>) projects.stream().filter(p -> p.getProjectCategory().equals(category)).collect(Collectors.toList());
            categoryItem.getChildren().addAll(factionsAsTreeItem(projectsOfCategory));
            categoryItem.setExpanded(true);
            categoryTreeItems.add(categoryItem);
        }
        return categoryTreeItems;
    }
    
    public ArrayList<TreeItem> factionsAsTreeItem(ArrayList<PaintProject> projects) {
        ArrayList<TreeItem> factionTreeItems = new ArrayList<>();
        HashSet<String> factions = factionsAsStrings(projects);
        for (String faction : factions) {
            TreeItem factionItem = new TreeItem(faction);
            ArrayList<PaintProject> projectsOfFaction = (ArrayList<PaintProject>) projects.stream().filter(p -> p.getProjectFaction().equals(faction)).collect(Collectors.toList());
            factionItem.getChildren().addAll(projectsAsNodes(projectsOfFaction));
            factionItem.setExpanded(true);
            factionTreeItems.add(factionItem);
        }
        return factionTreeItems;
    }
    
    public ArrayList<TreeItem> projectsAsNodes(ArrayList<PaintProject> projects) {
        ArrayList<TreeItem> projectsAsNodes = new ArrayList<>();
        projects.forEach(p -> projectsAsNodes.add(this.UI.projectNodeAsTreeItem(p)));
        return projectsAsNodes;
    }
    

    public UserInterface getUI() {
        return UI;
    }

    public void setUI(UserInterface UI) {
        this.UI = UI;
    }
}
