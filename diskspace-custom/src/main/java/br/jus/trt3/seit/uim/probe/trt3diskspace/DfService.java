package br.jus.trt3.seit.uim.probe.trt3diskspace;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;

/**
 *
 * @author sergiomv
 */
public class DfService {

    private String pathToDf;
    
    // GNU coreutils places the command at /usr/bin/df.
    // The other paths are here to try avoid possible
    // unconventional locations.
    private String[] searchPath = new String[] {
        "/usr/bin/df",  
        "/bin/df",
        "/sbin/df",
        "/usr/local/bin/df",
        "/usr/local/sbin/df"
    };

    public DfService() throws DfNotFoundException {
        ProbeHelper.myLog(">> DfService()");
        locateCommand();
        ProbeHelper.myLog("<< DfService()");
    }
    
    // for testing
    protected DfService(String pathToDf) {
        this.pathToDf = pathToDf;
    }
    
    private void locateCommand() throws DfNotFoundException {
        ProbeHelper.myLog(">> locateCommand()");
        
        File file = null;
        for (String possiblePath:searchPath) {
            ProbeHelper.myLog("Trying: " + possiblePath); //TODO make it debug level
            file = new File(possiblePath);
            if (file.exists()) {
                pathToDf = possiblePath;
                break;
            }
        }
        
        if (null == file) {
            ProbeHelper.myLog("Não encontrei o comando df",LogLevel.ERROR);
            throw new DfNotFoundException("Não encontrei o comando df");
        }
        
        ProbeHelper.myLog("<< locateCommand()");
    }

    public String getPathToDf() {
        return pathToDf;
    }

    public String getPercentageUsedAsString(String absolutePath) throws DfExecException,DfParseException {
        return String.valueOf(getPercentageUsed(absolutePath));
    }
    
    public Integer getPercentageUsed(String absolutePath) throws DfExecException,DfParseException {
        return extractPercentUsage(execAndReturnOutput(absolutePath));
    }
    
    protected String execAndReturnOutput(String absolutePath) throws DfExecException {        
        
        ByteArrayOutputStream outputStream = null;
        
        try {
            ProbeHelper.myLog(">> returnOutput(String)");
            CommandLine commandline = CommandLine.parse(pathToDf);
            commandline.addArgument("--output=pcent");
            commandline.addArgument(absolutePath);        
            ProbeHelper.myLog("Will execute: " + commandline.toString());
            outputStream = new ByteArrayOutputStream();
            DefaultExecutor exec = new DefaultExecutor();
            PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
            exec.setStreamHandler(streamHandler);
            exec.execute(commandline);
            String output = outputStream.toString();
            ProbeHelper.myLog("Result: " + output);
            ProbeHelper.myLog("<< returnOutput(String)");
            return(output);
        } catch (ExecuteException mapped) {
            ProbeHelper.myLog("ExecuteException: " + mapped.getMessage(),LogLevel.ERROR);
            throw new DfExecException(mapped.getMessage(),mapped);
        } catch (IOException mapped) {
            ProbeHelper.myLog("IOException: " + mapped.getMessage(),LogLevel.ERROR);
            throw new DfExecException(mapped.getMessage(),mapped);            
        } finally {
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException ignored) {
                    ProbeHelper.myLog("Ignoring IOException closing stream: " + ignored.getMessage(),LogLevel.WARN);
                }
            }
        }
    }
    
    /**
    * <pre> 
    * Expects:
    *
    *  Use%
    *  xxx%
    *</pre>
    */
    protected int extractPercentUsage(String output) throws DfParseException {
        ProbeHelper.myLog(">> returnOutput(String)");
        Integer percentageUsage;
        Matcher m = patPercent.matcher(output);
        if (m.matches()) {
            if (output.split("%").length > 3) {
                ProbeHelper.myLog("Too many % chars is suspicious: " + output,LogLevel.ERROR);
                throw new DfParseException("Too many % chars is suspicious: " + output);
            }
            try {
                percentageUsage = Integer.parseInt(m.group(1));
            } catch (NumberFormatException mapped) {
                ProbeHelper.myLog("Value is not an integer: " + m.group(1),LogLevel.ERROR);
                throw new DfParseException("Value is not an integer: " + m.group(1));
            }
        } else {
            throw new DfParseException("Error applying reg exp to: " + output);
        }
        
        ProbeHelper.myLog("<< returnOutput(String)");
        return percentageUsage;
    }
    
    private static Pattern patPercent = Pattern.compile(".*?([0-9]{1,3})\\%.*",Pattern.DOTALL);
}
