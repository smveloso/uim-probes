package br.jus.trt3.seit.uim.probe.trt3diskspace;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sergiomv
 */
public class DfServiceTest {
    
    public DfServiceTest() {
    }

    @Test
    public void testGetPercentageUsed() throws Exception {
        
        DfService dfService = new DfService("/foo/df");
        
        String garbageOutput = "jfkdjalkjfsklajklsjda";

        try {
            dfService.extractPercentUsage(garbageOutput);
            fail("Garbage output did not fire exception");
        } catch (DfParseException expected) {
        }
        
        String typicalOutputFromDf_1 = "\nUse%\n  5%\n";
        assertEquals("Should be ok for 5%",5,dfService.extractPercentUsage(typicalOutputFromDf_1));

        String typicalOutputFromDf_2 = "\nUse%\n 15%\n";
        assertEquals("Should be ok for 15%",15,dfService.extractPercentUsage(typicalOutputFromDf_2));

        String typicalOutputFromDf_3 = "\nUse%\n 100%\n";
        assertEquals("Should be ok for 100%",100,dfService.extractPercentUsage(typicalOutputFromDf_3));
        
        try {
            String atypicalOutputFromDf_1 = "\nUse%\n 100%\n90%\n";
            dfService.extractPercentUsage(atypicalOutputFromDf_1);
            fail("Should have failed atypical 1");
        } catch (DfParseException expected) {
        }
    }
    
}
