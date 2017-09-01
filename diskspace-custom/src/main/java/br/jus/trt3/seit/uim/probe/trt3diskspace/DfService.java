package br.jus.trt3.seit.uim.probe.trt3diskspace;

import java.io.File;

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
    
}
