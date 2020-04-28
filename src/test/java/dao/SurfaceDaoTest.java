package dao;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import maalausprojektikirjanpito.dao.SurfaceDao;
import maalausprojektikirjanpito.domain.Surface;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

public class SurfaceDaoTest {
    @Rule 
    public TemporaryFolder testFolder = new TemporaryFolder();
    
    Integer subprojectId;
    File testDb;
    SurfaceDao testDao;
    
    public SurfaceDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        subprojectId = 1;
        testDb = testFolder.newFile("test.db");
        
        testDao = new SurfaceDao(subprojectId, testDb.getAbsolutePath());
        testDao.init();
        
        testDao.create(new Surface(subprojectId, "test_surface"));
    }
    
    @After
    public void tearDown() {
        testDb.delete();
    }
    
    @Test
    public void surfacesAreReadCorrectlyFromDb() throws SQLException {
        List<Surface> surfaces = testDao.list();
        assertEquals(1, surfaces.size());
        Surface surface = surfaces.get(0);
        assertEquals(1, surface.getSubprojectId().intValue());
        assertEquals(1, surface.getSurfaceId().intValue());
        assertEquals("test_surface", surface.getSurfaceName());
        assertFalse(surface.getIsInTrash());
    }
    
    @Test
    public void surfacesCanBeCreatedAndAreReturnedCorrectly() throws SQLException {
        Surface surface = testDao.create(new Surface(subprojectId, "test_otherSurface"));
        List<Surface> surfaces = testDao.list();
        assertEquals(2, surfaces.size());
        Surface sameSurface = surfaces.get(1);
        assertEquals(2, sameSurface.getSurfaceId().intValue());
        assertEquals(1, sameSurface.getSubprojectId().intValue());
        assertEquals("test_otherSurface", sameSurface.getSurfaceName());
        assertFalse(sameSurface.getIsInTrash());
    }
    
    @Test
    public void usersAreReadSuccessfully() throws SQLException {
        Surface surface = testDao.read(1);
        assertEquals(1, surface.getSurfaceId().intValue());
        assertEquals(1, surface.getSubprojectId().intValue());
        assertEquals("test_surface", surface.getSurfaceName());
        assertFalse(surface.getIsInTrash());
    }
    
    @Test
    public void updatedUsersReturnedMatchTheUpdate() throws SQLException {
        Surface surface = testDao.read(1);
        surface.setSurfaceName("test_otherSurface");
        Surface updatedSurface = testDao.update(surface);
        assertNotNull(surface);
        assertEquals(1, surface.getSurfaceId().intValue());
        assertEquals(1, surface.getSubprojectId().intValue());
        assertEquals("test_otherSurface", surface.getSurfaceName());
        assertFalse(surface.getIsInTrash());
    }
    
    @Test
    public void deletedUsersAreRemovedFromDb() throws SQLException {
        testDao.create(new Surface(subprojectId, "test_otherSurface"));
        Surface surfaceToDelete = testDao.read(1);
        testDao.delete(1);
        Surface surface = testDao.read(2);
        assertEquals(2, surface.getSurfaceId().intValue());
        assertEquals(1, surface.getSubprojectId().intValue());
        assertEquals("test_otherSurface", surface.getSurfaceName());
        assertFalse(surface.getIsInTrash());
        List<Surface> surfaces = testDao.list();
        assertEquals(1, surfaces.size());
        assertFalse(surfaces.contains(surfaceToDelete));
    }
}
