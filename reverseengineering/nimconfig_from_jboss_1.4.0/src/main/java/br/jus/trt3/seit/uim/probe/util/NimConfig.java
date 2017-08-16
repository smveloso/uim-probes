package br.jus.trt3.seit.uim.probe.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

public class NimConfig
{
/*   79 */   private String sFilename = null;
/*   80 */   private Map<String, Object> htConfig = null;
/*   81 */   private static NimConfig nimconfig = null;
  private final String sLineSeparator;
  private String sResourceFile;
  
  protected NimConfig() {
/*   86 */     this.sLineSeparator = System.getProperty("line.separator", "\n");
  }
  











  public NimConfig(PDS pds)
    throws NimException
  {
/*  103 */     this();
/*  104 */     this.htConfig = createMap();
/*  105 */     Enumeration<?> enumeration = pds.keys();
/*  106 */     while (enumeration.hasMoreElements()) {
/*  107 */       String section = (String)enumeration.nextElement();
/*  108 */       Object object = pds.get(section);
/*  109 */       if ((object instanceof PDS)) {
/*  110 */         PDS sub = pds.getPDS(section);
/*  111 */         Enumeration<?> enum2 = sub.keys();
/*  112 */         while (enum2.hasMoreElements()) {
/*  113 */           String key = (String)enum2.nextElement();
/*  114 */           String value = sub.getString(key);
/*  115 */           setValue(section, key, value);
        }
      } else {
/*  118 */         String value = pds.getString(section);
/*  119 */         int l = section.lastIndexOf("/");
/*  120 */         setValue(section.substring(0, l), section.substring(l + 1), value);
      }
    }
  }
  





  public NimConfig(String filename)
    throws NimException
  {
/*  133 */     this();
/*  134 */     setFilename(filename);
  }
  



  public static synchronized NimConfig getInstance()
    throws NimException
  {
/*  143 */     if (nimconfig == null) {
/*  144 */       nimconfig = new NimConfig();
    }
/*  146 */     return nimconfig;
  }
  





  public Charset getCharset()
  {
    String fileEncoding = System.getProperty("file.encoding", null);
    Charset charset; 
    if (fileEncoding == null) {
        charset = Charset.defaultCharset();
    } else {
        charset = Charset.forName(fileEncoding);
    }
    return charset;
  }
  






  public synchronized void setFilename(String filename)
    throws NimException
  {
/*  175 */     this.sFilename = filename;
/*  176 */     reset();
  }
  


  public synchronized String getFilename()
  {
/*  183 */     return this.sFilename;
  }
  






  public synchronized void setResourceFile(String filename)
    throws NimException
  {
/*  195 */     this.sResourceFile = filename;
/*  196 */     reset();
  }
  






  protected Map<String, Object> createMap()
  {
/*  207 */     return new Hashtable();
  }
  





  public synchronized void reset()
    throws NimException
  {
/*  218 */     createHashtable(readCfgString());
  }
  









  public boolean isConfigValuePresent(String section, String key)
    throws NimException
  {
/*  233 */     if (this.htConfig == null) {
/*  234 */       throw new NimException(90, "No configuration defined - request data was:" + section + " key:" + key);
    }
    
/*  237 */     String[] sections = getSections(section);
    
/*  239 */     Map<String, Object> ht = this.htConfig;
/*  240 */     for (int n = 0; (sections != null) && (n < sections.length); n++) {
/*  241 */       Object object = ht.get(sections[n]);
/*  242 */       if ((object instanceof Map)) {
/*  243 */         ht = (Map)object;
      } else {
/*  245 */         return false;
      }
    }
    
/*  249 */     return ht.containsKey(key);
  }
  










  public String getValueAsString(String sections, String key)
    throws NimException
  {
/*  265 */     return getValueAsString(getSections(sections), key);
  }
  












  public synchronized String getValueAsStringDecrypted(String sections, String key, String seed)
    throws NimException
  {
/*  283 */     return getValueAsStringDecrypted(getSections(sections), key, seed);
  }
  










  public synchronized String getValueAsString(String[] sections, String key)
    throws NimException
  {
/*  299 */     return getValue(sections, key);
  }
  












