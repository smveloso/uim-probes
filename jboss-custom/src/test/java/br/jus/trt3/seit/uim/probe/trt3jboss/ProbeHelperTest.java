package br.jus.trt3.seit.uim.probe.trt3jboss;

import br.jus.trt3.seit.uim.probe.TestUtil;
import br.jus.trt3.seit.uim.probe.trt3jboss.customconfig.CustomConfigVO;
import br.jus.trt3.seit.uim.probe.trt3jboss.customconfig.Profile;
import java.io.File;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sergiomv
 */
public class ProbeHelperTest {
    
    private static final Logger logger = Logger.getLogger(ProbeHelperTest.class);

    public ProbeHelperTest() {
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

    /**
     * Test of readCustomConfig method, of class ProbeHelper.
     */
    @Test
    public void testReadCustomConfig() throws Exception {
        logger.debug(">> testReadCustomConfig()");
        
        int EXPECTED_PROFILE_COUNT = 2;
        String EXPECTED_PROFILE_1_NAME = "instancia01";
        String EXPECTED_PROFILE_2_NAME = "anotherinstance";
        int EXPECTED_PROFILE1_FOLDER_COUNT = 2;
        int EXPECTED_PROFILE2_FOLDER_COUNT = 1;
        
        File customConfigFile = TestUtil.getTestFileObject("json-test-3.json");       
        CustomConfigVO customConfigVO = ProbeHelper.readCustomConfig(customConfigFile);        
        assertNotNull("got null vo",customConfigVO);
        assertEquals("wrong number of profiles",EXPECTED_PROFILE_COUNT,customConfigVO.getProfiles().size());
        assertTrue("profile 1 not found by name",customConfigVO.getProfiles().containsKey(EXPECTED_PROFILE_1_NAME));
        assertTrue("profile 2 not found by name",customConfigVO.getProfiles().containsKey(EXPECTED_PROFILE_2_NAME));
        
        Profile profile1 = customConfigVO.getProfiles().get(EXPECTED_PROFILE_1_NAME);
        Profile profile2 = customConfigVO.getProfiles().get(EXPECTED_PROFILE_2_NAME);
        
        assertNotNull("null when retrieving profile1",profile1);
        assertNotNull("null when retrieving profile2",profile2);
        assertEquals("profile 1 has wrong name",EXPECTED_PROFILE_1_NAME,profile1.getName());
        assertEquals("profile 2 has wrong name",EXPECTED_PROFILE_2_NAME,profile2.getName());
        
        assertEquals("profile 1 has wrong count of folders",EXPECTED_PROFILE1_FOLDER_COUNT,profile1.getFolders().size());
        assertEquals("profile 2 has wrong count of folders",EXPECTED_PROFILE2_FOLDER_COUNT,profile2.getFolders().size());
        
        logger.debug("<< testReadCustomConfig()");
    }
    
}
