package maalausprojektikirjanpito.domain;

import java.io.File;
import java.sql.SQLException;
import java.util.*;
import maalausprojektikirjanpito.dao.PaintProjectDao;
import maalausprojektikirjanpito.dao.SubProjectDao;
import maalausprojektikirjanpito.dao.SurfaceDao;
import maalausprojektikirjanpito.dao.UserDao;
import static maalausprojektikirjanpito.domain.Utilities.*;


public class ManagerService {
    private String databaseUrl;
    private User loggedIn;
    private UserDao userDao;
    private PaintProjectDao projectsDao;
    private SubProjectDao subprojectDao;
    private SurfaceDao surfaceDao;
    private HashMap<String, ArrayList<PaintProject>> userProjectsByCategory;

    /**
     * Constructs a new ManagerService-object. UserDao-object inside ensures existence of a User table and database in the designated location.
     * @param databaseUrl URL of the selected database as a String
     */
    public ManagerService(String databaseUrl) {
        this.databaseUrl = databaseUrl;
        this.userDao = new UserDao(databaseUrl);
        this.projectsDao = new PaintProjectDao(databaseUrl);
        this.subprojectDao = new SubProjectDao(databaseUrl);
        
    }
    
    /**
     * Initializes the ManagerService-object in a controlled sequence.
     * @throws Exception 
     */
    public void init() throws Exception {
        // Ensure database file exists
        String relativePath = databaseUrl;
        File file = new File(relativePath);
        
        if (file.createNewFile()) {
            System.out.println("File " + relativePath + " created in ./db/ directory");
        } else {
            System.out.println("File " + relativePath + " already exists in the ./db/ directory");
        }
        
        // Initialize Dao-objects; ensure database's tables exist and dao's fetch their initial caches
        this.userDao.init();
        this.projectsDao.init();
        this.subprojectDao.init();
    }
    
    /**
     * Create a user to the system.
     * @param username User's username, must be between 3 to 20 characters.
     * @param password User's password, must be between 8 to 20 characters.
     * @return returns false if username already taken, username.length is less than 3 or password.length is less than 8, otherwise true
     * @throws SQLException
     */
    public boolean createUser(String username, String password) throws SQLException {
        // Check if the given pair meets the criterias for length
        if (!stringLengthCheck(username, 3, 20)) {
            System.out.println("Username has to have 3-20 characters");
            return false;
        } else if (!stringLengthCheck(password, 8, 20)) {
            System.out.println("Password has to have 8-20 characters");
            return false;
        // Check if the given username in lowercase is unique
        } else if (this.userDao.getCache().containsKey(username.toLowerCase())) {
            System.out.println("Username taken.");
            return false;
        }
        
        User user = userDao.create(new User(username, password));
        this.userDao.getCache().put(username.toLowerCase(), user);
        System.out.println(databaseUrl + ": " + user + " created successfully!");
        return true;
    }
    
    /**
     * Create a new PaintProject.
     * @param projectName for the project as String, must be 3-40 character long
     * @param projectCategory for the project as String, must be 3-40 character long
     * @return true if successful, false otherwise
     * @throws SQLException
     */
    public boolean createPaintProject(String projectName, String projectCategory) throws SQLException {
        // Check if the given pair meets the criterias for length
        if (!stringLengthCheck(projectName, 3, 40)) {
            System.out.println("Project's name has to have 3-40 characters");
            return false;
        } else if (!stringLengthCheck(projectCategory, 3, 40)) {
            System.out.println("Category's name has to have 3-40 characters");
            return false;
        
        }
        PaintProject projectToBeCreated = new PaintProject(this.getLoggedIn().getId(), projectName, projectCategory);
        
        // Check if the given username in lowercase is unique
        if (this.getUserProjectsByCategory().containsKey(projectCategory) && 
                this.getUserProjectsByCategory().get(projectCategory).contains(projectToBeCreated)) {
            System.out.println("Project already exists.");
            return false;
        }
        
        this.projectsDao.create(projectToBeCreated);
        this.userProjectsByCategory = this.returnUserProjectsByCategory();
        return true;
    }
    
    /**
     * login a user to the system.
     * @param username  User's username
     * @param password  User's password
     * @return false if either field is empty or no matching pair of username and password found, true if found
     */
    public boolean login(String username, String password) {
        String usernameInLowerCase = username.toLowerCase();
        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Either field was empty!");
            return false;
        } else if (this.userDao.getCache().containsKey(usernameInLowerCase) 
                && this.userDao.getCache().get(usernameInLowerCase).getUsername().equals(username) 
                && this.userDao.getCache().get(usernameInLowerCase).getPassword().equals(password)) {
            
            this.loggedIn = this.userDao.getCache().get(username.toLowerCase());
            System.out.println("Successfully logged in " + username + " " + password + "!");
            
            this.projectsDao.setUser(this.loggedIn.getId());
            this.userProjectsByCategory = this.returnUserProjectsByCategory();
            return true;
            
        }
        System.out.println("Login failed for some other reason.");
        return false;
    }

    /**
     * Find out currently logged in user.
     * @return logged in user, null if no user logged in
     */
    public User getLoggedIn() {
        if (this.loggedIn == null) {
            System.out.println("Nobody logged in!");
            return null;
        }
        System.out.println("Logged in: " + loggedIn.getUsername());
        return loggedIn;
    }
    
    /**
     * Log out current user. Set PaintProjectDao's UserID to -1.
     */
    public void logout() {
        System.out.println("Logging out");
        this.loggedIn = null;
        this.userProjectsByCategory = null;
        this.projectsDao.setUser(-1);
        this.getLoggedIn();
    }

    public HashMap<String, ArrayList<PaintProject>> getUserProjectsByCategory() {
        return userProjectsByCategory;
    }

    public void setUserProjectsByCategory(HashMap<String, ArrayList<PaintProject>> userProjectsByCategory) {
        this.userProjectsByCategory = userProjectsByCategory;
    }

    public PaintProjectDao getProjectsDao() {
        return projectsDao;
    }
    
    /**
     * Returns Logged User's projects sorted by category.
     * @return HashMap<String, ArrayList<PaintProject>>
     */
    public HashMap<String, ArrayList<PaintProject>> returnUserProjectsByCategory() {
        ArrayList<String> userProjectCategeories = projectsDao.categoriesOfUser(loggedIn.getId());
        HashMap<String, ArrayList<PaintProject>> userPaintProjectsByCategory = new HashMap<>();
        for (String category : userProjectCategeories) {
            userPaintProjectsByCategory.put(category, projectsDao.projectsInCategory(category));
        }
        return userPaintProjectsByCategory;
    }
}
