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
    
    @Test
    public void projectsAreReadCorrectlyFromDb() {
        List<PaintProject> projects = testDao.list();
        assertEquals(1, projects.size());
        PaintProject project = projects.get(0);
        assertEquals(1, project.getProject_id().intValue());
        assertEquals(1, project.getUser_id().intValue());
        assertEquals("test_project", project.getProject_name());
        assertEquals("test_category", project.getProject_category());
        assertFalse(project.getProject_archived());
        assertFalse(project.getProject_completed());
        assertFalse(project.getProject_intrash());
    }
    
    @Test
    public void projectsCanBeCreatedAndAreReturnedCorrectly() {
        PaintProject project = testDao.create(new PaintProject(testUser_id, "test_otherProject", "test_category"));
        List<PaintProject> projects = testDao.list();
        assertEquals(2, projects.size());
        PaintProject sameProject = projects.get(1);
        assertEquals(2, sameProject.getProject_id().intValue());
        assertEquals(1, sameProject.getUser_id().intValue());
        assertEquals("test_otherProject", sameProject.getProject_name());
        assertEquals("test_category", sameProject.getProject_category());
        assertFalse(sameProject.getProject_archived());
        assertFalse(sameProject.getProject_completed());
        assertFalse(sameProject.getProject_intrash());
    }
    
    @Test
    public void usersAreReadSuccessfully() {
        PaintProject project = testDao.read(1);
        assertEquals(1, project.getProject_id().intValue());
        assertEquals(1, project.getUser_id().intValue());
        assertEquals("test_project", project.getProject_name());
        assertEquals("test_category", project.getProject_category());
        assertFalse(project.getProject_archived());
        assertFalse(project.getProject_completed());
        assertFalse(project.getProject_intrash());
    }
    
    @Test
    public void updatedUsersReturnedMatchTheUpdate() {
        PaintProject project = testDao.read(1);
        project.setProject_name("test_otherProject");
        PaintProject updatedProject = testDao.update(project);
        assertNotNull(project);
        assertEquals(1, project.getProject_id().intValue());
        assertEquals(1, project.getUser_id().intValue());
        assertEquals("test_otherProject", project.getProject_name());
        assertEquals("test_category", project.getProject_category());
        assertFalse(project.getProject_archived());
        assertFalse(project.getProject_completed());
        assertFalse(project.getProject_intrash());
    }
    
    @Test
    public void deletedUsersAreRemovedFromDb() {
        testDao.create(new PaintProject(testUser_id, "test_otherProject", "test_category"));
        PaintProject projectToDelete = testDao.read(1);
        testDao.delete(1);
        PaintProject project = testDao.read(2);
        assertEquals(2, project.getProject_id().intValue());
        assertEquals(1, project.getUser_id().intValue());
        assertEquals("test_otherProject", project.getProject_name());
        assertEquals("test_category", project.getProject_category());
        assertFalse(project.getProject_archived());
        assertFalse(project.getProject_completed());
        assertFalse(project.getProject_intrash());
        List<PaintProject> projects = testDao.list();
        assertEquals(1, projects.size());
        assertFalse(projects.contains(projectToDelete));
    }
}
