/*     */ package br.jus.trt3.seit.uim.probe.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
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
/*     */ public class NimLog
/*     */ {
/*     */   public static final int FATAL = 0;
/*     */   public static final int ERROR = 1;
/*     */   public static final int WARN = 2;
/*     */   public static final int INFO = 3;
/*     */   public static final int DEBUG = 4;
/*     */   public static final int DEBUG_LOW = 5;
/*     */   public static final int TRACE = 5;
/*     */   public static final int SDK_HIGH = 6;
/*     */   public static final int SDK_LOW = 7;
/*  32 */   public static final String[] E_LEVELS = { "FATAL", "ERROR", "WARN ", "INFO ", "DEBUG", "DBLOW", "SDKH ", "SDKL " };
/*     */   
/*     */ 
/*  35 */   private static long MAX_LENGTH = 1048576L;
/*  36 */   private static long lMaxLength = MAX_LENGTH;
/*  37 */   private static String sFilename = null;
/*  38 */   private static int iLogLevel = 2;
/*  39 */   private static FileWriter fwLog = null;
/*  40 */   private static PrintStream psLog = null;
/*     */   private static long lLength;
/*  42 */   private static SimpleDateFormat formatter = new SimpleDateFormat("MMM dd HH:mm:ss:SSS");
/*  43 */   private String sClassName = null;
/*  44 */   private static boolean bUseErrorLevel = false;
/*  45 */   private static boolean bUseClassName = false;
/*  46 */   private static boolean bUseThreadName = true;
/*  47 */   private static boolean bUseLog4j = false;
/*  48 */   private static boolean bUseStandard = true;
/*  49 */   private static String sSync = "";
/*  50 */   private static boolean bSecondary = false;
/*     */   
/*     */   protected NimLog() {}
/*     */   
/*     */   private NimLog(String classname, boolean secondary)
/*     */   {
/*  56 */     bSecondary = secondary;
/*     */     
/*     */ 
/*  59 */     synchronized (sSync) {
/*  60 */       if ((psLog == null) && (fwLog == null)) {
/*     */         try {
/*  62 */           if (sFilename == null) {
/*  63 */             useStdout();
/*     */           } else {
/*  65 */             reopen(sFilename, iLogLevel);
/*     */           }
/*     */         } catch (Exception e) {
/*  68 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/*  72 */     this.sClassName = classname;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static NimLog getLogger(String classname)
/*     */   {
/*  83 */     return logFactory.getLogger(classname, false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static NimLog getLogger(Class<?> clazz)
/*     */   {
/*  94 */     return logFactory.getLogger(clazz.getName(), false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static NimLog getSecondaryLogger(String classname)
/*     */   {
/* 105 */     return logFactory.getLogger(classname, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static NimLog getSecondaryLogger(Class<?> clazz)
/*     */   {
/* 116 */     return logFactory.getLogger(clazz.getName(), true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLog4j(boolean log4j)
/*     */   {
/* 126 */     bUseLog4j = log4j;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setStandard(boolean standard)
/*     */   {
/* 136 */     bUseStandard = standard;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void useStdout()
/*     */     throws NimException
/*     */   {
/* 146 */     reopen("stdout", -1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void useThreadName(boolean usethreadname)
/*     */   {
/* 156 */     bUseThreadName = usethreadname;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void useClassName(boolean useclassname)
/*     */   {
/* 166 */     bUseClassName = useclassname;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void useErrorLevel(boolean useerrorlevel)
/*     */   {
/* 176 */     bUseErrorLevel = useerrorlevel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFilename(String filename)
/*     */     throws NimException
/*     */   {
/* 189 */     reopen(filename, -1);
/* 190 */     if ((bSecondary) && (psLog == null)) {
/* 191 */       close();
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
/*     */   public static void setLogLevel(int loglevel)
/*     */   {
/* 204 */     iLogLevel = loglevel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int getLogLevel()
/*     */   {
/* 213 */     return iLogLevel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setLogSize(long logsize)
/*     */   {
/* 220 */     MAX_LENGTH = logsize;
/* 221 */     lMaxLength = logsize;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void reopen(String filename, int loglevel)
/*     */     throws NimException
/*     */   {
/* 231 */     synchronized (sSync) {
/*     */       try {
/* 233 */         if ((filename != null) && ((fwLog == null) || (sFilename == null) || (!filename.equals(sFilename))))
/*     */         {
/* 235 */           close();
/* 236 */           sFilename = filename;
/* 237 */           if (sFilename.equals("stdout")) {
/* 238 */             psLog = System.out;
/*     */           } else {
/* 240 */             psLog = null;
/* 241 */             lLength = new File(sFilename).length();
/* 242 */             fwLog = new FileWriter(sFilename, true);
/*     */           }
/*     */         }
/*     */         
/* 246 */         if (loglevel != -1) {
/* 247 */           if (loglevel != iLogLevel) {
/* 248 */             log(3, "Log level is changed to " + loglevel);
/*     */           }
/* 250 */           iLogLevel = loglevel;
/* 251 */           if (iLogLevel >= 10) {
/* 252 */             lMaxLength = -1L;
/*     */           } else {
/* 254 */             lMaxLength = MAX_LENGTH;
/*     */           }
/*     */         }
/*     */       } catch (Exception e) {
/* 258 */         throw new NimException(100, "Unable to open log file: " + sFilename, e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void close()
/*     */   {
/* 267 */     psLog = null;
/* 268 */     if (fwLog != null) {
/*     */       try {
/* 270 */         fwLog.flush();
/* 271 */         fwLog.close();
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/* 275 */       fwLog = null;
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
/*     */   public void logStackTrace(int level, Throwable t)
/*     */   {
/* 293 */     if (level > iLogLevel)
/*     */       return;
/*     */     String msg;
/* 297 */     if (t == null) {
/* 298 */       msg = "logStackTrace: null Throwable (no stack trace)";
/*     */     } else {
/* 300 */       StringWriter sw = new StringWriter();
/* 301 */       PrintWriter pw = new PrintWriter(sw);
/* 302 */       t.printStackTrace(pw);
/* 303 */       pw.close();
/* 304 */       msg = sw.toString();
/*     */     }
/* 306 */     basicLog(level, msg);
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
/*     */   public void log(int loglevel, String text)
/*     */   {
/* 324 */     if (loglevel > iLogLevel) {
/* 325 */       return;
/*     */     }
/* 327 */     basicLog(loglevel, text);
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
/*     */   public void log(int loglevel, PDS pds)
/*     */   {
/* 343 */     if (loglevel > iLogLevel) {
/* 344 */       return;
/*     */     }
/* 346 */     log(loglevel, "" + NimUtility.displayPDS(pds));
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
/*     */   public void log(int loglevel, PDS pds, boolean multiline)
/*     */   {
/* 365 */     if (loglevel > iLogLevel) {
/* 366 */       return;
/*     */     }
/* 368 */     log(loglevel, "" + NimUtility.displayPDS(pds, multiline));
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
/*     */   public void log(int loglevel, String text, PDS pds)
/*     */   {
/* 387 */     if (loglevel > iLogLevel) {
/* 388 */       return;
/*     */     }
/* 390 */     log(loglevel, NimUtility.displayPDS(text, pds));
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
/*     */ 
/*     */   public void log(int loglevel, String text, PDS pds, boolean multiline)
/*     */   {
/* 411 */     if (loglevel > iLogLevel) {
/* 412 */       return;
/*     */     }
/* 414 */     log(loglevel, NimUtility.displayPDS(text, pds, multiline));
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
/*     */   protected void log(int level, String text, byte[] bytes)
/*     */   {
/* 429 */     if (level > iLogLevel) {
/* 430 */       return;
/*     */     }
/* 432 */     int l = 0;
/* 433 */     for (int i = bytes.length - 1; i > 0; i--) {
/* 434 */       if (bytes[i] != 0) {
/* 435 */         l = i;
/* 436 */         break;
/*     */       }
/*     */     }
/* 439 */     String s = new String(bytes, 0, l + 1);
/* 440 */     log(level, text + s);
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
/*     */   protected void logRaw(int level, String text, byte[] bytes)
/*     */   {
/* 455 */     if (level > iLogLevel) {
/* 456 */       return;
/*     */     }
/* 458 */     int l = 0;
/* 459 */     for (int i = bytes.length - 1; i > 0; i--) {
/* 460 */       if (bytes[i] != 0) {
/* 461 */         l = i;
/* 462 */         break;
/*     */       }
/*     */     }
/* 465 */     log(level, text + "\n" + NimUtility.displayHex(bytes, l + 1, 16));
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
/*     */   public void logAlways(int loglevel, String text)
/*     */   {
/* 482 */     basicLog(loglevel, text);
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
/*     */   protected synchronized void basicLog(int loglevel, String text)
/*     */   {
/* 496 */     String name = this.sClassName;
/* 497 */     if ((!bUseClassName) && (NimInfo.getInstance().getProbeName() != null)) {
/* 498 */       name = NimInfo.getInstance().getProbeName();
/*     */     }
/*     */     try {
/* 501 */       String logmsglog4j = text;
/* 502 */       String logmsgstandard = formatter.format(now());
/* 503 */       if ((bUseErrorLevel) && 
/* 504 */         (loglevel <= 7)) {
/* 505 */         logmsgstandard = logmsgstandard + " " + E_LEVELS[loglevel];
/*     */       }
/*     */       
/* 508 */       logmsgstandard = logmsgstandard + " [";
/* 509 */       if (bUseThreadName) {
/* 510 */         logmsgstandard = logmsgstandard + Thread.currentThread().getName();
/*     */       }
/* 512 */       logmsgstandard = logmsgstandard + (bUseThreadName ? ", " : "") + name;
/* 513 */       logmsgstandard = logmsgstandard + "]";
/* 514 */       logmsgstandard = logmsgstandard + " " + text + "\n";
/* 515 */       if (bUseLog4j) {
/* 516 */         NimLog4jHelper log4jhelper = new NimLog4jHelper();
/* 517 */         log4jhelper.log(loglevel, this.sClassName, logmsglog4j);
/*     */       }
/* 519 */       if (bUseStandard) {
/* 520 */         if (psLog != null) {
/* 521 */           psLog.print(logmsgstandard);
/*     */         } else {
/* 523 */           synchronized (sSync) {
/* 524 */             lLength += logmsgstandard.length();
/*     */             
/* 526 */             if ((lMaxLength != -1L) && (lLength >= lMaxLength) && (!bSecondary)) {
/* 527 */               close();
/* 528 */               renameFile(sFilename);
/* 529 */               reopen(sFilename, iLogLevel);
/* 530 */               lLength = new File(sFilename).length();
/* 531 */               lLength += logmsgstandard.length();
/*     */             }
/* 533 */             if (bSecondary) {
/* 534 */               reopen(sFilename, iLogLevel);
/*     */             }
/* 536 */             if (fwLog != null) {
/* 537 */               fwLog.write(logmsgstandard);
/* 538 */               fwLog.flush();
/*     */             }
/* 540 */             if (bSecondary) {
/* 541 */               close();
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 547 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void renameFile(String filename)
/*     */     throws Exception
/*     */   {
/* 559 */     boolean deleted = true;
/* 560 */     boolean renamed = false;
/* 561 */     File _f = new File("_" + filename);
/* 562 */     if ((_f.exists()) && (_f.isFile())) {
/* 563 */       deleted = _f.delete();
/*     */     }
/* 565 */     if (deleted) {
/* 566 */       File f = new File(filename);
/* 567 */       renamed = f.renameTo(_f);
/*     */     }
/*     */     
/* 570 */     if ((!deleted) || (!renamed)) {
/* 571 */       File f = new File(filename);
/* 572 */       if ((f.exists()) && (f.isFile())) {
/* 573 */         f.delete();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static Date now()
/*     */     throws Exception
/*     */   {
/* 587 */     return new Date(System.currentTimeMillis());
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 592 */     return "log: level = " + iLogLevel;
/*     */   }
/*     */   
/*     */   public void fatal(String text) {
/* 596 */     log(0, text);
/*     */   }
/*     */   
/*     */   public void error(String text) {
/* 600 */     log(1, text);
/*     */   }
/*     */   
/*     */   public void warn(String text) {
/* 604 */     log(2, text);
/*     */   }
/*     */   
/*     */   public void info(String text) {
/* 608 */     log(3, text);
/*     */   }
/*     */   
/*     */   public void debug(String text) {
/* 612 */     log(4, text);
/*     */   }
/*     */   
/*     */   public void trace(String text) {
/* 616 */     log(5, text);
/*     */   }
/*     */   
/*     */   public void sdkH(String text) {
/* 620 */     log(6, text);
/*     */   }
/*     */   
/*     */   public void sdkL(String text) {
/* 624 */     log(7, text);
/*     */   }
/*     */   
/*     */   public void always(String msg) {
/* 628 */     logAlways(3, msg);
/*     */   }
/*     */   
/*     */   public boolean isTraceEnabled() {
/* 632 */     return getLogLevel() >= 5;
/*     */   }
/*     */   
/*     */   public boolean isDebugEnabled() {
/* 636 */     return getLogLevel() >= 4;
/*     */   }
/*     */   
/*     */   public boolean isInfoEnabled() {
/* 640 */     return getLogLevel() >= 3;
/*     */   }
/*     */   
/*     */   public boolean isWarnEnabled() {
/* 644 */     return getLogLevel() >= 2;
/*     */   }
/*     */   
/*     */   public boolean isErrorEnabled() {
/* 648 */     return getLogLevel() >= 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static class DefaultNimLogFactory
/*     */     implements NimLog.INimLogFactory
/*     */   {
/*     */     public DefaultNimLogFactory()
/*     */     {
/* 662 */       String nlf = System.getProperty("com.nimsoft.nimbus.logfactory", null);
/* 663 */       if (null != nlf) {
/*     */         try {
/* 665 */           this.override = ((NimLog.INimLogFactory)Class.forName(nlf).newInstance());
/*     */         } catch (Exception ex) {
/* 667 */           System.out.println("logfactory class error: " + nlf);
/* 668 */           ex.printStackTrace(System.err);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     public NimLog getLogger(String classname, boolean secondary)
/*     */     {
/* 675 */       return (null == this.override)?new NimLog(classname, secondary):this.override.getLogger(classname, secondary);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 680 */     private NimLog.INimLogFactory override = null;
/*     */   }
/*     */   
/* 683 */   private static INimLogFactory logFactory = new DefaultNimLogFactory();
/*     */   
/*     */   public static abstract interface INimLogFactory
/*     */   {
/*     */     public abstract NimLog getLogger(String paramString, boolean paramBoolean);
/*     */   }
/*     */ }


/* Location:              C:\Users\sergiomv\Desktop\Env\DOC\CA\MEUS_TESTES\probe-sdk\jboss-probe-1.4.0-jars\original_jars_from_probe_deployment\probes\application\jboss\jar\jboss.jar!\com\nimsoft\nimbus\NimLog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */