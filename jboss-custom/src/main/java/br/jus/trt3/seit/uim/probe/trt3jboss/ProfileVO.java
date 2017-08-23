package br.jus.trt3.seit.uim.probe.trt3jboss;

import br.jus.trt3.seit.uim.probe.Trt3ProbeException;
import br.jus.trt3.seit.uim.probe.Util;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author sergiomv
 */
public class ProfileVO {

    public static final Integer PROFILE_JBOSS_DEFAULT_VERSION = 7;
    public static final String PROFILE_JBOSS_DEFAULT_IP = "127.0.0.1";
    public static final Integer PROFILE_JBOSS_DEFAULT_PORT = 4447;
    public static final String PROFILE_JBOSS_DEFAULT_CUSTOM_CONFIG_FILE = "/opt/nimsoft/probe/application/trt3jboss/trt3config.cfg";
    
    private Integer jbossPort = PROFILE_JBOSS_DEFAULT_PORT;
    private InetAddress jbossIp = null;
    private Integer jbossVersion = PROFILE_JBOSS_DEFAULT_VERSION;
    private File customConfigFile = new File(PROFILE_JBOSS_DEFAULT_CUSTOM_CONFIG_FILE);

    public ProfileVO() {
        try {
            jbossIp = InetAddress.getByName(PROFILE_JBOSS_DEFAULT_IP);
        } catch (UnknownHostException kaboom) {
            throw new RuntimeException("Default string is not an ip address. Should not happen.",kaboom);
        }
    }    

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ProfileVO:\n");
        sb.append("CONFIG  : ").append(getCustomConfigFile());
        sb.append("IP      : ").append(getJbossIp());
        sb.append("PORT    : ").append(getJbossPort());
        sb.append("VERSION : ").append(getJbossVersion());
        return sb.toString();
    }
    
    public Integer getJbossPort() {
        return jbossPort;
    }

    public void setJbossPort(Integer jbossPort) {
        this.jbossPort = jbossPort;
    }

    public InetAddress getJbossIp() {
        return jbossIp;
    }

    public void setJbossIp(InetAddress jbossIp) {
        this.jbossIp = jbossIp;
    }

    public Integer getJbossVersion() {
        return jbossVersion;
    }

    public void setJbossVersion(Integer jbossVersion) {
        this.jbossVersion = jbossVersion;
    }

    public File getCustomConfigFile() {
        return customConfigFile;
    }

    public void setCustomConfigFile(File customConfigFile) {
        this.customConfigFile = customConfigFile;
    }
    
}
