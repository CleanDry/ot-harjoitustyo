package dao;

import java.io.File;
import java.sql.SQLException;
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
        
        testDao = new PaintProjectDao(testDb.getAbsolutePath());
        testDao.setUser(testUser_id);
        testDao.init();
        
        testDao.create(new PaintProject(testUser_id, "test_project", "test_category"));
    }
    
    @After
    public void tearDown() {
        testDb.delete();
    }
    
    @Test
    public void projectsAreReadCorrectlyFromDb() throws SQLException {
        List<PaintProject> projects = testDao.list();
        assertEquals(1, projects.size());
        PaintProject project = projects.get(0);
        assertEquals(1, project.getProjectId().intValue());
        assertEquals(1, project.getUserId().intValue());
        assertEquals("test_project", project.getProjectName());
        assertEquals("test_category", project.getProjectCategory());
        assertFalse(project.getProjectArchived());
        assertFalse(project.getProjectCompleted());
        assertFalse(project.getProjectIntrash());
    }
    
    @Test
    public void projectsCanBeCreatedAndAreReturnedCorrectly() throws SQLException {
        PaintProject project = testDao.create(new PaintProject(testUser_id, "test_otherProject", "test_category"));
        List<PaintProject> projects = testDao.list();
        assertEquals(2, projects.size());
        PaintProject sameProject = projects.get(1);
        assertEquals(2, sameProject.getProjectId().intValue());
        assertEquals(1, sameProject.getUserId().intValue());
        assertEquals("test_otherProject", sameProject.getProjectName());
        assertEquals("test_category", sameProject.getProjectCategory());
        assertFalse(sameProject.getProjectArchived());
        assertFalse(sameProject.getProjectCompleted());
        assertFalse(sameProject.getProjectIntrash());
    }
    
    @Test
    public void usersAreReadSuccessfully() throws SQLException {
        PaintProject project = testDao.read(1);
        assertEquals(1, project.getProjectId().intValue());
        assertEquals(1, project.getUserId().intValue());
        assertEquals("test_project", project.getProjectName());
        assertEquals("test_category", project.getProjectCategory());
        assertFalse(project.getProjectArchived());
        assertFalse(project.getProjectCompleted());
        assertFalse(project.getProjectIntrash());
    }
    
    @Test
    public void updatedUsersReturnedMatchTheUpdate() throws SQLException {
        PaintProject project = testDao.read(1);
        project.setProjectName("test_otherProject");
        PaintProject updatedProject = testDao.update(project);
        assertNotNull(project);
        assertEquals(1, project.getProjectId().intValue());
        assertEquals(1, project.getUserId().intValue());
        assertEquals("test_otherProject", project.getProjectName());
        assertEquals("test_category", project.getProjectCategory());
        assertFalse(project.getProjectArchived());
        assertFalse(project.getProjectCompleted());
        assertFalse(project.getProjectIntrash());
    }
    
    @Test
    public void deletedUsersAreRemovedFromDb() throws SQLException {
        testDao.create(new PaintProject(testUser_id, "test_otherProject", "test_category"));
        PaintProject projectToDelete = testDao.read(1);
        testDao.delete(1);
        PaintProject project = testDao.read(2);
        assertEquals(2, project.getProjectId().intValue());
        assertEquals(1, project.getUserId().intValue());
        assertEquals("test_otherProject", project.getProjectName());
        assertEquals("test_category", project.getProjectCategory());
        assertFalse(project.getProjectArchived());
        assertFalse(project.getProjectCompleted());
        assertFalse(project.getProjectIntrash());
        List<PaintProject> projects = testDao.list();
        assertEquals(1, projects.size());
        assertFalse(projects.contains(projectToDelete));
    }
}
