package maalausprojektikirjanpito.ui.panes;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import maalausprojektikirjanpito.domain.ManagerService;
import maalausprojektikirjanpito.domain.PaintProject;
import static maalausprojektikirjanpito.domain.Utilities.categoriesAsStrings;
import static maalausprojektikirjanpito.domain.Utilities.factionsAsStrings;
import static maalausprojektikirjanpito.domain.Utilities.stringLengthCheck;


public class ProjectDetailsPane extends GridPane {
    public GridPane projectDetailsPane;
    private ManagerService service;
    private PaintProject currentlyViewedProject;
    
    // Nodes for projectNameDetailsBox
    private Label viewProjectHeaderLabel;
    private Label viewProjectFactionLabel;
    private Label viewProjectCategoryLabel;
    // Nodes for projectBooleanDetailsBox
    private Label viewProjectArchivedLabel;
    private Label viewProjectCompeletedLabel;
    private Label viewProjectInTrashLabel;
    // Nodes for editProjectDetailsBox
    Button goToEditProjectDetailsButton;
    private ComboBox<String> editProjectFactionInputBox;
    private ComboBox<String> editProjectCategoryInputBox;
    GridPane editProjectDetailsPane;

    public ProjectDetailsPane (ManagerService service) {
        this.service = service;
        this.currentlyViewedProject = this.service.getCurrentlyViewedProject();
        
        // Nodes for projectNameDetailsBox
        viewProjectHeaderLabel = new Label("Projects: No project selected");
        viewProjectHeaderLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        viewProjectFactionLabel = new Label("Faction");
        viewProjectFactionLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        viewProjectCategoryLabel = new Label("Category");
        viewProjectCategoryLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        VBox projectNameDetailsBox = new VBox(viewProjectHeaderLabel, viewProjectFactionLabel, viewProjectCategoryLabel);
        projectNameDetailsBox.setSpacing(6);
        
        // Nodes for projectBooleanDetailsBox
        viewProjectArchivedLabel = new Label("Archived: -");
        viewProjectCompeletedLabel = new Label("Completed: -");
        viewProjectInTrashLabel = new Label("In Trash: -");
        VBox projectBooleanDetailsBox = new VBox(this.viewProjectArchivedLabel, this.viewProjectCompeletedLabel, this.viewProjectInTrashLabel);
        projectBooleanDetailsBox.setSpacing(8);
        
        // Nodes for goToEditProjectDetailsButton
        goToEditProjectDetailsButton = new Button("Edit");
        goToEditProjectDetailsButton.setStyle("-fx-font-size:10");
        GridPane.setValignment(goToEditProjectDetailsButton, VPos.TOP);
        
        // Nodes for editProjectDetailsPane
        Label editProjectNameLabel = new Label("Project Name");
        TextField editedProjectNameInput = new TextField(this.currentlyViewedProject.getProjectName());
        editedProjectNameInput.setStyle("-fx-font-size:10");
        Label editProjectFactionLabel = new Label("Faction");
        editProjectFactionInputBox = new ComboBox();
        this.editProjectFactionInputBox.setValue(this.currentlyViewedProject.getProjectFaction());
        this.editProjectFactionInputBox.setEditable(true);
        this.editProjectFactionInputBox.setStyle("-fx-font-size:10");
        Label editProjectCategoryLabel = new Label("Category");
        editProjectCategoryInputBox = new ComboBox();
        this.editProjectCategoryInputBox.setValue(this.currentlyViewedProject.getProjectCategory());
        this.editProjectCategoryInputBox.setEditable(true);
        this.editProjectCategoryInputBox.setStyle("-fx-font-size:10");
        // Nodes for editProjectButtonsBox
        Button saveEditProjectButton = new Button("Save");
        saveEditProjectButton.setStyle("-fx-font-size:10");
        Button cancelProjectEditButton = new Button("Cancel");
        cancelProjectEditButton.setStyle("-fx-font-size:10");
        HBox editProjectButtonsBox = new HBox(saveEditProjectButton, cancelProjectEditButton);
        editProjectButtonsBox.setAlignment(Pos.BASELINE_RIGHT);
        editProjectButtonsBox.setSpacing(6);
        // editProjectMessageLabel
        Label editProjectMessageLabel = new Label("");
        editProjectMessageLabel.setTextFill(Color.RED);
        editProjectMessageLabel.setMaxWidth(240);
        editProjectMessageLabel.setWrapText(true);
        editProjectMessageLabel.setStyle("-fx-font-size:10");
        // editProjectDetailsPane and layout
        editProjectDetailsPane = new GridPane();
        GridPane.setRowSpan(editProjectMessageLabel, 3);
        editProjectDetailsPane.add(editProjectNameLabel, 0, 0);
        editProjectDetailsPane.add(editedProjectNameInput, 1, 0);
        editProjectDetailsPane.add(editProjectButtonsBox, 2, 0);
        editProjectDetailsPane.add(editProjectFactionLabel, 0, 1);
        editProjectDetailsPane.add(this.editProjectFactionInputBox, 1, 1);
        editProjectDetailsPane.add(editProjectMessageLabel, 2, 1);
        editProjectDetailsPane.add(editProjectCategoryLabel, 0, 2);
        editProjectDetailsPane.add(this.editProjectCategoryInputBox, 1, 2);
        editProjectDetailsPane.setHgap(9);
        editProjectDetailsPane.setVgap(3);
        
        
        // projectDetailsPane and layout
        projectDetailsPane = new GridPane();
        projectDetailsPane.setMinHeight(100);
        projectDetailsPane.add(projectNameDetailsBox, 0, 0);
        projectDetailsPane.add(projectBooleanDetailsBox, 1, 0);
        projectDetailsPane.add(goToEditProjectDetailsButton, 2, 0);
        projectDetailsPane.setHgap(42);
        GridPane.setMargin(projectBooleanDetailsBox, new Insets(3,0,0,0));
        
        
        // Button actions for projectDetailsPane
        goToEditProjectDetailsButton.setOnAction(event -> {
            if (this.currentlyViewedProject.getProjectId() != -1) {
                projectDetailsPane.getChildren().remove(goToEditProjectDetailsButton);
                editedProjectNameInput.setText(currentlyViewedProject.getProjectName());
                this.editProjectFactionInputBox.setValue(currentlyViewedProject.getProjectFaction());
                this.editProjectCategoryInputBox.setValue(currentlyViewedProject.getProjectCategory());
                projectDetailsPane.add(editProjectDetailsPane, 2, 0);
            }
        });
        
        // Button actions for editProjectDetailsBox
        saveEditProjectButton.setOnAction(event -> {
            String editedProjectName = editedProjectNameInput.getText().trim();
            String editedProjectFaction = this.editProjectFactionInputBox.getValue().trim();
            String editedProjectCategory = this.editProjectCategoryInputBox.getValue().trim();
            Boolean successfulEdit = true;
            if (!this.currentlyViewedProject.getProjectName().equals(editedProjectName)) {
                if (!(stringLengthCheck(editedProjectName, 3, 40) && editedProjectName.matches("[A-Za-z0-9\\s]*"))) {
                    editProjectMessageLabel.setText("Project name, faction and category must be 3-40 characters long and must only contain numbers and letters");
                    successfulEdit = false;
                } else {
                    this.currentlyViewedProject.setProjectName(editedProjectName);
                }
            }
            if (!this.currentlyViewedProject.getProjectFaction().equals(editedProjectFaction)) {
                if (!(stringLengthCheck(editedProjectFaction, 3, 40) && editedProjectFaction.matches("[A-Za-z0-9\\s]*"))) {
                    editProjectMessageLabel.setText("Project name, faction and category must be 3-40 characters long and must only contain numbers and letters");
                    successfulEdit = false;
                } else {
                    this.currentlyViewedProject.setProjectFaction(editedProjectFaction);
                }
            }
            if (!this.currentlyViewedProject.getProjectCategory().equals(editedProjectCategory)) {
                if (!(stringLengthCheck(editedProjectCategory, 3, 40) && editedProjectCategory.matches("[A-Za-z0-9\\s]*"))) {
                    editProjectMessageLabel.setText("Project name, faction and category must be 3-40 characters long and must only contain numbers and letters");
                    successfulEdit = false;
                } else {
                    this.currentlyViewedProject.setProjectCategory(editedProjectCategory);
                }
            }
            if (successfulEdit) {
                this.service.updateProject(this.currentlyViewedProject);
                editedProjectNameInput.setText("");
                editProjectMessageLabel.setText("");
                this.editProjectFactionInputBox.setValue("");
                this.editProjectCategoryInputBox.setValue("");
                projectDetailsPane.getChildren().remove(editProjectDetailsPane);
                projectDetailsPane.add(goToEditProjectDetailsButton, 2, 0);
//                this.service.redrawProjectsTree();
                this.refresh();
            }
        });
        
        cancelProjectEditButton.setOnAction(event -> {
            editedProjectNameInput.setText("");
            editProjectMessageLabel.setText("");
            this.editProjectFactionInputBox.setValue("");
            this.editProjectCategoryInputBox.setValue("");
            this.projectDetailsPane.getChildren().remove(editProjectDetailsPane);
            this.projectDetailsPane.add(goToEditProjectDetailsButton, 2, 0);
        });
    }

