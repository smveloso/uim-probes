package br.jus.trt3.seit.uim.probe.trt3jboss;

import br.jus.trt3.seit.uim.probe.JSONHelper;
import br.jus.trt3.seit.uim.probe.trt3jboss.customconfig.CustomConfigVO;
import com.nimsoft.nimbus.NimException;
import java.io.File;
import com.nimsoft.pf.common.log.Log;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author sergiomv
 */
public class ProbeHelper {

    // hard to test if left in ProbeMain (no default constructor)
    protected static CustomConfigVO readCustomConfig(File customConfigFile) throws NimException {
        myLog(">> readCustomConfig(File) -- " + customConfigFile.getAbsolutePath());

        try {
            CustomConfigVO vo = new CustomConfigVO();
            JSONObject jsonObject = JSONHelper.getJSONObject(customConfigFile);

            myLog("<< readCustomConfig(File)");
            return vo;
        
        } catch (JSONException mapped) {
            myLog("ERROR: " + mapped.getMessage(),LogLevel.ERROR);
            throw new NimException(NimException.E_ERROR,"ERROR: " + mapped.getMessage(),mapped);
        }
    }

    /** Logs a message as INFO
     *  Uses nimsoft´s log class
     * 
     * @param message Text to write to log output.
     */
    protected static void myLog(String message) {
        myLog(message,LogLevel.INFO);
    }

    /** Logs a message using specified level.
     *  Uses nimsoft´s log class
     * 
     * @param message Text to write to log output.
     * @param level
     */    
    protected static void myLog(String message, LogLevel level) {
        message = "[TRT3] |" + message;
        switch(level.ordinal()) {
           case 0:
               Log.fatal(message);
               break;
           case 1:
               Log.error(message);
               break;
           case 2:
               Log.warn(message);
               break;
           case 3:
               Log.info(message);
               break;
           case 4:
               Log.debug(message);
               break;
           case 5:
               Log.trace(message);
               break;
           default:
               throw new RuntimeException("LOGLEVEL UNKOWN: " + level.ordinal());
       }
    }

    
}
