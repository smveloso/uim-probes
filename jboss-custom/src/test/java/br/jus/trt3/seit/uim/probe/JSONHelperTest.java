package br.jus.trt3.seit.uim.probe;

import org.apache.log4j.Logger;
import org.json.JSONArray;
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
        
        // ------ 1
        
        String testFileName = "json-test-1.json";
        String jsonText = TestUtil.getAllLinesAsString(testFileName);
        
        String key1 = "CMS_Old_Gen";
        String expected1 = "java.lang:type=MemoryPool,name=CMS Old Gen\",Usage.max";
        
        JSONObject jsonObject = JSONHelper.getJSONObject(jsonText);
        
        assertNotNull("1. null value",jsonObject.getString(key1));
        assertEquals("1. wrong value",expected1,jsonObject.getString(key1));
        assertEquals("1. wrong value len",53,jsonObject.getString(key1).length());
        assertFalse("1. escaping failed",jsonObject.getString(key1).contains("\\"));

        // ------ 2
        
        testFileName = "json-test-2.json";
        jsonText = TestUtil.getAllLinesAsString(testFileName);

        String keyElements = "elements";
        String keyElement = "element";
        String keyMonitors = "monitors";
        
        String keyMonitor_1_1 = "CMS_Old_Gen_Max";
        String keyMonitor_1_2 = "CMS_Old_Gen_Used";
        String keyMonitor_2_1 = "GC_ParNew_Colletction_Count";
        String keyMonitor_2_2 = "GC_CMS_Collection_Count";
        
        String valueMonitor_1_1 = "java.lang:type=MemoryPool,name=CMS Old Gen\",Usage.max";
        String valueMonitor_1_2 = "java.lang:type=MemoryPool,name=CMS Old Gen\\\",Usage.used";
        String valueMonitor_2_1 = "java.lang:type=GarbageCollector,name=ParNew\\\",CollectionCount";
        String valueMonitor_2_2 = "java.lang:type=GarbageCollector,name=ConcurrentMarkSweep\\\",CollectionCount";
                
        jsonObject = JSONHelper.getJSONObject(jsonText);
        JSONArray elements = jsonObject.getJSONArray(keyElements);
        assertNotNull("2. null value for elements array",elements);
        assertEquals("2. wrong nro of elements",2,elements.length());
                
        JSONObject element0 = elements.getJSONObject(0);
        JSONObject element1 = elements.getJSONObject(1);
        
        assertNotNull("2. null element 0 ",element0);
        assertNotNull("2. null element 1 ",element1);
        
        // JSONArrays are ordered.
        assertEquals("2. wrong element id 0","memorypool",element0.getString(keyElement));
        assertEquals("2. wrong element id 1","gc",element1.getString(keyElement));
        
        JSONArray element0monitors = element0.getJSONArray(keyMonitors);
        JSONArray element1monitors = element1.getJSONArray(keyMonitors);
        
        assertEquals("2. wrong monitor count for memorypool",2,element0monitors.length());
        assertEquals("2. wrong monitor count for gc",2,element1monitors.length());

        JSONObject element0monitors0 = element0monitors.getJSONObject(0);
        
        
        assertEquals("2. wrong name for monitor 0 of element 0",keyMonitor_1_1,element0monitors0.getString("name"));
        assertEquals("2. wrong value for monitor 0 of element 0",valueMonitor_1_1,element0monitors0.getString("value"));
        assertEquals("2. wrong type for monitor 0 of element 0","B",element0monitors0.getString("type"));
        
        // ------ 3
        
        testFileName = "json-test-3.json";
        jsonText = TestUtil.getAllLinesAsString(testFileName);

        String rootKey = "profiles";
        String keyProfile = "profile";
        String keyFolders = "folders";
        String keyFolder = "folder";
        keyMonitors = "monitors";
        
        String keyMonitorName = "name";
        String keyMonitorValue = "value";
        String keyMonitorType = "type";
        
        jsonObject = JSONHelper.getJSONObject(jsonText);
        
        assertNotNull("3. null jsonobject",jsonObject);
        JSONArray profiles = jsonObject.getJSONArray(rootKey);
        int profilesLength = 2;
        assertNotNull("3. null root jsonarray",profiles);
        assertEquals("3. root jsonarray has wrong number of elements",profilesLength,profiles.length());
        
        String firstProfileName = "instancia01";
        String secondProfileName = "anotherinstance";
        
        assertEquals("3. 1st profile has wrong name",firstProfileName,profiles.getJSONObject(0).getString(keyProfile));
        assertEquals("3. 2nd profile has wrong name",secondProfileName,profiles.getJSONObject(1).getString(keyProfile));
        
        JSONArray firstProfileFolders = profiles.getJSONObject(0).getJSONArray(keyFolders);
        assertNotNull("3. 1st profile folders array is null",firstProfileFolders);
        assertEquals("3. 1st profile folders array has wrong length",2,firstProfileFolders.length());
        
        JSONArray secondProfileFolders = profiles.getJSONObject(1).getJSONArray(keyFolders);
        assertNotNull("3. 2nd profile folders array is null",secondProfileFolders);
        assertEquals("3. 2nd profile folders array has wrong length",1,secondProfileFolders.length());
        
        String firstProfileFolderOneName = "memorypool";
        String firstProfileFolderTwoName = "gc";
        
        assertEquals("3. 1st profile folder one has wrong name",firstProfileFolderOneName,firstProfileFolders.getJSONObject(0).getString(keyFolder));
        assertEquals("3. 1st profile folder two has wrong name",firstProfileFolderTwoName,firstProfileFolders.getJSONObject(1).getString(keyFolder));
        
        assertEquals("3. 1st profile folder one has wrong number of monitors",2,firstProfileFolders.getJSONObject(0).getJSONArray(keyMonitors).length());

        JSONObject firstProfileFolderOneMonitorOne = firstProfileFolders.getJSONObject(0).getJSONArray(keyMonitors).getJSONObject(0);
        assertNotNull("3. 1st profile folder one monitor one is null", firstProfileFolderOneMonitorOne);
        assertEquals("3. 1st profile folder one monitor one has wrong name", "CMS_Old_Gen_Max" , firstProfileFolderOneMonitorOne.getString(keyMonitorName));
        assertEquals("3. 1st profile folder one monitor one has wrong value", "java.lang:type=MemoryPool,name=CMS Old Gen\",Usage.max" , firstProfileFolderOneMonitorOne.getString(keyMonitorValue));
        assertEquals("3. 1st profile folder one monitor one has wrong type", "QOS_TRTJBOSS_MEMORY_USAGE" , firstProfileFolderOneMonitorOne.getString(keyMonitorType));
        
        logger.debug("<< testGetJSONObject()");
    }
    
}
