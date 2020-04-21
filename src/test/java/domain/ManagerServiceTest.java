package domain;

import java.io.File;
import maalausprojektikirjanpito.dao.UserDao;
import maalausprojektikirjanpito.domain.ManagerService;
import maalausprojektikirjanpito.domain.User;
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
    }
    
    @After
    public void tearDown() {
        testDb.delete();
    }
    
    @Test
    public void userCreationSucceedsIfUnique() {
        assertTrue(service.createUser("username", "password"));
    }
    
    @Test
    public void userCreationFailsIfNotUnique() {
        service.createUser("username", "password");
        assertFalse(service.createUser("username", "password"));
        assertFalse(service.createUser("UsErNaMe", "password"));
    }
    
    @Test
    public void userCreationFailsIfNameOrPwNotLongEnoughAndSucceedsOtherwise() {
        // System.out.println("Test begun");
        assertFalse(service.createUser("username", "pass"));
        // System.out.println("1st fails");
        assertFalse(service.createUser("us", "password"));
        // System.out.println("2st fails");
        assertFalse(service.createUser("us", "pass"));
        // System.out.println("3st fails");
        assertFalse(service.createUser("usernamethatiswaytoolongandcomplex", "entirewholepassphrasethatisalsotoolong"));
        assertTrue(service.createUser("username", "password"));
        // System.out.println("4st succeeds");
    }
    
    @Test
    public void loginfFailsIfFieldsEmpty() {
        assertFalse(service.login("", ""));
    }
    
    @Test
    public void loginfFailsIfPairNotMatching() {
        assertTrue(service.createUser("username", "password"));
        assertFalse(service.login("username", "pass"));
        assertFalse(service.login("user", "password"));
        assertFalse(service.login("user", "pass"));
    }
    
    @Test
    public void loginSucceedsIfEntryCorrect() {
        service.createUser("username", "password");
        assertTrue(service.login("username", "password"));
    }
}
