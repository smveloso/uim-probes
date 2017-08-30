package br.jus.trt3.seit.uim.probe.trt3jboss;

import br.jus.trt3.seit.uim.probe.trt3jboss.customconfig.Monitor;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author sergiomv
 */
public class JavaGatewayFacade {
        
    /** The name or ip address of the jboss server host */
    private String jbossServer;
    
    /** The tcp port of the target jboss instance */
    private Integer jbossInstancePort;
    
    /** The version (4-10) of the jboss server */
    private Integer jbossVersion;

    public String getJbossServer() {
        return jbossServer;
    }

    public void setJbossServer(String jbossServer) {
        this.jbossServer = jbossServer;
    }

    public Integer getJbossInstancePort() {
        return jbossInstancePort;
    }

    public void setJbossInstancePort(Integer jbossInstancePort) {
        this.jbossInstancePort = jbossInstancePort;
    }

    public Integer getJbossVersion() {
        return jbossVersion;
    }

    public void setJbossVersion(Integer jbossVersion) {
        this.jbossVersion = jbossVersion;
    }
    
    public void collect(ProbeMain.ElementMonitorList list) throws JSONException {
        
        JSONObject request = new JSONObject();
        
        request.put("request","java gateway jmx");
        request.put("conn",jbossServer);
        request.put("port",jbossInstancePort);
        request.put("jbossVersion",jbossVersion);
        
        //TODO username
        //TODO password
        
        List<String> keyList = new ArrayList<String>();
        
        for (ProbeMain.ElementMonitorHolder holder:list.getList()) {
            String jmxItem = holder.getMonitor().getValue();
            jmxItem = "jmx[" + jmxItem + "]";
            keyList.add(jmxItem);
        }
        
        request.put("keys", keyList);
        
        //TODO will need JMXItemChecker to be public
        //JMXItemChecker jmxItemChecker = new JMXItemChecker(request);
        JSONArray response = null;
        //JSONArray response = jmxItemChecker.getValues();
        
        for (int k=0; k<response.length();++k) {
            JSONObject jsonObject = response.getJSONObject(k);
            if (null == jsonObject.optString("error") && null != jsonObject.optString("value")) {
                String value = jsonObject.getString("value");
                list.getList().get(k).getMonitor().setMetricValue(value);
                list.getList().get(k).getMonitor().setValueCollected(true);
            };
            
        }
        
    }
    

    
}
