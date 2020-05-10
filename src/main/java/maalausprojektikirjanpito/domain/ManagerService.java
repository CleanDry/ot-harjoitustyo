package maalausprojektikirjanpito.domain;

import java.io.File;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.scene.paint.Paint;
import maalausprojektikirjanpito.dao.LayerDao;
import maalausprojektikirjanpito.dao.PaintProjectDao;
import maalausprojektikirjanpito.dao.SubProjectDao;
import maalausprojektikirjanpito.dao.SurfaceDao;
import maalausprojektikirjanpito.dao.SurfaceTreatmentDao;
import maalausprojektikirjanpito.dao.UserDao;
import static maalausprojektikirjanpito.domain.Utilities.*;


public class ManagerService {
    private String databaseUrl;
    private User loggedIn;
    private UserDao userDao;
    private PaintProjectDao projectsDao;
    private SurfaceTreatmentDao surfaceTreatmentDao;
    private LayerDao layerDao;
    private SubProjectDao subprojectDao;
    private SurfaceDao surfaceDao;
    private HashMap<String, ArrayList<PaintProject>> userProjectsByCategory;
    
    private PaintProject currentlyViewedProject;
    private Surface currentlyViewedSurface;

    /**
     * Constructs a new ManagerService-object. UserDao-object inside ensures existence of a User table and database in the designated location.
     * @param databaseUrl URL of the selected database as a String
     */
    public ManagerService(String databaseUrl) {
        this.databaseUrl = databaseUrl;
        this.userDao = new UserDao(databaseUrl);
        this.projectsDao = new PaintProjectDao(databaseUrl);
        this.surfaceTreatmentDao = new SurfaceTreatmentDao(databaseUrl);
        this.layerDao = new LayerDao(databaseUrl, this.surfaceTreatmentDao);
        this.surfaceDao = new SurfaceDao(databaseUrl, this.layerDao);
        this.subprojectDao = new SubProjectDao(databaseUrl, this.surfaceDao);
        
        currentlyViewedProject = new PaintProject(-1,-1,"No project selected", "-", "-", false, false, false);
        currentlyViewedSurface = new Surface(-1, -1, "No surface selected", false);
    }
    
    /**
     * Initializes the ManagerService-object in a controlled sequence.
     * @throws Exception 
     */
    public void init() throws Exception {
        // Ensure database file exists
        File file = new File(databaseUrl);
        
        if (file.createNewFile()) {
            System.out.println("File " + databaseUrl + " created in App root directory");
        } else {
            System.out.println("File " + databaseUrl + " already exists in the App root directory");
        }
        
        // Initialize Dao-objects; ensure database's tables exist and dao's fetch their initial caches
        this.userDao.init();
        this.projectsDao.init();
        this.surfaceTreatmentDao.init();
        this.layerDao.init();
        this.surfaceDao.init();
        this.subprojectDao.init();
    }
    
    /**
     * Create a user to the system.
     * @param username User's username, must be between 3 to 20 characters.
     * @param password User's password, must be between 8 to 20 characters.
     * @return returns false if username already taken, username.length is less than 3 or password.length is less than 8, otherwise true
     */
    public boolean createUser(String username, String password) {
        try {
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
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ManagerService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
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
            System.out.println("Successfully logged in " + username + "!");
            
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
        }
        return loggedIn;
    }
    
    /**
     * Log out current user. Set PaintProjectDao's UserID to -1.
     */
    public void logout() {
        System.out.println("Logging out");
        this.loggedIn = null;
        this.userProjectsByCategory = null;
        this.getLoggedIn();
    }
    
    /**
     * Create a new PaintProject.
     * @param projectName for the project as String, must be 3-40 character long
     * @param projectFaction for the project as String, must be 3-40 character long
     * @param projectCategory for the project as String, must be 3-40 character long
     * @return true if successful, false otherwise
     * @throws SQLException
     */
    public boolean createPaintProject(String projectName, String projectFaction, String projectCategory) throws SQLException {
        // Check if the given pair meets the criterias for length
        if (!stringLengthCheck(projectName, 3, 40)) {
            System.out.println("Project's name has to have 3-40 characters");
            return false;
        } else if (!stringLengthCheck(projectFaction, 3, 40)) {
            System.out.println("Faction's name has to have 3-40 characters");
            return false;
        } else if (!stringLengthCheck(projectCategory, 3, 40)) {
            System.out.println("Category's name has to have 3-40 characters");
            return false;
        }
        PaintProject projectToBeCreated = new PaintProject(this.getLoggedIn().getId(), projectName, projectFaction, projectCategory);
        
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
    
    public ArrayList<PaintProject> fetchUserProjects() {
        ArrayList<PaintProject> userProjectsToReturn = new ArrayList<>();
        try {
            userProjectsToReturn = (ArrayList<PaintProject>) this.projectsDao.list();
        } catch (SQLException ex) {
            Logger.getLogger(ManagerService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userProjectsToReturn;
    }
    
    public boolean updateProject(PaintProject project) {
        try {
            this.projectsDao.update(project);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ManagerService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
    public SubProject createSubproject(Integer projectId, String subprojectName) {
        SubProject subprojectToBeCreated = new SubProject(projectId, subprojectName);
        try {
            return this.subprojectDao.create(subprojectToBeCreated);
        } catch (SQLException ex) {
            Logger.getLogger(ManagerService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ArrayList<SubProject> fetchSubprojects(PaintProject project) {
        ArrayList<SubProject> updatedSubprojects = (ArrayList<SubProject>) this.subprojectDao.list();
        updatedSubprojects = (ArrayList<SubProject>) updatedSubprojects.stream().filter(sb -> Objects.equals(sb.projectId, project.projectId)).collect(Collectors.toList());
        return updatedSubprojects;
    }
    
    
    public boolean createSurface(Integer subprojectId, String surfaceName) {
        return this.subprojectDao.createNewSurface(new Surface(subprojectId, surfaceName));
    }
    
    
    public Layer createLayer(String layerName, String layerNote) {
        try {
            return this.layerDao.create(new Layer(layerName, layerNote));
        } catch (SQLException ex) {
            Logger.getLogger(ManagerService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public boolean addLayerToSurface(Integer surfaceId, Integer layerId) {
        return this.surfaceDao.addLayerToSurface(surfaceId, layerId);
    }
    
    public ArrayList<Layer> fetchLayers(Surface surface) {
        if (surface.surfaceId == -1) {
            return new ArrayList<>();
        }
        Surface updatedSurface = this.surfaceDao.read(surface.surfaceId);
        return updatedSurface.getLayers();
    }
    
    
    public SurfaceTreatment createTreatment(String treatmentName, String treatmentType, String treatmentManufacturer, Paint treatmentColour) {
        try {
            return this.surfaceTreatmentDao.create(new SurfaceTreatment(treatmentName, treatmentType, treatmentManufacturer, treatmentColour));
        } catch (SQLException ex) {
            Logger.getLogger(ManagerService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
      
    public boolean addTreatmentToLayer(Integer layerId, Integer surfaceTreatmentId) {
        return this.layerDao.addTreatmentToLayer(layerId, surfaceTreatmentId);
    }
    
    public ArrayList<SurfaceTreatment> fetchAllSurfaceTreatments() {
        ArrayList<SurfaceTreatment> listOfAllTreatments = new ArrayList<>();
        this.surfaceTreatmentDao.list().forEach(st -> listOfAllTreatments.add(st));
        return listOfAllTreatments;
    }
    
    
    public HashMap<String, ArrayList<PaintProject>> getUserProjectsByCategory() {
        return userProjectsByCategory;
    }

    public void setUserProjectsByCategory(HashMap<String, ArrayList<PaintProject>> userProjectsByCategory) {
        this.userProjectsByCategory = userProjectsByCategory;
    }

    public PaintProject getCurrentlyViewedProject() {
        return currentlyViewedProject;
    }

    public void setCurrentlyViewedProject(PaintProject currentlyViewedProject) {
        this.currentlyViewedProject = currentlyViewedProject;
    }

    public Surface getCurrentlyViewedSurface() {
        return currentlyViewedSurface;
    }

    public void setCurrentlyViewedSurface(Surface currentlyViewedSurface) {
        this.currentlyViewedSurface = currentlyViewedSurface;
    }
}
