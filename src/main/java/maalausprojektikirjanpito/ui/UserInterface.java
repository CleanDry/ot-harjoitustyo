package maalausprojektikirjanpito.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import maalausprojektikirjanpito.domain.ManagerService;
import maalausprojektikirjanpito.domain.PaintProject;
import maalausprojektikirjanpito.domain.SubProject;
import maalausprojektikirjanpito.domain.Surface;
import maalausprojektikirjanpito.domain.Layer;
import maalausprojektikirjanpito.domain.SurfaceTreatment;
import static maalausprojektikirjanpito.domain.Utilities.categoriesAsStrings;
import static maalausprojektikirjanpito.domain.Utilities.factionsAsStrings;
import static maalausprojektikirjanpito.domain.Utilities.stringLengthCheck;
import maalausprojektikirjanpito.ui.panes.ProjectDetailsPane;
import maalausprojektikirjanpito.ui.panes.SurfacePane;

public class UserInterface extends Application {
    private ManagerService managerService;
    private TreeViewHelper treeViewHelper;
    
    private Label loggedInAsLabel;
    private ComboBox<String> newProjectFactionInputBox;
    private ComboBox<String> newProjectCategoryInputBox;
    private ComboBox<String> newSurfaceSubprojectInputBox;
    
    private TreeView projectsTree;
    private TreeView subprojectsTree;
//    private TreeView layersTree;
    private Scene loginScene;
    private Scene mainScene;
    
    SurfacePane surfacePane;
    ProjectDetailsPane projectDetailsPane;
    

    @Override
    public void init() throws Exception {
//        Properties properties = new Properties();
//
//        properties.load(new FileInputStream("config.properties"));
//        
//        String userFile = properties.getProperty("userFile");
//        String todoFile = properties.getProperty("todoFile");
//            
//        FileUserDao userDao = new FileUserDao(userFile);
//        FileTodoDao todoDao = new FileTodoDao(todoFile, userDao);
        managerService = new ManagerService("PaintProjectManager.db");
        managerService.init();
        treeViewHelper = new TreeViewHelper(this);
        
        
        loggedInAsLabel = new Label();
        newProjectFactionInputBox = new ComboBox();
        newProjectCategoryInputBox = new ComboBox();
        newSurfaceSubprojectInputBox = new ComboBox();
        projectsTree = new TreeView<>(new TreeItem());
        subprojectsTree = new TreeView<>(new TreeItem("Subprojects"));
        
        surfacePane = new SurfacePane(this.managerService);
        projectDetailsPane = new ProjectDetailsPane(this.managerService);
    }
    
