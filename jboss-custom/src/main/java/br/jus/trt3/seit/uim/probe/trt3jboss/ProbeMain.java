package br.jus.trt3.seit.uim.probe.trt3jboss;

import br.jus.trt3.seit.uim.probe.trt3jboss.customconfig.CustomConfigVO;
import br.jus.trt3.seit.uim.probe.Trt3ProbeException;
import br.jus.trt3.seit.uim.probe.Util;
import br.jus.trt3.seit.uim.probe.trt3jboss.customconfig.Folder;
import br.jus.trt3.seit.uim.probe.trt3jboss.customconfig.Monitor;
import br.jus.trt3.seit.uim.probe.trt3jboss.customconfig.Profile;

import com.nimsoft.pf.common.pom.MvnPomVersion;
import com.nimsoft.nimbus.NimException;
import com.nimsoft.pf.common.log.Log;
import com.nimsoft.probe.framework.devkit.ProbeBase;
import com.nimsoft.probe.framework.devkit.interfaces.IProbeInventoryCollection;
import com.nimsoft.probe.framework.devkit.interfaces.IInventoryDataset;
import com.nimsoft.probe.framework.devkit.InventoryDataset;
import com.nimsoft.probe.framework.devkit.configuration.CtdPropertyDefinitionsList;
import com.nimsoft.probe.framework.devkit.configuration.ResourceConfig;
import com.nimsoft.probe.framework.devkit.inventory.Element;
import com.nimsoft.probe.framework.devkit.inventory.typedefs.*;
import com.nimsoft.vm.cfg.IProbeResourceTypeInfo;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProbeMain extends ProbeBase implements IProbeInventoryCollection {

    /*
     * Probe Name, Version, and Vendor are required when initializing the probe.
     */
    public final static  String PROBE_NAME = "trt3jboss";
    public static final  String PROBE_VERSION = MvnPomVersion.get("br.jus.trt3.seit.uim.probe", PROBE_NAME);
    public static final  String PROBE_VENDOR = "TRT-3";
    
    
    private static final String DEFAULT_TIMEOUT_PROP = "default_timeout";
    
    /** 4 - JBoss AS 4.x
     *  5 - JBoss AS 5.x
     *  6 - JBoss AS 6.x
     *  7 - JBoss AS 7.x
     *  8 - Wildfly
     */
    private static final String PROFILE_JBOSS_VERSION_PROP = "jboss_version";
    
    private static final String PROFILE_JBOSS_IP_PROP = "jboss_ip";

    private static final String PROFILE_JBOSS_PORT_PROP = "jboss_port";
    
    private static final String PROFILE_JBOSS_CUSTOM_CONFIG_FILE_PROP = "jboss_custom_config_file";

    
    private static final Integer MIN_JBOSS_VERSION = 4;
    private static final Integer MAX_JBOSS_VERSION = 8;

    private static final Integer MIN_JBOSS_PORT = 1025;
    private static final Integer MAX_JBOSS_PORT = 65535;
    
    /**
     * Every probe is a stand alone Java program that must start itself up and
     * register itself with the bus. The Probe Framework provides all the logic
     * for doing this in the {@code ProbeBase} base class. So all we must do when
     * implementing a probe is create a simple {@code main()} method that invokes
     * the proper life cycle methods in {@code ProbeBase}
     *
     * @param args
     */
    public static void main(final String[] args) {
        try {
            ProbeBase.initLogging(args);
            ProbeMain probeProcess = new ProbeMain(args);
            Log.info("Probe " + PROBE_NAME + " startup"); 
            probeProcess.execute();
        } catch (final Exception e) {
            ProbeBase.reportProbeStartupError(e, PROBE_NAME);
        }
    }

    /**
     * You must implement a constructor that calls the super constructor 
     * and passes in the parameters shown below. You will call your 
     * constructor from the main() method and you may modify the method
     * signature to suit your needs. For example if you wanted to pass some
     * additional arguments. 
     * @throws NimException
     */
    public ProbeMain(String[] args) throws NimException {
        super(args, PROBE_NAME, PROBE_VERSION, PROBE_VENDOR);
        ProbeHelper.myLog(">> ProbeMain(String[])", LogLevel.DEBUG);
        // Indicate this is a local probe
        setLocalMode(true);
        //useShortTargetName(true); local_dirscan does this
        ProbeHelper.myLog("<< ProbeMain(String[])", LogLevel.DEBUG);
    }
    
    /**
     * This is where you configure what you want to display in the probe configuration UI.
     * 
     * Note: Implementing this method is optional. If your probe does not require the ability to
     * specify configuration options in the Probe Configuration UI then you may skip implementing
     * this method.
     */
    @Override
    public void addDefaultProbeConfigurationToGraph() {
        
        ProbeHelper.myLog(">> addDefaultProbeConfigurationToGraph()", LogLevel.DEBUG);
        
        // Add standard actions to add/delete/verify a profile        
        ElementDef resDef = ElementDef.getElementDef("RESOURCE");
        resDef.addStandardAction(IProbeResourceTypeInfo.StandardActionType.DeleteProfileAction);
        resDef.addStandardAction(IProbeResourceTypeInfo.StandardActionType.VerifySelectionAction, "Verify Profile Configuration");
        resDef.addStandardAction(IProbeResourceTypeInfo.StandardActionType.AddProfileActionOnProbe, "Add Profile");
        
        // add SETUP properties
        CtdPropertyDefinitionsList setupPropDefs = CtdPropertyDefinitionsList.createCtdPropertyDefinitionsList("SETUP", getGraph());

        // adds the traversal depth property, with a default value
        setupPropDefs.addIntegerPropertyUsingEditField(DEFAULT_TIMEOUT_PROP, "Default Timeout (s)", 30);
        
        // Set the properties that will be available when a new profile is created in the probe configuration UI
        CtdPropertyDefinitionsList profilePropDefs = CtdPropertyDefinitionsList.createCtdPropertyDefinitionsList("RESOURCE", getGraph());
        profilePropDefs.addStandardIdentifierProperty();
        profilePropDefs.addStandardAlarmMessageProperty();
        profilePropDefs.addStandardIntervalProperty();
        profilePropDefs.addStandardActiveProperty();

        profilePropDefs.addIntegerPropertyUsingEditField(PROFILE_JBOSS_VERSION_PROP, "JBoss Version", ProfileVO.PROFILE_JBOSS_DEFAULT_VERSION);
        profilePropDefs.setCfgPathname(PROFILE_JBOSS_VERSION_PROP, "properties/"+PROFILE_JBOSS_VERSION_PROP);
        
        profilePropDefs.addStringPropertyUsingEditField(PROFILE_JBOSS_IP_PROP, "Server IP", ProfileVO.PROFILE_JBOSS_DEFAULT_IP);
        profilePropDefs.setCfgPathname(PROFILE_JBOSS_IP_PROP, "properties/"+PROFILE_JBOSS_IP_PROP);

        profilePropDefs.addIntegerPropertyUsingEditField(PROFILE_JBOSS_PORT_PROP, "Server Port", ProfileVO.PROFILE_JBOSS_DEFAULT_PORT);
        profilePropDefs.setCfgPathname(PROFILE_JBOSS_PORT_PROP, "properties/"+PROFILE_JBOSS_PORT_PROP);

        profilePropDefs.addStringPropertyUsingEditField(PROFILE_JBOSS_CUSTOM_CONFIG_FILE_PROP, "Config File", ProfileVO.PROFILE_JBOSS_DEFAULT_CUSTOM_CONFIG_FILE);
        profilePropDefs.setCfgPathname(PROFILE_JBOSS_CUSTOM_CONFIG_FILE_PROP, "properties/"+PROFILE_JBOSS_CUSTOM_CONFIG_FILE_PROP);        
        
        // You must always invoke the super method
        super.addDefaultProbeConfigurationToGraph();
        ProbeHelper.myLog("<< addDefaultProbeConfigurationToGraph()", LogLevel.DEBUG);
    }
 
    /**
     * Allows the user to test a profile configuration from the UI by using 
     * the pull down: Actions->verify.
     * </p>
     * All probe framework probes must implement this method.
     * </p>
     * The method should be implemented to validate the probe configuration. For example
     * if the configuration specifies remote system connectivity information. If unable 
     * to successfully verify the configuration then this method should throw an {@code Exception}
     * with a message about the nature of the problem.
     * </p>
     * Note: The tests performed here do not need to be limited to connectivity. You should verify
     * anything and everything related to your probe configuration.
     * 
     * Note: The methods {@code testResource()} and {@code getUpdatedInventory()} are both from the 
     * interface {@code IProbeInventoryCollection}. You will need to implement these methods if your 
     * probe implements {@code IProbeInventoryCollection}. Please see the JavaDoc on that interface 
     * for more details.
     * 
     * @param resource Configuration information.
     *
     * @return IInventoryDataset Optional. This is reserved for a future enhancement for an advanced probe 
     * to provide additional resource configuration information. However this is presently not used,
     * so the best practice is to return {@code null}
     *
     * @throws Exception if any errors are encountered during the testing of the configuration.
     */
    @Override
    public IInventoryDataset testResource(ResourceConfig res) throws NimException, InterruptedException {  
        ProbeHelper.myLog(">> testResource(ResourceConfig) for " + res.getName());
        validateResourceConfiguration(res);
        ProbeHelper.myLog("<< testResource(ResourceConfig)");
        return null;
    }
    
    /**
     * This is called by the framework on the inventory collection cycle. In this method 
     * we construct the inventory, and attach metrics. We always attach all metrics, and the
     * framework will determine which ones to publish based on how the probe is configured.
     * 
     * We return data to the framework in both the returned InventoryDataset, AND the passed
     * in ResourceConfig. Every inventory Element you create will be attached to the InventoryDataset,
     * those Elements must also be constructed in a hierarchy attached to ResourceConfig.
     * 
     * Note: The methods {@code testResource()} and {@code getUpdatedInventory()} are both from the 
     * interface {@code IProbeInventoryCollection}. You will need to implement these methods if your 
     * probe implements {@code IProbeInventoryCollection}. Please see the JavaDoc on that interface 
     * for more details.
     * @param resourceConfig
     * @param previousDataset
     * @return 
     * @throws com.nimsoft.nimbus.NimException
     * @throws java.lang.InterruptedException
     */
    @Override
    public IInventoryDataset getUpdatedInventory(ResourceConfig resourceConfig, IInventoryDataset previousDataset) throws NimException, InterruptedException {
        ProbeHelper.myLog(">> getUpdatedInventory(ResourceConfig,IInventoryDataset)");
        int counter = resourceConfig.updateCounter;
        String profileName = resourceConfig.getName();
        
        ProbeHelper.myLog("Pass    : " + counter);
        ProbeHelper.myLog("Profile : " + profileName);
        
        ProfileVO voProfile = validateResourceConfiguration(resourceConfig);
        ProbeHelper.myLog("vo profile:");
        ProbeHelper.myLog(voProfile.toString());

        // Create a new empty InventoryDataset
        InventoryDataset inventoryDataset = new InventoryDataset(resourceConfig);
        
        File customConfigFile = voProfile.getCustomConfigFile();
        
        // we dont want an error if the file is not there
        if (customConfigFile.exists()) {        
            CustomConfigVO voConfig = ProbeHelper.readCustomConfig(customConfigFile);
            buildStructure(inventoryDataset,resourceConfig,voProfile,voConfig,profileName);
        } else {
            ProbeHelper.myLog("COULD NOT FIND CUSTOM CONFIG FILE: " + customConfigFile.getName());
        }
        
        ProbeHelper.myLog("<< getUpdatedInventory(...)");
        return inventoryDataset;
    }
    
    /** Invoked from 'testResource'.
     * 
     *  Will throw an Exception if validation fails,
     *  otherwise will return a VO with the configuration
     *  data.
     * 
     *  PROFILE_JBOSS_VERSION_PROP must be null or between 4 and 8.
     *  PROFILE_JBOSS_PORT_PROP must be null or indicate a valid tcp port.
     *  PROFILE_JBOSS_IP_PROP must be null or indicate a valid IP address of the local host.
     * 
     * @param res 
     */
    private ProfileVO validateResourceConfiguration(ResourceConfig res) throws NimException {
        ProbeHelper.myLog(">> validateResourceConfiguration(ResourceConfig)",LogLevel.DEBUG);
        try {
            ProfileVO vo = new ProfileVO();        
            String jbossVersionString = res.getResourceProperty(PROFILE_JBOSS_VERSION_PROP);
            if (jbossVersionString != null) {
                int jbossVersion = Util.toInteger(jbossVersionString);
                if (jbossVersion < MIN_JBOSS_VERSION || jbossVersion > MAX_JBOSS_VERSION) {
                    throw new NimException(NimException.E_INVAL,"Not a valid jboss version: " + jbossVersion);
                }
                vo.setJbossVersion(jbossVersion);
            }    

            String jbossPortString = res.getResourceProperty(PROFILE_JBOSS_PORT_PROP);
            if (jbossPortString != null) {
                int jbossPort = Util.toInteger(jbossPortString);
                if (jbossPort < MIN_JBOSS_PORT || jbossPort > MAX_JBOSS_PORT) {
                    throw new Trt3ProbeException("not a valid jboss port: " + jbossPort);
                }
                vo.setJbossPort(jbossPort);
            }

            String jbossIpString = res.getResourceProperty(PROFILE_JBOSS_IP_PROP);
            if (jbossIpString != null) {
                vo.setJbossIp(Util.toIpAddress(jbossIpString));
            }

            String jbossCustomConfigFile = res.getResourceProperty(PROFILE_JBOSS_CUSTOM_CONFIG_FILE_PROP);
            File jbossFile;
            if (jbossCustomConfigFile != null) {
                jbossFile = new File(jbossCustomConfigFile);
            } else {
                jbossFile = vo.getCustomConfigFile(); // uses the default
            }

            if (!jbossFile.exists() || !jbossFile.canRead()) {
               throw new Trt3ProbeException("not a valid config file: " + jbossFile.getAbsolutePath()); 
            }

            vo.setCustomConfigFile(jbossFile);

            ProbeHelper.myLog("<< validateResourceConfiguration(ResourceConfig)",LogLevel.DEBUG);
            return vo;
        } catch (Trt3ProbeException mapped) {
            ProbeHelper.myLog("Error reading profile parameters: " + mapped.getMessage(),LogLevel.ERROR);
            throw new NimException(NimException.E_INVAL, "Error reading profile parameters: " + mapped.getMessage(), mapped);
        }            
    }

    protected void buildStructure(InventoryDataset inventoryDataset, 
                                  ResourceConfig resourceConfig, 
                                  ProfileVO voProfile, 
                                  CustomConfigVO voConfig,
                                  String profileName) throws NimException, 
                                                             InterruptedException {
        ProbeHelper.myLog(">> buildStructure(...)");
        
        // locate the entry that matches the current profile
        if (voConfig.getProfiles().containsKey(profileName)) {
            Profile profile = voConfig.getProfiles().get(profileName);
            ProbeHelper.myLog("profile found: " + profileName);
            ProbeHelper.myLog("# folders: " + profile.getFolders().size());
            
            List<ElementMonitorHolder> elementMonitorList = new ArrayList<>();
            
            // for each Folder ...
            for (String folderName:profile.getFolders().keySet()) {
                Folder folder = profile.getFolders().get(folderName);
                ProbeHelper.myLog("folder     : " + folderName);
                ProbeHelper.myLog("# monitors : " + folder.getMonitors().size());
                
                com.nimsoft.probe.framework.devkit.inventory.Folder uimFolder = 
                        com.nimsoft.probe.framework.devkit.inventory.Folder
                                .addInstance(inventoryDataset, 
                                             new EntityId(resourceConfig,folder.getName()), 
                                             folder.getName(), 
                                             resourceConfig);
                
                // for each Monitor ...
                for (String monitorName:folder.getMonitors().keySet()) {
                    Monitor monitor = folder.getMonitors().get(monitorName);
                    
                    try {
                    
                        String monitorClassName = qosToEntityClassName.get(monitor.getQos())[0];
                        String monitorMetricName = qosToEntityClassName.get(monitor.getQos())[1];

                        ProbeHelper.myLog("class  : " + monitorClassName);
                        ProbeHelper.myLog("metric : " + monitorMetricName);
                        
                        Method m = Class.forName(monitorClassName).getMethod("addInstance", 
                                                                    IInventoryDataset.class,
                                                                    EntityId.class,
                                                                    String.class,
                                                                    Element[].class);
                        
                        ProbeHelper.myLog("Got method addInstance via reflection!",LogLevel.DEBUG);
                        
                        String target = profileName + ":" + monitor.getName();
                        
                        Element uimElement = (Element) m.invoke(null, // addInstance is static
                                                                inventoryDataset, 
                                                                new EntityId(uimFolder,target),
                                                                target,
                                                                new Element[] {uimFolder});

                        ProbeHelper.myLog("Method invoked to create and register the element OK!",LogLevel.DEBUG);
                        
                        elementMonitorList.add(new ElementMonitorHolder(uimElement, monitor));
                        
                        //uimElement.setMetric(uimElement.getMetricDef(monitorMetricName), 1024); //TODO save data to collect later (jmx)
                        //ProbeHelper.myLog("Metric set!",LogLevel.DEBUG);
                        
                    } catch (ClassNotFoundException|NoSuchMethodException|IllegalAccessException|IllegalArgumentException|InvocationTargetException boom) {
                        ProbeHelper.myLog("INSTROSPECTION ERROR!",LogLevel.ERROR);
                        throw new RuntimeException("KABOOM: " + boom.getMessage(),boom);
                    }
                    
                }

            }
 
            // TODO get the jmx item value !!! all at once ? use loop above (too slow ???)

        } else {
            ProbeHelper.myLog("COULD NOT FIND CUSTOM CONFIG FOR PROFILE: " + profileName,LogLevel.WARN);
            ProbeHelper.myLog("DOING NOTHING",LogLevel.WARN);
        }

    }
        
    private static final Map<String,String[]> qosToEntityClassName = 
            new HashMap<>();
    
    static {
        qosToEntityClassName.put("QOS_TRTJBOSS_MEMORY_USAGE",new String[]{"br.jus.trt3.seit.uim.probe.types.TrtJbossMemory","TrtJbossMemoryUsage"});
        qosToEntityClassName.put("QOS_TRTJBOSS_GENERIC_COUNTER",new String[]{"br.jus.trt3.seit.uim.probe.types.TrtJbossCounter","TrtJbossCounter"});
    }

    static class ElementMonitorHolder {
        
        private Element element;
        private Monitor monitor;

        public ElementMonitorHolder(Element element, Monitor monitor) {
            this.element = element;
            this.monitor = monitor;
        }
        
        public Element getElement() {
            return element;
        }

        public Monitor getMonitor() {
            return monitor;
        }
        
    }
    
    static class ElementMonitorList {
        
        private List<ElementMonitorHolder> list = new ArrayList<>();

        public List<ElementMonitorHolder> getList() {
            return list;
        }
        
        public void add(ElementMonitorHolder holder) {
            list.add(holder);
        }
        
    }
    
}
