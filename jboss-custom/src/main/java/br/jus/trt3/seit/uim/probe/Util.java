package br.jus.trt3.seit.uim.probe;

//TODO: mover essa classe para um projeto a ser reusado em todas as probes.

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 *
 * @author sergiomv
 */
public class Util {
    
    public static Integer toInteger(String txt) throws Trt3ProbeException {        
        try {
            Integer theInteger = Integer.parseInt(txt);
            return theInteger;
        } catch (NumberFormatException mapped) {
            throw new Trt3ProbeException("string not convertible to integer: " + txt, mapped);
        }
    }
    
    public static InetAddress toIpAddress(String txt) throws Trt3ProbeException {
        try {
            return InetAddress.getByName(txt);
        } catch (UnknownHostException mapped) {
            throw new Trt3ProbeException("string is not an ip address: " + txt, mapped);
        }
    }
    
}
