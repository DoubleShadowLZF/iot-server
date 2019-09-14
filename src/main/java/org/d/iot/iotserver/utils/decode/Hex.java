//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.d.iot.iotserver.utils.decode;

/**
 * 十六进制
 */
public class Hex {
    public Hex() {
    }

    public static int writeInfo(String s, byte[] bytep, Integer offset, int len) {
        byte[] bdevp = fillWithZero(s, len);
        fillWithBytes(bytep, bdevp, offset, len);
        offset = offset + len;
        return offset;
    }

    public static int writeHexStr(String s, byte[] bytep, int offset, int len) {
        byte[] bdevp = hexStringToBytes(s);
        fillWithBytes(bytep, bdevp, offset, len);
        return offset + len;
    }

    public static byte[] fillWithZero(String s, int len) {
        StringBuilder sb = new StringBuilder(s);
        byte[] bp = new byte[len];
        byte[] bs = sb.toString().getBytes();
        int l = sb.toString().getBytes().length;

        int i;
        for(i = 0; i < l; ++i) {
            bp[i] = bs[i];
        }

        if (l < len - 1) {
            for(i = l; i < len; ++i) {
                bp[i] = 0;
            }
        }

        return bp;
    }

    public static void fillWithBytes(byte[] bsa, byte[] bsb, int offet, int len) {
        for(int i = 0; i < len; ++i) {
            bsa[i + offet] = bsb[i];
        }

    }

    private static String dumpLine(byte[] datas, int offset, int len, int lineLen, boolean showChar) {
        String hex = "";
        String str = "";
        int count = 0;

        int i;
        for(i = offset; i < offset + len && i < datas.length; ++i) {
            byte b = datas[i];
            String t = Integer.toHexString(b).toUpperCase();
            if (t.length() < 2) {
                t = "0" + t;
            } else if (t.length() > 2) {
                t = t.substring(t.length() - 2, t.length());
            }

            hex = hex + t + " ";
            if (showChar) {
                if (b >= 33 && (b <= 47 || b >= 58) && b != 96 && b <= 126) {
                    str = str + (char)b;
                } else {
                    str = str + ".";
                }
            }

            ++count;
        }

        if (count < lineLen) {
            for(i = count; i < lineLen; ++i) {
                hex = hex + "   ";
            }
        }

        return showChar ? hex + "    " + str : hex;
    }

    public static String dumpBytes(byte[] datas, int offset, int length, int lineWrap, boolean showChar, boolean showAddr) {
        StringBuilder sb = new StringBuilder();
        if (length <= 0) {
            length = datas.length - offset;
        }

        int len = lineWrap > 0 && lineWrap <= length ? lineWrap : length;

        for(int i = offset; i < offset + length && i < datas.length; i += len) {
            if (showAddr) {
                String addr = Integer.toHexString(i);
                if (addr.length() < 6) {
                    for(int j = addr.length(); j < 6; ++j) {
                        addr = "0" + addr;
                    }
                }

                sb.append("0x").append(addr.toUpperCase()).append(" ");
            }

            sb.append(dumpLine(datas, i, len, len, showChar)).append(lineWrap > 0 && i + len < offset + length ? "\n" : "");
        }

        return sb.toString();
    }

    public static String dumpBytes(byte[] datas, int offset, int length, int lineWrap, boolean showChar) {
        return dumpBytes(datas, offset, length, lineWrap, showChar, true);
    }

    public static String dumpBytes(byte[] datas, int offset, int length) {
        return dumpBytes(datas, offset, length, length, false);
    }

    public static String dumpBytes(byte[] datas) {
        return dumpBytes(datas, 0, datas.length);
    }

    public static String bytesToHexString(byte[] bs, int group, int offset, int length) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (bs != null && bs.length > 0) {
            if (group < 0) {
                group = 4;
            }

            for(int i = offset; i < offset + length && i < bs.length; ++i) {
                if (group > 0 && (i - offset) % group == 0 && i > offset) {
                    stringBuilder.append(" ");
                }

                int t = bs[i] & 255;
                String ht = Integer.toHexString(t);
                if (ht.length() < 2) {
                    stringBuilder.append('0');
                }

                stringBuilder.append(ht.toUpperCase());
            }

            return stringBuilder.toString();
        } else {
            return null;
        }
    }

    public static byte[] hexStringToBytes(String hexStr) {
        if (hexStr != null && !hexStr.equals("")) {
            hexStr = hexStr.toUpperCase();
            int l = hexStr.length() / 2;
            char[] hexChs = hexStr.toCharArray();
            byte[] bs = new byte[l];

            for(int i = 0; i < l; ++i) {
                int pos = i * 2;
                bs[i] = (byte)(charToByte(hexChs[pos]) << 4 | charToByte(hexChs[pos + 1]));
            }

            return bs;
        } else {
            return null;
        }
    }

    private static byte charToByte(char c) {
        return (byte)"0123456789ABCDEF".indexOf(c);
    }

    public static void main(String[] args) throws Exception {
        byte[] datas = new byte[]{1, 2, 3, 4, 65, 123, 98, 1, 3, 0, 0, 11, 14, 57, 39, 20, 1, 2, 3, 4, 65, 123, 98, 1, 3, 0, 0, 11, 14, 57, 39, 21, 1, 2, 3, 4, 65, 123, 98, 1, 3, 0, 0, 11, 14, 57, 39, 23, 1, 2, 3, 4, 65, 123, 98, 1, 3, 0, 0, 11, 36, -33};
        System.out.println(dumpBytes(datas, 0, datas.length));
        byte b = 9;
        System.out.println(Integer.toHexString(b & 255));
        System.out.println(bytesToHexString(datas, 0, 0, datas.length));
    }
}