    public GridPane getProjectDetailsPane() {
        return this.projectDetailsPane;
    }
    
    
    public void refresh() {
        this.currentlyViewedProject = this.service.getCurrentlyViewedProject();
        ArrayList<PaintProject> updatedProjects = this.service.fetchUserProjects();
        this.editProjectFactionInputBox.getItems().setAll(factionsAsStrings(updatedProjects));
        this.editProjectCategoryInputBox.getItems().setAll(categoriesAsStrings(updatedProjects));
        this.viewProjectHeaderLabel.setText("Project: " + this.currentlyViewedProject.getProjectName());
        this.viewProjectFactionLabel.setText(this.currentlyViewedProject.getProjectFaction());
        this.viewProjectCategoryLabel.setText(this.currentlyViewedProject.getProjectCategory());
        this.viewProjectArchivedLabel.setText("Archived: " + this.currentlyViewedProject.getProjectArchived().toString());
        this.viewProjectCompeletedLabel.setText("Completed: " + this.currentlyViewedProject.getProjectCompleted().toString());
        this.viewProjectInTrashLabel.setText("In Trash: " + this.currentlyViewedProject.getProjectIntrash().toString());
        this.projectDetailsPane.getChildren().remove(editProjectDetailsPane);
        this.projectDetailsPane.getChildren().remove(goToEditProjectDetailsButton);
        this.projectDetailsPane.add(goToEditProjectDetailsButton, 2, 0);
    }
}

        
        
        
        
        