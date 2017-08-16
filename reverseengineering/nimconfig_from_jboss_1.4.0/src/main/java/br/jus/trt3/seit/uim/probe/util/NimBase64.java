 package br.jus.trt3.seit.uim.probe.util;
 
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.InputStreamReader;
 import java.io.StringWriter;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 public class NimBase64
 {
   private static final String BUFFER = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
   
   public static String encode(byte[] plain)
   {
/*  29 */     StringBuffer encoded = new StringBuffer();
/*  30 */     int ppos = 0;
/*  31 */     while (ppos < plain.length) {
/*  32 */       byte ptext = plain[(ppos++)];
/*  33 */       int bits = (ptext & 0xFC) >> 2;
/*  34 */       encoded.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(bits));
       
/*  36 */       bits = (ptext & 0x3) << 4;
/*  37 */       if (ppos < plain.length) {
/*  38 */         ptext = plain[(ppos++)];
/*  39 */         bits |= (ptext & 0xF0) >> 4;
/*  40 */         encoded.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(bits));
       } else {
/*  42 */         encoded.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(bits));
/*  43 */         encoded.append("==");
/*  44 */         break;
       }
       
/*  47 */       bits = (ptext & 0xF) << 2;
/*  48 */       if (ppos < plain.length) {
/*  49 */         ptext = plain[(ppos++)];
/*  50 */         bits |= (ptext & 0xC0) >> 6;
/*  51 */         encoded.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(bits));
       } else {
/*  53 */         encoded.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(bits));
/*  54 */         encoded.append("=");
/*  55 */         break;
       }
       
/*  58 */       bits = ptext & 0x3F;
/*  59 */       encoded.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(bits));
     }
/*  61 */     return encoded.toString();
   }
   
 
 
 
 
 
 
 
 
 
 
   public static byte[] decode(InputStream input)
     throws NimException, IOException
   {
/*  77 */     StringWriter sw = new StringWriter();
/*  78 */     InputStreamReader in = new InputStreamReader(input);
/*  79 */     char[] buffer = new char[1024];
/*  81 */     int n = 0;
/*  82 */     while (-1 != (n = in.read(buffer))) {
/*  83 */       sw.write(buffer, 0, n);
     }
/*  86 */     return decode(sw.toString());
   }
   
 
 
 
 
 
 
 
   public static byte[] decode(String encodedIn)
     throws NimException
   {
/*  99 */     String encoded = encodedIn.replaceAll("\\s", "");
     
/* 101 */     int l = encoded.length() / 4 * 3;
/* 102 */     if (encoded.endsWith("==")) {
/* 103 */       l -= 2;
/* 104 */     } else if (encoded.endsWith("=")) {
/* 105 */       l--;
     }
/* 107 */     byte[] decoded = new byte[l];
/* 108 */     int ppos = 0;
/* 109 */     int epos = 0;
/* 110 */     while (ppos < encoded.length()) {
/* 111 */       int enc = get(encoded.charAt(ppos++));
/* 112 */       int bits = enc << 2;
/* 113 */       enc = get(encoded.charAt(ppos++));
/* 114 */       bits |= (enc & 0x30) >> 4;
/* 115 */       decoded[(epos++)] = ((byte)bits);
       
/* 117 */       bits = (enc & 0xF) << 4;
/* 118 */       enc = get(encoded.charAt(ppos++));
/* 119 */       if (enc == -1) {
         break;
       }
       
/* 123 */       bits |= (enc & 0x3C) >> 2;
/* 124 */       decoded[(epos++)] = ((byte)bits);
       
/* 126 */       bits = (enc & 0x3) << 6;
/* 127 */       enc = get(encoded.charAt(ppos++));
/* 128 */       if (enc == -1) {
         break;
       }
/* 131 */       bits |= enc & 0x3F;
/* 132 */       decoded[(epos++)] = ((byte)bits);
     }
/* 134 */     return decoded;
   }
   
   private static int get(char c) throws NimException {
/* 138 */     if ((c >= 'A') && (c <= 'Z'))
/* 139 */       return c - 'A';
/* 140 */     if ((c >= 'a') && (c <= 'z'))
/* 141 */       return c + '\032' - 97;
/* 142 */     if ((c >= '0') && (c <= '9'))
/* 143 */       return c + '4' - 48;
/* 144 */     if (c == '+')
/* 145 */       return 62;
/* 146 */     if (c == '/')
/* 147 */       return 63;
/* 148 */     if (c == '=') {
/* 149 */       return -1;
     }
/* 151 */     throw new NimException(110, "Illegal base64 char found '" + c + "'");
   }
 }


/* Location:              C:\Users\sergiomv\Desktop\Env\DOC\CA\MEUS_TESTES\probe-sdk\jboss-probe-1.4.0-jars\original_jars_from_probe_deployment\probes\application\jboss\jar\jboss.jar!\com\nimsoft\nimbus\NimBase64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */