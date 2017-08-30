package br.jus.trt3.seit.uim.probe.trt3jboss.customconfig;

/**
 * {"name":"CMS_Old_Gen_Max","value":"java.lang:type=MemoryPool,name=CMS Old Gen\",Usage.max","type":"QOS_TRTJBOSS_MEMORY_USAGE"},
 * 
 * @author sergiomv
 */
public class Monitor {

    // the 'target' name in uim. in practice, corresponds to a jmx item name
    private String name;

    // the jmx item path
    private String value;

    // the name of the qos object that is associated with this target
    // used to pick the 'element' to use when sending the data back
    // to the uim hub
    private String qos;

    // the value of the jmx item
    private Object metricValue;
    
    // wether the metricValue was successfully collected
    private boolean valueCollected;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getQos() {
        return qos;
    }

    public void setQos(String qos) {
        this.qos = qos;
    }

    public Object getMetricValue() {
        return metricValue;
    }

    public void setMetricValue(Object metricValue) {
        this.metricValue = metricValue;
    }

    public boolean isValueCollected() {
        return valueCollected;
    }

    public void setValueCollected(boolean valueCollected) {
        this.valueCollected = valueCollected;
    }
    
}
