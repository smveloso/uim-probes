package br.jus.trt3.seit.uim.probe.util;

import java.util.Hashtable;

public class NimException
  extends Exception
{
  public static final int OK = 0;
  public static final int E_ERROR = 1;
  public static final int E_COMM = 2;
  public static final int E_INVAL = 3;
  public static final int E_NOENT = 4;
  public static final int E_ISENT = 5;
  public static final int E_ACCESS = 6;
  public static final int E_AGAIN = 7;
  public static final int E_NOMEM = 8;
  public static final int E_NOSPC = 9;
  public static final int E_EPIPE = 10;
  public static final int E_NOCMD = 11;
  public static final int E_LOGIN = 12;
  public static final int E_SIDEXP = 13;
  public static final int E_ILLMAC = 14;
  public static final int E_ILLSID = 15;
  public static final int E_SIDSESS = 16;
  public static final int E_EXPIRED = 17;
  public static final int E_NOLIC = 18;
  public static final int E_INVLIC = 19;
  public static final int E_ILLLIC = 20;
  public static final int E_INVOP = 21;
  public static final int E_ILL_TYPE = 37;
  public static final int E_NOMATCH = 38;
  public static final int E_VAR = 92;
  public static final int E_CONFIG = 90;
  public static final int E_SESSION = 80;
  public static final int E_LOG = 100;
  public static final int E_INIT = 105;
  public static final int E_SECURITY = 110;
  public static final int E_SECURITY_REMOVED = 111;
  public static final int E_CALLBACK = 120;
  public static final int E_QOS = 130;
/*  79 */   private static Hashtable htErrmsg = new Hashtable();
  int code;
  
/*  82 */   static { htErrmsg.put(Integer.valueOf(1), "error");
/*  83 */     htErrmsg.put(Integer.valueOf(2), "communication error");
/*  84 */     htErrmsg.put(Integer.valueOf(3), "invalid argument");
/*  85 */     htErrmsg.put(Integer.valueOf(4), "not found");
/*  86 */     htErrmsg.put(Integer.valueOf(5), "already defined");
/*  87 */     htErrmsg.put(Integer.valueOf(6), "permission denied");
/*  88 */     htErrmsg.put(Integer.valueOf(7), "temporarily out of resources");
/*  89 */     htErrmsg.put(Integer.valueOf(8), "out of resources");
/*  90 */     htErrmsg.put(Integer.valueOf(9), "no space left");
/*  91 */     htErrmsg.put(Integer.valueOf(10), "broken connection");
/*  92 */     htErrmsg.put(Integer.valueOf(11), "command not found");
/*  93 */     htErrmsg.put(Integer.valueOf(12), "login failed");
/*  94 */     htErrmsg.put(Integer.valueOf(13), "SID has expired");
/*  95 */     htErrmsg.put(Integer.valueOf(14), "illegal MAC");
/*  96 */     htErrmsg.put(Integer.valueOf(15), "illegal SID");
/*  97 */     htErrmsg.put(Integer.valueOf(16), "invalid session identity");
/*  98 */     htErrmsg.put(Integer.valueOf(17), "has expired");
/*  99 */     htErrmsg.put(Integer.valueOf(18), "no valid license");
/* 100 */     htErrmsg.put(Integer.valueOf(19), "invalid license");
/* 101 */     htErrmsg.put(Integer.valueOf(20), "illegal license");
/* 102 */     htErrmsg.put(Integer.valueOf(90), "Configuration error");
/* 103 */     htErrmsg.put(Integer.valueOf(80), "Session error");
/* 104 */     htErrmsg.put(Integer.valueOf(100), "Log error");
/* 105 */     htErrmsg.put(Integer.valueOf(110), "Security error");
/* 106 */     htErrmsg.put(Integer.valueOf(120), "Callback error");
  }
  
  public NimException(int code)
  {
/* 125 */     this.code = code;
  }

  public NimException(int code, String text)
  {
/* 135 */     super(text);
/* 136 */     this.code = code;
  }

  public NimException(int code, Throwable th)
  {
/* 146 */     super(th);
/* 147 */     this.code = code;
  }
  
  public NimException(int code, String text, Throwable th)
  {
/* 159 */     super(text, th);
/* 160 */     this.code = code;
  }

  public int getCode()
  {
/* 175 */     return this.code;
  }
  
  public String getMsgForCode()
  {
/* 184 */     return getMsgForCode(this.code);
  }

  public static String getMsgForCode(int code)
  {
/* 194 */     return (String)htErrmsg.get(new Integer(code));
  }
  
  public Throwable getThrowable()
  {
/* 203 */     return getCause();
  }
  
  public String toString()
  {
/* 208 */     String errmsg = getMsgForCode();
/* 209 */     if (errmsg == null) {
/* 210 */       errmsg = "";
    }
/* 212 */     if (getCause() != null) {
/* 213 */       return "(" + this.code + ") " + errmsg + ", " + getMessage() + ": " + getCause().getMessage();
    }
/* 215 */     return "(" + this.code + ") " + errmsg + ", " + getMessage();
  }
  
  public NimException() {}
  
  public void init() {}
}


/* Location:              C:\Users\sergiomv\Desktop\Env\DOC\CA\MEUS_TESTES\probe-sdk\jboss-probe-1.4.0-jars\original_jars_from_probe_deployment\probes\application\jboss\jar\jboss.jar!\com\nimsoft\nimbus\NimException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */