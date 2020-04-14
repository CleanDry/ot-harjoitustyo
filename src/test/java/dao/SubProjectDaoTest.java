package dao;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import maalausprojektikirjanpito.dao.SubProjectDao;
import maalausprojektikirjanpito.domain.SubProject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 *
 * @author sampulli
 */
public class SubProjectDaoTest {
    @Rule 
    public TemporaryFolder testFolder = new TemporaryFolder();
    
    Integer testProject_id;
    File testDb;
    SubProjectDao testDao;
    
    public SubProjectDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        testProject_id = 1;
        testDb = testFolder.newFile("test.db");
        
        testDao = new SubProjectDao(testProject_id, testDb.getAbsolutePath());
        
        testDao.create(new SubProject(testProject_id, "test_subproject"));
    }
    
    @After
    public void tearDown() {
        testDb.delete();
    }
    
    @Test
    public void subprojectsAreReadCorrectlyFromDb() {
        List<SubProject> subProjects = testDao.list();
        assertEquals(1, subProjects.size());
        SubProject subProject = subProjects.get(0);
        assertEquals(1, subProject.getSubProject_id().intValue());
        assertEquals(1, subProject.getProject_id().intValue());
        assertEquals("test_subproject", subProject.getSubProject_name());
        assertFalse(subProject.isSubProject_completed());
        assertFalse(subProject.isSubProject_isInTrash());
    }
    
//    @Test
//    public void subprojectsCanBeCreatedAndAreReturnedCorrectly() throws SQLException {
//        SubProject subProject = testDao.create(new SubProject(testProject_id, "test_otherSubProject"));
//        List<SubProject> subProjects = testDao.list();
//        assertEquals(2, subProjects.size());
//        SubProject sameSubProject = subProjects.get(1);
//        assertEquals(2, project.getProject_id().intValue());
//        assertEquals(1, project.getUser_id().intValue());
//        assertEquals("test_otherProject", project.getProject_name());
//        assertFalse(project.getProject_archived());
//        assertFalse(project.getProject_completed());
//        assertFalse(project.getProject_intrash());
//    }
//    
//    @Test
//    public void usersAreReadSuccessfully() {
//        SubProject project = testDao.read(1);
//        assertEquals(1, project.getProject_id().intValue());
//        assertEquals(1, project.getUser_id().intValue());
//        assertEquals("test_project", project.getProject_name());
//        assertEquals("test_category", project.getProject_category());
//        assertFalse(project.getProject_archived());
//        assertFalse(project.getProject_completed());
//        assertFalse(project.getProject_intrash());
//    }
//    
//    @Test
//    public void updatedUsersReturnedMatchTheUpdate() {
//        SubProject project = testDao.read(1);
//        project.setProject_name("test_otherProject");
//        SubProject updatedProject = testDao.update(project);
//        assertNotNull(project);
//        assertEquals(1, project.getProject_id().intValue());
//        assertEquals(1, project.getUser_id().intValue());
//        assertEquals("test_otherProject", project.getProject_name());
//        assertEquals("test_category", project.getProject_category());
//        assertFalse(project.getProject_archived());
//        assertFalse(project.getProject_completed());
//        assertFalse(project.getProject_intrash());
//    }
//    
//    @Test
//    public void deletedUsersAreRemovedFromDb() {
//        testDao.create(new SubProject(testUser_id, "test_otherProject", "test_category"));
//        SubProject projectToDelete = testDao.read(1);
//        testDao.delete(1);
//        SubProject project = testDao.read(2);
//        assertEquals(2, project.getProject_id().intValue());
//        assertEquals(1, project.getUser_id().intValue());
//        assertEquals("test_otherProject", project.getProject_name());
//        assertEquals("test_category", project.getProject_category());
//        assertFalse(project.getProject_archived());
//        assertFalse(project.getProject_completed());
//        assertFalse(project.getProject_intrash());
//        List<SubProject> projects = testDao.list();
//        assertEquals(1, projects.size());
//        assertFalse(projects.contains(projectToDelete));
//    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
