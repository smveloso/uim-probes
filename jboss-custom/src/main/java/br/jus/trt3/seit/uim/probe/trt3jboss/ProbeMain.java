package br.jus.trt3.seit.uim.probe.trt3jboss;

import br.jus.trt3.seit.uim.probe.Trt3ProbeException;
import br.jus.trt3.seit.uim.probe.Util;
import br.jus.trt3.seit.uim.probe.types.*;

import com.nimsoft.pf.common.pom.MvnPomVersion;
import com.nimsoft.nimbus.NimException;
import com.nimsoft.pf.common.log.Log;
import com.nimsoft.probe.framework.devkit.ProbeBase;
import com.nimsoft.probe.framework.devkit.interfaces.IProbeInventoryCollection;
import com.nimsoft.probe.framework.devkit.interfaces.IInventoryDataset;
import com.nimsoft.probe.framework.devkit.InventoryDataset;
import com.nimsoft.probe.framework.devkit.configuration.CtdPropertyDefinitionsList;
import com.nimsoft.probe.framework.devkit.configuration.ResourceConfig;
import com.nimsoft.probe.framework.devkit.inventory.typedefs.*;
import com.nimsoft.vm.cfg.IProbeResourceTypeInfo;
import java.io.File;

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
        myLog(">> ProbeMain(String[])", LogLevel.DEBUG);
        // Indicate this is a local probe
        setLocalMode(true);
        //useShortTargetName(true); local_dirscan does this
        myLog("<< ProbeMain(String[])", LogLevel.DEBUG);
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
        
        myLog(">> addDefaultProbeConfigurationToGraph()", LogLevel.DEBUG);
        
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
        myLog("<< addDefaultProbeConfigurationToGraph()", LogLevel.DEBUG);
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
        myLog("==== testResource: " + res.getName());
        myLog(">> testResource(ResourceConfig)");
        
        /**
         * ***** Insert your test logic here *****
         * If your test is successful you need not do anything, simply 
         * allow this method to return null. If you need to report an error
         * then throw a NimException
         */
        
        try {
            validateResourceConfiguration(res);
        } catch (Trt3ProbeException mapped) {
            throw new NimException(NimException.E_INVAL, mapped.getMessage(), mapped);
        }
        
        // If we get to here then our tests were successful. Since we dont have 
        // any advanced information we wish to return we can simply return null
        myLog("<< testResource(ResourceConfig)");
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
     */
    @Override
    public IInventoryDataset getUpdatedInventory(ResourceConfig resourceConfig, IInventoryDataset previousDataset) throws NimException, InterruptedException {
        // A recommended best practice is to read configuration information
        // on each call to getUpdatedInventory(). This ensures configuration changes
        // take effect without the need for a full restart of the probe.
        // Also, please note that the configuration information is cached by the 
        // framework, so there is very low overhead here.
        int counter = resourceConfig.updateCounter;
        
        myLog(">> getUpdatedInventory(ResourceConfig,IInventoryDataset)");
        myLog("Pass: " + counter);
        myLog("Name: " + resourceConfig.getName());
        
        ProfileVO vo = null;
        try {
            vo = validateResourceConfiguration(resourceConfig);
        } catch (Trt3ProbeException mapped) {
            myLog("ERROR getting vo for profile: " + mapped.getMessage());
            throw new NimException(NimException.E_INVAL, "ERROR getting vo for profile", mapped);
        }
        
        myLog(vo.toString());
        
        // Create a new empty InventoryDataset
        InventoryDataset inventoryDataset = new InventoryDataset(resourceConfig);
        
        //TODO: Ler o arquivo de configuração e criar a estrutura usando os Elements 
        //      definidos em probe_schema.xml

        TrtJbossMemory heapMemory = TrtJbossMemory.addInstance(inventoryDataset, new EntityId("HeapMemory"), "HeapMemory", resourceConfig);
        heapMemory.setMetric(TrtJbossMemory.TrtJbossMemoryUsage, 1024);

        TrtJbossMemory youngGenMemory = TrtJbossMemory.addInstance(inventoryDataset, new EntityId("YGMemory"), "YGMemory", resourceConfig);
        youngGenMemory.setMetric(TrtJbossMemory.TrtJbossMemoryUsage, 1024);
        
        
        myLog("<< getUpdatedInventory(...)");
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
    private ProfileVO validateResourceConfiguration(ResourceConfig res) throws Trt3ProbeException {
        myLog(">> validateResourceConfiguration(ResourceConfig)",LogLevel.DEBUG);
        
        ProfileVO vo = new ProfileVO();
        
        String jbossVersionString = res.getResourceProperty(PROFILE_JBOSS_VERSION_PROP);
        if (jbossVersionString != null) {
            int jbossVersion = Util.toInteger(jbossVersionString);
            if (jbossVersion < MIN_JBOSS_VERSION || jbossVersion > MAX_JBOSS_VERSION) {
                throw new Trt3ProbeException("not a valid jboss version: " + jbossVersion);
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
        
        myLog("<< validateResourceConfiguration(ResourceConfig)",LogLevel.DEBUG);
        return vo;
    }

    /** Logs a message as INFO
     *  Uses nimsoft´s log class
     * 
     * @param message Text to write to log output.
     */
    private void myLog(String message) {
        myLog(message,LogLevel.INFO);
    }

    /** Logs a message using specified level.
     *  Uses nimsoft´s log class
     * 
     * @param message Text to write to log output.
     */    
    private void myLog(String message, LogLevel level) {
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
