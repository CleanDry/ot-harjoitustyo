package dao;

import java.io.*;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import maalausprojektikirjanpito.dao.UserDao;
import maalausprojektikirjanpito.domain.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;


public class UserDaoTest {
    @Rule 
    public TemporaryFolder testFolder = new TemporaryFolder();
    
    File testDb;
    UserDao testDao;
    
    public UserDaoTest() {
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
        
        testDao = new UserDao(testDb.getAbsolutePath());
        testDao.init();
        
        testDao.create(new User("username", "password"));
    }
    
    @After
    public void tearDown() {
        testDb.delete();
    }
    
    @Test
    public void usersAreReadCorrectlyFromDb() throws SQLException {
        List<User> users = testDao.list();
        assertEquals(1, users.size());
        User user = users.get(0);
        assertEquals(1, user.getId().intValue());
        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
    }
    
    @Test
    public void usersAreCanBeCreatedAndAreReturnedCorrectly() throws SQLException {
        User user = testDao.create(new User("user", "passphrase"));
        assertEquals("user", user.getUsername());
        assertEquals("passphrase", user.getPassword());
        List<User> users = testDao.list();
        assertEquals(2, users.size());
        User sameUser = users.get(1);
        assertEquals(2, sameUser.getId().intValue());
        assertEquals("user", sameUser.getUsername());
        assertEquals("passphrase", sameUser.getPassword());
    }
    
    @Test
    public void usersAreReadSuccessfully() throws SQLException {
        User user = testDao.read(1);
        assertEquals(1, user.getId().intValue());
        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
    }
    
    @Test
    public void updatedUsersReturnedMatchTheUpdate() throws SQLException {
        User user = testDao.update(new User(1, "user", "passphrase"));
        assertEquals(1, user.getId().intValue());
        assertEquals("user", user.getUsername());
        assertEquals("passphrase", user.getPassword());
    }
    
    @Test
    public void deletedUsersAreRemovedFromDb() throws SQLException {
        User user = testDao.create(new User("user", "passphrase"));
        User userToDelete = testDao.read(1);
        testDao.delete(1);
        assertEquals(2, user.getId().intValue());
        assertEquals("user", user.getUsername());
        assertEquals("passphrase", user.getPassword());
        List<User> users = testDao.list();
        assertEquals(1, users.size());
        assertFalse(users.contains(userToDelete));
    }
}
