/*      */ package br.jus.trt3.seit.uim.probe.util;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.nio.charset.Charset;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Set;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PDS
/*      */   implements Serializable
/*      */ {
/*      */   private static final String PDS_ENCODING_CHARSET = "pds.encoding.charset";
/*      */   private static final String PDS_DECODING_CHARSET = "pds.decoding.charset";
/*   84 */   private static final String DEFAULT_CHARSET = Charset.defaultCharset().name();
/*      */   
/*   86 */   private static transient NimLog logger = null;
/*      */   
/*      */ 
/*      */   public static final int PDS_I = 1;
/*      */   
/*      */   public static final int PDS_PPI = 3;
/*      */   
/*      */   public static final int PDS_PCH = 7;
/*      */   
/*      */   public static final int PDS_PPCH = 8;
/*      */   
/*      */   public static final int PDS_F = 16;
/*      */   
/*      */   public static final int PDS_PDS = 21;
/*      */   
/*      */   public static final int PDS_VOID = 22;
/*      */   
/*      */   public static final int PDS_PPDS = 24;
/*      */   
/*      */   public static final int PDS_I64 = 27;
/*      */   
/*  107 */   static final String[] PDS_TYPE_NAMES = { "N/A (0)", "int (1)", "N/A (2)", "int table (3)", "N/A (4)", "N/A (5)", "N/A (6)", "String (7)", "String table (8)", "N/A (9)", "N/A (10)", "N/A (11)", "N/A (12)", "N/A (13)", "N/A (14)", "N/A (15)", "double (16)", "N/A (17)", "N/A (18)", "N/A (19)", "N/A (20)", "PDS (21)", "byte[] (22)", "N/A (23)", "PDS table (24)", "N/A (25)", "N/A (26)", "int64 (24)" };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*  149 */   public static String encoding = System.getProperty("pds.encoding.charset", DEFAULT_CHARSET);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  157 */   private static String decoding = System.getProperty("pds.decoding.charset", DEFAULT_CHARSET);
/*      */   
/*      */ 
/*      */   Map<String, Element> ht;
/*      */   
/*      */ 
/*      */ 
/*      */   public PDS()
/*      */   {
/*  166 */     this.ht = getHashTable();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PDS(String[] paramsAndValues)
/*      */     throws NimException
/*      */   {
/*  179 */     this();
/*  180 */     if (paramsAndValues.length % 2 != 0) {
/*  181 */       throw new IllegalArgumentException("Must provide an equal number of parameters and values into this method.");
/*      */     }
/*      */     
/*  184 */     for (int i = 0; i < paramsAndValues.length; i += 2) {
/*  185 */       put(paramsAndValues[i], paramsAndValues[(i + 1)], 7);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Map<String, Element> getInternalHT()
/*      */   {
/*  195 */     return this.ht;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Map<String, Element> getHashTable()
/*      */   {
/*  205 */     return new HashMap();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public static void setEncoding(String charset)
/*      */   {
/*  224 */     encoding = charset;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getEncoding()
/*      */   {
/*  237 */     return encoding;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public static void setDecoding(String charset)
/*      */   {
/*  255 */     decoding = charset;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getDecoding()
/*      */   {
/*  270 */     return decoding;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PDS(byte[] buf)
/*      */     throws NimException
/*      */   {
/*  285 */     this(buf, 0, buf.length);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PDS(byte[] buf, int offset, int length)
/*      */     throws NimException
/*      */   {
/*  301 */     this();
/*  302 */     makePDS(buf, offset, length);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PDS(byte[] buf, int offset, int length, String charset)
/*      */     throws NimException, UnsupportedEncodingException
/*      */   {
/*  320 */     this();
/*  321 */     makePDS(buf, offset, length, charset);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   void clean()
/*      */   {
/*  328 */     this.ht = getHashTable();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int nativecountbytes()
/*      */   {
/*      */     try
/*      */     {
/*  345 */       return nativecountbytes(getEncoding());
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (UnsupportedEncodingException doh)
/*      */     {
/*      */ 
/*  352 */       throw new RuntimeException(doh);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int nativecountbytes(String charset)
/*      */     throws UnsupportedEncodingException
/*      */   {
/*  366 */     int cnt = 0;
/*  367 */     Enumeration<String> keys = Collections.enumeration(this.ht.keySet());
/*  368 */     while (keys.hasMoreElements()) {
/*  369 */       String key = (String)keys.nextElement();
/*  370 */       Element element = (Element)this.ht.get(key);
/*  371 */       cnt += key.getBytes(charset).length + 1;
/*  372 */       String s = String.valueOf(element.type);
/*  373 */       cnt += s.getBytes(charset).length + 1;
/*      */       
/*  375 */       switch (element.type) {
/*      */       case 1: 
/*      */       case 7: 
/*      */       case 16: 
/*      */       case 27: 
/*  380 */         if (element.value != null) {
/*  381 */           int l = element.value.getBytes(charset).length + 1;
/*  382 */           s = String.valueOf(l);
/*  383 */           cnt += s.getBytes(charset).length + 1;
/*  384 */           cnt += l;
/*      */         } else {
/*  386 */           cnt += 2;
/*      */         }
/*  388 */         break;
/*      */       case 22: 
/*  390 */         if (element.buf != null) {
/*  391 */           s = String.valueOf(element.buf.length);
/*  392 */           cnt += s.getBytes(charset).length + 1;
/*  393 */           cnt += element.buf.length;
/*      */         } else {
/*  395 */           s = String.valueOf(0);
/*  396 */           cnt += s.getBytes(charset).length + 1;
/*  397 */           cnt += 0;
/*      */         }
/*  399 */         break;
/*      */       case 21: 
/*  401 */         if (element.pds != null) {
/*  402 */           int l = element.pds.nativecountbytes(charset);
/*  403 */           s = String.valueOf(l);
/*  404 */           cnt += s.getBytes(charset).length + 1;
/*  405 */           cnt += l;
/*      */         } else {
/*  407 */           s = String.valueOf(0);
/*  408 */           cnt += s.getBytes(charset).length + 1;
/*  409 */           cnt += 0;
/*      */         }
/*  411 */         break;
/*      */       case 3: 
/*      */       case 8: 
/*      */       case 24: 
/*  415 */         if (element.tpds != null) {
/*  416 */           int l = element.tpds.nativecountbytes(charset);
/*  417 */           s = String.valueOf(l);
/*  418 */           cnt += s.getBytes(charset).length + 1;
/*  419 */           cnt += l;
/*      */         } else {
/*  421 */           s = String.valueOf(0);
/*  422 */           cnt += s.getBytes(charset).length + 1;
/*  423 */           cnt += 0;
/*      */         }
/*      */         break;
/*      */       }
/*      */     }
/*  428 */     return cnt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int makebuffer(byte[] buf, int offset)
/*      */     throws NimException
/*      */   {
/*      */     try
/*      */     {
/*  447 */       return makebuffer(buf, offset, getEncoding());
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (UnsupportedEncodingException uee)
/*      */     {
/*      */ 
/*  454 */       throw new RuntimeException(uee);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int makebuffer(byte[] buf, int offset, String charset)
/*      */     throws NimException, UnsupportedEncodingException
/*      */   {
/*  475 */     Enumeration<String> keys = Collections.enumeration(this.ht.keySet());
/*  476 */     int localOffset = offset;
/*  477 */     while (keys.hasMoreElements()) {
/*  478 */       String key = (String)keys.nextElement();
/*  479 */       Element element = (Element)this.ht.get(key);
/*      */       
/*      */ 
/*  482 */       localOffset = str2bytes(buf, localOffset, key, charset);
/*  483 */       buf[(localOffset++)] = 0;
/*      */       
/*      */ 
/*  486 */       String s = String.valueOf(element.type);
/*  487 */       localOffset = str2bytes(buf, localOffset, s, charset);
/*  488 */       buf[(localOffset++)] = 0;
/*      */       
/*      */ 
/*  491 */       switch (element.type) {
/*      */       case 1: 
/*      */       case 7: 
/*      */       case 16: 
/*      */       case 27: 
/*  496 */         if (element.value != null) {
/*  497 */           int l = element.value.getBytes(charset).length + 1;
/*  498 */           s = String.valueOf(l);
/*      */         } else {
/*  500 */           s = "0";
/*      */         }
/*  502 */         break;
/*      */       case 22: 
/*  504 */         s = String.valueOf(element.buf.length);
/*  505 */         break;
/*      */       case 21: 
/*  507 */         s = String.valueOf(element.pds.nativecountbytes(charset));
/*  508 */         break;
/*      */       case 3: 
/*      */       case 8: 
/*      */       case 24: 
/*  512 */         int l = element.tpds.nativecountbytes(charset);
/*  513 */         s = String.valueOf(l);
/*  514 */         break;
/*      */       case 2: case 4: case 5: case 6: case 9: case 10: case 11: case 12: case 13: case 14: case 15: case 17: case 18: case 19: case 20: case 23: case 25: case 26: default: 
/*  516 */         throw new RuntimeException("Type value invalid " + element.type + "(processing " + NimUtility.displayPDS(this) + ")");
/*      */       }
/*      */       
/*  519 */       localOffset = str2bytes(buf, localOffset, s, charset);
/*  520 */       buf[(localOffset++)] = 0;
/*      */       
/*      */ 
/*  523 */       switch (element.type) {
/*      */       case 1: 
/*      */       case 7: 
/*      */       case 16: 
/*      */       case 27: 
/*  528 */         if (element.value != null) {
/*  529 */           localOffset = str2bytes(buf, localOffset, element.value, charset);
/*  530 */           buf[(localOffset++)] = 0;
/*      */         }
/*      */         break;
/*      */       case 22: 
/*  534 */         System.arraycopy(element.buf, 0, buf, localOffset, element.buf.length);
/*  535 */         localOffset += element.buf.length;
/*  536 */         break;
/*      */       case 21: 
/*  538 */         localOffset = element.pds.makebuffer(buf, localOffset, charset);
/*  539 */         break;
/*      */       case 3: 
/*      */       case 8: 
/*      */       case 24: 
/*  543 */         localOffset = element.tpds.makebuffer(buf, localOffset, charset);
/*  544 */         break;
/*      */       case 2: case 4: case 5: case 6: case 9: case 10: case 11: case 12: case 13: case 14: case 15: case 17: case 18: case 19: case 20: case 23: case 25: case 26: default: 
/*  546 */         throw new RuntimeException("Type value invalid " + element.type + "(processing " + NimUtility.displayPDS(this) + ")");
/*      */       }
/*      */       
/*      */     }
/*  550 */     return localOffset;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int str2bytes(byte[] buf, int offset, String s)
/*      */   {
/*      */     try
/*      */     {
/*  569 */       return str2bytes(buf, offset, s, getEncoding());
/*      */     }
/*      */     catch (UnsupportedEncodingException uee)
/*      */     {
/*  573 */       throw new RuntimeException(uee);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected int str2bytes(byte[] buf, int offset, String s, String charset)
/*      */     throws UnsupportedEncodingException
/*      */   {
/*  592 */     if (s == null) {
/*  593 */       return offset;
/*      */     }
/*      */     
/*  596 */     int idx = offset;
/*      */     try
/*      */     {
/*  599 */       byte[] encodedBytes = s.getBytes(charset);
/*      */       
/*  601 */       int len = encodedBytes.length;
/*  602 */       for (int i = 0; i < len; i++) {
/*  603 */         byte val = encodedBytes[i];
/*      */         
/*      */ 
/*      */ 
/*  607 */         buf[(idx++)] = val;
/*      */       }
/*      */     } catch (Exception er) {
/*  610 */       if (logger == null) {
/*  611 */         logger = NimLog.getLogger(PDS.class.getName());
/*      */       }
/*  613 */       logger.log(6, "Failed to do getBytes using encoding:" + charset);
/*  614 */       logger.logStackTrace(6, er);
/*      */     }
/*  616 */     return idx;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public long size()
/*      */   {
/*  626 */     return nativecountbytes();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public byte[] getPDSAsBytes()
/*      */     throws NimException
/*      */   {
/*      */     try
/*      */     {
/*  636 */       return getPDSAsBytes(getEncoding());
/*      */ 
/*      */ 
/*      */     }
/*      */     catch (UnsupportedEncodingException uee)
/*      */     {
/*      */ 
/*  643 */       throw new RuntimeException(uee);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public byte[] getPDSAsBytes(String charset)
/*      */     throws UnsupportedEncodingException, NimException
/*      */   {
/*  658 */     return internalFormat(charset);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private byte[] internalFormat(String charset)
/*      */     throws UnsupportedEncodingException, NimException
/*      */   {
/*  673 */     int cnt = nativecountbytes(charset);
/*  674 */     byte[] buf = new byte[cnt];
/*  675 */     makebuffer(buf, 0, charset);
/*  676 */     return buf;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void makePDS(byte[] buf, int offset, int len)
/*      */     throws NimException
/*      */   {
/*      */     try
/*      */     {
/*  697 */       makePDS(buf, offset, len, getDecoding());
/*      */     } catch (UnsupportedEncodingException uee) {
/*  699 */       throw new NimException(1, uee);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void makePDS(byte[] buf, int offset, int len, String charset)
/*      */     throws NimException, UnsupportedEncodingException
/*      */   {
/*  718 */     makePDSInternal(buf, offset, len, charset, "");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void makePDSInternal(byte[] buf, int offset, int len, String charset, String keyPrefix)
/*      */     throws NimException, UnsupportedEncodingException
/*      */   {
/*  726 */     int nc = len;
/*      */     
/*  728 */     int cpidx = offset;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  736 */     if (charset == null) {
/*  737 */       throw new UnsupportedEncodingException("Specified charset is null");
/*      */     }
/*      */     
/*  740 */     checkBounds(offset + len - 1, buf.length);
/*      */     
/*  742 */     clean();
/*  743 */     while (nc > 0)
/*      */     {
/*  745 */       int sidx = cpidx;
/*      */       int ch;
/*      */       do {
/*  748 */         nc--;  if (nc > 0) {
/*  749 */           ch = buf[(cpidx++)];
/*      */         } else {
/*  751 */           return;
/*      */         }
/*  753 */       } while (ch != 0);
/*  754 */       int seglen = cpidx - sidx;
/*  755 */       if (seglen <= 0) {
/*  756 */         return;
/*      */       }
/*  758 */       String name = new String(buf, sidx, seglen - 1, charset);
/*      */       
/*  760 */       if (!"".equals(name.trim()))
/*      */       {
/*      */ 
/*      */ 
/*  764 */         String keyPath = keyPrefix + ":" + name;
/*      */         
/*      */ 
/*  767 */         nc--; if (nc > 0) {
/*  768 */           ch = buf[(cpidx++)];
/*      */         } else {
/*  770 */           return;
/*      */         }
/*      */         
/*  773 */         boolean typeFound = false;
/*  774 */         int val = 0;
/*      */         
/*  776 */         while ((ch >= 48) && (ch <= 57))
/*      */         {
/*      */ 
/*  779 */           val = val * 10 + ch - 48;
/*  780 */           typeFound = true;
/*      */           
/*  782 */           nc--; if (nc > 0) {
/*  783 */             ch = buf[(cpidx++)];
/*      */           } else {
/*  785 */             return;
/*      */           }
/*      */         }
/*  788 */         int type = val;
/*  789 */         if (!typeFound) {
/*  790 */           throw new RuntimeException("No type found for " + keyPath + " (processing " + NimUtility.bytesToHexString(buf, offset, len) + ")");
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  796 */         nc--; if (nc > 0) {
/*  797 */           ch = buf[(cpidx++)];
/*      */         } else {
/*  799 */           return;
/*      */         }
/*      */         
/*  802 */         boolean lengthFound = false;
/*  803 */         val = 0;
/*      */         
/*  805 */         while ((ch >= 48) && (ch <= 57))
/*      */         {
/*      */ 
/*  808 */           lengthFound = true;
/*  809 */           val = val * 10 + ch - 48;
/*      */           
/*  811 */           nc--; if (nc <= 0) break;
/*  812 */           ch = buf[(cpidx++)];
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*  817 */         int length = val;
/*  818 */         if (!lengthFound) {
/*  819 */           throw new RuntimeException("Length not found for " + keyPath + " (processing " + NimUtility.bytesToHexString(buf, offset, len) + ")");
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  827 */         int offsetLength = length;
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  832 */         sidx = cpidx;
/*  833 */         cpidx += offsetLength;
/*  834 */         nc -= offsetLength;
/*  835 */         Element element = getElement(name);
/*  836 */         element.type = type;
/*  837 */         switch (type) {
/*      */         case 1: 
/*      */         case 7: 
/*      */         case 16: 
/*      */         case 27: 
/*  842 */           if (length > 0) {
/*  843 */             int strLength = length - 1;
/*  844 */             checkBounds(sidx + strLength, buf.length);
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  854 */             for (int i = 0; i < length; i++) {
/*  855 */               if (buf[(sidx + i)] == 0) {
/*  856 */                 strLength = i;
/*  857 */                 break;
/*      */               }
/*      */             }
/*  860 */             element.value = new String(buf, sidx, strLength, charset);
/*      */           } else {
/*  862 */             element.value = null;
/*      */           }
/*  864 */           break;
/*      */         case 22: 
/*  866 */           checkBounds(sidx + length - 1, buf.length);
/*  867 */           element.buf = new byte[length];
/*  868 */           System.arraycopy(buf, sidx, element.buf, 0, length);
/*  869 */           break;
/*      */         
/*      */ 
/*      */         case 21: 
/*  873 */           element.pds = new PDS();
/*  874 */           element.pds.makePDSInternal(buf, sidx, length, charset, keyPath);
/*      */           
/*      */ 
/*  877 */           break;
/*      */         case 3: 
/*      */         case 8: 
/*      */         case 24: 
/*  881 */           element.tpds = new PDS();
/*  882 */           element.tpds.makePDSInternal(buf, sidx, length, charset, keyPath);
/*      */         }
/*      */         
/*      */         
/*      */ 
/*  887 */         this.ht.put(name, element);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void putInt(String key, int value)
/*      */     throws NimException
/*      */   {
/*  899 */     put(key, value);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void put(String key, int value)
/*      */     throws NimException
/*      */   {
/*  910 */     put(key, "" + value, 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void putDouble(String key, double value)
/*      */     throws NimException
/*      */   {
/*  921 */     put(key, value);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void put(String key, double value)
/*      */     throws NimException
/*      */   {
/*      */     String str;
/*      */     
/*      */     
/*  933 */     if (Double.isNaN(value)) {
/*  934 */       str = "nan"; 
               } else {
/*  935 */       if (Double.POSITIVE_INFINITY == value) {
/*  936 */         str = "inf"; 
                 } else {
/*  937 */         if (Double.NEGATIVE_INFINITY == value) {
/*  938 */           str = "-inf";
/*      */         } else
/*  940 */           str = String.valueOf(value);
/*      */       } }
/*  942 */     put(key, str, 16);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void putString(String key, String value)
/*      */     throws NimException
/*      */   {
/*  953 */     put(key, value);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void put(String key, String value)
/*      */     throws NimException
/*      */   {
/*  964 */     put(key, value, 7);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void put(String key, String value, int type)
/*      */     throws NimException
/*      */   {
/*  977 */     Element element = getElement(key);
/*  978 */     element.type = type;
/*  979 */     element.value = value;
/*  980 */     this.ht.put(key, element);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void putBytes(String key, byte[] bytes)
/*      */     throws NimException
/*      */   {
/*  991 */     put(key, bytes);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void put(String key, byte[] bytes)
/*      */     throws NimException
/*      */   {
/* 1002 */     Element element = getElement(key);
/* 1003 */     element.type = 22;
/* 1004 */     element.buf = new byte[bytes.length];
/* 1005 */     System.arraycopy(bytes, 0, element.buf, 0, bytes.length);
/* 1006 */     this.ht.put(key, element);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void putPDS(String key, PDS pds)
/*      */     throws NimException
/*      */   {
/* 1017 */     put(key, pds);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void put(String key, PDS pds)
/*      */     throws NimException
/*      */   {
/* 1028 */     if (pds == null) {
/* 1029 */       return;
/*      */     }
/*      */     
/* 1032 */     Element element = getElement(key);
/* 1033 */     element.type = 21;
/* 1034 */     element.pds = pds;
/* 1035 */     this.ht.put(key, element);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void putTableString(String key, String value)
/*      */     throws NimException
/*      */   {
/* 1046 */     putTable(key, value);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void putTable(String key, String value)
/*      */     throws NimException
/*      */   {
/* 1057 */     Element element = getElement(key);
/* 1058 */     if (element.tpds == null) {
/* 1059 */       element.tpds = new PDS();
/*      */     }
/* 1061 */     element.tpds.put("" + element.tpds.ht.size(), value);
/* 1062 */     element.type = 8;
/* 1063 */     this.ht.put(key, element);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void putTableInt(String key, int value)
/*      */     throws NimException
/*      */   {
/* 1074 */     putTable(key, value);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void putTable(String key, int value)
/*      */     throws NimException
/*      */   {
/* 1085 */     Element element = getElement(key);
/* 1086 */     if (element.tpds == null) {
/* 1087 */       element.tpds = new PDS();
/*      */     }
/* 1089 */     element.tpds.put("" + element.tpds.ht.size(), value);
/* 1090 */     element.type = 3;
/* 1091 */     this.ht.put(key, element);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void putTablePDS(String key, PDS value)
/*      */     throws NimException
/*      */   {
/* 1102 */     putTable(key, value);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void putTable(String key, PDS value)
/*      */     throws NimException
/*      */   {
/* 1113 */     Element element = getElement(key);
/* 1114 */     if (element.tpds == null) {
/* 1115 */       element.tpds = new PDS();
/*      */     }
/* 1117 */     element.tpds.put("" + element.tpds.ht.size(), value);
/* 1118 */     element.type = 24;
/* 1119 */     this.ht.put(key, element);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void putTableStrings(String key, String[] values)
/*      */     throws NimException
/*      */   {
/* 1130 */     putTable(key, values);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void putTable(String key, String[] values)
/*      */     throws NimException
/*      */   {
/* 1141 */     for (String value : values) {
/* 1142 */       putTable(key, value);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void putTableInts(String key, int[] values)
/*      */     throws NimException
/*      */   {
/* 1154 */     putTable(key, values);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void putTable(String key, int[] values)
/*      */     throws NimException
/*      */   {
/* 1165 */     for (int value : values) {
/* 1166 */       putTable(key, value);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void putTablePDSs(String key, PDS[] values)
/*      */     throws NimException
/*      */   {
/* 1178 */     putTable(key, values);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void putTable(String key, PDS[] values)
/*      */     throws NimException
/*      */   {
/* 1189 */     for (PDS value : values) {
/* 1190 */       putTable(key, value);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private Element getElement(String key)
/*      */     throws NimException
/*      */   {
/* 1203 */     if ((key == null) || (key.equals(""))) {
/* 1204 */       throw new NimException(3, "Empty key is not allowed");
/*      */     }
/* 1206 */     Element element = (Element)this.ht.get(key);
/* 1207 */     if (element == null) {
/* 1208 */       element = new Element();
/*      */     }
/* 1210 */     return element;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getType(String key)
/*      */     throws NimException
/*      */   {
/* 1222 */     Element element = (Element)this.ht.get(key);
/* 1223 */     if (element == null) {
/* 1224 */       throw new NimException(38, "No element found for key " + key);
/*      */     }
/* 1226 */     return element.type;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTypeAsName(String key)
/*      */     throws NimException
/*      */   {
/* 1237 */     return PDS_TYPE_NAMES[getType(key)];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object get(String key)
/*      */     throws NimException
/*      */   {
/* 1249 */     getType(key);
/* 1250 */     Element element = (Element)this.ht.get(key);
/*      */     
/* 1252 */     if (element.value != null) {
/* 1253 */       return element.value;
/*      */     }
/* 1255 */     if (element.buf != null) {
/* 1256 */       return element.buf;
/*      */     }
/* 1258 */     if (element.pds != null) {
/* 1259 */       return element.pds;
/*      */     }
/* 1261 */     if (element.tpds != null) {
/* 1262 */       return element.tpds;
/*      */     }
/* 1264 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getInt(String key, int def)
/*      */     throws NimException
/*      */   {
/*      */     try
/*      */     {
/* 1283 */       return Integer.parseInt(getString(key, String.valueOf(def)));
/*      */     } catch (Exception e) {}
/* 1285 */     return def;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getInt(String key)
/*      */     throws NimException
/*      */   {
/* 1299 */     if (getType(key) != 1) {
/* 1300 */       throw new NimException(37, "Wrong type, the element type is " + getTypeAsName(key));
/*      */     }
/*      */     
/* 1303 */     return Integer.parseInt(getString(key));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getIntIfExists(String key)
/*      */     throws NimException
/*      */   {
/* 1318 */     if (checkKey(key) != 1) {
/* 1319 */       return 0;
/*      */     }
/* 1321 */     return getInt(key);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double getDoubleIfExists(String key)
/*      */     throws NimException
/*      */   {
/* 1336 */     if (checkKey(key) != 16) {
/* 1337 */       return 0.0D;
/*      */     }
/* 1339 */     return getDouble(key);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public long getLong(String key, long def)
/*      */     throws NimException
/*      */   {
/* 1357 */     long l = def;
/*      */     try {
/* 1359 */       if (checkKey(key) == 27)
/*      */       {
/* 1361 */         String value = getString(key, null);
/* 1362 */         Long L = unpackLong(value);
/* 1363 */         if (L != null) {}
/* 1364 */         return L.longValue();
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1369 */       return Long.parseLong(getString(key, String.valueOf(def)));
/*      */     }
/*      */     catch (Exception e) {}
/* 1372 */     return def;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void putLong(String key, long value)
/*      */     throws NimException
/*      */   {
/* 1384 */     put(key, packLong(value), 27);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public long getLong(String key)
/*      */     throws NimException
/*      */   {
/* 1397 */     long l = 0L;
/* 1398 */     if (getType(key) != 27) {
/* 1399 */       throw new NimException(37, "Wrong type, the element type is " + getTypeAsName(key));
/*      */     }
/*      */     
/* 1402 */     String value = getString(key, null);
/* 1403 */     Long longVal = unpackLong(value);
/* 1404 */     if (longVal != null) {
/* 1405 */       l = longVal.longValue();
/*      */     }
/* 1407 */     return l;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String packLong(long value)
/*      */   {
/* 1418 */     long highPart = value >> 32;
/* 1419 */     long lowPart = 0xFFFFFFFF & value;
/* 1420 */     return highPart + "." + lowPart;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private Long unpackLong(String value)
/*      */   {
/* 1430 */     Long longVal = null;
/* 1431 */     if (value != null) {
/* 1432 */       int l = value.indexOf('.');
/* 1433 */       if (l != -1) {
/* 1434 */         long hi = Long.parseLong(value.substring(0, l));
/* 1435 */         long low = Long.parseLong(value.substring(l + 1));
/* 1436 */         longVal = Long.valueOf(low | hi << 32);
/*      */       }
/*      */     }
/* 1439 */     return longVal;
/*      */   }
/*      */   
/*      */   private double parseDouble(String str) {
/*      */     try {
/* 1444 */       return Double.valueOf(str).doubleValue();
/*      */     }
/*      */     catch (NumberFormatException nfe)
/*      */     {
/* 1448 */       if (NimUtility.isBlank(str))
/* 1449 */         throw nfe;
/* 1450 */       if ((str.startsWith("nan")) || (str.startsWith("-nan")))
/* 1451 */         return Double.NaN;
/* 1452 */       if ((str.equals("inf")) || (str.equals("infinity")))
/* 1453 */         return Double.POSITIVE_INFINITY;
/* 1454 */       if ((str.equals("-inf")) || (str.equals("-infinity"))) {
/* 1455 */         return Double.NEGATIVE_INFINITY;
/*      */       }
/* 1457 */       throw nfe;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double getDouble(String key, double def)
/*      */     throws NimException
/*      */   {
/*      */     try
/*      */     {
/* 1476 */       return parseDouble(getString(key, String.valueOf(def)));
/*      */     } catch (Exception e) {}
/* 1478 */     return def;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double getDouble(String key)
/*      */     throws NimException
/*      */   {
/* 1492 */     if (getType(key) != 16) {
/* 1493 */       throw new NimException(37, "Wrong type, the element type is " + getTypeAsName(key));
/*      */     }
/*      */     
/* 1496 */     return parseDouble(getString(key));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getString(String key, String def)
/*      */     throws NimException
/*      */   {
/* 1510 */     Element element = (Element)this.ht.get(key);
/* 1511 */     if (element == null) {
/* 1512 */       return def;
/*      */     }
/* 1514 */     return getString(key);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getStringIfExists(String key)
/*      */     throws NimException
/*      */   {
/* 1527 */     if (checkKey(key) != 7) {
/* 1528 */       return null;
/*      */     }
/*      */     
/* 1531 */     return getString(key);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getString(String key)
/*      */     throws NimException
/*      */   {
/* 1546 */     if ((getType(key) != 1) && (getType(key) != 27) && (getType(key) != 16) && (getType(key) != 7))
/*      */     {
/* 1548 */       throw new NimException(37, "Wrong type, the element type is " + getTypeAsName(key));
/*      */     }
/*      */     
/* 1551 */     return getValueAsString(key);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getValueAsString(String key)
/*      */   {
/* 1562 */     String retval = "";
/* 1563 */     Element element = (Element)this.ht.get(key);
/* 1564 */     if (element != null) {
/* 1565 */       retval = element.value;
/*      */     }
/* 1567 */     return retval;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean containsKey(String key)
/*      */   {
/* 1578 */     return this.ht.containsKey(key);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public byte[] getBytes(String key, byte[] def)
/*      */     throws NimException
/*      */   {
/* 1594 */     Element element = (Element)this.ht.get(key);
/* 1595 */     if (element == null) {
/* 1596 */       return def;
/*      */     }
/* 1598 */     return getBytes(key);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public byte[] getBytes(String key)
/*      */     throws NimException
/*      */   {
/* 1613 */     if ((getType(key) != 22) && (getType(key) != 7)) {
/* 1614 */       throw new NimException(37, "Wrong type, the element type is " + getTypeAsName(key));
/*      */     }
/*      */     
/* 1617 */     byte[] bytes = null;
/* 1618 */     Element element = (Element)this.ht.get(key);
/* 1619 */     if (element != null) {
/* 1620 */       bytes = element.buf;
/*      */     }
/* 1622 */     return bytes;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PDS getPDS(String key, PDS def)
/*      */     throws NimException
/*      */   {
/* 1638 */     Element element = (Element)this.ht.get(key);
/* 1639 */     if (element == null) {
/* 1640 */       return def;
/*      */     }
/* 1642 */     return getPDS(key);
/*      */   }
/*      */   
/*      */   public Object removeKey(String key) {
/* 1646 */     return this.ht.remove(key);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PDS getPDS(String key)
/*      */     throws NimException
/*      */   {
/* 1661 */     if (getType(key) != 21) {
/* 1662 */       throw new NimException(37, "Wrong type, the element type is " + getTypeAsName(key));
/*      */     }
/*      */     
/* 1665 */     PDS pds = null;
/* 1666 */     Element element = (Element)this.ht.get(key);
/* 1667 */     if (element != null) {
/* 1668 */       pds = element.pds;
/*      */     }
/* 1670 */     return pds;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getTableInt(String key, int index)
/*      */     throws NimException
/*      */   {
/* 1686 */     int[] is = getTableInts(key);
/*      */     
/* 1688 */     if (index >= is.length) {
/* 1689 */       throw new NimException(3, "Legal index values are 0 - " + (is.length - 1));
/*      */     }
/*      */     
/* 1692 */     return is[index];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int[] getTableInts(String key)
/*      */     throws NimException
/*      */   {
/* 1706 */     if (getType(key) != 3) {
/* 1707 */       throw new NimException(37, "Wrong type, the element type is " + getTypeAsName(key));
/*      */     }
/*      */     
/* 1710 */     Element element = (Element)this.ht.get(key);
/* 1711 */     int[] is = new int[element.tpds.ht.size()];
/* 1712 */     for (int i = 0; i < element.tpds.ht.size(); i++) {
/* 1713 */       is[i] = element.tpds.getInt("" + i);
/*      */     }
/* 1715 */     return is;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTableString(String key, int index)
/*      */     throws NimException
/*      */   {
/* 1731 */     String[] ss = getTableStrings(key);
/*      */     
/* 1733 */     if (index >= ss.length) {
/* 1734 */       throw new NimException(3, "Legal index values are 0 - " + (ss.length - 1));
/*      */     }
/*      */     
/* 1737 */     return ss[index];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String[] getTableStrings(String key)
/*      */     throws NimException
/*      */   {
/* 1751 */     if (getType(key) != 8) {
/* 1752 */       throw new NimException(37, "Wrong type, the element type is " + getTypeAsName(key));
/*      */     }
/*      */     
/* 1755 */     Element element = (Element)this.ht.get(key);
/* 1756 */     String[] ss = new String[element.tpds.ht.size()];
/* 1757 */     for (int i = 0; i < element.tpds.ht.size(); i++) {
/* 1758 */       ss[i] = element.tpds.getString("" + i);
/*      */     }
/* 1760 */     return ss;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PDS getTablePDS(String key, int index)
/*      */     throws NimException
/*      */   {
/* 1776 */     if (getType(key) != 24) {
/* 1777 */       throw new NimException(37, "Wrong type, the element type is " + getTypeAsName(key));
/*      */     }
/*      */     
/* 1780 */     Element element = (Element)this.ht.get(key);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1785 */     Set<String> keySet = element.tpds.ht.keySet();
/* 1786 */     Iterator<String> iterKeys = keySet.iterator();
/* 1787 */     while (iterKeys.hasNext()) {
/* 1788 */       String strIndex = (String)iterKeys.next();
/* 1789 */       if (Integer.parseInt(strIndex) == index) {
/* 1790 */         return element.tpds.getPDS(strIndex);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1795 */     throw new NimException(3, "Index [" + index + "] doesn't exist in table PDS " + "for key '" + key + "'.");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int checkKey(String key)
/*      */   {
/* 1808 */     Element element = (Element)this.ht.get(key);
/*      */     
/* 1810 */     if (element == null) {
/* 1811 */       return 0;
/*      */     }
/*      */     
/* 1814 */     return element.type;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PDS[] getTablePDSsIfPresent(String key)
/*      */     throws NimException
/*      */   {
/* 1827 */     if (checkKey(key) != 24) {
/* 1828 */       return null;
/*      */     }
/* 1830 */     return getTablePDSs(key);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PDS[] getTablePDSs(String key)
/*      */     throws NimException
/*      */   {
/* 1844 */     if (getType(key) != 24) {
/* 1845 */       throw new NimException(37, "Wrong type, the element type is " + getTypeAsName(key));
/*      */     }
/*      */     
/* 1848 */     Element element = (Element)this.ht.get(key);
/*      */     
/* 1850 */     Set<String> keySet = element.tpds.ht.keySet();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1857 */     ArrayList<String> sortedKeys = new ArrayList(keySet);
/* 1858 */     Collections.sort(sortedKeys, new Comparator()
/*      */     {
/*      */       public int compare(Object pdsKey1, Object pdsKey2) {
/* 1861 */         return Integer.parseInt(pdsKey1.toString()) - Integer.parseInt(pdsKey2.toString());
/*      */       }
/*      */       
/* 1864 */     });
/* 1865 */     PDS[] pdss = new PDS[element.tpds.ht.size()];
/*      */     
/* 1867 */     int pos = 0;
/* 1868 */     for (String string : sortedKeys) {
/* 1869 */       String pdsKey = string;
/* 1870 */       pdss[(pos++)] = element.tpds.getPDS(pdsKey);
/*      */     }
/*      */     
/* 1873 */     return pdss;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Enumeration<String> keys()
/*      */   {
/* 1882 */     Enumeration<String> enumeration = null;
/* 1883 */     if (this.ht != null) {
/* 1884 */       enumeration = Collections.enumeration(this.ht.keySet());
/*      */     }
/* 1886 */     return enumeration;
/*      */   }
/*      */   
/*      */   private void checkBounds(int endIndex, int length) throws RuntimeException {
/* 1890 */     if (endIndex >= length) {
/* 1891 */       throw new RuntimeException("corrupt PDS: can't read index " + endIndex + " from a byte buffer of length " + length);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected static class Element
/*      */     implements Serializable
/*      */   {
/*      */     int type;
/*      */     
/*      */ 
/*      */     String value;
/*      */     
/*      */ 
/*      */     byte[] buf;
/*      */     
/*      */ 
/*      */     PDS pds;
/*      */     
/*      */     PDS tpds;
/*      */     
/*      */ 
/*      */     public int getType()
/*      */     {
/* 1916 */       return this.type;
/*      */     }
/*      */     
/*      */     public PDS getTpds() {
/* 1920 */       return this.tpds;
/*      */     }
/*      */     
/*      */     public PDS getPds() {
/* 1924 */       return this.pds;
/*      */     }
/*      */     
/*      */     public String getValue() {
/* 1928 */       return this.value;
/*      */     }
/*      */     
/*      */     public String toString()
/*      */     {
/* 1933 */       return "Element [buf=" + (this.buf != null ? arrayToString(this.buf, this.buf.length) : null) + ", pds=" + this.pds + ", tpds=" + this.tpds + ", type=" + this.type + ", value=" + this.value + "]";
/*      */     }
/*      */     
/*      */ 
/*      */     private String arrayToString(Object array, int len)
/*      */     {
/* 1939 */       StringBuffer buffer = new StringBuffer();
/* 1940 */       buffer.append("[");
/* 1941 */       for (int i = 0; i < len; i++) {
/* 1942 */         if (i > 0) {
/* 1943 */           buffer.append(", ");
/*      */         }
/* 1945 */         if ((array instanceof byte[])) {
/* 1946 */           buffer.append(((byte[])(byte[])array)[i]);
/*      */         }
/*      */       }
/* 1949 */       buffer.append("]");
/* 1950 */       return buffer.toString();
/*      */     }
/*      */     
/*      */     private boolean eq(Object one, Object two) {
/* 1954 */       return one == null ? false : two == null ? true : one.equals(two);
/*      */     }
/*      */     
/*      */     public boolean equals(Object obj)
/*      */     {
/* 1959 */       if (obj == null) {
/* 1960 */         return false;
/*      */       }
/* 1962 */       if (obj == this) {
/* 1963 */         return true;
/*      */       }
/* 1965 */       if (!(obj instanceof Element)) {
/* 1966 */         return false;
/*      */       }
/*      */       
/* 1969 */       Element element = (Element)obj;
/* 1970 */       return (this.type == element.type) && (eq(this.value, element.value)) && (eq(this.pds, element.pds)) && (eq(this.tpds, element.tpds)) && (Arrays.equals(this.buf, element.buf));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private int hash(Object obj)
/*      */     {
/* 1978 */       return obj == null ? 0 : obj.hashCode();
/*      */     }
/*      */     
/*      */     public int hashCode()
/*      */     {
/* 1983 */       int code = 11;
/* 1984 */       code += 37 * code + this.type;
/* 1985 */       code += 37 * code + hash(this.pds);
/* 1986 */       code += 37 * code + hash(this.tpds);
/* 1987 */       code += 37 * code + hash(this.value);
/* 1988 */       code += Arrays.hashCode(this.buf);
/* 1989 */       return code;
/*      */     }
/*      */   }
/*      */   
/*      */   public String toString()
/*      */   {
/* 1995 */     return "PDS [ht=" + this.ht + "]";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Map<?, PDS> getPDSWithKeyPrefix(String prefix)
/*      */   {
/* 2006 */     HashMap<Object, PDS> result = new HashMap();
/*      */     
/* 2008 */     for (Object name : this.ht.entrySet()) {
/* 2009 */       Map.Entry<?, ?> entry = (Map.Entry)name;
/*      */       
/* 2011 */       if (((String)entry.getKey()).startsWith(prefix)) {
/* 2012 */         Element element = (Element)entry.getValue();
/*      */         
/* 2014 */         if (element.pds != null) {
/* 2015 */           result.put(entry.getKey(), element.pds);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 2020 */     return result;
/*      */   }
/*      */   
/*      */   public boolean equals(Object obj)
/*      */   {
/* 2025 */     if (obj == null) {
/* 2026 */       return false;
/*      */     }
/* 2028 */     if (obj == this) {
/* 2029 */       return true;
/*      */     }
/* 2031 */     if (!(obj instanceof PDS)) {
/* 2032 */       return false;
/*      */     }
/*      */     
/* 2035 */     PDS pds = (PDS)obj;
/* 2036 */     return this.ht == null ? false : pds.ht == null ? true : this.ht.equals(pds.ht);
/*      */   }
/*      */   
/*      */   public int hashCode()
/*      */   {
/* 2041 */     return this.ht == null ? -1 : this.ht.hashCode();
/*      */   }
/*      */ }


/* Location:              C:\Users\sergiomv\Desktop\Env\DOC\CA\MEUS_TESTES\probe-sdk\jboss-probe-1.4.0-jars\original_jars_from_probe_deployment\probes\application\jboss\jar\jboss.jar!\com\nimsoft\nimbus\PDS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */