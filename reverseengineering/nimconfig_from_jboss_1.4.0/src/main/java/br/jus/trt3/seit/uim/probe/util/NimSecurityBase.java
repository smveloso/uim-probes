/*     */ package br.jus.trt3.seit.uim.probe.util;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.Provider;
/*     */ import java.security.Security;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NimSecurityBase
/*     */ {
/*  17 */   private static final char[] HexChars = "0123456789abcdef".toCharArray();
/*  18 */   private int blockSize = 1;
/*     */   
/*     */ 
/*     */   private Object theGeneratedKey;
/*     */   
/*     */ 
/*  24 */   private int iMode = 0;
/*     */   
/*     */ 
/*     */ 
/*     */   protected boolean hasSecurity()
/*     */   {
/*  30 */     return this.iMode == 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void setKey(int outlen, int start)
/*     */     throws NimException
/*     */   {
/*  39 */     setKey();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void setKey()
/*     */     throws NimException
/*     */   {
/*  48 */     this.iMode = 0;
/*  49 */     String key = "The quick brown fox jumps over the lazy dog";
/*  50 */     byte[] bytes = new byte[1024];
/*  51 */     byte[] kbytes = key.getBytes();
/*  52 */     for (int i = 0; i < bytes.length; i++) {
/*  53 */       if (i < kbytes.length) {
/*  54 */         bytes[i] = kbytes[i];
/*     */       } else {
/*  56 */         bytes[i] = ((byte)i);
/*     */       }
/*     */     }
/*  59 */     byte[] md5digest = md5DigestAsBytes(bytes);
/*     */     try {
/*  61 */       this.theGeneratedKey = Twofish_Algorithm.makeKey(md5digest);
/*  62 */       this.blockSize = Twofish_Algorithm.blockSize();
/*     */     } catch (Exception ike) {
/*  64 */       throw new NimException(110, "Invalid security key '" + new String(md5digest) + "'", ike);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 

/*     */   protected void setKey(String key)
/*     */     throws NimException
/*     */   {
/*  80 */     setKey(key, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void setKey(String key, String charset)
/*     */     throws NimException
/*     */   {
/*     */     try
/*     */     {
/*  93 */       setTwofishKey(key, charset);
/*     */     } catch (UnsupportedEncodingException e) {
/*  95 */       throw new NimException(1, "Unsupported charset: " + charset, e);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTwofishKey(String key, String charset)
/*     */     throws UnsupportedEncodingException
/*     */   {
/* 117 */     this.iMode = 1;
/* 119 */     byte[] kbytes; if (NimUtility.isEmpty(charset)) {
/* 120 */       kbytes = key.getBytes();
/*     */     } else {
/* 122 */       kbytes = key.getBytes(charset);
/*     */     }
/*     */     byte[] md5digest;
/*     */     try
/*     */     {
/* 127 */       md5digest = md5DigestAsBytes(kbytes);
/*     */     }
/*     */     catch (NimException md5NotFound)
/*     */     {
/* 131 */       throw new RuntimeException(md5NotFound);
/*     */     }
/*     */     try {
/* 134 */       this.theGeneratedKey = Twofish_Algorithm.makeKey(md5digest);
/* 135 */       this.blockSize = Twofish_Algorithm.blockSize();
/*     */ 
/*     */     }
/*     */     catch (Exception never)
/*     */     {
/* 140 */       throw new RuntimeException(never);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected byte[] encryptDecrypt(byte[] bytearray, boolean encrypt)
/*     */   {
/* 151 */     int l = bytearray.length;
/* 152 */     int lret = bytearray.length;
/* 153 */     int idx = 0;
/* 154 */     if (l % this.blockSize != 0) {
/* 155 */       lret = lret + this.blockSize - l % this.blockSize;
/*     */     }
/* 157 */     byte[] bsresult = new byte[lret];
/* 158 */     while (idx < l) {
/* 159 */       byte[] bs = new byte[this.blockSize];
/*     */       
/* 161 */       for (int j = 0; j < this.blockSize; j++) {
/* 162 */         if (idx + j < l) {
/* 163 */           bs[j] = bytearray[(idx + j)];
/*     */         } else {
/* 165 */           bs[j] = 0;
/*     */         }
/*     */       }
/* 168 */       byte[] bsret = null;
/* 169 */       if (encrypt) {
/* 170 */         bsret = Twofish_Algorithm.blockEncrypt(bs, 0, this.theGeneratedKey);
/*     */       } else {
/* 172 */         bsret = Twofish_Algorithm.blockDecrypt(bs, 0, this.theGeneratedKey);
/*     */       }
/* 174 */       for (int j = 0; j < this.blockSize; j++) {
/* 175 */         bsresult[(idx + j)] = bsret[j];
/*     */       }
/* 177 */       idx += this.blockSize;
/*     */     }
/*     */     
/* 180 */     return bsresult;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int getMode()
/*     */   {
/* 189 */     return this.iMode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String md5Digest(String text)
/*     */     throws NimException
/*     */   {
/* 199 */     return md5Digest(text.getBytes());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String md5Digest(byte[] bytes)
/*     */     throws NimException
/*     */   {
/* 209 */     return toHexString(md5DigestAsBytes(bytes));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String toHexString(byte[] values)
/*     */   {
/* 218 */     char[] result = new char[values.length * 2];
/* 219 */     int length = values.length;
/* 220 */     for (int i = 0; i < length; i++)
/*     */     {
/* 222 */       result[(2 * i)] = HexChars[((values[i] & 0xF0) >>> 4)];
/* 223 */       result[(2 * i + 1)] = HexChars[(values[i] & 0xF)];
/*     */     }
/* 225 */     return new String(result);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] md5DigestAsBytes(byte[] bytes)
/*     */     throws NimException
/*     */   {
/*     */     try
/*     */     {
/* 240 */       return MessageDigest.getInstance("MD5").digest(bytes);
/*     */     }
/*     */     catch (NoSuchAlgorithmException never) {
/* 243 */       throw new NimException(110, "MD5 algorithm not found", never);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected static void listProviders()
/*     */   {
/* 252 */     Provider[] providers = Security.getProviders();
/* 253 */     for (int j = 0; j < providers.length; j++) {
/* 254 */       Provider provider = providers[j];
/* 255 */       System.out.println(provider.getInfo());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\sergiomv\Desktop\Env\DOC\CA\MEUS_TESTES\probe-sdk\jboss-probe-1.4.0-jars\original_jars_from_probe_deployment\probes\application\jboss\jar\jboss.jar!\com\nimsoft\nimbus\NimSecurityBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */