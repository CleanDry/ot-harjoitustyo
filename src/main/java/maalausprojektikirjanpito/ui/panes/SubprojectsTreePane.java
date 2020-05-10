package maalausprojektikirjanpito.ui.panes;

import java.util.ArrayList;
import java.util.stream.Collectors;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import maalausprojektikirjanpito.domain.ManagerService;
import maalausprojektikirjanpito.domain.SubProject;
import maalausprojektikirjanpito.domain.Surface;
import static maalausprojektikirjanpito.domain.Utilities.stringLengthCheck;


public class SubprojectsTreePane extends BorderPane {
    private BorderPane subprojectsPane;
    private ManagerService service;
    private SurfacePane surfacePane;

    private TreeView subprojectsTree;
    
    VBox goToNewElementCreationBox;
    TextField newSubprojectInput;
    Label newSubprojectMessage;
    VBox createNewSubprojectBox;
    
    TextField newSurfaceNameInput;
    
    private ComboBox<String> newSurfaceSubprojectInputBox;
    Label newSurfaceMessage;
    HBox newSurfaceButtonBox;
    VBox createNewSurfaceBox;

    
    public SubprojectsTreePane(ManagerService service, SurfacePane surfacePane) {
        this.service = service;
        this.surfacePane = surfacePane;
        
        subprojectsTree = new TreeView<>(new TreeItem("Subprojects"));
        BorderPane.setMargin(subprojectsTree, new Insets(0, 0, 12, 0));
        
        Button goToCreateNewSubprojectButton = new Button("Create a new Subproject");
        Button goToCreateNewSurfaceButton = new Button("Create a new Surface");
        goToNewElementCreationBox = new VBox(goToCreateNewSubprojectButton, goToCreateNewSurfaceButton);
        goToNewElementCreationBox.setSpacing(6);
        
        Label newSubprojectLabel = new Label("Subproject name");
        newSubprojectInput = new TextField();
        newSubprojectMessage = new Label();
        newSubprojectMessage.setTextFill(Color.RED);
        newSubprojectMessage.setMaxWidth(240);
        newSubprojectMessage.setWrapText(true);
        
        Button createNewSubprojectButton = new Button("Create");
        Button closeNewSubprojectButton = new Button("Cancel");
        HBox newSubprojectButtonBox = new HBox(createNewSubprojectButton, closeNewSubprojectButton);
        newSubprojectButtonBox.setSpacing(6);
        createNewSubprojectBox = new VBox(newSubprojectLabel, newSubprojectInput, newSubprojectMessage, newSubprojectButtonBox);
        createNewSubprojectBox.setSpacing(6);
        
        Label newSurfaceLabel = new Label("Surface name");
        newSurfaceNameInput = new TextField();
        Label newSurfaceSubprojectLabel = new Label("Subproject of the surface");
        newSurfaceSubprojectInputBox = new ComboBox();
        this.newSurfaceSubprojectInputBox.setValue("");
        this.newSurfaceSubprojectInputBox.setEditable(true);
        newSurfaceMessage = new Label("");
        newSurfaceMessage.setTextFill(Color.RED);
        newSurfaceMessage.setMaxWidth(240);
        newSurfaceMessage.setWrapText(true);
        Button createNewSurfaceButton = new Button("Create");
        Button closeNewSurfaceButton = new Button("Cancel");
        newSurfaceButtonBox = new HBox(createNewSurfaceButton, closeNewSurfaceButton);
        newSurfaceButtonBox.setSpacing(6);
        
        createNewSurfaceBox = new VBox(newSurfaceLabel, newSurfaceNameInput, newSurfaceSubprojectLabel, this.newSurfaceSubprojectInputBox, newSurfaceMessage, newSurfaceButtonBox);
        createNewSurfaceBox.setSpacing(6);
        
        
        // subprojectsPane and layout
        subprojectsPane = new BorderPane();
        subprojectsPane.setCenter(subprojectsTree);
        subprojectsPane.setBottom(goToNewElementCreationBox);
        
        
        // Button actions for subprojectsPane
        goToCreateNewSubprojectButton.setOnAction(event -> {
            if (this.service.getCurrentlyViewedProject().getProjectId() != -1) {
                subprojectsPane.setBottom(createNewSubprojectBox);
            }
        });
        goToCreateNewSurfaceButton.setOnAction(event -> {
            if (this.service.getCurrentlyViewedProject().getProjectId() != -1) {
                subprojectsPane.setBottom(createNewSurfaceBox);
            }
        });
        closeNewSubprojectButton.setOnAction(event -> {
            subprojectsPane.setBottom(goToNewElementCreationBox);
            newSubprojectInput.setText("");
            newSubprojectMessage.setText("");
        });
        closeNewSurfaceButton.setOnAction(event -> {
            subprojectsPane.setBottom(goToNewElementCreationBox);
            newSurfaceNameInput.setText("");
            newSurfaceSubprojectInputBox.setValue("");
            newSurfaceMessage.setText("");
        });
        createNewSubprojectButton.setOnAction(event -> {
            String newSubprojectName = newSubprojectInput.getText().trim();
            if (!stringLengthCheck(newSubprojectName, 3, 40)) {
                newSubprojectMessage.setText("Subproject name must be 3-40 characters long");
            } else if (!newSubprojectName.matches("[A-Za-z0-9\\s]*")) {
                newSubprojectMessage.setText("Project name, faction and category must only contain numbers and letters");
            } else {
                this.service.createSubproject(this.service.getCurrentlyViewedProject().getProjectId(), newSubprojectName);
                newSubprojectInput.setText("");
                newSubprojectMessage.setText("");
                this.refresh();
                subprojectsPane.setBottom(goToNewElementCreationBox);
            }
        });
        createNewSurfaceButton.setOnAction(event -> {
            String newSurfaceName = newSurfaceNameInput.getText().trim();
            String newSubprojectName = newSurfaceSubprojectInputBox.getValue().trim();
            if (!(stringLengthCheck(newSurfaceName, 3, 40) && stringLengthCheck(newSubprojectName, 3, 40))) {
                newSurfaceMessage.setText("Surface and Subproject names must be 3-40 characters long");
            } else if (!(newSurfaceName.matches("[A-Za-z0-9\\s]*") && newSubprojectName.matches("[A-Za-z0-9\\s]*"))) {
                newSurfaceMessage.setText("Project name, faction and category must only contain numbers and letters");
            } else {
                SubProject subproject = this.service.createSubproject(this.service.getCurrentlyViewedProject().getProjectId(), newSubprojectName);
                if (subproject != null) {
                    this.service.createSurface(subproject.getSubProjectId(), newSurfaceName);
                }
                newSurfaceNameInput.setText("");
                newSurfaceSubprojectInputBox.setValue("");
                newSurfaceMessage.setText("");
                this.refresh();
                subprojectsPane.setBottom(goToNewElementCreationBox);
            }
        });
    }

    public BorderPane getSubprojectsPane() {
        return subprojectsPane;
    }
    
    public void refresh() {
        ArrayList<SubProject> updatedSubProjects = this.service.fetchSubprojects(this.service.getCurrentlyViewedProject());
        subprojectsTree.setRoot(this.getSubprojectTreeItems(updatedSubProjects));
        newSurfaceSubprojectInputBox.getItems().addAll(updatedSubProjects.stream().map(sb -> sb.getSubProjectName()).collect(Collectors.toList()));
    
    }
    
    public TreeItem getSubprojectTreeItems(ArrayList<SubProject> subproject) {
        TreeItem newRootItem = new TreeItem("Subprojects");
        newRootItem.getChildren().addAll(this.subprojectsAsTreeItems(subproject));
        newRootItem.setExpanded(true);
        return newRootItem;
    }
    
    public ArrayList<TreeItem> subprojectsAsTreeItems(ArrayList<SubProject> subprojects) {
        ArrayList<TreeItem> treeItems = new ArrayList<>();
        subprojects.stream().forEach(sb -> {
            TreeItem subprojectTreeItem = new TreeItem(sb.getSubProjectName());
            sb.getSurfaces().stream().forEach(s -> subprojectTreeItem.getChildren().add(surfaceNodeAsTreeItem(s)));
            subprojectTreeItem.setExpanded(true);
            treeItems.add(subprojectTreeItem);
        });
        return treeItems;
    }
    
    public TreeItem surfaceNodeAsTreeItem(Surface surface) {
        Label surfaceName = new Label(surface.getSurfaceName());
        HBox.setMargin(surfaceName, new Insets(0, 16, 0, 0));
        Button goToSurfaceViewerButton = new Button(">");
        goToSurfaceViewerButton.setStyle("-fx-font-size:7");
        goToSurfaceViewerButton.setOnAction(event -> {
            this.service.setCurrentlyViewedSurface(surface);
            this.surfacePane.refresh();
        });
        HBox surfaceAsNodeBox = new HBox(surfaceName, goToSurfaceViewerButton);
        surfaceAsNodeBox.setSpacing(10);
        TreeItem surfaceAsTreeItem = new TreeItem(surfaceAsNodeBox);
        return surfaceAsTreeItem;
    }
}
