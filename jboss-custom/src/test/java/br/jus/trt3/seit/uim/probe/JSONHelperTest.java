package br.jus.trt3.seit.uim.probe;

import org.apache.log4j.Logger;
import org.json.JSONObject;
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
public class JSONHelperTest {
    
    private static final Logger logger = Logger.getLogger(JSONHelperTest.class);
    
    public JSONHelperTest() {
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
    @Test
    public void testGetJSONObject() throws Exception {
        logger.debug(">> testGetJSONObject()");
        
        String testFileName = "json-test-1.json";
        String jsonText = TestUtil.getAllLinesAsString(testFileName);
        
        String key1 = "CMS_Old_Gen";
        String expected1 = "java.lang:type=MemoryPool,name=CMS Old Gen\",Usage.max";
        
        JSONObject jsonObject = JSONHelper.getJSONObject(jsonText);
        
        assertNotNull("1. null value",jsonObject.getString(key1));
        assertEquals("1. wrong value",expected1,jsonObject.getString(key1));
        assertEquals("1. wrong value len",53,jsonObject.getString(key1).length());
        assertFalse("1. escaping failed",jsonObject.getString(key1).contains("\\"));
        
        logger.debug("<< testGetJSONObject()");
    }
    
}
