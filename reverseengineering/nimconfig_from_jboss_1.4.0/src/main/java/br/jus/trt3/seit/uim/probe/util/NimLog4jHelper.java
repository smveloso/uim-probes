/*    */ package br.jus.trt3.seit.uim.probe.util;
/*    */ 
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class NimLog4jHelper
/*    */ {
/*    */   protected void log(int loglevel, String classname, String formattedmessage)
/*    */     throws Exception
/*    */   {
/* 10 */     switch (loglevel) {
/*    */     case 0: 
/* 12 */       Logger.getLogger(classname).fatal(formattedmessage);
/* 13 */       break;
/*    */     case 1: 
/* 15 */       Logger.getLogger(classname).error(formattedmessage);
/* 16 */       break;
/*    */     case 2: 
/* 18 */       Logger.getLogger(classname).warn(formattedmessage);
/* 19 */       break;
/*    */     case 3: 
/* 21 */       Logger.getLogger(classname).info(formattedmessage);
/* 22 */       break;
/*    */     case 4: 
/* 24 */       Logger.getLogger(classname).debug(formattedmessage);
/* 25 */       break;
/*    */     default: 
/* 27 */       Logger.getLogger(classname).trace(formattedmessage);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\sergiomv\Desktop\Env\DOC\CA\MEUS_TESTES\probe-sdk\jboss-probe-1.4.0-jars\original_jars_from_probe_deployment\probes\application\jboss\jar\jboss.jar!\com\nimsoft\nimbus\NimLog4jHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */