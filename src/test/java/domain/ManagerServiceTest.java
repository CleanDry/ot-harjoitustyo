package domain;

import java.io.File;
import java.sql.SQLException;
import maalausprojektikirjanpito.domain.ManagerService;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;


public class ManagerServiceTest {
    
    @Rule 
    public TemporaryFolder testFolder = new TemporaryFolder();
    
    File testDb;
    ManagerService service;
    
    
    public ManagerServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        testDb = testFolder.newFile("test.db");
        
        service = new ManagerService(testDb.getAbsolutePath());
        service.init();
        service.createUser("testUser", "testPassword", "testPassword");
        service.login("testUser", "testPassword");
    }
    
    @After
    public void tearDown() {
        service.logout();
        testDb.delete();
    }
    
    @Test
    public void userCreationSucceedsIfUnique() throws SQLException {
        assertEquals("success", service.createUser("username", "password", "password"));
    }
    
    @Test
    public void userCreationFailsIfNotUnique() throws SQLException {
        service.createUser("username", "password", "password");
        assertEquals("Sorry, the username is taken!", service.createUser("username", "password", "password"));
        assertEquals("Sorry, the username is taken!", service.createUser("UsErNaMe", "password", "password"));
    }
    
    @Test
    public void userCreationFailsIfNameOrPwNotLongEnoughAndSucceedsOtherwise() throws SQLException {
        // System.out.println("Test begun");
        assertEquals("Username must be 3-20 characters long and password 8-20", service.createUser("username", "pass", "pass"));
        // System.out.println("1st fails");
        assertEquals("Username must be 3-20 characters long and password 8-20", service.createUser("us", "password", "password"));
        // System.out.println("2st fails");
        assertEquals("Username must be 3-20 characters long and password 8-20", service.createUser("us", "pass", "pass"));
        // System.out.println("3st fails");
        assertEquals("Username must be 3-20 characters long and password 8-20", service.createUser("usernamethatiswaytoolongandcomplex", "entirewholepassphrasethatisalsotoolong", "entirewholepassphrasethatisalsotoolong"));
        assertEquals("success", service.createUser("username", "password", "password"));
        // System.out.println("4st succeeds");
    }
    
    @Test
    public void loginfFailsIfFieldsEmpty() {
        assertFalse(service.login("", ""));
    }
    
    @Test
    public void loginfFailsIfPairNotMatching() throws SQLException {
        assertEquals("success", service.createUser("username", "password", "password"));
        assertFalse(service.login("username", "pass"));
        assertFalse(service.login("user", "password"));
        assertFalse(service.login("user", "pass"));
    }
    
    @Test
    public void loginSucceedsIfEntryCorrect() throws SQLException {
        service.createUser("username", "password", "password");
        assertTrue(service.login("username", "password"));
    }
    
    @Test
    public void paintProjectNameIsProperLength() throws SQLException {
        service.login("testUser", "testPassword");
        assertFalse(service.createPaintProject("s", "projectFaction", "projectCategory"));
        assertFalse(service.createPaintProject("namethatislongerthanfortycharacterslongthatfails", "projectFaction", "projectCategory"));
        assertTrue(service.createPaintProject("projectName", "projectFaction", "projectCategory"));
    }
    
    @Test
    public void paintProjectCategoryIsProperLength() throws SQLException {
        service.login("testUser", "testPassword");
        assertFalse(service.createPaintProject("projectName", "projectFaction", "c"));
        assertFalse(service.createPaintProject("projectName", "projectFaction", "namethatislongerthanfortycharacterslongthatfails"));
        assertTrue(service.createPaintProject("projectName", "projectFaction", "projectCategory"));
    }
    
    @Test
    public void paintProjectCreateDoesNotAllowDuplicates() throws SQLException {
        service.login("testUser", "testPassword");
        service.createPaintProject("projectName", "projectFaction", "projectCategory");
        assertFalse(service.createPaintProject("projectName", "projectFaction", "projectCategory"));
    }
    
    @Test
    public void getLoggedInReturnsUser() throws SQLException {
        service.login("testUser", "testPassword");
        assertEquals("testUser", service.getLoggedIn().getUsername());
    }
}
