
package org.d.iot.iotserver.utils.decode;

import org.springframework.util.StringUtils;

import java.io.*;

public class Bytes {
    public Bytes() {
    }

    /**
     * 将对象进行 base64 转码
     * @param object 对象
     * @return 对象的base64字符串
     */
    public static String objectBase64Encode(Object object) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream objout = new ObjectOutputStream(out);
            objout.writeObject(object);
            byte[] byteArray = out.toByteArray();
            return Base64.encodeBytes(byteArray);
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }

    /**
     * 将对象进行 base64 解码
     * @param str base64 字符串
     * @return 对象
     */
    public static Object objectBase64Decode(String str) {
        try {
            byte[] byteArray = Base64.decode(str);
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteArray);
            ObjectInputStream objIn = new ObjectInputStream(byteIn);
            return objIn.readObject();
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }

    /**
     * 将对象序列化
     * @param m 对象
     * @return 序列化字节数组
     * @throws Exception
     */
    public static byte[] objectToBytes(Serializable m) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(m);
        byte[] b = baos.toByteArray();
        oos.close();
        return b;
    }

    /**
     * 反序列化对象
     * @param b 序列化数组
     * @return 对象
     * @throws Exception
     */
    public static Object bytesToObject(byte[] b) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object o = ois.readObject();
        ois.close();
        return o;
    }

    /**
     * 按大端格式将long转化为字节数组
     * @param longValue long值
     * @return 十六进制字节数组
     */
    public static byte[] longToBytes(long longValue) {
        return long2Bytes(longValue, true);
    }

    /**
     * 将 longVal ，按大端或小端格式转化成字节数组
     * @param longVal long值
     * @param highFirst true 为大端，false为小端
     * @return 十六进制字节数组
     */
    public static byte[] long2Bytes(long longVal, boolean highFirst) {
        byte[] byteValue = new byte[8];
        int shift = 0;

        for(int x = 0; x < 8; ++x) {
            shift -= 8;
            if (highFirst) {
                byteValue[x] = (byte)((int)(longVal >>> shift));
            } else {
                byteValue[7 - x] = (byte)((int)(longVal >>> shift));
            }
        }

        return byteValue;
    }

    /**
     * 将 intVal ，按大端或小端格式转化成字节数组
     * @param intVal int值
     * @param highFirst true 为大端，false为小端
     * @return 十六进制字节数组
     */
    public static byte[] int2Bytes(int intVal, boolean highFirst) {
        byte[] byteValue = new byte[4];
        int shift = 0;

        for(int x = 0; x < 4; ++x) {
            shift -= 8;
            if (highFirst) {
                byteValue[x] = (byte)(intVal >>> shift);
            } else {
                byteValue[3 - x] = (byte)(intVal >>> shift);
            }
        }

        return byteValue;
    }

    /**
     * 将 intVal ，按大端格式转化成字节数组
     * @param intValue ing值
     * @return 十六进制字节数组
     */
    public static byte[] intToBytes(int intValue) {
        byte[] byteValue = new byte[4];
        int shift = 0;

        for(int x = 0; x < 4; ++x) {
            shift -= 8;
            byteValue[x] = (byte)(intValue >>> shift);
        }

        return byteValue;
    }

    /**
     * bytes按大端或小端格式，从偏移量 offset 开始，进行 length 截取或补齐
     *
     * [0,0,5,0,0] =>
     * 大端格式，偏移量为2，length 为3时，
     * 结果： 十进制 ： 327680 =>  十六进制： 0x500
     * 小端格式，偏移量未，length为3时，
     * 结果：十进制： 5 => 十六进制： 0x005
     *
     * @param highFirst true 为大端，false 为小端
     * @param offset 偏移量
     * @param length 偏移长度，不足0 补齐
     * @param bytes 进行操作的数组
     * @return 十进制int值
     */
    public static int bytes2Int(boolean highFirst, int offset, int length, int... bytes) {
        byte[] tmp = new byte[bytes.length];

        for(int i = 0; i < tmp.length; ++i) {
            tmp[i] = (byte)bytes[i];
        }

        return bytes2Int(highFirst, offset, length, tmp);
    }

    /**
     * bytes按大端或小端格式，从偏移量 offset 开始，进行 length 截取或补齐
     *
     * [0,0,5,0,0] =>
     * 大端格式，偏移量为2，length 为3时，
     * 结果： 十进制 ： 327680 =>  十六进制： 0x500
     * 小端格式，偏移量未，length为3时，
     * 结果：十进制： 5 => 十六进制： 0x005
     *
     * @param highFirst true 为大端，false 为小端
     * @param offset 偏移量
     * @param length 偏移长度，不足0 补齐
     * @param bytes 进行操作的数组
     * @return 十进制int值
     */
        public static int bytes2Int(boolean highFirst, int offset, int length, byte... bytes) {
        int intVal = 0;
        // int 为 32个字节，所以字节数组为4
        byte[] tmp = new byte[4];
        if (offset < 0 || offset >= bytes.length) {
            offset = 0;
        }

        if (length >= bytes.length - offset) {
            length = bytes.length - offset;
        }

        if (length > 4) {
            length = 4;
        }

        int i;
        for(i = 3; i >= 0; --i) {
            if (highFirst) {
                tmp[3 - i] = length > i ? bytes[offset + length - 1 - i] : 0;
            } else {
                tmp[3 - i] = length > i ? bytes[offset + i] : 0;
            }
        }

        //将十六进制转化为十进制
        for(i = 0; i < tmp.length; ++i) {
            intVal <<= 8;
            intVal |= tmp[i] & 255;
        }

        return intVal;
    }

    /**
     * 将十六进制的字节数组转化为十进制的int值
     * @param b 十六进制的数组
     * @return 十进制int值
     */
    public static int bytesToInt(byte... b) {
        int intValue = 0;

        for(int x = 0; x < 4; ++x) {
            intValue <<= 8;
            intValue |= b[x] & 255;
        }

        return intValue;
    }

    /**
     * bytes按大端或小端格式，从偏移量 offset 开始，进行 length 截取或补齐
     *
     * [0,0,5,0,0] =>
     * 大端格式，偏移量为2，length 为3时，
     * 结果： 十进制 ： 327680 =>  十六进制： 0x500
     * 小端格式，偏移量未，length为3时，
     * 结果：十进制： 5 => 十六进制： 0x005
     *
     * @param highFirst true 为大端，false 为小端
     * @param offset 偏移量
     * @param len 偏移长度，不足0 补齐
     * @param bytes 进行操作的数组
     * @return 十进制的long值
     */
    public static long bytes2Long(boolean highFirst, int offset, int len, byte... bytes) {
        long longValue = 0L;
        byte[] tmp = new byte[8];
        if (offset < 0 || offset >= bytes.length) {
            offset = 0;
        }

        if (offset + len >= bytes.length) {
            len = bytes.length - offset;
        }

        if (len > 8) {
            len = 8;
        }

        int i;
        for(i = 7; i >= 0; --i) {
            if (highFirst) {
                tmp[7 - i] = len > i ? bytes[offset + len - 1 - i] : 0;
            } else {
                tmp[7 - i] = len > i ? bytes[offset + i] : 0;
            }
        }

        for(i = 0; i < tmp.length; ++i) {
            longValue <<= 8;
            longValue |= (long)(tmp[i] & 255);
        }

        return longValue;
    }

    /**
     * bytes按大端格式转为long值
     *
     * [0,0,5,0,0] =>
     * 大端格式，偏移量为2，length 为3时，
     * 结果： 十进制 ： 327680 =>  十六进制： 0x500
     * 小端格式，偏移量未，length为3时，
     * 结果：十进制： 5 => 十六进制： 0x005
     *
     * @param byteValues true 为大端，false 为小端
     * @return 十进制的long值
     */
    public static long bytesToLong(byte[] byteValues) {
        return bytes2Long(true, 0, byteValues.length, byteValues);
    }

    public static byte[] doubleToBytes(double doubleValue) throws Exception {
        long longValue = Double.doubleToLongBits(doubleValue);
        return longToBytes(longValue);
    }

    public static double bytesToDouble(byte[] bytes) throws Exception {
        long longValue = bytesToLong(bytes);
        return Double.longBitsToDouble(longValue);
    }

    public static byte[] floatToBytes(float floatValue) throws Exception {
        int intValue = Float.floatToIntBits(floatValue);
        return intToBytes(intValue);
    }

    public static float bytesToFloat(byte[] bytes) throws Exception {
        int intValue = bytesToInt(bytes);
        return Float.intBitsToFloat(intValue);
    }

    public static byte[] stringToBytes(String str, int length) throws Exception {
        return stringToBytes(str, length, "UTF-8");
    }

    public static byte[] stringToBytes(String str, int length, String charset) throws Exception {
        byte[] bytes = new byte[length];
        if (str != null) {
            byte[] strBytes = StringUtils.isEmpty(charset) ? str.getBytes() : str.getBytes(charset);
            if (strBytes.length > bytes.length) {
                System.arraycopy(strBytes, 0, bytes, 0, bytes.length);
            } else {
                System.arraycopy(strBytes, 0, bytes, 0, strBytes.length);
            }
        }

        return bytes;
    }

    public static String bytes2Str(String charset, int offset, int len, byte... bytes) throws Exception {
        if (offset < 0) {
            offset = 0;
        }

        if (offset >= bytes.length) {
            offset = bytes.length - 1;
        }

        if (len + offset >= bytes.length) {
            len = bytes.length - offset;
        }

        int lastIndex = offset;
        boolean isLast = false;

        for(int i = offset + len - 1; i > offset; --i) {
            if (bytes[i] != 0) {
                lastIndex = i;
                break;
            }
        }

        len = lastIndex - offset + 1;
        return !StringUtils.isEmpty(charset) ? new String(bytes, offset, len, charset) : new String(bytes, offset, len);
    }

    public static String bytesToString(byte[] bytes) throws Exception {
        return bytes2Str((String)null, 0, bytes.length, bytes);
    }

    public static int[] intToIntArray(int intValue) throws Exception {
        int[] intArray = new int[4];
        int shift = 8;

        for(int x = 0; x < 4; ++x) {
            int tmp = intValue << x * shift;
            intArray[x] = tmp >>> 24;
        }

        return intArray;
    }

    public static byte[] toUnicodeBytes(String str) throws Exception {
        if (str != null && str.length() != 0) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            for(int i = 0; i < str.length(); ++i) {
                char c = str.charAt(i);
                byte[] b = intToBytes(c);
                out.write(b, 2, 2);
            }

            return out.toByteArray();
        } else {
            return null;
        }
    }

    public static String fromUnicodeBytes(byte[] bytes) throws Exception {
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < bytes.length; i += 2) {
            int intAsc = bytesToInt((byte)0, (byte)0, bytes[i], bytes[i + 1]);
            sb.append((char)intAsc);
        }

        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        byte[] b = new byte[]{0, 0, 5, 0, 0};
        byte tmpb = -2;
        print(doubleToBytes(23.67));
        Bytes.print(long2Bytes(256L,true));
        System.out.println(tmpb);
        System.out.println(bytes2Int(false, 2, 3, (byte[])b));
        System.out.println(bytes2Int(true, 2, 3, (byte[])b));
        System.out.println(bytes2Int(true, 2, 2, (byte[])b));
        System.out.println(bytes2Int(true, 2, 1, (byte[])b));
        System.out.println(bytes2Int(true, 2, 0, (byte[])b));
        System.out.println(bytesToInt((byte)0, (byte)0, (byte)12, (byte)0));
        System.out.println(bytesToInt((byte)0, (byte)5, (byte)0, (byte)0));
        System.out.println(bytesToInt((byte)5, (byte)0, (byte)0, (byte)0));
        print(int2Bytes(5, false));
        print(int2Bytes(5, true));
    }

    public static void print(byte... bytes) {
        byte[] var1 = bytes;
        int var2 = bytes.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            byte b = var1[var3];

            System.out.print(Integer.toHexString(b) + " ");
        }

        System.out.println();
    }

    public static void main1(String[] args) throws Exception {
        int intValue = -300;
        System.out.println(Binary.intToBin(intValue, true, 8));
        System.out.println(Binary.intToBin(intValue >>> -8, true, 8));
        System.out.println(Binary.intToBin(intValue >>> -16, true, 8));
        System.out.println(Binary.intToBin(intValue >>> -24, true, 8));
        System.out.println(Binary.intToBin(intValue >>> -32, true, 8));
        System.out.println((intValue >>> -8) + "\t" + (intValue << 8));
        System.out.println((intValue >>> -16) + "\t" + (intValue << 16));
        System.out.println((intValue >>> -24) + "\t" + (intValue << 24));
        System.out.println((intValue >>> -32) + "\t" + (intValue << 32));
        System.out.println((byte)(intValue >>> -8));
        System.out.println((byte)(intValue >>> -16));
        System.out.println((byte)(intValue >>> -24));
        System.out.println((byte)(intValue >>> -32));
        System.out.println(bytesToInt((byte)(intValue >>> -8), (byte)(intValue >>> -16), (byte)(intValue >>> -24), (byte)(intValue >>> -32)));
        System.out.println();
        long longValue = (long)intValue;
        System.out.println("long开始  \t" + new String(Binary.longToBin(longValue, true, 8)));
        longValue <<= 16;
        System.out.println("long左移16\t" + new String(Binary.longToBin(longValue, true, 8)));
        longValue >>>= 16;
        System.out.println("long右移16\t" + new String(Binary.longToBin(longValue, true, 8)));
        System.out.println(intValue + "\t" + longValue + "\t" + (int)longValue);
        byte byteValue = -128;
        System.out.println("byte原始位\t" + new String(Binary.byteToBin(byteValue, true, 8)));
        short shortValue = (short)byteValue;
        System.out.println("short开始 \t" + new String(Binary.shortToBin(shortValue, true, 8)));
        shortValue = (short)(shortValue << 8);
        System.out.println("short左移8\t" + new String(Binary.shortToBin(shortValue, true, 8)));
        shortValue = (short)(shortValue >>> 16);
        System.out.println("short右移8\t" + new String(Binary.shortToBin(shortValue, true, 8)));
        System.out.println(byteValue + "\t" + shortValue + "\t" + (byte)shortValue);
        System.out.println("int开始  \t" + new String(Binary.intToBin(byteValue, true, 8)));
        intValue = byteValue << 16;
        System.out.println("int左移16\t" + new String(Binary.intToBin(intValue, true, 8)));
        intValue >>>= 16;
        System.out.println("int右移16\t" + new String(Binary.intToBin(intValue, true, 8)));
        System.out.println(byteValue + "\t" + intValue + "\t" + (byte)intValue);
        System.out.println((char)intValue);
        System.out.println(Binary.intToBin(1, true, 8));
        System.out.println(Binary.intToBin(1, false, 8));
        System.out.println("-------------------------------------");
        char charValue = 20098;
        int[] intArray = intToIntArray(charValue);
        System.out.println(Binary.intToBin(charValue, true, 8));

        for(int i = 0; i < intArray.length; ++i) {
            System.out.print(intArray[i] + "\t");
            System.out.println(Binary.intToBin(intArray[i], true, 8));
        }

    }
}
