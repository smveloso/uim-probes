 package br.jus.trt3.seit.uim.probe.util;
 
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.IOException;
 import java.io.UnsupportedEncodingException;
 
 
 
 
 
 
 public class NimSecurity
   extends NimSecurityBase
 {
   public static final String SECURITY_CHARSET_ISO8859_1 = "ISO-8859-1";
   public static final String SECURITY_CHARSET_UTF8 = "UTF-8";
   public static final int ACCESS_OPEN = 0;
   public static final int ACCESS_READ = 1;
   public static final int ACCESS_WRITE = 2;
   public static final int ACCESS_ADMIN = 3;
   public static final int ACCESS_SUPER = 4;
/*  23 */   private static NimSecurity nimsecurity = null;
   
/*  25 */   NimLog logger = NimLog.getLogger(NimSecurity.class.getName());
   
 
 
 
   protected static NimSecurity getInternalInstance()
     throws NimException
   {
/*  33 */     if (nimsecurity == null) {
/*  34 */       nimsecurity = new NimSecurity();
     }
/*  36 */     return nimsecurity;
   }
   
 
 
 
 
 
 
 
 

   public String encrypt(String seed, String string)
     throws NimException
   {
     try
     {
/*  55 */       return encrypt(seed, string, null);
     }
     catch (UnsupportedEncodingException never) {
/*  58 */       throw new NimException(3, never);
     }
   }
   
 
 
 
 
 
 
 
 
 
 
 
 
 
 

   public String encryptISO(String seed, String plaintext)
     throws NimException
   {
     try
     {
/*  84 */       return encrypt(seed, plaintext, "ISO-8859-1");
     }
     catch (UnsupportedEncodingException never) {
/*  87 */       throw new NimException(3, never);
     }
   }
   
 
 
 
 
 
 
 
 
 
 
 
 
 
   public String encryptUTF8(String seed, String plaintext)
   {
     try
     {
       return encrypt(seed, plaintext, "UTF-8");
     }
     catch (UnsupportedEncodingException never) {
       throw new RuntimeException(never);
     }
   }
   
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
   public String encrypt(String seed, String plaintext, String charset)
     throws UnsupportedEncodingException
   {
    setTwofishKey(seed, charset);
    byte[] plainBytes; 
    if (NimUtility.isEmpty(charset)) {
        plainBytes = plaintext.getBytes();
    } else {
        plainBytes = plaintext.getBytes(charset);
    }
    
    byte[] encrypted = encryptDecrypt(plainBytes, true);
    return NimBase64.encode(encrypted);
    
   }
   
 
 
 
 
 
 
 
 
   /**
    * @deprecated
    */
   public String decrypt(String seed, String string)
     throws NimException
   {
     try
     {
       return decrypt(seed, string, null);
     }
     catch (UnsupportedEncodingException never) {
       throw new NimException(3, never);
     }
   }
 
   /**
    * @deprecated
    */
   public String decryptISO(String seed, String string)
     throws NimException
   {
     try
     {
       return decrypt(seed, string, "ISO-8859-1");
     }
     catch (UnsupportedEncodingException never) {
       throw new NimException(3, never);
     }
   }
   
 
 
 
 
 
 
 
 
 
 
   public String decryptUTF8(String seed, String encrypted)
     throws NimException
   {
     try
     {
       return decrypt(seed, encrypted, "UTF-8");
     }
     catch (UnsupportedEncodingException never) {
       throw new NimException(3, never);
     }
   }
   
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
   public String decrypt(String seed, String encrypted, String charset)
     throws NimException, UnsupportedEncodingException
   {
     setTwofishKey(seed, charset);
     byte[] decrypted = encryptDecrypt(NimBase64.decode(encrypted), false);
     String plaintext;
     if (NimUtility.isEmpty(charset)) {
       plaintext = new String(decrypted);
     } else {
       plaintext = new String(decrypted, charset);
     }
     
 
     return plaintext.trim();
   }
   
 
 
 
 
   public byte[] encrypt(byte[] bytearray)
     throws NimException
   {
     return encryptDecrypt(bytearray, true);
   }
   
 
 
 
 
 
   public byte[] decrypt(byte[] bytes)
     throws NimException
   {
     return encryptDecrypt(bytes, false);
   }
   
 
 
 
 
 
 
 
 
 
 
 
 
 
   public PDS encrypt(PDS pds)
     throws NimException
   {
     try
     {
       return encrypt(pds, PDS.getEncoding());
     } catch (UnsupportedEncodingException uee) {
       throw new NimException(1, uee);
     }
   }
   
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
   public PDS encrypt(PDS pds, String charset)
     throws NimException, UnsupportedEncodingException
   {
     PDS encryptedPDS = new PDS();
     byte[] plainBytes = new byte[pds.nativecountbytes(charset)];
     int length = pds.makebuffer(plainBytes, 0, charset);
     byte[] encryptedBytes = encrypt(plainBytes);
     encryptedPDS.put("crdta", encryptedBytes);
     encryptedPDS.put("crlen", length);
     return encryptedPDS;
   }
   
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
   public PDS decrypt(PDS pdsencrypted)
     throws NimException
   {
     byte[] crdta = pdsencrypted.getBytes("crdta");
     int length = pdsencrypted.getInt("crlen");
     return new PDS(decrypt(crdta), 0, length);
   }
   
 
 
 
 
 
 
 
 
 
 
 
 
 
   public PDS decrypt(PDS encrypted, String charset)
     throws NimException, UnsupportedEncodingException
   {
     byte[] crdta = encrypted.getBytes("crdta");
     int crlen = encrypted.getInt("crlen");
     return new PDS(decrypt(crdta), 0, crlen, charset);
   }
   
 
 
 
 
 
 
 
 
   public PDS base64Encrypt(PDS pds)
     throws NimException
   {
     PDS pdsencoded = new PDS();
     byte[] bs = new byte[pds.nativecountbytes()];
     int offset = pds.makebuffer(bs, 0);
     String encoded = NimBase64.encode(bs);
     pdsencoded.put("crdta", encoded);
     pdsencoded.put("crlen", bs.length);
     return pdsencoded;
   }
   
 
 
 
 
   public static String md5Sum(String filename)
     throws NimException
   {
     try
     {
       File file = new File(filename);
       int l = (int)file.length();
       byte[] msg = new byte[l];
       FileInputStream fis = new FileInputStream(file);
       int nbytes = fis.read(msg);
       if (nbytes != l) {
         throw new NimException(110, "" + l + " bytes are unavailable");
       }
       fis.close();
       
       return md5Digest(msg);
     } catch (IOException ioe) {
       throw new NimException(110, "Unable to open file " + filename, ioe);
     }
   }
 }


/* Location:              C:\Users\sergiomv\Desktop\Env\DOC\CA\MEUS_TESTES\probe-sdk\jboss-probe-1.4.0-jars\original_jars_from_probe_deployment\probes\application\jboss\jar\jboss.jar!\com\nimsoft\nimbus\NimSecurity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */