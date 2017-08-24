package br.jus.trt3.seit.uim.probe;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author sergiomv
 */
public class TestUtil {
    
    private static final Logger logger = Logger.getLogger(TestUtil.class);
    
    public static LineNumberReader getTestFileContents(String fileName) {
        LineNumberReader lnr;
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        lnr = new LineNumberReader(new InputStreamReader(is));
        return lnr;
    }
    
    public static void closeReaderSilently(Reader reader) {
        try {
            if (null != reader) {
                reader.close();
            }
        } catch (IOException ignored) {
        }
    }
    
    public static List<String> getAllLines(String fileName) throws IOException {
        Reader reader = null;
        try {
            reader = getTestFileContents(fileName);
            return getAllLines(reader);
        } finally {
            closeReaderSilently(reader);
        }
    }
    
    public static List<String> getAllLines(Reader reader) throws IOException {

        List<String> lines = new ArrayList<>();
        LineNumberReader lnr = null;
        
        if (null != reader) {
            lnr = new LineNumberReader(reader);
            String line;
            do {
                line = lnr.readLine();
                if (null != line) {
                    lines.add(line);
                }
            } while (line != null);
        } else {
            throw new NullPointerException("Got null reader");
        }
        
        return lines;
    }
    
    public static String getAllLinesAsString(String fileName) throws IOException {
        StringBuilder sb;
        sb = new StringBuilder();
        for (String line:getAllLines(fileName)) {
            sb.append(line);
        }
        return sb.toString();
    }
    
    public static String getAllLinesAsString(Reader reader) throws IOException {
        StringBuilder sb;
        sb = new StringBuilder();
        for (String line:getAllLines(reader)) {
            sb.append(line);
        }
        return sb.toString();
    }
    
    public static void writeToFile(File file,List<String> lines) throws IOException {        
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
            for (String line:lines) {
                fw.write(line + "\n");
            }
        } finally {
            if (null != fw) {
                try {
                    fw.close();
                } catch (Throwable anything) {
                    logger.warn("Error closing fw: " + anything.getMessage(),anything);
                }
            }
        }
    }

}
