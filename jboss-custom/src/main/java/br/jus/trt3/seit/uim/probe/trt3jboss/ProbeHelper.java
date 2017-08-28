package br.jus.trt3.seit.uim.probe.trt3jboss;

import br.jus.trt3.seit.uim.probe.JSONHelper;
import br.jus.trt3.seit.uim.probe.trt3jboss.customconfig.CustomConfigVO;
import br.jus.trt3.seit.uim.probe.trt3jboss.customconfig.Folder;
import br.jus.trt3.seit.uim.probe.trt3jboss.customconfig.Profile;
import com.nimsoft.nimbus.NimException;
import java.io.File;
import com.nimsoft.pf.common.log.Log;
import org.json.JSONArray;
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

            //TODO pre-validate the structure

            //TODO maybe I can use a newer version (jsonarray is iterable in newer versions)
            
            JSONArray profilesArray = jsonObject.getJSONArray("profiles");
                    
            for (int k=0; k<profilesArray.length();++k) {
                JSONObject jsonProfile = profilesArray.getJSONObject(k);
                Profile profile = extractProfile(jsonProfile);
                myLog("Adding profile " + profile.getName(),LogLevel.DEBUG);
                vo.addProfile(profile);
            }
            
            myLog("<< readCustomConfig(File)");
            return vo;
        
        } catch (JSONException mapped) {
            myLog("ERROR: " + mapped.getMessage(),LogLevel.ERROR);
            throw new NimException(NimException.E_ERROR,"ERROR: " + mapped.getMessage(),mapped);
        }
    }

    protected static Profile extractProfile(JSONObject jsonProfile) throws JSONException {
        Profile profile = new Profile();
        
        profile.setName(jsonProfile.getString("profile"));
        extractFolders(profile,jsonProfile);
        
        return profile;
    }

    private static void extractFolders(Profile profile, JSONObject jsonProfile) throws JSONException {
        JSONArray foldersArray = jsonProfile.getJSONArray("folders");
        for (int k=0;k<foldersArray.length();++k) {
            JSONObject jsonFolder = foldersArray.getJSONObject(k);
            Folder folder = extractFolder(jsonFolder);
            profile.addFolder(folder);
        }
    }
    
    private static Folder extractFolder(JSONObject jsonFolder) throws JSONException {
        Folder folder = new Folder();
        
        folder.setName(jsonFolder.getString("folder"));
        extractMonitors(folder,jsonFolder);
        
        return folder;
    }

    private static void extractMonitors(Folder folder, JSONObject jsonFolder) {
        
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
