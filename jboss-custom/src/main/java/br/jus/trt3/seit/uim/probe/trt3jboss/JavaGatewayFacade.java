package br.jus.trt3.seit.uim.probe.trt3jboss;

import br.jus.trt3.seit.uim.probe.trt3jboss.customconfig.Monitor;
import com.zabbix.gateway.JMXItemChecker;
import com.zabbix.gateway.ZabbixException;
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
    
    public void collect(List<ProbeMain.ElementMonitorHolder> list) throws JavaGatewayException {
        
        try {
            JSONObject request = new JSONObject();

            request.put("request","java gateway jmx");
            request.put("conn",jbossServer);
            request.put("port",jbossInstancePort);
            request.put("jbossVersion",jbossVersion);

            //TODO username
            //TODO password

            List<String> keyList = new ArrayList<String>();

            for (ProbeMain.ElementMonitorHolder holder:list) {
                String jmxItem = holder.getMonitor().getValue();
                jmxItem = "jmx[" + jmxItem + "]";
                keyList.add(jmxItem);
            }

            request.put("keys", keyList);

            JMXItemChecker jmxItemChecker = new JMXItemChecker(request);

            ProbeHelper.myLog("About to invoke checker.getValues() ...");
            JSONArray response = jmxItemChecker.getValues();
            ProbeHelper.myLog("... back from checker.getValues()!");
            
            for (int k=0; k<response.length();++k) {
                JSONObject jsonObject = response.getJSONObject(k);
                Monitor monitor = list.get(k).getMonitor();
                if (null == jsonObject.optString("error") && null != jsonObject.optString("value")) {
                    String value = jsonObject.getString("value");
                    monitor.setMetricValue(value);
                    monitor.setValueCollected(true);
                } else {
                    ProbeHelper.myLog("Failed to collect: " + monitor.getQos() + "[" + monitor.getValue() + "]",LogLevel.WARN);
                    ProbeHelper.myLog("ERROR: " + jsonObject.getString("error"),LogLevel.WARN);
                    monitor.setValueCollected(false);
                }
            }

        } catch (JSONException mapped) {
            String msg = "JSON EXCEPTION: " + mapped.getMessage();
            ProbeHelper.myLog(msg, LogLevel.ERROR);
            throw new JavaGatewayException(msg,mapped);
        } catch (ZabbixException mapped) {
            String msg = "ZABBIX EXCEPTION: " + mapped.getMessage();
            ProbeHelper.myLog(msg, LogLevel.ERROR);
            throw new JavaGatewayException(msg,mapped);            
        }
        
    }
        
}
