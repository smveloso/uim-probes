package br.jus.trt3.seit.uim.probe.trt3jboss.customconfig;

import java.util.HashMap;
import java.util.Map;

/**
 * 

A profile has 'n' folders, each one with 'm' targets.

profile ---> uim profile.
folder  ---> no correspondence. ui effect.
target  ---> uim target, possibly combined with folder.
a target´s (single) jmx item corresponds to a (single) uim monitor
 
{"profiles":
 [
    {
    "profile":"instancia01",
    "folders":
    [
    
        { 
        "folder":"memorypool",
        "monitors":
        [
           {"name":"CMS_Old_Gen_Max","value":"java.lang:type=MemoryPool,name=CMS Old Gen\",Usage.max","type":"QOS_TRTJBOSS_MEMORY_USAGE"},
           {"name":"CMS_Old_Gen_Used","value":"java.lang:type=MemoryPool,name=CMS Old Gen\",Usage.used","type":"QOS_TRTJBOSS_MEMORY_USAGE"}
        ]
        },
        { 
        "folder":"gc",
        "monitors":
        [
           {"name":"GC_ParNew_Colletction_Count","value":"java.lang:type=GarbageCollector,name=ParNew\",CollectionCount","type":"QOS_TRTJBOSS_GENERIC_COUNTER"},
           {"name":"GC_CMS_Collection_Count","value":"java.lang:type=GarbageCollector,name=ConcurrentMarkSweep\",CollectionCount","type":"QOS_TRTJBOSS_GENERIC_COUNTER"}
        ]
        }
    
    ]
    },
    {
    "profile":"anotherinstance",
    "folders": [...]
    }
 ]
}
 *
 * @author sergiomv
 */
public class CustomConfigVO {
    
    private Map<String,Profile> profiles = new HashMap<>();

    public Map<String, Profile> getProfiles() {
        return profiles;
    }

    public void addProfile(Profile profile) {
        String key = profile.getName();
        if (profiles.containsKey(key)) {
            throw new IllegalArgumentException("Duplicate profile: " + key);
        }
        profiles.put(key, profile);
    }
    
}

