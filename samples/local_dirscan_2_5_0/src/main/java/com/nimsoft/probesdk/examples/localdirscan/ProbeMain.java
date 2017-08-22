
package com.nimsoft.probesdk.examples.localdirscan;


import java.io.File;

import org.apache.commons.io.FileUtils;

import com.nimsoft.probesdk.examples.localdirscan.types.*;
import com.nimsoft.pf.common.pom.MvnPomVersion;
import com.nimsoft.nimbus.NimConfig;
import com.nimsoft.nimbus.NimException;
import com.nimsoft.pf.common.log.Log;
import com.nimsoft.probe.framework.devkit.ProbeBase;
import com.nimsoft.probe.framework.devkit.interfaces.IProbeInventoryCollection;
import com.nimsoft.probe.framework.devkit.interfaces.IInventoryDataset;
import com.nimsoft.probe.framework.devkit.InventoryDataset;
import com.nimsoft.probe.framework.devkit.configuration.CtdPropertyDefinitionsList;
import com.nimsoft.probe.framework.devkit.inventory.Element;
import com.nimsoft.probe.framework.devkit.configuration.ResourceConfig;
import com.nimsoft.probe.framework.devkit.inventory.typedefs.*;
import com.nimsoft.vm.cfg.IProbeResourceTypeInfo;

/**
 * This is an example of a local probe which scans the directory specified by a profile/resource configuration.
 * Multiple profiles(s) can be added.  Besides demonstrating inventory collection, the probe shows how to add custom configuration
 * properties to the resource, and probe setup section.
 */
public class ProbeMain extends ProbeBase implements IProbeInventoryCollection {

    /*
     * Probe Name, Version, and Vendor are required when initializing the probe.
     */
    public final static  String PROBE_NAME = "local_dirscan";
    public static final  String PROBE_VERSION = MvnPomVersion.get("com.nimsoft.probe-sdk-examples", PROBE_NAME);
    public static final  String PROBE_VENDOR = "CA Technologies";
    
    /*
     * Configuration properties
     */
    private static final String TARGET_DIR_PROP = "target_directory";
    private static final String TRAVERSAL_DEPTH_PROP = "traversal_depth";
    
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
        // Indicate that this is a local probe so that it is displayed correctly in the UI
        setLocalMode(true);
        // Set the flag to indicate we want to use short target names
        useShortTargetName(true);
    }

    /**
     * This is where you configure what you want to display in the probe configuration UI.
     * There are 2 different areas of configuration, the "Setup" and the "Resource" properties.
     * In this example we add the "Directory Traversal Depth" to the probe "Setup" options. We
     * also add the "Target Directory" to the "Resource" properties.
     * 
     * Note: Implementing this method is optional. If your probe does not require the ability to
     * specify configuration options in the Probe Configuration UI then you may skip implementing
     * this method.
     */
    @Override
    public void addDefaultProbeConfigurationToGraph() {
        
        Log.info(">> addDefaultProbeConfigurationToGraph()"); 
        
        // To enable the user to add resources for the probe in the probe oriented configuration UI,  
        // the following adds a resource definition to the profile
        ElementDef resDef = ElementDef.getElementDef("RESOURCE");
        resDef.addStandardAction(IProbeResourceTypeInfo.StandardActionType.DeleteProfileAction);
        resDef.addStandardAction(IProbeResourceTypeInfo.StandardActionType.VerifySelectionAction, "Verify Directory Configuration");
        resDef.addStandardAction(IProbeResourceTypeInfo.StandardActionType.AddProfileActionOnProbe, "Add directory Resource");

        // add SETUP properties
        CtdPropertyDefinitionsList setupPropDefs = CtdPropertyDefinitionsList.createCtdPropertyDefinitionsList("SETUP", getGraph());

        // adds the traversal depth property, with a default value
        setupPropDefs.addIntegerPropertyUsingEditField(TRAVERSAL_DEPTH_PROP, "Directory Traversal Depth", 4);


        // Next set the properties that will be available when a new profile is created in the probe configuration UI
        CtdPropertyDefinitionsList profilePropDefs = CtdPropertyDefinitionsList.createCtdPropertyDefinitionsList("RESOURCE", getGraph());
        // Add an identifier (essentially the profile's name)
        profilePropDefs.addStandardIdentifierProperty();
        // Currently required, select the alarm message to alarm with when the resource is unavailable
        profilePropDefs.addStandardAlarmMessageProperty();
        // Add the polling interval property
        profilePropDefs.addStandardIntervalProperty();
        // Add a checkbox to select whether the profile is active or inactive (inactive ones are not used)
        profilePropDefs.addStandardActiveProperty();
        // Add a property for selecting the directory to be monitored
        profilePropDefs.addStringPropertyUsingEditField(TARGET_DIR_PROP, "Target Directory", "C:\\tmp");
        profilePropDefs.setCfgPathname(TARGET_DIR_PROP, "properties/"+TARGET_DIR_PROP);

        // Finally, make the call to update the default configuration and get these settings published to UIM so that they
        // appear correctly in the probe configuration UI
        super.addDefaultProbeConfigurationToGraph();
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
       Log.info("==== testResource: " + res.getName());
        
        // Test that target directory configuration is valid
        // If it not we throw an exception stating why
        String targetDir = res.getResourceProperty(TARGET_DIR_PROP);

        if (targetDir == null || targetDir.isEmpty()) {
            String errMsg = "No Target Directory Specified.";
            Log.error("testResource: " + res.getName() + "   " + errMsg);
            throw new NimException(NimException.E_ERROR, errMsg);
        }

        File root = new File(targetDir);
        if (!root.exists()) {
            String errMsg = "Target Directory Does Not Exist: " + targetDir;
            Log.error("testResource: " + res.getName() + "   " + errMsg);
            throw new NimException(NimException.E_ERROR, errMsg);
        }
        
        // If we get to here then our tests were successful. Since we dont have 
        // any advanced information we wish to return we can simply return null
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
        
        Log.info(">> getUpdatedInventory(...)"); 
        
        dumpResourceConfig(resourceConfig);
        
        
        // A recommended best practice is to read configuration information
        // on each call to getUpdatedInventory(). This ensures configuration changes
        // take effect without the need for a full restart of the probe.
        // Also, please note that the configuration information is cached by the 
        // framework, so there is very low overhead here.
        int counter = resourceConfig.updateCounter;
        String targetDir = resourceConfig.getResourceProperty(TARGET_DIR_PROP);
        int traversalDepth = NimConfig.getInstance().getValueAsInt("/setup", TRAVERSAL_DEPTH_PROP, 4);
        
        Log.info("==== Begin getUpdatedInventory: Pass-" + counter + "   " + resourceConfig.getName());
        
        // Build the model using the ResourceConfig as the root element
        InventoryDataset inventoryDataset = new InventoryDataset(resourceConfig);
        buildInventory(new File(targetDir), traversalDepth, inventoryDataset, resourceConfig);
        Log.info("==== End getUpdatedInventory: Pass-" + counter + "   " + resourceConfig.getName());
        Log.info(">> getUpdatedInventory(...)");         
        return inventoryDataset;
    }
    
    /**
     * Traverse the directory structure and build our inventory tree and
     * associated metrics. 
     * @param fileOrDirectory A file or directory to add to inventory and gather metrics on
     * @param depth The present recursion depth. This limits how deep the recursion will go.  
     * @param inventoryDataset This is both an input an output. As new Elements are discovered, they 
     * will be added to the InventoryDataset
     * @param parent The parent inventory item. The root parent will be ResourceConfig.
     * @param isRoot indicates if this is the root directory in the tree. Necessary to know so we can construct the lable correctly
     * @throws NimException
     * @throws InterruptedException if a probe shutdown request from the framework is detected
     */
    private void buildInventory(File fileOrDirectory, int depth, IInventoryDataset inventoryDataset, Element parent) throws NimException, InterruptedException {
        // If your probe has a long running collection cycle it is a recommended
        // best practice to periodically call handleInventoryCollectionInterrupt() 
        // to give the probe an opportunity to check if it has received a shutdown request. 
        // This example is not long running, but we still demonstrate the practice here
        handleInventoryCollectionInterrupt();

        if (fileOrDirectory.isFile()) {
            addFileAndMetricsToInventory(fileOrDirectory, inventoryDataset, parent);
        } else if (fileOrDirectory.isDirectory()) {
            addDirectoryAndMetricsToInventory(fileOrDirectory, depth, inventoryDataset, parent);
        }
    }
    
    /**
     * Model the passed in File as our StorageFile data type. Connect it to the passed in parent,
     * add it to the InventoryDataset and add metrics.
     * @param file
     * @param inventoryDataset
     * @param parent
     * @throws NimException
     * @throws InterruptedException
     */
    private void addFileAndMetricsToInventory(File file,IInventoryDataset inventoryDataset, Element parent) throws NimException, InterruptedException{
        String absolutePath = file.getAbsolutePath();
        // Use static factory method to create a new instance of our model object and add it to parent and InventoryDataset.
        StorageFile storageFile = StorageFile.addInstance(inventoryDataset, new EntityId(absolutePath), absolutePath, parent);
        // Set the metrics this probe collects on the StorageFile. We always set them all,
        // and let the framework determine which ones to publish to the bus.
        storageFile.setMetric(StorageFile.SizeInKB, new Double((double) (file.length()) / 1024));
    }
    
    /**
     * Model the passed in directory as our StorageDirectory data type. Connect it to the passed in 
     * parent and add it to the InventoryDataset. Also add metrics.
     * @param directory
     * @param depth
     * @param inventoryDataset
     * @param parent
     * @throws NimException
     * @throws InterruptedException
     */
    private void addDirectoryAndMetricsToInventory(File directory, int depth, IInventoryDataset inventoryDataset, Element parent) throws NimException, InterruptedException{
        String absolutePath = directory.getAbsolutePath();
        // Use static factory method to create a new instance of our model object and add it to parent and InventoryDataset. 
        StorageDirectory storageDirectory = StorageDirectory.addInstance(inventoryDataset, new EntityId(absolutePath), absolutePath, parent);
        // Set the metrics this probe collects on the StorageDirectory. We always set them all,
        // and let the framework determine which ones to publish to the bus.
        storageDirectory.setMetric(StorageDirectory.UsageInMB, FileUtils.sizeOfDirectory(directory) / 1024000);
        storageDirectory.setMetric(StorageDirectory.FileCount, getFileCount(directory));
        if (depth > 0) {
            // We have not yet hit the max traversal depth, so process the contents of this directory
            File[] files = directory.listFiles();
            if (files != null) {
                for(File f : files){
                    // Recursively call buildInventory to process this sub directory. Note that 
                    // we decrement the depth counter to limit how deep we will recurse.
                    // Also note how we pass the newly created storageDirectory as the parent. This
                    // gives us the tree structure we desire. All items found will be children of this
                    // directory
                    buildInventory(f, depth--, inventoryDataset, storageDirectory);
                }
            } 
        } 
    }
    
    /**
     * Simple helper routine to get a count of all
     * files and sub directories in a given directory
     * @param directory
     * @return count
     */
    private int getFileCount(File directory) {
        int count = 0;
        String[] contents = directory.list();
        // Since directory.list() can return null we better check.
        if (contents != null) {
            count = contents.length;
        }
        return count;
    }

    private void dumpResourceConfig(ResourceConfig resourceConfig) {
        Log.info(">> dumpResourceConfig(ResourceConfig)");
        Log.info("getBaseMonitorName: " + resourceConfig.getBaseMonitorName());
        Log.info("getBaseTargetPath: " + resourceConfig.getBaseTargetPath());
        Log.info("getCiName: " + resourceConfig.getCiName());
        //Log.info("getConfigItemId: " + resourceConfig.getConfigItemId());
        Log.info("getIntervalStr: " + resourceConfig.getIntervalStr());
        Log.info("getIpAddr: " + resourceConfig.getIpAddr());
        Log.info("getName: " + resourceConfig.getName());
        Log.info("getSource: " + resourceConfig.getSource());
    }

}