  public synchronized String getValueAsStringDecrypted(String[] sections, String key, String seed)
    throws NimException
  {
/*  317 */     return getValueDecrypted(sections, key, seed);
  }
  












  public synchronized String getValueAsString(String sections, String key, String def)
    throws NimException
  {
/*  335 */     return getValueAsString(getSections(sections), key, def);
  }
  














  public synchronized String getValueAsStringDecrypted(String sections, String key, String seed, String def)
    throws NimException
  {
/*  355 */     return getValueAsStringDecrypted(getSections(sections), key, seed, def);
  }
  














  public synchronized String getValueAsStringDecrypted(String[] sections, String key, String seed, String def)
    throws NimException
  {
/*  375 */     return getValueDecrypted(sections, key, seed, def);
  }
  












  public synchronized String getValueAsString(String[] sections, String key, String def)
    throws NimException
  {
/*  393 */     return getValue(sections, key, def);
  }
  












  public synchronized int getValueAsInt(String sections, String key, int def)
    throws NimException
  {
/*  411 */     return getValueAsInt(getSections(sections), key, def);
  }
  












  public synchronized long getValueAsLong(String sections, String key, int def)
    throws NimException
  {
/*  429 */     return getValueAsLong(getSections(sections), key, def);
  }
  












  public synchronized int getValueAsInt(String[] sections, String key, int def)
    throws NimException
  {
/*  447 */     return Integer.parseInt(getValue(sections, key, "" + def));
  }
  












  public synchronized long getValueAsLong(String[] sections, String key, int def)
    throws NimException
  {
/*  465 */     return Long.parseLong(getValue(sections, key, "" + def));
  }
  












  public synchronized double getValueAsDouble(String sections, String key, double def)
    throws NimException
  {
/*  483 */     return getValueAsDouble(getSections(sections), key, def);
  }
  












  public synchronized double getValueAsDouble(String[] sections, String key, double def)
    throws NimException
  {
/*  501 */     return new Double(getValue(sections, key, "" + def)).doubleValue();
  }
  












  public synchronized boolean getValueAsBoolean(String sections, String key, boolean def)
    throws NimException
  {
/*  519 */     return getValueAsBoolean(getSections(sections), key, def);
  }
  












  public synchronized boolean getValueAsBoolean(String[] sections, String key, boolean def)
    throws NimException
  {
/*  537 */     String strValue = getValue(sections, key, "" + def);
    




/*  543 */     if ((strValue.equals("yes")) || (strValue.equals("1"))) {
/*  544 */       strValue = "true";
/*  545 */     } else if ((strValue.equals("no")) || (strValue.equals("0"))) {
/*  546 */       strValue = "false";
    }
    
/*  549 */     return Boolean.valueOf(strValue).booleanValue();
  }
  












  public synchronized String getValue(String sections, String key, String def)
    throws NimException
  {
/*  567 */     return getValue(getSections(sections), key, def);
  }
  














  public synchronized String getValueDecrypted(String sections, String key, String seed, String def)
    throws NimException
  {
/*  587 */     return getValueDecrypted(getSections(sections), key, seed, def);
  }
  











  public synchronized String getValue(String[] sections, String key, String def)
    throws NimException
  {
    try
    {
/*  606 */       return getValue(sections, key);
    }
    catch (NimException ne) {}
/*  609 */     return def;
  }
  














  public synchronized String getValueDecrypted(String[] sections, String key, String seed, String def)
    throws NimException
  {
    try
    {
/*  631 */       return getValueDecrypted(sections, key, seed);
    }
    catch (NimException ne) {}
/*  634 */     return def;
  }
  













  public synchronized String getValueDecrypted(String sections, String key, String seed)
    throws NimException
  {
/*  653 */     return getValueDecrypted(getSections(sections), key, seed);
  }
  










  public synchronized String getValue(String sections, String key)
    throws NimException
  {
/*  669 */     return getValue(getSections(sections), key);
  }
  















  @Deprecated
  public synchronized String getValueDecrypted(String[] sections, String key, String seed)
    throws NimException
  {
    try
    {
/*  693 */       return getValueDecryptedCharset(sections, key, seed, null);
    }
    catch (UnsupportedEncodingException never) {
/*  696 */       throw new RuntimeException(never);
    }
  }
  













  public synchronized String getValueDecryptedUTF8(String section, String key, String seed)
    throws NimException
  {
/*  716 */     return getValueDecryptedUTF8(getSections(section), key, seed);
  }
  












  public synchronized String getValueDecryptedUTF8(String[] sections, String key, String seed)
    throws NimException
  {
    try
    {
/*  736 */       String charset = "UTF-8";
/*  737 */       return getValueDecryptedCharset(sections, key, seed, "UTF-8");
    }
    catch (UnsupportedEncodingException never) {
/*  740 */       throw new RuntimeException(never);
    }
  }
  




















  public synchronized String getValueDecryptedCharset(String[] sections, String key, String seed, String charset)
    throws NimException, UnsupportedEncodingException
  {
    String encrypted = getValue(sections, key);
    String decrypted; 
    if (NimUtility.isEmpty(encrypted)) {
        decrypted = null;
    } else {
        decrypted = new NimSecurity().decrypt(seed, encrypted, charset);
    }
    return decrypted;
  }
  










  public synchronized String getValue(String[] sections, String key)
    throws NimException
  {
/*  789 */     if (this.htConfig == null) {
/*  790 */       throw new NimException(90, "No configuration defined - request data was:" + Arrays.asList(sections) + " key:" + key);
    }
    

/*  794 */     Map<String, Object> ht = this.htConfig;
/*  795 */     String ssection = "";
/*  796 */     for (int n = 0; (sections != null) && (n < sections.length); n++) {
/*  797 */       if (n > 0) {
/*  798 */         ssection = ssection + "/";
      }
/*  800 */       ssection = ssection + sections[n];
/*  801 */       Object object = ht.get(sections[n]);
/*  802 */       if ((object instanceof Map)) {
/*  803 */         ht = (Map)object;
      } else {
/*  805 */         throw new NimException(90, "No section found for " + ssection);
      }
    }
/*  808 */     Object object = ht.get(key);
/*  809 */     if ((object instanceof String)) {
/*  810 */       return (String)object;
    }
    
/*  813 */     throw new NimException(90, "No value found for key " + ssection + "/" + key);
  }
  












  @Deprecated
  public synchronized Hashtable<String, Object> getHashtable(String[] sections)
    throws NimException
  {
/*  832 */     Map<String, Object> ht = getMap(sections);
/*  833 */     return (Hashtable)ht;
  }
  
  public synchronized Map<String, Object> getMap(String[] sections) throws NimException {
/*  837 */     Map<String, Object> ht = this.htConfig;
/*  838 */     String ssection = "";
/*  839 */     for (int n = 0; (sections != null) && (n < sections.length); n++) {
/*  840 */       if (n > 0) {
/*  841 */         ssection = ssection + "/";
      }
/*  843 */       ssection = ssection + sections[n];
/*  844 */       Object object = ht.get(sections[n]);
/*  845 */       if (object != null) {
/*  846 */         if ((object instanceof Map)) {
/*  847 */           ht = (Map)object;
        } else {
/*  849 */           throw new NimException(90, "No section, at getHashtable, found for " + ssection);
        }
      }
      else {
/*  853 */         return createMap();
      }
    }
/*  856 */     return ht;
  }
  







  public synchronized String[] getSectionList(String sections)
    throws NimException
  {
/*  869 */     return getSectionList(getSections(sections));
  }
  







  public synchronized String[] getSectionList(String[] sections)
    throws NimException
  {
/*  882 */     return getList(sections, true, false);
  }
  







  public synchronized String[] getKeyList(String sections)
    throws NimException
  {
/*  895 */     return getKeyList(getSections(sections));
  }
  







  public synchronized String[] getKeyList(String[] sections)
    throws NimException
  {
/*  908 */     return getList(sections, false, true);
  }
  











  public synchronized void setValue(String sections, String key, int value)
    throws NimException
  {
/*  925 */     setValue(getSections(sections), key, value);
  }
  











  public synchronized void setValue(String[] sections, String key, int value)
    throws NimException
  {
/*  942 */     setValue(sections, key, "" + value);
  }
  











  public synchronized void setValue(String sections, String key, boolean value)
    throws NimException
  {
/*  959 */     setValue(getSections(sections), key, value);
  }
  











  public synchronized void setValue(String[] sections, String key, boolean value)
    throws NimException
  {
/*  976 */     setValue(sections, key, "" + (value ? "true" : "false"));
  }
  











  public synchronized void setValue(String sections, String key, String value)
    throws NimException
  {
/*  993 */     setValue(getSections(sections), key, value);
  }
  



















  @Deprecated
  public synchronized void setValueEncrypted(String sections, String key, String value, String seed)
    throws NimException
  {
     setValueEncrypted(getSections(sections), key, value, seed);
  }
  












  public synchronized void setValueEncrypted(String[] sections, String key, String value, String seed)
    throws NimException
  {
    try
    {
       setValueEncryptedCharset(sections, key, value, seed, null);
    }
    catch (UnsupportedEncodingException never) {
       throw new NimException(1, never);
    }
  }
  















  public synchronized void setValueEncryptedUTF8(String section, String key, String value, String seed)
    throws NimException
  {
     setValueEncryptedUTF8(getSections(section), key, value, seed);
  }
  














  public synchronized void setValueEncryptedUTF8(String[] sections, String key, String value, String seed)
    throws NimException
  {
    try
    {
       String charset = "UTF-8";
       setValueEncryptedCharset(sections, key, value, seed, "UTF-8");
    }
    catch (UnsupportedEncodingException never) {
       throw new NimException(1, never);
    }
  }
  




















  public synchronized void setValueEncryptedCharset(String[] sections, String key, String value, String seed, String charset)
    throws NimException, UnsupportedEncodingException
  {
     String encrypted = new NimSecurity().encrypt(seed, value, charset);
     setValue(sections, key, encrypted);
  }
  











  public synchronized void setValue(String[] sections, String key, String value)
    throws NimException
  {
     Map<String, Object> ht = this.htConfig;
     String ssection = "";
     for (int n = 0; (sections != null) && (n < sections.length); n++) {
       if (n > 0) {
         ssection = ssection + "/";
      }
       ssection = ssection + sections[n];
       Object object = ht.get(sections[n]);
       if (object != null) {
         if ((object instanceof Map)) {
           ht = (Map)object;
        } else {
           throw new NimException(90, "Section already in use as a key " + ssection);
        }
      }
      else {
         Map<String, Object> htnew = createMap();
         ht.put(sections[n], htnew);
         ht = htnew;
      }
    }
     if (value == null) {
       ht.put(key, "");
    } else {
       ht.put(key, value);
    }
  }
  





  public synchronized void remove(String sections)
  {
     remove(getSections(sections));
  }
  





  public synchronized void remove(String[] sections)
  {
     if (sections == null) {
       return;
    }
     if (sections.length == 0) {
       this.htConfig = createMap();
    }
     Map<String, Object> ht = this.htConfig;
     for (int n = 0; n < sections.length - 1; n++) {
       Object object = ht.get(sections[n]);
       if ((object instanceof Map)) {
         ht = (Map)object;
      } else {
         return;
      }
    }
     ht.remove(sections[(sections.length - 1)]);
  }
  







  public synchronized void remove(String sections, String key)
  {
     remove(getSections(sections), key);
  }
  







  public synchronized void remove(String[] sections, String key)
  {
     if (sections == null) {
       return;
    }
     Map<String, Object> ht = this.htConfig;
     for (String section : sections) {
       Object object = ht.get(section);
       if ((object instanceof Map)) {
         ht = (Map)object;
      } else {
         return;
      }
    }
    
     ht.remove(key);
  }
  









  @Deprecated
  public synchronized Hashtable<String, Object> getHashtable()
    throws NimException
  {
     return (Hashtable)this.htConfig;
  }
  
  public synchronized Map<String, Object> getMap() {
     return this.htConfig;
  }
  
  private String[] getList(String[] sections, boolean findsections, boolean findkeys)
    throws NimException
  {
     Map<String, Object> ht = getMap(sections);
     Enumeration<String> enumeration = Collections.enumeration(ht.keySet());
     Vector<String> v = new Vector();
     while (enumeration.hasMoreElements()) {
       String key = (String)enumeration.nextElement();
       Object object = ht.get(key);
       if ((findsections) && ((object instanceof Map))) {
         v.add(key);
      }
       if ((findkeys) && ((object instanceof String))) {
         v.add(key);
      }
    }
     String[] ss = new String[v.size()];
     v.copyInto(ss);
     return ss;
  }
  
  private String readFileContent(Reader reader, int length) throws NimException {
      StringBuilder builder;
     if (length == -1) {
       builder = new StringBuilder();
    } else {
       builder = new StringBuilder(length);
    }
    try {
       char[] buffer = new char[1024];
       int n = reader.read(buffer);
       while (n != -1) {
         builder.append(buffer, 0, n);
         n = reader.read(buffer);
      }
       return builder.toString();
    } catch (IOException doh) {
       throw new NimException(90, doh);
    }
  }
  
  private String readCfgString() throws NimException {
    int length;
    BufferedReader reader;
    try {
      InputStream in;
       if (this.sResourceFile != null) {
        length = -1;
        in = getClass().getResourceAsStream(this.sResourceFile);
        if (in == null) {
            throw new IllegalArgumentException("Failed to locate resource file " + this.sResourceFile + " in classpath.");
        }
      }
      else
      {
         File file = new File(this.sFilename);
         length = (int)file.length();
         in = new FileInputStream(file);
      }
       reader = new BufferedReader(new InputStreamReader(in, getCharset()));
    } catch (IOException doh) {
       throw new NimException(90, doh);
    }
    try
    {
       return readFileContent(reader, length);
    } finally {
      try {
         reader.close();
      } catch (IOException doh) {
         throw new NimException(90, doh);
      }
    }
  }
  

  /**
   * @deprecated
   */
  protected void createHashtable(BufferedInputStream bis)
    throws NimException
  {
     BufferedReader reader = new BufferedReader(new InputStreamReader(bis, getCharset()));
    
     createHashtable(readFileContent(reader, -1));
  }
  
  protected void createHashtable(String content) throws NimException {
     Parser parser = new Parser(content);
     this.htConfig = parser.parse();
  }
  











  public synchronized PDS getPDS(boolean flatformat)
    throws NimException
  {
     PDS pds = new PDS();
     getPDS(null, flatformat, pds);
     return pds;
  }
  
  protected void getPDS(String section, boolean flatformat, PDS pds) throws NimException
  {
     if (section == null) {
       section = "";
    }
     String[] sections = getSectionList(section);
     for (int i = 0; (sections != null) && (i < sections.length); i++) {
       getPDS(section + "/" + sections[i], flatformat, pds);
    }
     String[] keys = getKeyList(section);
     if ((keys != null) && (keys.length > 0)) {
       if (flatformat)
      {
         for (String key : keys) {
           pds.put(section + "/" + key, getValueAsString(section, key));
        }
      } else {
         PDS subpds = new PDS();
         for (String key : keys) {
           subpds.put(key, getValueAsString(section, key));
        }
         pds.putPDS(section, subpds);
      }
    }
  }
  




  public synchronized void save()
    throws NimException
  {
     writeHashtableToFile();
  }
  




  public synchronized void save(String fileName)
    throws NimException
  {
     writeHashtableToFile(fileName);
  }
  
  private void writeHashtableToFile() throws NimException {
     writeHashtableToFile(this.sFilename);
  }
  
  private void writeHashtableToFile(String fileName) throws NimException
  {
     String s = toString();
    try {
       BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), getCharset()));
      

      try
      {
         writer.write(s);
      } finally {
         writer.close();
      }
    } catch (Exception e) {
       throw new NimException(90, "Unable to open config file: " + fileName, e);
    }
  }
  

  private StringBuffer toStringBuffer(Map<String, Object> ht, StringBuffer sb, int level)
    throws NimException
  {
     if (sb == null) {
       sb = new StringBuffer();
    }
     Enumeration<String> enumeration = Collections.enumeration(ht.keySet());
     while (enumeration.hasMoreElements()) {
       String key = (String)enumeration.nextElement();
       Object object = ht.get(key);
       if (object != null) {
         if ((object instanceof Map)) {
           sb.append(bias(level) + "<" + key + ">" + this.sLineSeparator);
           sb = toStringBuffer((Map)object, sb, level + 1);
           sb.append(bias(level) + "</" + key + ">" + this.sLineSeparator);
        } else {
           sb.append(bias(level) + key + " = " + object + this.sLineSeparator);
        }
      }
    }
     return sb;
  }
  
  private static String bias(int level) {
     String s = "";
     for (int i = 0; i < level * 4; i++) {
       s = s + " ";
    }
     return s;
  }
  
  private static String[] getSections(String sections)
  {
     StringTokenizer st = new StringTokenizer(sections, "/");
     Vector<String> v = new Vector();
     while (st.hasMoreTokens()) {
       v.add(st.nextToken());
    }
     String[] ss = new String[v.size()];
     v.copyInto(ss);
     return ss;
  }
  
  public String toString()
  {
    try {
       return toStringBuffer(this.htConfig, null, 0).toString();
    } catch (NimException ne) {
       return ne.toString();
    }
  }
  
  private class Parser {
    final String content;
    int index;
    int line;
    
    private Parser(String content) {
       this.content = content;
       this.index = 0;
       this.line = 1;
    }
    
    private Map<String, Object> parse() throws NimException {
       Map<String, Object> map = null;
      try
      {
         while (this.index < this.content.length()) {
           char c = this.content.charAt(this.index++);
           if ('<' == c) {
             map = parse(map, getTag());
           } else if ('\n' == c) {
             this.line += 1;
          }
        }
         if (map == null) {
           throw new NimException(90, "No < in the config file");
        }
      } catch (Exception e) {
         throw new NimException(90, e);
      }
       return map;
    }
    
    private Map<String, Object> parse(Map<String, Object> parentMap, String myTag)
      throws NimException
    {
       String keyvalue = null;
      
       Map<String, Object> ht = NimConfig.this.createMap();
       if (parentMap == null) {
         parentMap = NimConfig.this.createMap();
      }
      
       boolean insidevalue = false;
      try {
         while (this.index < this.content.length()) {
           char c = this.content.charAt(this.index++);
           switch (c) {
          case '\t': 
          case '\r': 
          case ' ': 
             if (insidevalue) {
               keyvalue = keyvalue + c;
            }
            break;
          case '<': 
             if (!insidevalue)
            {
               String tag = getTag();
              
               if (tag.startsWith("/")) {
                 tag = tag.substring(1);
                
                 if (!tag.equals(myTag)) {
                   throw new NimException(90, "Config error at line " + this.line + "</" + tag);
                }
                
                 parentMap.put(myTag, ht);
                 return parentMap;
              }
               parse(ht, tag);
            } else {
               keyvalue = keyvalue + '<';
            }
             break;
          case '\n': 
             if (keyvalue != null)
            {
               int l = keyvalue.indexOf("=");
               if (l == -1) {
                 throw new NimException(90, "Config error at line " + this.line + keyvalue + "; missing '='");
              }
              
               String key = keyvalue.substring(0, l).trim();
               String value = keyvalue.substring(l + 1).trim();
              

               ht.put(key, value);
               keyvalue = null;
            }
             this.line += 1;
             insidevalue = false;
             break;
          case '=': 
             insidevalue = true;
          default: 
             if (keyvalue == null) {
               keyvalue = "";
            }
             keyvalue = keyvalue + c;
          }
        }
      }
      catch (Exception e) {
         throw new NimException(1, e);
      }
       return null;
    }
    
    private String getTag() throws Exception {
       StringBuilder tag = new StringBuilder();
      

       while (this.index < this.content.length()) {
         char c = this.content.charAt(this.index++);
         switch (c) {
        case '>': 
           return tag.toString();
        case '\n': 
           this.line += 1;
        }
         tag.append(c);
      }
      

       return tag.toString();
    }
  }
}


/* Location:              C:\Users\sergiomv\Desktop\Env\DOC\CA\MEUS_TESTES\probe-sdk\jboss-probe-1.4.0-jars\original_jars_from_probe_deployment\probes\application\jboss\jar\jboss.jar!\com\nimsoft\nimbus\NimConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */