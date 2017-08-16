package Twofish;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

public class Twofish_Properties
{
  static final boolean GLOBAL_DEBUG = false;
  static final String ALGORITHM = "Twofish";
  static final double VERSION = 0.2D;
  static final String FULL_NAME = "Twofish ver. " + 0.2D;
  static final String NAME = "Twofish_Properties";
  static final Properties properties = new Properties();
  private static final String[][] DEFAULT_PROPERTIES = { { "Trace.Twofish_Algorithm", "true" }, { "Debug.Level.*", "1" }, { "Debug.Level.Twofish_Algorithm", "9" } };
  
  public static String getProperty(String paramString)
  {
    return properties.getProperty(paramString);
  }
  
  public static String getProperty(String paramString1, String paramString2)
  {
    return properties.getProperty(paramString1, paramString2);
  }
  
  public static void list(PrintStream paramPrintStream)
  {
    list(new PrintWriter(paramPrintStream, true));
  }
  
  public static void list(PrintWriter paramPrintWriter)
  {
    paramPrintWriter.println("#");
    paramPrintWriter.println("# ----- Begin Twofish properties -----");
    paramPrintWriter.println("#");
    Enumeration localEnumeration = properties.propertyNames();
    while (localEnumeration.hasMoreElements())
    {
      String str1 = (String)localEnumeration.nextElement();
      String str2 = properties.getProperty(str1);
      paramPrintWriter.println(str1 + " = " + str2);
    }
    paramPrintWriter.println("#");
    paramPrintWriter.println("# ----- End Twofish properties -----");
  }
  
  public static Enumeration propertyNames()
  {
    return properties.propertyNames();
  }
  
  static boolean isTraceable(String paramString)
  {
    String str = "Trace." + paramString;
    str = properties.getProperty(str);
    if (str == null) {
      return false;
    }
    return new Boolean(str).booleanValue();
  }
  
  static int getLevel(String paramString)
  {
    String str = "Debug.Level." + paramString;
    str = properties.getProperty(str);
    if (str == null)
    {
      str = properties.getProperty("Debug.Level.*");
      if (str == null) {
        return 0;
      }
    }
    try
    {
      return Integer.parseInt(str);
    }
    catch (NumberFormatException localNumberFormatException) {}
    return 0;
  }
  
  static PrintWriter getOutput()
  {
    String str = properties.getProperty("Output");
    PrintWriter localPrintWriter;
    if ((str != null) && (str.equals("out"))) {
      localPrintWriter = new PrintWriter(System.out, true);
    } else {
      localPrintWriter = new PrintWriter(System.err, true);
    }
    return localPrintWriter;
  }
  
  static
  {
    InputStream localInputStream = Twofish_Properties.class.getResourceAsStream("Twofish.properties");
    int i = localInputStream == null ? 0 : 1;
    if (i != 0) {
      try
      {
        properties.load(localInputStream);
        localInputStream.close();
      }
      catch (Exception localException)
      {
        i = 0;
      }
    }
    if (i == 0)
    {
      int j = DEFAULT_PROPERTIES.length;
      for (int k = 0; k < j; k++) {
        properties.put(DEFAULT_PROPERTIES[k][0], DEFAULT_PROPERTIES[k][1]);
      }
    }
  }
}


/* Location:              C:\Users\sergiomv\Desktop\Env\DOC\CA\MEUS_TESTES\probe-sdk\jboss-probe-1.4.0-jars\original_jars_from_probe_deployment\probes\application\jboss\jar\jboss.jar!\Twofish\Twofish_Properties.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */