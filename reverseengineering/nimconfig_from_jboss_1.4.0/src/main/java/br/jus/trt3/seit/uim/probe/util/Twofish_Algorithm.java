package br.jus.trt3.seit.uim.probe.util;

import java.io.PrintWriter;
import java.security.InvalidKeyException;

public final class Twofish_Algorithm
{
  static final String NAME = "Twofish_Algorithm";
  static final boolean IN = true;
  static final boolean OUT = false;
  static final boolean DEBUG = false;
  static final int debuglevel = 0;
  static final PrintWriter err = null;
  static final boolean TRACE = Twofish_Properties.isTraceable("Twofish_Algorithm");
  static final int BLOCK_SIZE = 16;
  private static final int ROUNDS = 16;
  private static final int MAX_ROUNDS = 16;
  private static final int INPUT_WHITEN = 0;
  private static final int OUTPUT_WHITEN = 4;
  private static final int ROUND_SUBKEYS = 8;
  private static final int TOTAL_SUBKEYS = 40;
  private static final int SK_STEP = 33686018;
  private static final int SK_BUMP = 16843009;
  private static final int SK_ROTL = 9;
  private static final int[][] P = { { -87, 103, -77, -24, 4, -3, -93, 118, -102, -110, 65408, 120, -28, -35, -47, 56, 13, -58, 53, -104, 24, -9, -20, 108, 67, 117, 55, 38, -6, 19, -108, 72, -14, -48, -117, 48, -124, 84, -33, 35, 25, 91, 61, 89, -13, -82, -94, -126, 99, 1, -125, 46, -39, 81, -101, 124, -90, -21, -91, -66, 22, 12, -29, 97, -64, -116, 58, -11, 115, 44, 37, 11, -69, 78, -119, 107, 83, 106, -76, -15, -31, -26, -67, 69, -30, -12, -74, 102, -52, -107, 3, 86, -44, 28, 30, -41, -5, -61, -114, -75, -23, -49, -65, -70, -22, 119, 57, -81, 51, -55, 98, 113, -127, 121, 9, -83, 36, -51, -7, -40, -27, -59, -71, 77, 68, 8, -122, -25, -95, 29, -86, -19, 6, 112, -78, -46, 65, 123, -96, 17, 49, -62, 39, -112, 32, -10, 96, -1, -106, 92, -79, -85, -98, -100, 82, 27, 95, -109, 10, -17, -111, -123, 73, -18, 45, 79, -113, 59, 71, -121, 109, 70, -42, 62, 105, 100, 42, -50, -53, 47, -4, -105, 5, 122, -84, Byte.MAX_VALUE, -43, 26, 75, 14, -89, 90, 40, 20, 63, 41, -120, 60, 76, 2, -72, -38, -80, 23, 85, 31, -118, 125, 87, -57, -115, 116, -73, -60, -97, 114, 126, 21, 34, 18, 88, 7, -103, 52, 110, 80, -34, 104, 101, -68, -37, -8, -56, -88, 43, 64, -36, -2, 50, -92, -54, 16, 33, -16, -45, 93, 15, 0, 111, -99, 54, 66, 74, 94, -63, -32 }, { 117, -13, -58, -12, -37, 123, -5, -56, 74, -45, -26, 107, 69, 125, -24, 75, -42, 50, -40, -3, 55, 113, -15, -31, 48, 15, -8, 27, -121, -6, 6, 63, 94, -70, -82, 91, -118, 0, -68, -99, 109, -63, -79, 14, 65408, 93, -46, -43, -96, -124, 7, 20, -75, -112, 44, -93, -78, 115, 76, 84, -110, 116, 54, 81, 56, -80, -67, 90, -4, 96, 98, -106, 108, 66, -9, 16, 124, 40, 39, -116, 19, -107, -100, -57, 36, 70, 59, 112, -54, -29, -123, -53, 17, -48, -109, -72, -90, -125, 32, -1, -97, 119, -61, -52, 3, 111, 8, -65, 64, -25, 43, -30, 121, 12, -86, -126, 65, 58, -22, -71, -28, -102, -92, -105, 126, -38, 122, 23, 102, -108, -95, 29, 61, -16, -34, -77, 11, 114, -89, 28, -17, -47, 83, 62, -113, 51, 38, 95, -20, 118, 42, 73, -127, -120, -18, 33, -60, 26, -21, -39, -59, 57, -103, -51, -83, 49, -117, 1, 24, 35, -35, 31, 78, 45, -7, 72, 79, -14, 101, -114, 120, 92, 88, 25, -115, -27, -104, 87, 103, Byte.MAX_VALUE, 5, 100, -81, 99, -74, -2, -11, -73, 60, -91, -50, -23, 104, 68, -32, 77, 67, 105, 41, 46, -84, 21, 89, -88, 10, -98, 110, 71, -33, 52, 53, 106, -49, -36, 34, -55, -64, -101, -119, -44, -19, -85, 18, -94, 13, 82, -69, 2, 47, -87, -41, 97, 30, -76, 80, 4, -10, -62, 22, 37, -122, 86, 85, 9, -66, -111 } };
  private static final int P_00 = 1;
  private static final int P_01 = 0;
  private static final int P_02 = 0;
  private static final int P_03 = 1;
  private static final int P_04 = 1;
  private static final int P_10 = 0;
  private static final int P_11 = 0;
  private static final int P_12 = 1;
  private static final int P_13 = 1;
  private static final int P_14 = 0;
  private static final int P_20 = 1;
  private static final int P_21 = 1;
  private static final int P_22 = 0;
  private static final int P_23 = 0;
  private static final int P_24 = 0;
  private static final int P_30 = 0;
  private static final int P_31 = 1;
  private static final int P_32 = 1;
  private static final int P_33 = 0;
  private static final int P_34 = 1;
  private static final int GF256_FDBK = 361;
  private static final int GF256_FDBK_2 = 180;
  private static final int GF256_FDBK_4 = 90;
  private static final int[][] MDS = new int[4][10240];
  private static final int RS_GF_FDBK = 333;
  private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
  
  static void debug(String paramString)
  {
    System.out.println(">>> Twofish_Algorithm: " + paramString);
  }
  
  static void trace(boolean paramBoolean, String paramString)
  {
    if (TRACE) {
      System.out.println((paramBoolean?"==> ":"<== ") + "Twofish_Algorithm" + "." + paramString);
    }
  }
  
  static void trace(String paramString)
  {
    if (TRACE) {
      System.out.println("<=> Twofish_Algorithm." + paramString);
    }
  }
  
  private static final int LFSR1(int paramInt)
  {
    return paramInt >> 1 ^ ((paramInt & 0x1) != 0 ? 180 : 0);
  }
  
  private static final int LFSR2(int paramInt)
  {
    return paramInt >> 2 ^ ((paramInt & 0x2) != 0 ? 180 : 0) ^ ((paramInt & 0x1) != 0 ? 90 : 0);
  }
  
  private static final int Mx_1(int paramInt)
  {
    return paramInt;
  }
  
  private static final int Mx_X(int paramInt)
  {
    return paramInt ^ paramInt >> 2 ^ ((paramInt & 0x2) != 0 ? 180 : 0) ^ ((paramInt & 0x1) != 0 ? 90 : 0);
  }
  
  private static final int Mx_Y(int paramInt)
  {
    return paramInt ^ paramInt >> 1 ^ ((paramInt & 0x1) != 0 ? 180 : 0) ^ paramInt >> 2 ^ ((paramInt & 0x2) != 0 ? 180 : 0) ^ ((paramInt & 0x1) != 0 ? 90 : 0);
  }
  
  private static final int b0(int paramInt)
  {
    return paramInt & 0xFF;
  }
  
  private static final int b1(int paramInt)
  {
    return paramInt >>> 8 & 0xFF;
  }
  
  private static final int b2(int paramInt)
  {
    return paramInt >>> 16 & 0xFF;
  }
  
  private static final int b3(int paramInt)
  {
    return paramInt >>> 24 & 0xFF;
  }
  
  public static synchronized Object makeKey(byte[] paramArrayOfByte)
    throws InvalidKeyException
  {
    if (paramArrayOfByte == null) {
      throw new InvalidKeyException("Empty key");
    }
    int i = paramArrayOfByte.length;
    if ((i != 8) && (i != 16) && (i != 24) && (i != 32)) {
      throw new InvalidKeyException("Incorrect key length");
    }
    int j = i / 8;
    int[] arrayOfInt1 = new int[4];
    int[] arrayOfInt2 = new int[4];
    int[] arrayOfInt3 = new int[4];
    int n = 0;
    int k = 0;
    for (int m = j - 1; (k < 4) && (n < i); m--)
    {
      arrayOfInt1[k] = (paramArrayOfByte[(n++)] & 0xFF | (paramArrayOfByte[(n++)] & 0xFF) << 8 | (paramArrayOfByte[(n++)] & 0xFF) << 16 | (paramArrayOfByte[(n++)] & 0xFF) << 24);
      arrayOfInt2[k] = (paramArrayOfByte[(n++)] & 0xFF | (paramArrayOfByte[(n++)] & 0xFF) << 8 | (paramArrayOfByte[(n++)] & 0xFF) << 16 | (paramArrayOfByte[(n++)] & 0xFF) << 24);
      arrayOfInt3[m] = RS_MDS_Encode(arrayOfInt1[k], arrayOfInt2[k]);
      k++;
    }
    int[] arrayOfInt4 = new int[40];
    int i1;
    k = i1 = 0;
    while (k < 20)
    {
      int i2 = F32(j, i1, arrayOfInt1);
      int i3 = F32(j, i1 + 16843009, arrayOfInt2);
      i3 = i3 << 8 | i3 >>> 24;
      i2 += i3;
      arrayOfInt4[(2 * k)] = i2;
      i2 += i3;
      arrayOfInt4[(2 * k + 1)] = (i2 << 9 | i2 >>> 23);
      k++;
      i1 += 33686018;
    }
    int i4 = arrayOfInt3[0];
    int i5 = arrayOfInt3[1];
    int i6 = arrayOfInt3[2];
    int i7 = arrayOfInt3[3];
    int[] arrayOfInt5 = new int[10240];
    for (k = 0; k < 256; k++)
    {
      int i11;
      int i10;
      int i9;
      int i8 = i9 = i10 = i11 = k;
      switch (j & 0x3)
      {
      case 1: 
        arrayOfInt5[(2 * k)] = MDS[0][(P[0][i8] & 0xFF ^ i4 & 0xFF)];
        arrayOfInt5[(2 * k + 1)] = MDS[1][(P[0][i9] & 0xFF ^ i4 >>> 8 & 0xFF)];
        arrayOfInt5[(512 + 2 * k)] = MDS[2][(P[1][i10] & 0xFF ^ i4 >>> 16 & 0xFF)];
        arrayOfInt5[(512 + 2 * k + 1)] = MDS[3][(P[1][i11] & 0xFF ^ i4 >>> 24 & 0xFF)];
        break;
      case 0: 
        i8 = P[1][i8] & 0xFF ^ i7 & 0xFF;
        i9 = P[0][i9] & 0xFF ^ i7 >>> 8 & 0xFF;
        i10 = P[0][i10] & 0xFF ^ i7 >>> 16 & 0xFF;
        i11 = P[1][i11] & 0xFF ^ i7 >>> 24 & 0xFF;
      case 3: 
        i8 = P[1][i8] & 0xFF ^ i6 & 0xFF;
        i9 = P[1][i9] & 0xFF ^ i6 >>> 8 & 0xFF;
        i10 = P[0][i10] & 0xFF ^ i6 >>> 16 & 0xFF;
        i11 = P[0][i11] & 0xFF ^ i6 >>> 24 & 0xFF;
      case 2: 
        arrayOfInt5[(2 * k)] = MDS[0][(P[0][(P[0][i8] & 0xFF ^ i5 & 0xFF)] & 0xFF ^ i4 & 0xFF)];
        arrayOfInt5[(2 * k + 1)] = MDS[1][(P[0][(P[1][i9] & 0xFF ^ i5 >>> 8 & 0xFF)] & 0xFF ^ i4 >>> 8 & 0xFF)];
        arrayOfInt5[(512 + 2 * k)] = MDS[2][(P[1][(P[0][i10] & 0xFF ^ i5 >>> 16 & 0xFF)] & 0xFF ^ i4 >>> 16 & 0xFF)];
        arrayOfInt5[(512 + 2 * k + 1)] = MDS[3][(P[1][(P[1][i11] & 0xFF ^ i5 >>> 24 & 0xFF)] & 0xFF ^ i4 >>> 24 & 0xFF)];
      }
    }
    Object[] arrayOfObject = { arrayOfInt5, arrayOfInt4 };
    return arrayOfObject;
  }
  