    public void redrawProjectsTree() {
        ArrayList<PaintProject> updatedProjects = this.managerService.fetchUserProjects();
        projectsTree.setRoot(this.treeViewHelper.getTreeViewItems(updatedProjects));
        newProjectFactionInputBox.getItems().addAll(factionsAsStrings(updatedProjects));
        newProjectCategoryInputBox.getItems().addAll(categoriesAsStrings(updatedProjects));
    }
    
    
    public void redrawSubprojectsTree() {
        ArrayList<SubProject> updatedSubProjects = this.managerService.fetchSubprojects(this.managerService.getCurrentlyViewedProject());
        subprojectsTree.setRoot(this.treeViewHelper.getSubprojectTreeItems(updatedSubProjects));
        newSurfaceSubprojectInputBox.getItems().addAll(updatedSubProjects.stream().map(sb -> sb.getSubProjectName()).collect(Collectors.toList()));
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
            this.redrawSubprojectsTree();
            
        });
        HBox projectAsNodeBox = new HBox(projectName, completed, goToProjectViewerButton);
        projectAsNodeBox.setSpacing(10);
        TreeItem projectAsTreeItem = new TreeItem(projectAsNodeBox);
        return projectAsTreeItem;
    }
    
    public TreeItem surfaceNodeAsTreeItem(Surface surface) {
        Label surfaceName = new Label(surface.getSurfaceName());
        HBox.setMargin(surfaceName, new Insets(0, 16, 0, 0));
        Button goToSurfaceViewerButton = new Button(">");
        goToSurfaceViewerButton.setStyle("-fx-font-size:7");
        goToSurfaceViewerButton.setOnAction(event -> {
            this.managerService.setCurrentlyViewedSurface(surface);
            this.surfacePane.refresh();
        });
        HBox surfaceAsNodeBox = new HBox(surfaceName, goToSurfaceViewerButton);
        surfaceAsNodeBox.setSpacing(10);
        TreeItem surfaceAsTreeItem = new TreeItem(surfaceAsNodeBox);
        return surfaceAsTreeItem;
    }
    
    public TreeItem layerNodeAsTreeItem(Layer layer) {
        TreeItem treeItemToReturn = new TreeItem();
        HBox treeItemHeaderBox = new HBox();
        treeItemHeaderBox.getChildren().add(new Label(layer.getLayerName()));
        for (SurfaceTreatment st : layer.getTreatments()) {
            treeItemHeaderBox.getChildren().add(new Circle(6, st.getTreatmentColour()));
            treeItemToReturn.getChildren().add(new TreeItem(this.managerService.surfaceTreatmentAsNode(st)));
        }
        Label layerNote = new Label(layer.getLayerNote());
        layerNote.setMaxWidth(240);
        layerNote.setWrapText(true);
        treeItemHeaderBox.getChildren().add(layerNote);
        treeItemToReturn.setValue(treeItemHeaderBox);
        treeItemToReturn.setExpanded(true);
        return treeItemToReturn;
    }
    
    
    @Override
    public void start(Stage primaryStage) throws Exception { 
        
        // Login scene
        Label loginHeader = new Label("Welcome to the Paint Project Manager");
        loginHeader.setFont(Font.font("Verdana", FontWeight.BOLD, 16));

        
        // Individual nodes for loginInputBox
        Label usernameLabel = new Label("Username");
        TextField usernameInput = new TextField();
        Label passwordLabel = new Label("Password");
        PasswordField passwordInput = new PasswordField();
        Button loginButton = new Button("Log in");
        Label loginMessage = new Label("");
        loginMessage.setMinHeight(26);
        Label goToNewUserLabel = new Label("or");
        goToNewUserLabel.setMinHeight(26);
        Button goToNewUserButton = new Button("Create a new user");
        
        // Layout elements for loginInputBox
        HBox loginButtonBox = new HBox(10);
        loginButtonBox.getChildren().addAll(loginButton, loginMessage);
        loginButtonBox.setSpacing(15);
        HBox goToNewUserBox = new HBox(10);
        goToNewUserBox.getChildren().addAll(goToNewUserLabel, goToNewUserButton);
        goToNewUserBox.setSpacing(10);
        VBox.setMargin(passwordLabel, new Insets(6, 0, 0, 0));
        VBox.setMargin(loginButtonBox, new Insets(6, 0, 0, 0));
        VBox.setMargin(goToNewUserBox, new Insets(24, 0, 0, 0));
        VBox loginInputBox = new VBox(10);
        loginInputBox.getChildren().addAll(usernameLabel, usernameInput, passwordLabel, passwordInput, loginButtonBox, goToNewUserBox);
        loginInputBox.setPadding(new Insets(0, 20, 0, 20));
        loginInputBox.setSpacing(6);
        
        
        // Individual nodes for newUserInputPane
        Label userCreationHeader = new Label("Create a new User:");
        userCreationHeader.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        Label newUsernameLabel = new Label("Enter a username:");
        TextField newUsernameInput = new TextField();
        Label newUsernameMessage = new Label();
        Label newPasswordLabel = new Label("Enter a password:");
        PasswordField newPasswordInput = new PasswordField();
        Label newPasswordInputMessage = new Label();
        Label verifyNewPasswordLabel = new Label("Please enter the same password again:");
        PasswordField verifyNewPasswordInput = new PasswordField();
        Label verifyNewPasswordMessage = new Label();
        Button createNewUserButton = new Button("Create");
        
        // Layout elements for newUserInputPane
        GridPane newUserInputPane = new GridPane();
        newUserInputPane.add(userCreationHeader, 0, 0);
        GridPane.setMargin(newUsernameLabel, new Insets(12, 0, 0, 0));
        newUserInputPane.add(newUsernameLabel, 0, 1);
        newUserInputPane.add(newUsernameInput, 0, 2);
        newUserInputPane.add(newUsernameMessage, 1, 2);
        GridPane.setMargin(newPasswordLabel, new Insets(6, 0, 0, 0));
        newUserInputPane.add(newPasswordLabel, 0, 3);
        newUserInputPane.add(newPasswordInput, 0, 4);
        newUserInputPane.add(newPasswordInputMessage, 1, 4);
        GridPane.setMargin(verifyNewPasswordLabel, new Insets(6, 0, 0, 0));
        newUserInputPane.add(verifyNewPasswordLabel, 0, 5);
        newUserInputPane.add(verifyNewPasswordInput, 0, 6);
        newUserInputPane.add(verifyNewPasswordMessage, 1, 6);
        newUserInputPane.add(createNewUserButton, 0, 7);
        GridPane.setMargin(createNewUserButton, new Insets(18, 0, 0, 0));
        
        newUserInputPane.setPadding(new Insets(0, 20, 0, 20));
        newUserInputPane.setHgap(10);
        newUserInputPane.setVgap(6);
        
        
        // loginPane configuration
        GridPane loginPane = new GridPane();
        loginPane.add(loginHeader, 0, 0);
        loginPane.add(loginInputBox, 0, 1);
        
        loginPane.setPrefSize(1200, 700);
        loginPane.setAlignment(Pos.CENTER);
        loginPane.setVgap(30);
        loginPane.setHgap(10);
        
        // loginPane button actions
        // Log on if credentials match, display loginMessage otherwise
        loginButton.setOnAction(event -> {
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            if (managerService.login(username, password) ){
                loginMessage.setText("");
                this.redrawProjectsTree();
                this.redrawSubprojectsTree();
                loggedInAsLabel.setText("Logged in as: " + username);
                primaryStage.setScene(mainScene);  
                usernameInput.setText("");
                passwordInput.setText("");
            } else {
                loginMessage.setText("Sorry! Username or password incorrect");
                loginMessage.setTextFill(Color.RED);
            }      
        });
        
        // Display newUserInputPane on event
        goToNewUserButton.setOnAction(event -> {
            loginMessage.setText("");
            usernameInput.setText("");
            passwordInput.setText("");
            loginPane.getChildren().remove(loginInputBox);
            loginPane.add(newUserInputPane, 0, 1);
        });
        
        // Verify new credentials, create a new user and log in if successful. Display messages otherwise.
        createNewUserButton.setOnAction(event -> {
            String newUsername = newUsernameInput.getText().trim();
            String newPassword = newPasswordInput.getText().trim();
            String verifiedNewPassword = verifyNewPasswordInput.getText().trim();
            Boolean inputAcceptable = true;
            if (!stringLengthCheck(newUsername, 3, 20)) {
                newUsernameMessage.setText("Username must be 3-20 characters long");
                newUsernameMessage.setTextFill(Color.RED);
                newUsernameInput.setText("");
                inputAcceptable = false;
            }
            if (!newUsername.matches("[A-Za-z0-9]*")) {
                newUsernameMessage.setText("Username must contain only letters or numbers");
                newUsernameMessage.setTextFill(Color.RED);
                newUsernameInput.setText("");
                inputAcceptable = false;
            }
            if (!stringLengthCheck(newPassword, 3, 20)) {
                newPasswordInputMessage.setText("Password must be 3-20 characters long");
                newPasswordInputMessage.setTextFill(Color.RED);
                newPasswordInput.setText("");
                inputAcceptable = false;
            }
            if (!newPassword.matches("[A-Za-z0-9]*")) {
                newPasswordInputMessage.setText("Password must contain only letters or numbers");
                newPasswordInputMessage.setTextFill(Color.RED);
                newPasswordInput.setText("");
                inputAcceptable = false;
            }
            if (!newPassword.equals(verifiedNewPassword)) {
                verifyNewPasswordMessage.setText("Please make sure the passwords match!");
                verifyNewPasswordMessage.setTextFill(Color.RED);
                inputAcceptable = false;
            }
            
            if (inputAcceptable) {
                newUsernameInput.setText("");
                newPasswordInput.setText("");
                verifyNewPasswordInput.setText("");
                if (managerService.createUser(newUsername, newPassword) && managerService.login(newUsername, newPassword)) {
                    this.redrawProjectsTree();
                    this.redrawSubprojectsTree();
                    loggedInAsLabel.setText("Logged in as: " + newUsername);
                    primaryStage.setScene(mainScene);
                } else {
                    verifyNewPasswordInput.setText("Sorry, an error occurred while creating the user or logging in!");
                }
            }
        });
        
        loginScene = new Scene(loginPane);
        
        // Nodes for the main scene
        Label mainSceneHeader = new Label("Projects");
        mainSceneHeader.setMinHeight(26);
        mainSceneHeader.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        Button logOutUserButton = new Button("Log Out");
        loggedInAsLabel.setMinHeight(26);
        HBox loggedInUserBox = new HBox(10);
        loggedInUserBox.getChildren().addAll(loggedInAsLabel, logOutUserButton);
        
        // Layout elements for main scene
        BorderPane mainPaneHeaderPane = new BorderPane();
        mainPaneHeaderPane.setLeft(mainSceneHeader);
        mainPaneHeaderPane.setRight(loggedInUserBox);
        mainPaneHeaderPane.setPadding(new Insets(24, 36, 0, 36));
        
        // main scene button actions
        logOutUserButton.setOnAction(event -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Paint Project Manager");
            alert.setHeaderText("Are you sure you want to log out?");
            alert.setContentText("Any unsaved data will be lost.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                primaryStage.setScene(loginScene);
                this.managerService.logout();
            }    
        });
        

        // Nodes for projectsPane
        // projectsTree is in class attributes
        projectsTree.setStyle("-fx-border-color: white;");
        projectsTree.setShowRoot(false);
        
        Button goToNewProjectCreationButton = new Button("Create a new Project");
        Label newProjectNameLabel = new Label("Project name");
        TextField newProjectNameInput = new TextField();
        Label newProjectFactionLabel = new Label("Faction");
        // newProjectFactionInput is in class attributes
        newProjectFactionInputBox.setEditable(true);
        newProjectFactionInputBox.setValue("");
        Label newProjectCategoryLabel = new Label("Category");
        // newProjectCategoryInput is in class attributes
        newProjectCategoryInputBox.setEditable(true);
        newProjectCategoryInputBox.setValue("");
        Label createNewProjectMessage = new Label();
        createNewProjectMessage.setMinHeight(26);
        createNewProjectMessage.setMaxWidth(240);
        createNewProjectMessage.setWrapText(true);
        Button createNewProjectButton = new Button("Create a new Project");
        Button closeNewProjectCreationButton = new Button("Cancel");
        HBox newProjectCreationButtonBox = new HBox(createNewProjectButton, closeNewProjectCreationButton);
        newProjectCreationButtonBox.setSpacing(12);
        
        GridPane newProjectCreationPane = new GridPane();
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
        
        // Layout elements for projectsPane
        BorderPane projectsPane = new BorderPane();
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
                    this.redrawProjectsTree();
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
        
        // Nodes for subprojectsPane
        // SubprojectsTree in class attributes
//        subprojectsTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> treeViewClickHandler(newValue));
        BorderPane.setMargin(subprojectsTree, new Insets(0, 0, 12, 0));
        
        Button goToCreateNewSubprojectButton = new Button("Create a new Subproject");
        Button goToCreateNewSurfaceButton = new Button("Create a new Surface");
        VBox goToNewElementCreationBox = new VBox(goToCreateNewSubprojectButton, goToCreateNewSurfaceButton);
        goToNewElementCreationBox.setSpacing(6);
        
        Label newSubprojectLabel = new Label("Subproject name");
        TextField newSubprojectInput = new TextField();
        Label newSubprojectMessage = new Label();
        newSubprojectMessage.setTextFill(Color.RED);
        newSubprojectMessage.setMaxWidth(240);
        newSubprojectMessage.setWrapText(true);
        Button createNewSubprojectButton = new Button("Create");
        Button closeNewSubprojectButton = new Button("Cancel");
        HBox newSubprojectButtonBox = new HBox(createNewSubprojectButton, closeNewSubprojectButton);
        newSubprojectButtonBox.setSpacing(6);
        VBox createNewSubprojectBox = new VBox(newSubprojectLabel, newSubprojectInput, newSubprojectMessage, newSubprojectButtonBox);
        createNewSubprojectBox.setSpacing(6);
        
        Label newSurfaceLabel = new Label("Surface name");
        TextField newSurfaceNameInput = new TextField();
        Label newSurfaceSubprojectLabel = new Label("Subproject of the surface");
        this.newSurfaceSubprojectInputBox.setValue("");
        this.newSurfaceSubprojectInputBox.setEditable(true);
        Label newSurfaceMessage = new Label();
        newSurfaceMessage.setTextFill(Color.RED);
        newSurfaceMessage.setMaxWidth(240);
        newSurfaceMessage.setWrapText(true);
        Button createNewSurfaceButton = new Button("Create");
        Button closeNewSurfaceButton = new Button("Cancel");
        HBox newSurfaceButtonBox = new HBox(createNewSurfaceButton, closeNewSurfaceButton);
        newSurfaceButtonBox.setSpacing(6);
        VBox createNewSurfaceBox = new VBox(newSurfaceLabel, newSurfaceNameInput, newSurfaceSubprojectLabel, this.newSurfaceSubprojectInputBox, newSurfaceMessage, newSurfaceButtonBox);
        createNewSurfaceBox.setSpacing(6);
        
        // Layout for subprojectsPane
        BorderPane subprojectsPane = new BorderPane();
        subprojectsPane.setCenter(subprojectsTree);
        subprojectsPane.setBottom(goToNewElementCreationBox);
        
        // Button actions for subprojectsPane
        goToCreateNewSubprojectButton.setOnAction(event -> {
            if (this.managerService.getCurrentlyViewedProject().getProjectId() != -1) {
                subprojectsPane.setBottom(createNewSubprojectBox);
            }
        });
        goToCreateNewSurfaceButton.setOnAction(event -> {
            if (this.managerService.getCurrentlyViewedProject().getProjectId() != -1) {
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
                this.managerService.createSubproject(this.managerService.getCurrentlyViewedProject().getProjectId(), newSubprojectName);
                newSubprojectInput.setText("");
                newSubprojectMessage.setText("");
                this.redrawSubprojectsTree();
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
                SubProject subproject = this.managerService.createSubproject(this.managerService.getCurrentlyViewedProject().getProjectId(), newSubprojectName);
                if (subproject != null) {
                    this.managerService.createSurface(subproject.getSubProjectId(), newSurfaceName);
                }
                newSurfaceNameInput.setText("");
                newSurfaceSubprojectInputBox.setValue("");
                newSurfaceMessage.setText("");
                this.redrawSubprojectsTree();
                subprojectsPane.setBottom(goToNewElementCreationBox);
            }
        });
        
        // Layout for viewProjectPane
        BorderPane viewProjectPane = new BorderPane();
        viewProjectPane.setTop(projectDetailsPane.getProjectDetailsPane());
        viewProjectPane.setLeft(subprojectsPane);
        viewProjectPane.setCenter(this.surfacePane.getSurfacePane());
        BorderPane.setMargin(this.surfacePane, new Insets(0, 0, 0, 20));
        
        // Building the main scene
        BorderPane mainPane = new BorderPane();
        mainPane.setTop(mainPaneHeaderPane);
        mainPane.setLeft(projectsPane);
        mainPane.setCenter(viewProjectPane);
        mainPane.setPadding(new Insets(20));
        BorderPane.setMargin(viewProjectPane, new Insets(20, 50, 20, 0));
        mainPane.setBackground(Background.EMPTY);
        
        mainScene = new Scene(mainPane, 1400, 900);
        
        // setup primary stage
        primaryStage.setTitle("Paint Project Manager");
        primaryStage.setScene(loginScene);
        // debug view!
        this.managerService.login("Mikke", "KillerApp");
        this.redrawProjectsTree();
        loggedInAsLabel.setText("Logged in as: Debug");
        primaryStage.setScene(mainScene);
        // debug view end!
        primaryStage.show();
        primaryStage.setOnCloseRequest(e->{
            managerService.logout();
            if (managerService.getLoggedIn() != null) {
                e.consume();   
            }
        });
    }
    
    @Override
    public void stop() {
        managerService.logout();
        System.out.println("Closing the program!");
    }  
    
    public static void main(String[] args) {
        launch(args);
    }
}