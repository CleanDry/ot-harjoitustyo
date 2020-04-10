package dao;

import java.io.File;
import java.util.List;
import maalausprojektikirjanpito.dao.PaintProjectDao;
import maalausprojektikirjanpito.domain.PaintProject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;


public class PaintProjectDaoTest {
    @Rule 
    public TemporaryFolder testFolder = new TemporaryFolder();
    
    Integer testUser_id;
    File testDb;
    PaintProjectDao testDao;
    
    public PaintProjectDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        testUser_id = 1;
        testDb = testFolder.newFile("test.db");
        
        testDao = new PaintProjectDao(testUser_id, testDb.getAbsolutePath());
        
        testDao.create(new PaintProject(testUser_id, "test_project", "test_category"));
    }
    
    @After
    public void tearDown() {
        testDb.delete();
    }
    
//    @Test
//    public void usersAreReadCorrectlyFromDb() {
//        List<User> users = testDao.list();
//        assertEquals(1, users.size());
//        User user = users.get(0);
//        assertEquals(1, user.getId().intValue());
//        assertEquals("username", user.getUsername());
//        assertEquals("password", user.getPassword());
//    }
//    
//    @Test
//    public void usersAreCanBeCreatedAndAreReturnedCorrectly() {
//        User user = testDao.create(new User("user", "passphrase"));
//        assertEquals("user", user.getUsername());
//        assertEquals("passphrase", user.getPassword());
//        List<User> users = testDao.list();
//        assertEquals(2, users.size());
//        User sameUser = users.get(1);
//        assertEquals(2, sameUser.getId().intValue());
//        assertEquals("user", sameUser.getUsername());
//        assertEquals("passphrase", sameUser.getPassword());
//    }
//    
//    @Test
//    public void usersAreReadSuccessfully() {
//        User user = testDao.read(1);
//        assertEquals(1, user.getId().intValue());
//        assertEquals("username", user.getUsername());
//        assertEquals("password", user.getPassword());
//    }
//    
//    @Test
//    public void updatedUsersReturnedMatchTheUpdate() {
//        User user = testDao.update(new User(1, "user", "passphrase"));
//        assertEquals(1, user.getId().intValue());
//        assertEquals("user", user.getUsername());
//        assertEquals("passphrase", user.getPassword());
//    }
//    
//    @Test
//    public void deletedUsersAreRemovedFromDb() {
//        User user = testDao.create(new User("user", "passphrase"));
//        User userToDelete = testDao.read(1);
//        testDao.delete(1);
//        assertEquals(2, user.getId().intValue());
//        assertEquals("user", user.getUsername());
//        assertEquals("passphrase", user.getPassword());
//        List<User> users = testDao.list();
//        assertEquals(1, users.size());
//        assertFalse(users.contains(userToDelete));
//    }
}
