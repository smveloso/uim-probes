package br.jus.trt3.seit.uim.probe;

//TODO: mover essa classe para um projeto a ser reusado em todas as probes.

/**
 *
 * @author sergiomv
 */
public class Trt3ProbeException extends Exception {

    public Trt3ProbeException() {
    }

    public Trt3ProbeException(String message) {
        super(message);
    }

    public Trt3ProbeException(String message, Throwable cause) {
        super(message, cause);
    }

    public Trt3ProbeException(Throwable cause) {
        super(cause);
    }
    
}
