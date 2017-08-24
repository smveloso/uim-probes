package br.jus.trt3.seit.uim.probe;

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
    
}
