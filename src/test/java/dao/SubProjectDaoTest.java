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
    
    Integer testProjectId;
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
        testProjectId = 1;
        testDb = testFolder.newFile("test.db");
        
        testDao = new SubProjectDao(testDb.getAbsolutePath());
        testDao.init();
        
        testDao.create(new SubProject(testProjectId, "test_subproject"));
    }
    
    @After
    public void tearDown() {
        testDb.delete();
    }
    
    @Test
    public void subprojectsAreReadCorrectlyFromDb() throws SQLException {
        List<SubProject> subProjects = testDao.list();
        assertEquals(1, subProjects.size());
        SubProject subProject = subProjects.get(0);
        assertEquals(1, subProject.getSubProjectId().intValue());
        assertEquals(1, subProject.getProjectId().intValue());
        assertEquals("test_subproject", subProject.getSubProjectName());
        assertFalse(subProject.isSubProjectCompleted());
        assertFalse(subProject.isSubProjectInTrash());
    }
    
    @Test
    public void subprojectsCanBeCreatedAndAreReturnedCorrectly() throws SQLException {
        SubProject subProject = testDao.create(new SubProject(testProjectId, "test_otherSubProject"));
        List<SubProject> subProjects = testDao.list();
        assertEquals(2, subProjects.size());
        SubProject sameSubProject = subProjects.get(1);
        assertEquals(2, sameSubProject.getSubProjectId().intValue());
        assertEquals(1, sameSubProject.getProjectId().intValue());
        assertEquals("test_otherSubProject", sameSubProject.getSubProjectName());
        assertFalse(sameSubProject.isSubProjectCompleted());
        assertFalse(sameSubProject.isSubProjectInTrash());
    }
    
    @Test
    public void subprojectsAreReadSuccessfully() throws SQLException {
        SubProject subProject = testDao.read(1);
        assertEquals(1, subProject.getSubProjectId().intValue());
        assertEquals(1, subProject.getProjectId().intValue());
        assertEquals("test_subproject", subProject.getSubProjectName());
        assertFalse(subProject.isSubProjectCompleted());
        assertFalse(subProject.isSubProjectInTrash());
    }
    
    @Test
    public void updatedSubprojectsReturnedMatchTheUpdate() throws SQLException {
        SubProject subProject = testDao.read(1);
        subProject.setSubProjectName("test_otherSubProject");
        SubProject updatedSubProject = testDao.update(subProject);
        assertNotNull(subProject);
        assertEquals(1, updatedSubProject.getSubProjectId().intValue());
        assertEquals(1, updatedSubProject.getProjectId().intValue());
        assertEquals("test_otherSubProject", updatedSubProject.getSubProjectName());
        assertFalse(updatedSubProject.isSubProjectCompleted());
        assertFalse(updatedSubProject.isSubProjectInTrash());
    }
    
    @Test
    public void deletedSubprojectsAreRemovedFromDb() throws SQLException {
        testDao.create(new SubProject(testProjectId, "test_otherSubProject"));
        SubProject subProjectToDelete = testDao.read(1);
        testDao.delete(1);
        SubProject subProject = testDao.read(2);
        assertEquals(2, subProject.getSubProjectId().intValue());
        assertEquals(1, subProject.getProjectId().intValue());
        assertEquals("test_otherSubProject", subProject.getSubProjectName());
        assertFalse(subProject.isSubProjectCompleted());
        assertFalse(subProject.isSubProjectInTrash());
        List<SubProject> projects = testDao.list();
        assertEquals(1, projects.size());
        assertFalse(projects.contains(subProjectToDelete));
    }
}
