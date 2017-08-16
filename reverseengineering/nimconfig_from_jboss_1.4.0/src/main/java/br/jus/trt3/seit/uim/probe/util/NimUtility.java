/*     */ package br.jus.trt3.seit.uim.probe.util;
/*     */ 
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.util.Enumeration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NimUtility
/*     */ {
/*  14 */   public static int MS_PER_MINUTE = 60000;
/*     */   
/*     */ 
/*  17 */   public static int MS_PER_HOUR = MS_PER_MINUTE * 60;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String displayPDS(PDS pds)
/*     */   {
/*  25 */     return displayPDS(null, pds, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String displayPDS(String text, PDS pds)
/*     */   {
/*  37 */     return displayPDS(text, pds, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String displayPDS(PDS pds, boolean multiline)
/*     */   {
/*  49 */     return displayPDS(null, pds, multiline);
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
/*     */   public static String displayPDS(String text, PDS pds, boolean multiline)
/*     */   {
/*  63 */     StringBuffer sb = new StringBuffer();
/*  64 */     collectPDSInfo(text, pds, sb, multiline, 0);
/*  65 */     return sb.toString();
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
/*     */   private static void collectPDSInfo(String pdskey, PDS pds, StringBuffer sb, boolean multiline, int level)
/*     */   {
/*     */     try
/*     */     {
/*  80 */       if (multiline) {
/*  81 */         sb.append("\n");
/*     */       }
/*  83 */       for (int i = -1; i < level; i++) {
/*  84 */         sb.append("    ");
/*     */       }
/*  86 */       if (pdskey != null) {
/*  87 */         sb.append(pdskey + ": ");
/*     */       }
/*  89 */       boolean b = false;
/*  90 */       if (pds != null) {
/*  91 */         Enumeration keys = pds.keys();
/*  92 */         while ((keys != null) && (keys.hasMoreElements())) {
/*  93 */           if (b) {
/*  94 */             sb.append(", ");
/*     */           }
/*  96 */           String key = (String)keys.nextElement();
/*  97 */           int type = 0;
/*  98 */           type = pds.getType(key);
/*     */           
/* 100 */           switch (type) {
/*     */           case 7: 
/* 102 */             sb.append(key + "=" + pds.getString(key));
/* 103 */             b = true;
/* 104 */             break;
/*     */           case 1: 
/* 106 */             sb.append(key + "=" + pds.getInt(key));
/* 107 */             b = true;
/* 108 */             break;
/*     */           case 27: 
/* 110 */             sb.append(key + "=" + pds.getLong(key));
/* 111 */             b = true;
/* 112 */             break;
/*     */           case 16: 
/* 114 */             sb.append(key + "=" + pds.getDouble(key));
/* 115 */             b = true;
/* 116 */             break;
/*     */           case 22: 
/* 118 */             byte[] bytes = pds.getBytes(key);
/* 119 */             int l = bytes.length;
/* 120 */             if (bytes.length > 100) {
/* 121 */               l = 100;
/*     */             }
/* 123 */             sb.append(key + "=" + new String(bytes, 0, l));
/* 124 */             b = true;
/* 125 */             break;
/*     */           case 3: 
/* 127 */             sb.append("table " + key + ": ");
/* 128 */             int[] is = pds.getTableInts(key);
/* 129 */             for (int i = 0; i < is.length; i++) {
/* 130 */               if (i > 0) {
/* 131 */                 sb.append(",");
/*     */               }
/* 133 */               sb.append("" + is[i]);
/*     */             }
/* 135 */             b = true;
/* 136 */             break;
/*     */           case 8: 
/* 138 */             sb.append("table " + key + ": ");
/* 139 */             String[] ss = pds.getTableStrings(key);
/* 140 */             for (int i = 0; i < ss.length; i++) {
/* 141 */               if (i > 0) {
/* 142 */                 sb.append(",");
/*     */               }
/* 144 */               sb.append("" + ss[i]);
/*     */             }
/* 146 */             b = true;
/* 147 */             break;
/*     */           case 24: 
/* 149 */             sb.append("table " + key + ": ");
/* 150 */             PDS[] pdss = pds.getTablePDSs(key);
/* 151 */             for (int i = 0; i < pdss.length; i++) {
/* 152 */               if (i > 0) {
/* 153 */                 sb.append(",");
/*     */               }
/* 155 */               sb.append(displayPDS(pdss[i], multiline));
/*     */             }
/* 157 */             b = true;
/* 158 */             break;
/*     */           case 21: 
/*     */             break;
/*     */           case 2: case 4: case 5: case 6: case 9: case 10: case 11: case 12: case 13: case 14: case 15: case 17: case 18: case 19: case 20: case 23: case 25: case 26: default: 
/* 162 */             sb.append("type " + type + " not found");
/* 163 */             b = true;
/*     */           }
/*     */         }
/* 166 */         keys = pds.keys();
/* 167 */         while ((keys != null) && (keys.hasMoreElements())) {
/* 168 */           String key = (String)keys.nextElement();
/*     */           
/* 170 */           if (pds.getType(key) == 21) {
/* 171 */             collectPDSInfo(key, pds.getPDS(key), sb, multiline, level + 1);
/*     */           }
/*     */         }
/*     */       } else {
/* 175 */         sb.append("null");
/*     */       }
/*     */     } catch (Exception e) {
/* 178 */       sb.append("Got Exception " + e);
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
/*     */   public static String displayHex(byte[] bytes, int inrow)
/*     */   {
/* 191 */     return displayHex(bytes, bytes.length, inrow);
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
/*     */   public static String displayHex(byte[] bytes, int length, int inrow)
/*     */   {
/* 204 */     StringBuffer sb = new StringBuffer();
/* 205 */     for (int i = 0; i < length; i++) {
/* 206 */       if ((i % inrow == 0) && (i > 0)) {
/* 207 */         sb.append("\n");
/*     */       }
/* 209 */       String s = Integer.toHexString(bytes[i]);
/* 210 */       if (s.startsWith("ffffff")) {
/* 211 */         s = s.substring(6);
/*     */       }
/* 213 */       if (s.length() == 1) {
/* 214 */         s = "0" + s;
/*     */       }
/* 216 */       sb.append("0x" + s);
/* 217 */       if (i < length - 1) {
/* 218 */         sb.append(",");
/*     */       }
/*     */     }
/* 221 */     sb.append("\n");
/* 222 */     return sb.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String bytesToHexString(byte[] bytes)
/*     */   {
/* 232 */     return bytesToHexString(bytes, 0, bytes.length);
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
/*     */   public static String bytesToHexString(byte[] bytes, int offset, int length)
/*     */   {
/* 245 */     StringBuilder sb = new StringBuilder(length * 2);
/* 246 */     for (int i = offset; i < offset + length; i++) {
/* 247 */       sb.append(Integer.toHexString((bytes[i] & 0xFF) >>> 4));
/* 248 */       sb.append(Integer.toHexString(bytes[i] & 0xF));
/*     */     }
/* 250 */     return sb.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] hexStringToBytes(String hex)
/*     */   {
/* 261 */     int length = hex.length() / 2;
/* 262 */     byte[] bytes = new byte[length];
/* 263 */     for (int i = 0; i < length; i++) {
/* 264 */       int hi = 2 * i;
/* 265 */       bytes[i] = ((byte)(Byte.parseByte(hex.substring(hi, hi + 1), 16) << 4)); int 
/* 266 */         tmp46_45 = i; byte[] tmp46_44 = bytes;tmp46_44[tmp46_45] = ((byte)(tmp46_44[tmp46_45] | Byte.parseByte(hex.substring(hi + 1, hi + 2), 16)));
/*     */     }
/* 268 */     return bytes;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isEmpty(String str)
/*     */   {
/* 278 */     return (str == null) || (str.length() == 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isNotEmpty(String str)
/*     */   {
/* 289 */     return !isEmpty(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isBlank(String str)
/*     */   {
/* 300 */     return (str == null) || (str.trim().length() == 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isNotBlank(String str)
/*     */   {
/* 310 */     return !isBlank(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int getIntervalInMillis(String interval)
/*     */   {
/* 322 */     if (isBlank(interval)) {
/* 323 */       return -1;
/*     */     }
/*     */     
/* 326 */     int val = getMillis(interval, "hours", MS_PER_HOUR);
/* 327 */     if (val == -1) val = getMillis(interval, "hrs", MS_PER_HOUR);
/* 328 */     if (val == -1) val = getMillis(interval, "h", MS_PER_HOUR);
/* 329 */     if (val == -1) val = getMillis(interval, "minutes", MS_PER_MINUTE);
/* 330 */     if (val == -1) val = getMillis(interval, "min", MS_PER_MINUTE);
/* 331 */     if (val == -1) val = getMillis(interval, "m", MS_PER_MINUTE);
/* 332 */     if (val == -1) val = getMillis(interval, "seconds", 1000);
/* 333 */     if (val == -1) val = getMillis(interval, "sec", 1000);
/* 334 */     if (val == -1) val = getMillis(interval, "s", 1000);
/* 335 */     if (val == -1) val = getMillis(interval, "milliseconds", 1);
/* 336 */     if (val == -1) val = getMillis(interval, "millis", 1);
/* 337 */     if (val == -1) val = getMillis(interval, "ms", 1);
/* 338 */     return val;
/*     */   }
/*     */   
/*     */   private static int getMillis(String str, String unit, int multiplier) {
/* 342 */     int val = -1;
/* 343 */     if (str.endsWith(unit)) {
/* 344 */       String num = str.substring(0, str.length() - unit.length()).trim();
/* 345 */       val = Integer.parseInt(num) * multiplier;
/*     */     }
/* 347 */     return val;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getStackTrace(Throwable throwable)
/*     */   {
/* 358 */     String msg = null;
/* 359 */     if (throwable != null) {
/* 360 */       StringWriter sw = new StringWriter();
/* 361 */       PrintWriter pw = new PrintWriter(sw);
/* 362 */       throwable.printStackTrace(pw);
/* 363 */       pw.close();
/* 364 */       msg = sw.toString();
/*     */     }
/* 366 */     return msg;
/*     */   }
/*     */ }


/* Location:              C:\Users\sergiomv\Desktop\Env\DOC\CA\MEUS_TESTES\probe-sdk\jboss-probe-1.4.0-jars\original_jars_from_probe_deployment\probes\application\jboss\jar\jboss.jar!\com\nimsoft\nimbus\NimUtility.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */