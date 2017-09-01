package br.jus.trt3.seit.uim.probe.trt3diskspace;

import com.nimsoft.pf.common.log.Log;

/** SEE SAME-NAMED CLASS IN TRT3JBOSS !!!! REFACTOR.
 *
 * @author sergiomv
 */
public class ProbeHelper {
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
