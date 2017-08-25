package br.jus.trt3.seit.uim.probe.trt3jboss.customconfig;

import java.util.HashMap;
import java.util.Map;

/**
    { 
        "folder":"memorypool",
        "monitors":
        [
           {"name":"CMS_Old_Gen_Max","value":"java.lang:type=MemoryPool,name=CMS Old Gen\",Usage.max","type":"QOS_TRTJBOSS_MEMORY_USAGE"},
           {"name":"CMS_Old_Gen_Used","value":"java.lang:type=MemoryPool,name=CMS Old Gen\",Usage.used","type":"QOS_TRTJBOSS_MEMORY_USAGE"}
        ]
    }
 * 
 * 
 * 
 *
 * @author sergiomv
 */
public class Folder {
    
    private String name;
    
    private Map<String,Monitor> monitors = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void addMonitor(Monitor monitor) {
        String key = monitor.getName();
        if (monitors.containsKey(key)) {
            throw new IllegalArgumentException("Duplicate monitor: " + key + " in folder: " + getName());
        }
        monitors.put(key, monitor);
    }
    
    public Map<String, Monitor> getMonitors() {
        return monitors;
    }     
    
}
