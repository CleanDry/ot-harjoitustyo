package maalausprojektikirjanpito.ui;

import java.util.Optional;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import maalausprojektikirjanpito.domain.ManagerService;
import static maalausprojektikirjanpito.domain.Utilities.stringLengthCheck;
import maalausprojektikirjanpito.ui.panes.ProjectDetailsPane;
import maalausprojektikirjanpito.ui.panes.ProjectsTreePane;
import maalausprojektikirjanpito.ui.panes.SubprojectsTreePane;
import maalausprojektikirjanpito.ui.panes.SurfacePane;

public class UserInterface extends Application {
    private ManagerService managerService;
    private Label loggedInAsLabel;
    private Scene loginScene;
    private Scene appMainScene;
    private SurfacePane surfacePane;
    private ProjectDetailsPane projectDetailsPane;
    private SubprojectsTreePane subprojectsTreePane;
    private ProjectsTreePane projectsTreePane;

    @Override
    public void init() throws Exception {
//        Properties properties = new Properties(); ?
        managerService = new ManagerService("PaintProjectManager.db");
        managerService.init();
        loggedInAsLabel = new Label();
        surfacePane = new SurfacePane(this.managerService);
        projectDetailsPane = new ProjectDetailsPane(this.managerService);
        subprojectsTreePane = new SubprojectsTreePane(this.managerService, this.surfacePane);
        projectsTreePane = new ProjectsTreePane(this.managerService, this.subprojectsTreePane, this.projectDetailsPane);
        projectDetailsPane.setProjectsPane(projectsTreePane);
    }
    
    
    @Override
    public void start(Stage primaryStage) throws Exception { 
        
        // Nodes for loginPane
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
        
        // loginPane and layout
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
                this.projectsTreePane.refresh();
                this.subprojectsTreePane.refresh();
                loggedInAsLabel.setText("Logged in as: " + username);
                primaryStage.setScene(appMainScene);  
                usernameInput.setText("");
                passwordInput.setText("");
            } else {
                loginMessage.setText("Sorry! Username or password incorrect");
                loginMessage.setTextFill(Color.RED);
            }      
        });
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
                    this.projectsTreePane.refresh();
                    this.subprojectsTreePane.refresh();
                    loggedInAsLabel.setText("Logged in as: " + newUsername);
                    primaryStage.setScene(appMainScene);
                } else {
                    verifyNewPasswordInput.setText("Sorry, an error occurred while creating the user or logging in!");
                }
            }
        });
        loginScene = new Scene(loginPane);
        
        
        // Elements for appMainViewPane
        // Nodes for managerHeaderPane
        Label managerHeaderLabel = new Label("Projects");
        managerHeaderLabel.setMinHeight(26);
        managerHeaderLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        Button logOutUserButton = new Button("Log Out");
        loggedInAsLabel.setMinHeight(26);
        HBox loggedInUserBox = new HBox(10);
        loggedInUserBox.getChildren().addAll(loggedInAsLabel, logOutUserButton);
        // managerHeaderPane and layout
        BorderPane managerHeaderPane = new BorderPane();
        managerHeaderPane.setLeft(managerHeaderLabel);
        managerHeaderPane.setRight(loggedInUserBox);
        managerHeaderPane.setPadding(new Insets(24, 36, 0, 36));
        // managerHeaderPane button actions
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
        // projectPane and layout
        BorderPane projectPane = new BorderPane();
        projectPane.setTop(this.projectDetailsPane.getProjectDetailsPane());
        projectPane.setLeft(this.subprojectsTreePane.getSubprojectsPane());
        projectPane.setCenter(this.surfacePane.getSurfacePane());
        BorderPane.setMargin(this.surfacePane, new Insets(0, 0, 0, 20));
        
        // Building the appMainViewPane
        BorderPane appMainViewPane = new BorderPane();
        appMainViewPane.setTop(managerHeaderPane);
        appMainViewPane.setLeft(this.projectsTreePane.getProjectsPane());
        appMainViewPane.setCenter(projectPane);
        appMainViewPane.setPadding(new Insets(20));
        BorderPane.setMargin(projectPane, new Insets(20, 50, 20, 0));
        appMainViewPane.setBackground(Background.EMPTY);
        
        appMainScene = new Scene(appMainViewPane, 1400, 900);
        
        // setup primary stage
        primaryStage.setTitle("Paint Project Manager");
        primaryStage.setScene(loginScene);
        // debug view!
        this.managerService.login("Mikke", "KillerApp");
        this.projectsTreePane.refresh();
        loggedInAsLabel.setText("Logged in as: Debug");
        primaryStage.setScene(appMainScene);
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