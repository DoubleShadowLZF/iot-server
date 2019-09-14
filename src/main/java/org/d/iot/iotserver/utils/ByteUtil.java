package org.d.iot.iotserver.utils;

/**
 * ClassName: ByteUtil <br/>
 * Description: 字节工具类 <br/>
 * date: 2019/9/5 23:16<br/>
 *
 * @author Double <br/>
 * @since JDK 1.8
 */
public class ByteUtil {

    public static byte[] int2Bytes(int intVal,boolean isBigDomain){
        byte[] byteValue = new byte[4];
        int shift = 0;
        for (int i = 0; i < 4; i++) {
            shift -= 8;
            if(isBigDomain){
                byteValue[i] = (byte)(intVal >>> shift);
            }else{
                byteValue[3- i] = (byte)(intVal >>> shift);
            }
        }
        return byteValue;
    }

    public static byte[] int2Bytes(int intValue){
        byte[] byteValue = new byte[4];
        int shift = 0;
        for (int i = 0; i < 4; i++) {
            shift -= 8;
            byteValue[i] = (byte)(intValue >>> shift);
        }
        return byteValue;
    }


    public static int bytes2Int(boolean isBigDomain,int offset ,int length,int ... bytes){
        byte[] tmp = new byte[bytes.length];
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = (byte)bytes[i];
        }
        return bytes2Int(isBigDomain,offset,length,tmp);
    }

    public static int bytes2Int(boolean isBigDomain,int offset ,int length , byte ... bytes){
        int intVal = 0;
        byte [] tmp = new byte[4];
        if(offset < 0 || offset >= bytes.length){
            offset = 0;
        }

        if(offset + length >= bytes.length){
            length = bytes.length - offset;
        }

        if(length > 4){
            length =4 ;
        }

        for (int i = 3; i >= 0 ; i--) {
            if(isBigDomain){
                tmp[3 - i] = length > i ? bytes[offset + length - 1 -i ] : 0 ;
            }else{
                tmp[ 3- i] = length > i ?bytes[offset + i] : 0;
            }
        }

        for (int i = 0; i < tmp.length; i++) {
            intVal <<= 8;
            intVal |= tmp[i] & 255;
        }
        return intVal;
    }

    public static int bytes2Int(byte ... b ){
        int intVal = 0;
        for (int i = 0; i < 4; i++) {
            intVal <<= 8 ;
            intVal |= b[i] & 255;
        }
        return intVal;
    }

}