  public static byte[] blockEncrypt(byte[] paramArrayOfByte, int paramInt, Object paramObject)
  {
    Object[] arrayOfObject = (Object[])paramObject;
    int[] arrayOfInt1 = (int[])arrayOfObject[0];
    int[] arrayOfInt2 = (int[])arrayOfObject[1];
    int i = paramArrayOfByte[(paramInt++)] & 0xFF | (paramArrayOfByte[(paramInt++)] & 0xFF) << 8 | (paramArrayOfByte[(paramInt++)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt++)] & 0xFF) << 24;
    int j = paramArrayOfByte[(paramInt++)] & 0xFF | (paramArrayOfByte[(paramInt++)] & 0xFF) << 8 | (paramArrayOfByte[(paramInt++)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt++)] & 0xFF) << 24;
    int k = paramArrayOfByte[(paramInt++)] & 0xFF | (paramArrayOfByte[(paramInt++)] & 0xFF) << 8 | (paramArrayOfByte[(paramInt++)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt++)] & 0xFF) << 24;
    int m = paramArrayOfByte[(paramInt++)] & 0xFF | (paramArrayOfByte[(paramInt++)] & 0xFF) << 8 | (paramArrayOfByte[(paramInt++)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt++)] & 0xFF) << 24;
    i ^= arrayOfInt2[0];
    j ^= arrayOfInt2[1];
    k ^= arrayOfInt2[2];
    m ^= arrayOfInt2[3];
    int i2 = 8;
    for (int i3 = 0; i3 < 16; i3 += 2)
    {
      int n = Fe32(arrayOfInt1, i, 0);
      int i1 = Fe32(arrayOfInt1, j, 3);
      k ^= n + i1 + arrayOfInt2[(i2++)];
      k = k >>> 1 | k << 31;
      m = m << 1 | m >>> 31;
      m ^= n + 2 * i1 + arrayOfInt2[(i2++)];
      n = Fe32(arrayOfInt1, k, 0);
      i1 = Fe32(arrayOfInt1, m, 3);
      i ^= n + i1 + arrayOfInt2[(i2++)];
      i = i >>> 1 | i << 31;
      j = j << 1 | j >>> 31;
      j ^= n + 2 * i1 + arrayOfInt2[(i2++)];
    }
    k ^= arrayOfInt2[4];
    m ^= arrayOfInt2[5];
    i ^= arrayOfInt2[6];
    j ^= arrayOfInt2[7];
    byte[] arrayOfByte = { (byte)k, (byte)(k >>> 8), (byte)(k >>> 16), (byte)(k >>> 24), (byte)m, (byte)(m >>> 8), (byte)(m >>> 16), (byte)(m >>> 24), (byte)i, (byte)(i >>> 8), (byte)(i >>> 16), (byte)(i >>> 24), (byte)j, (byte)(j >>> 8), (byte)(j >>> 16), (byte)(j >>> 24) };
    return arrayOfByte;
  }
  
  public static byte[] blockDecrypt(byte[] paramArrayOfByte, int paramInt, Object paramObject)
  {
    Object[] arrayOfObject = (Object[])paramObject;
    int[] arrayOfInt1 = (int[])arrayOfObject[0];
    int[] arrayOfInt2 = (int[])arrayOfObject[1];
    int i = paramArrayOfByte[(paramInt++)] & 0xFF | (paramArrayOfByte[(paramInt++)] & 0xFF) << 8 | (paramArrayOfByte[(paramInt++)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt++)] & 0xFF) << 24;
    int j = paramArrayOfByte[(paramInt++)] & 0xFF | (paramArrayOfByte[(paramInt++)] & 0xFF) << 8 | (paramArrayOfByte[(paramInt++)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt++)] & 0xFF) << 24;
    int k = paramArrayOfByte[(paramInt++)] & 0xFF | (paramArrayOfByte[(paramInt++)] & 0xFF) << 8 | (paramArrayOfByte[(paramInt++)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt++)] & 0xFF) << 24;
    int m = paramArrayOfByte[(paramInt++)] & 0xFF | (paramArrayOfByte[(paramInt++)] & 0xFF) << 8 | (paramArrayOfByte[(paramInt++)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt++)] & 0xFF) << 24;
    i ^= arrayOfInt2[4];
    j ^= arrayOfInt2[5];
    k ^= arrayOfInt2[6];
    m ^= arrayOfInt2[7];
    int n = 39;
    for (int i3 = 0; i3 < 16; i3 += 2)
    {
      int i1 = Fe32(arrayOfInt1, i, 0);
      int i2 = Fe32(arrayOfInt1, j, 3);
      m ^= i1 + 2 * i2 + arrayOfInt2[(n--)];
      m = m >>> 1 | m << 31;
      k = k << 1 | k >>> 31;
      k ^= i1 + i2 + arrayOfInt2[(n--)];
      i1 = Fe32(arrayOfInt1, k, 0);
      i2 = Fe32(arrayOfInt1, m, 3);
      j ^= i1 + 2 * i2 + arrayOfInt2[(n--)];
      j = j >>> 1 | j << 31;
      i = i << 1 | i >>> 31;
      i ^= i1 + i2 + arrayOfInt2[(n--)];
    }
    k ^= arrayOfInt2[0];
    m ^= arrayOfInt2[1];
    i ^= arrayOfInt2[2];
    j ^= arrayOfInt2[3];
    byte[] arrayOfByte = { (byte)k, (byte)(k >>> 8), (byte)(k >>> 16), (byte)(k >>> 24), (byte)m, (byte)(m >>> 8), (byte)(m >>> 16), (byte)(m >>> 24), (byte)i, (byte)(i >>> 8), (byte)(i >>> 16), (byte)(i >>> 24), (byte)j, (byte)(j >>> 8), (byte)(j >>> 16), (byte)(j >>> 24) };
    return arrayOfByte;
  }
  
  public static boolean self_test()
  {
    return self_test(16);
  }
  
  private static final int RS_MDS_Encode(int paramInt1, int paramInt2)
  {
    int i = paramInt2;
    for (int j = 0; j < 4; j++) {
      i = RS_rem(i);
    }
    i ^= paramInt1;
    for (int k = 0; k < 4; k++) {
      i = RS_rem(i);
    }
    return i;
  }
  
  private static final int RS_rem(int paramInt)
  {
    int i = paramInt >>> 24 & 0xFF;
    int j = (i << 1 ^ ((i & 0x80) != 0 ? 333 : 0)) & 0xFF;
    int k = i >>> 1 ^ ((i & 0x1) != 0 ? 166 : 0) ^ j;
    int m = paramInt << 8 ^ k << 24 ^ j << 16 ^ k << 8 ^ i;
    return m;
  }
  
  private static final int F32(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    int i = paramInt2 & 0xFF;
    int j = paramInt2 >>> 8 & 0xFF;
    int k = paramInt2 >>> 16 & 0xFF;
    int m = paramInt2 >>> 24 & 0xFF;
    int n = paramArrayOfInt[0];
    int i1 = paramArrayOfInt[1];
    int i2 = paramArrayOfInt[2];
    int i3 = paramArrayOfInt[3];
    int i4 = 0;
    switch (paramInt1 & 0x3)
    {
    case 1: 
      i4 = MDS[0][(P[0][i] & 0xFF ^ n & 0xFF)] ^ MDS[1][(P[0][j] & 0xFF ^ n >>> 8 & 0xFF)] ^ MDS[2][(P[1][k] & 0xFF ^ n >>> 16 & 0xFF)] ^ MDS[3][(P[1][m] & 0xFF ^ n >>> 24 & 0xFF)];
      break;
    case 0: 
      i = P[1][i] & 0xFF ^ i3 & 0xFF;
      j = P[0][j] & 0xFF ^ i3 >>> 8 & 0xFF;
      k = P[0][k] & 0xFF ^ i3 >>> 16 & 0xFF;
      m = P[1][m] & 0xFF ^ i3 >>> 24 & 0xFF;
    case 3: 
      i = P[1][i] & 0xFF ^ i2 & 0xFF;
      j = P[1][j] & 0xFF ^ i2 >>> 8 & 0xFF;
      k = P[0][k] & 0xFF ^ i2 >>> 16 & 0xFF;
      m = P[0][m] & 0xFF ^ i2 >>> 24 & 0xFF;
    case 2: 
      i4 = MDS[0][(P[0][(P[0][i] & 0xFF ^ i1 & 0xFF)] & 0xFF ^ n & 0xFF)] ^ MDS[1][(P[0][(P[1][j] & 0xFF ^ i1 >>> 8 & 0xFF)] & 0xFF ^ n >>> 8 & 0xFF)] ^ MDS[2][(P[1][(P[0][k] & 0xFF ^ i1 >>> 16 & 0xFF)] & 0xFF ^ n >>> 16 & 0xFF)] ^ MDS[3][(P[1][(P[1][m] & 0xFF ^ i1 >>> 24 & 0xFF)] & 0xFF ^ n >>> 24 & 0xFF)];
    }
    return i4;
  }
  
  private static final int Fe32(int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    return paramArrayOfInt[(2 * _b(paramInt1, paramInt2))] ^ paramArrayOfInt[(2 * _b(paramInt1, paramInt2 + 1) + 1)] ^ paramArrayOfInt[(512 + 2 * _b(paramInt1, paramInt2 + 2))] ^ paramArrayOfInt[(512 + 2 * _b(paramInt1, paramInt2 + 3) + 1)];
  }
  
  private static final int _b(int paramInt1, int paramInt2)
  {
    int i = 0;
    switch (paramInt2 % 4)
    {
    case 0: 
      i = paramInt1 & 0xFF;
      break;
    case 1: 
      i = paramInt1 >>> 8 & 0xFF;
      break;
    case 2: 
      i = paramInt1 >>> 16 & 0xFF;
      break;
    case 3: 
      i = paramInt1 >>> 24 & 0xFF;
    }
    return i;
  }
  
  public static int blockSize()
  {
    return 16;
  }
  
  private static boolean self_test(int paramInt)
  {
    boolean bool = false;
    try
    {
      byte[] arrayOfByte1 = { 1, 35, 69, 103, -119, -85, -51, -17, -2, -36, -70, -104, 118, 84, 50, 16, 0, 17, 34, 51, 68, 85, 102, 119 };
      byte[] arrayOfByte2 = new byte[16];
      Object localObject = makeKey(arrayOfByte1);
      byte[] arrayOfByte3 = blockEncrypt(arrayOfByte2, 0, localObject);
      byte[] arrayOfByte4 = blockDecrypt(arrayOfByte3, 0, localObject);
      bool = areEqual(arrayOfByte2, arrayOfByte4);
      if (!bool) {
        throw new RuntimeException("Symmetric operation failed");
      }
    }
    catch (Exception localException) {}
    return bool;
  }
  
  private static boolean areEqual(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    int i = paramArrayOfByte1.length;
    if (i != paramArrayOfByte2.length) {
      return false;
    }
    for (int j = 0; j < i; j++) {
      if (paramArrayOfByte1[j] != paramArrayOfByte2[j]) {
        return false;
      }
    }
    return true;
  }
  
  private static String intToString(int paramInt)
  {
    char[] arrayOfChar = new char[8];
    for (int i = 7; i >= 0; i--)
    {
      arrayOfChar[i] = HEX_DIGITS[(paramInt & 0xF)];
      paramInt >>>= 4;
    }
    return new String(arrayOfChar);
  }
  
  private static String toString(byte[] paramArrayOfByte)
  {
    return toString(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  private static String toString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    char[] arrayOfChar = new char[paramInt2 * 2];
    int i = paramInt1;
    int j = 0;
    while (i < paramInt1 + paramInt2)
    {
      int k = paramArrayOfByte[(i++)];
      arrayOfChar[(j++)] = HEX_DIGITS[(k >>> 4 & 0xF)];
      arrayOfChar[(j++)] = HEX_DIGITS[(k & 0xF)];
    }
    return new String(arrayOfChar);
  }
  
  public static void main(String[] paramArrayOfString)
  {
    self_test(24);
  }
  
  static
  {
    long l = System.currentTimeMillis();
    int[] arrayOfInt1 = new int[2];
    int[] arrayOfInt2 = new int[2];
    int[] arrayOfInt3 = new int[2];
    for (int i = 0; i < 256; i++)
    {
      int j = P[0][i] & 0xFF;
      arrayOfInt1[0] = j;
      arrayOfInt2[0] = (Mx_X(j) & 0xFF);
      arrayOfInt3[0] = (Mx_Y(j) & 0xFF);
      j = P[1][i] & 0xFF;
      arrayOfInt1[1] = j;
      arrayOfInt2[1] = (Mx_X(j) & 0xFF);
      arrayOfInt3[1] = (Mx_Y(j) & 0xFF);
      MDS[0][i] = (arrayOfInt1[1] | arrayOfInt2[1] << 8 | arrayOfInt3[1] << 16 | arrayOfInt3[1] << 24);
      MDS[1][i] = (arrayOfInt3[0] | arrayOfInt3[0] << 8 | arrayOfInt2[0] << 16 | arrayOfInt1[0] << 24);
      MDS[2][i] = (arrayOfInt2[1] | arrayOfInt3[1] << 8 | arrayOfInt1[1] << 16 | arrayOfInt3[1] << 24);
      MDS[3][i] = (arrayOfInt2[0] | arrayOfInt1[0] << 8 | arrayOfInt3[0] << 16 | arrayOfInt2[0] << 24);
    }
    l = System.currentTimeMillis() - l;
  }
}


/* Location:              C:\Users\sergiomv\Desktop\Env\DOC\CA\MEUS_TESTES\probe-sdk\jboss-probe-1.4.0-jars\original_jars_from_probe_deployment\probes\application\jboss\jar\jboss.jar!\Twofish\Twofish_Algorithm.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */