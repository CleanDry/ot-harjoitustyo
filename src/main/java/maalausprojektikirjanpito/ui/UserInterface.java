package maalausprojektikirjanpito.ui;

import java.util.Set;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import maalausprojektikirjanpito.domain.ManagerService;
import maalausprojektikirjanpito.domain.PaintProject;

public class UserInterface extends Application {
    private ManagerService managerService;
    
    private Scene loginScene;
    private Scene createNewUserScene;
    private Scene mainScene;
    
    private VBox categoryNodes;
    

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
    }
    
    public Node createCategoryNode(String category) {
        Label categoryName = new Label(category);
        Label categoryElements = new Label(String.valueOf(managerService.getUserProjectsByCategory().get(category).size()));
        HBox categoryNode = new HBox(10);
        categoryNode.getChildren().addAll(categoryName, categoryElements);
        return categoryNode;
    }
    
    public Node createProjectNode(PaintProject project) {
        HBox box = new HBox(10);
        Label label = new Label(project.getProjectName());
        label.setMinHeight(28);
        Label elemenentsIncluded = new Label(project.getSubprojects().size() + " elements");
        CheckBox isComplete = new CheckBox();
        CheckBox toBeArchived = new CheckBox();
        CheckBox toBeRemoved = new CheckBox();
        
        box.getChildren().addAll(label, elemenentsIncluded, isComplete, toBeArchived, toBeRemoved);
        return box;
    }
    
    public void redrawCategoriesList() {
        this.categoryNodes.getChildren().clear();
        Set<String> categoriesOfAUser = managerService.getUserProjectsByCategory().keySet();
        categoriesOfAUser.forEach(category -> {
            categoryNodes.getChildren().add(createCategoryNode(category));
        });
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
        loginButton.setOnAction(event -> {
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            if (managerService.login(username, password) ){
                loginMessage.setText("");
                redrawCategoriesList();
                primaryStage.setScene(mainScene);  
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
        
        createNewUserButton.setOnAction(event -> {
            String newUsername = newUsernameInput.getText();
            String newPassword = newPasswordInput.getText();
            String newPasswordVerified = verifyNewPasswordInput.getText();
            if (!newPassword.equals(newPasswordVerified)) {
                verifyNewPasswordMessage.setText("Please make sure the passwords match!");
                verifyNewPasswordMessage.setTextFill(Color.RED);
            }
        });
        
        loginScene = new Scene(loginPane);
        
        
        // browse project categories -scene
        // rest of scenes?
        // Building the primary scene
        BorderPane mainPane = new BorderPane();
        mainScene = new Scene(mainPane, 1200, 700);
        
        // setup primary stage
        primaryStage.setTitle("Paint Project Manager");
        primaryStage.setScene(loginScene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e->{
            System.out.println("closing");
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