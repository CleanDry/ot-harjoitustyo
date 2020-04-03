package domain;

import maalausprojektikirjanpito.domain.ManagerService;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class ManagerServiceTest {
    
    ManagerService service = new ManagerService();
    
    public ManagerServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void userCreationSucceedsIfUnique() {
        assertTrue(service.createUser("username", "password"));
    }
    
    @Test
    public void userCreationFailsIfNotUnique() {
        service.createUser("username", "password");
        assertFalse(service.createUser("username", "password"));
    }
    
    @Test
    public void userCreationFailsIfNameOrPwNotLongEnoughAndSucceedsOtherwise() {
        assertFalse(service.createUser("username", "pass"));
        assertFalse(service.createUser("us", "password"));
        assertFalse(service.createUser("us", "pass"));
        assertTrue(service.createUser("username", "password"));
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
