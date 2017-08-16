/*     */ package br.jus.trt3.seit.uim.probe.util;
/*     */ 
/*     */ 
/*     */ public class NimInfo
/*     */ {
/*     */   private String sProbeName;
/*     */   
/*     */   private String sVersion;
/*     */   
/*     */   private String sCompanyName;
/*     */   
/*     */   private long lPid;
/*     */   private final int iStarted;
/*     */   private long lRestarted;
/*  15 */   private static NimInfo niminfo = null;
/*     */   
/*     */   private NimInfo(String sProbeName, String sVersion, String sCompanyName, long lPid)
/*     */   {
/*  19 */     this();
/*  20 */     this.sProbeName = sProbeName;
/*  21 */     this.sVersion = sVersion;
/*  22 */     this.sCompanyName = sCompanyName;
/*  23 */     this.lPid = lPid;
/*     */   }
/*     */   
/*     */   private NimInfo() {
/*  27 */     this.iStarted = ((int)(System.currentTimeMillis() / 1000L));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static synchronized NimInfo getInstance()
/*     */   {
/*  36 */     if (niminfo == null) {
/*  37 */       niminfo = new NimInfo();
/*     */     }
/*  39 */     return niminfo;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void markRestart()
/*     */   {
/*  46 */     this.lRestarted = (System.currentTimeMillis() / 1000L);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setProbeName(String probename)
/*     */   {
/*  56 */     this.sProbeName = probename;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getProbeName()
/*     */   {
/*  65 */     return this.sProbeName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setVersion(String version)
/*     */   {
/*  75 */     this.sVersion = version;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getVersion()
/*     */   {
/*  84 */     return this.sVersion;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setCompanyName(String companyname)
/*     */   {
/*  94 */     this.sCompanyName = companyname;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getCompanyName()
/*     */   {
/* 103 */     return this.sCompanyName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPid(long pid)
/*     */   {
/* 113 */     this.lPid = pid;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getPid()
/*     */   {
/* 122 */     return this.lPid;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getStarted()
/*     */   {
/* 131 */     return this.iStarted;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getRestarted()
/*     */   {
/* 140 */     return this.lRestarted;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Builder getBuilder()
/*     */   {
/* 149 */     return new Builder();
/*     */   }

/*     */   public static class Builder
/*     */   {
/* 158 */     private String probeName = "main";
/* 159 */     private String version = "1.0";
/* 160 */     private String companyName = "Nimsoft";
/* 161 */     private long pid = 0L;

/*     */     public NimInfo build()
/*     */     {
/* 173 */       return new NimInfo(this.probeName, this.version, this.companyName, this.pid);
/*     */     }
 
/*     */     public Builder setProbeName(String probeName)
/*     */     {
/* 182 */       this.probeName = probeName;
/* 183 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Builder setVersion(String version)
/*     */     {
/* 192 */       this.version = version;
/* 193 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Builder setCompanyName(String companyName)
/*     */     {
/* 202 */       this.companyName = companyName;
/* 203 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Builder setPid(long pid)
/*     */     {
/* 212 */       this.pid = pid;
/* 213 */       return this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\sergiomv\Desktop\Env\DOC\CA\MEUS_TESTES\probe-sdk\jboss-probe-1.4.0-jars\original_jars_from_probe_deployment\probes\application\jboss\jar\jboss.jar!\com\nimsoft\nimbus\NimInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */