package br.jus.trt3.seit.uim.probe.trt3jboss.customconfig;

import java.util.HashMap;
import java.util.Map;

/**

   {
    "profile":"instancia01",
    "folders":
    [
    (...)
 
 * @author sergiomv
 */
public class Profile {

    private String name;
    
    private Map<String,Folder> folders = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Folder> getFolders() {
        return folders;
    }

    public void addFolder(Folder folder) {
        String key = folder.getName();
        if (folders.containsKey(key)) {
            throw new IllegalArgumentException("Duplicate folder: " + key + " in profile: " + getName());
        }
        folders.put(key, folder);
    }
    
}
