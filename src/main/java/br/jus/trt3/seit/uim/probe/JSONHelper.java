package br.jus.trt3.seit.uim.probe;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author sergiomv
 */
public class JSONHelper {

    public static JSONObject getJSONObject(String text) throws JSONException {
        return new JSONObject(text);
    }
    
    public static JSONObject getJSONObject(File file) throws JSONException {
        try {
            String text = getFileText(file);
            return getJSONObject(text);
        } catch (Trt3ProbeException mapped) {
            throw new JSONException("Error processing file: " + mapped.getMessage());
        }
    }
    
    protected static String getFileText(File file) throws Trt3ProbeException {

        if (!file.exists() || !file.canRead()) {
            throw new Trt3ProbeException("Cannot find or read file: " + file.getAbsolutePath());
        }

        LineNumberReader lnr = null;
        
        try {
                        
            StringBuilder sb;
            sb = new StringBuilder();

            lnr = new LineNumberReader(new FileReader(file));

            String line;
            do {
                line = lnr.readLine();
                if (null != line) {
                    sb.append(line);
                }
            } while (line != null);        

            return sb.toString();
        
        } catch (IOException mapped) {                
            throw new Trt3ProbeException("IO error: " + mapped.getMessage(),mapped);
        } finally {
            try {
                if (null != lnr) {
                    lnr.close();
                }
            } catch (IOException ignored) {
            }
        }
    }
}
