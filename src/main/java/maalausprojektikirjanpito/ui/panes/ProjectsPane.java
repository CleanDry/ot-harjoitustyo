package maalausprojektikirjanpito.ui.panes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import maalausprojektikirjanpito.domain.ManagerService;
import maalausprojektikirjanpito.domain.PaintProject;
import static maalausprojektikirjanpito.domain.Utilities.categoriesAsStrings;
import static maalausprojektikirjanpito.domain.Utilities.factionsAsStrings;
import static maalausprojektikirjanpito.domain.Utilities.stringLengthCheck;
import maalausprojektikirjanpito.ui.UserInterface;


public class ProjectsPane extends BorderPane {
    private BorderPane projectsPane;
    private ManagerService managerService;
    private SubprojectsPane subprojectsPane;
    private ProjectDetailsPane projectDetailsPane;
    
    private ComboBox<String> newProjectFactionInputBox;
    private ComboBox<String> newProjectCategoryInputBox;
    
    private TreeView projectsTree;
    
    Button goToNewProjectCreationButton;
    HBox newProjectCreationButtonBox;
    
    GridPane newProjectCreationPane;

    public ProjectsPane(ManagerService managerService, SubprojectsPane subprojectsPane, ProjectDetailsPane projectDetailsPane) {
        this.managerService = managerService;
        this.subprojectsPane = subprojectsPane;
        this.projectDetailsPane = projectDetailsPane;
        
        // Nodes for projectsPane
        projectsTree = new TreeView<>(new TreeItem());
        projectsTree.setStyle("-fx-border-color: white;");
        projectsTree.setShowRoot(false);
        goToNewProjectCreationButton = new Button("Create a new Project");
        Label newProjectNameLabel = new Label("Project name");
        TextField newProjectNameInput = new TextField();
        Label newProjectFactionLabel = new Label("Faction");
        newProjectFactionInputBox = new ComboBox();
        newProjectFactionInputBox.setEditable(true);
        newProjectFactionInputBox.setValue("");
        Label newProjectCategoryLabel = new Label("Category");
        newProjectCategoryInputBox = new ComboBox();
        newProjectCategoryInputBox.setEditable(true);
        newProjectCategoryInputBox.setValue("");
        Label createNewProjectMessage = new Label();
        createNewProjectMessage.setMinHeight(26);
        createNewProjectMessage.setMaxWidth(240);
        createNewProjectMessage.setWrapText(true);
        Button createNewProjectButton = new Button("Create a new Project");
        Button closeNewProjectCreationButton = new Button("Cancel");
        newProjectCreationButtonBox = new HBox(createNewProjectButton, closeNewProjectCreationButton);
        newProjectCreationButtonBox.setSpacing(12);
        
        newProjectCreationPane = new GridPane();
        newProjectCreationPane.add(newProjectNameLabel, 0, 0);
        newProjectCreationPane.add(newProjectNameInput, 0, 1);
        newProjectCreationPane.add(newProjectFactionLabel, 0, 2);
        newProjectCreationPane.add(newProjectFactionInputBox, 0, 3);
        newProjectCreationPane.add(newProjectCategoryLabel, 0, 4);
        newProjectCreationPane.add(newProjectCategoryInputBox, 0, 5);
        newProjectCreationPane.add(createNewProjectMessage, 0, 6);
        newProjectCreationPane.add(newProjectCreationButtonBox, 0, 7);
        newProjectCreationPane.setVgap(6);
        newProjectCreationPane.setPadding(new Insets(18, 0, 0, 0));
        GridPane.setColumnSpan(createNewProjectMessage, 2);
        GridPane.setMargin(createNewProjectMessage, new Insets(12, 0, 0, 0));
        GridPane.setMargin(newProjectCreationButtonBox, new Insets(12, 0, 0, 0));
        
        
        // projectsPane and layout
        projectsPane = new BorderPane();
        projectsPane.setCenter(projectsTree);
        projectsPane.setBottom(goToNewProjectCreationButton);
        BorderPane.setMargin(projectsTree, new Insets(0, 0, 12, 0));
        projectsPane.setPadding(new Insets(20, 20, 50, 50));
        
        
        // projectsPane button actions
        goToNewProjectCreationButton.setOnAction(event -> projectsPane.setBottom(newProjectCreationPane));
        closeNewProjectCreationButton.setOnAction(event -> {
            projectsPane.setBottom(goToNewProjectCreationButton);
            newProjectNameInput.setText("");
            newProjectFactionInputBox.setValue("");
            newProjectCategoryInputBox.setValue("");
            createNewProjectMessage.setText("");
        });
        
        createNewProjectButton.setOnAction(event -> {
            String newProjectName = newProjectNameInput.getText().trim();
            String newProjectFaction = newProjectFactionInputBox.getValue().trim();
            String newProjectCategory = newProjectCategoryInputBox.getValue().trim();
            if (!(stringLengthCheck(newProjectName, 3, 40) && stringLengthCheck(newProjectFaction, 3, 40) && stringLengthCheck(newProjectCategory, 3, 40))) {
                createNewProjectMessage.setText("Project name, faction and category must be 3-40 characters long");
                createNewProjectMessage.setTextFill(Color.RED);
            } else if (!(newProjectName.matches("[A-Za-z0-9\\s]*") && newProjectFaction.matches("[A-Za-z0-9\\s]*") && newProjectCategory.matches("[A-Za-z0-9\\s]*"))) {
                createNewProjectMessage.setText("Project name, faction and category must only contain numbers and letters");
                createNewProjectMessage.setTextFill(Color.RED);
            } else {
                try {
                    this.managerService.createPaintProject(newProjectName, newProjectFaction, newProjectCategory);
                    this.refresh();
                } catch (SQLException ex) {
                    Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
                }
                newProjectNameInput.setText("");
                newProjectFactionInputBox.setValue("");
                newProjectCategoryInputBox.setValue("");
                createNewProjectMessage.setText("");
                projectsPane.setBottom(goToNewProjectCreationButton);
            }
        });
    }

    public BorderPane getProjectsPane() {
        return projectsPane;
    }
    
    public void refresh() {
        ArrayList<PaintProject> updatedProjects = this.managerService.fetchUserProjects();
        projectsTree.setRoot(this.getTreeViewItems(updatedProjects));
        newProjectFactionInputBox.getItems().addAll(factionsAsStrings(updatedProjects));
        newProjectCategoryInputBox.getItems().addAll(categoriesAsStrings(updatedProjects));
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
        projects.forEach(p -> projectsAsNodes.add(this.projectNodeAsTreeItem(p)));
        return projectsAsNodes;
    }
    
    public TreeItem projectNodeAsTreeItem(PaintProject project) {
        Label projectName = new Label(project.getProjectName());
        HBox.setMargin(projectName, new Insets(0, 16, 0, 0));
        CheckBox completed = new CheckBox();
        if (project.getProjectCompleted()) {
            completed.arm();
        }
        completed.setStyle("-fx-font-size:10");
        Button goToProjectViewerButton = new Button(">");
        goToProjectViewerButton.setStyle("-fx-font-size:7");
        goToProjectViewerButton.setOnAction(event -> {
            this.managerService.setCurrentlyViewedProject(project);
            this.projectDetailsPane.refresh();
            this.subprojectsPane.refresh();
            
        });
        HBox projectAsNodeBox = new HBox(projectName, completed, goToProjectViewerButton);
        projectAsNodeBox.setSpacing(10);
        TreeItem projectAsTreeItem = new TreeItem(projectAsNodeBox);
        return projectAsTreeItem;
    }
}