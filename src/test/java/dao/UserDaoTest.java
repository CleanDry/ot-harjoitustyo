package dao;

import java.io.*;
import java.sql.*;
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
    
    File userDb;
    UserDao dao;
    Connection connection;
    
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
        userDb = testFolder.newFile("test.db");
        System.out.println("jdbc:sqlite:" + userDb.getAbsolutePath());
        connection = DriverManager.getConnection("jdbc:sqlite:" + userDb.getAbsolutePath());
        System.out.println(connection.getMetaData());
        dao = new UserDao();
        
        dao.create(new User("username", "password"));
    }
    
    @After
    public void tearDown() {
        userDb.delete();
    }
    
    @Test
    public void usersAreReadCorrectlyFromDb() {
        List<User> users = dao.list();
        assertEquals(1, users.size());
        User user = users.get(0);
        assertEquals(1, user.getId().intValue());
        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
    }
}
