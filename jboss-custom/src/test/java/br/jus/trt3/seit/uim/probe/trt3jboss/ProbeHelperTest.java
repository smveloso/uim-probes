package br.jus.trt3.seit.uim.probe.trt3jboss;

import br.jus.trt3.seit.uim.probe.TestUtil;
import br.jus.trt3.seit.uim.probe.trt3jboss.customconfig.CustomConfigVO;
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
        
        File customConfigFile = TestUtil.getTestFileObject("json-test-3.json");       
        CustomConfigVO customConfigVO = ProbeHelper.readCustomConfig(customConfigFile);        
        assertNotNull("got null vo",customConfigVO);
        
        assertEquals("wrong number of profiles",EXPECTED_PROFILE_COUNT,customConfigVO.getProfiles().size());
        
        logger.debug("<< testReadCustomConfig()");
    }
    
}
