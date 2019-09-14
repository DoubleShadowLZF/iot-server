package org.d.iot.iotserver.utils.decode;

public class Binary {
    public Binary() {
    }

    public static char[] byteToBin(byte b, boolean highFirst, int groupBits) {
        return toCharBits((long)b, 8, highFirst, groupBits);
    }

    public static char[] charToBin(char b, boolean highFirst, int groupBits) {
        return toCharBits((long)b, 16, highFirst, groupBits);
    }

    public static char[] shortToBin(short b, boolean highFirst, int groupBits) {
        return toCharBits((long)b, 16, highFirst, groupBits);
    }

    public static char[] intToBin(int b, boolean highFirst, int groupBits) {
        return toCharBits((long)b, 32, highFirst, groupBits);
    }

    public static char[] longToBin(long b, boolean highFirst, int groupBits) {
        return toCharBits(b, 64, highFirst, groupBits);
    }

    public static char[] floatToBin(float a, boolean highFirst, int groupBits) {
        return toCharBits((long)Float.floatToIntBits(a), 32, highFirst, groupBits);
    }

    public static char[] doubleToBin(double a, boolean highFirst, int groupBits) {
        return toCharBits(Double.doubleToLongBits(a), 64, highFirst, groupBits);
    }

    private static char[] toCharBits(long num, int size, boolean highFirst, int groupBits) {
        char[] charBits = new char[size];
        int i;
        if (highFirst) {
            for(i = size - 1; i >= 0; --i) {
                charBits[i] = (char)((num & 1L) == 0L ? 48 : 49);
                num >>>= 1;
            }
        } else {
            for(i = 0; i < size; ++i) {
                charBits[i] = (char)((num & 1L) == 0L ? 48 : 49);
                num >>>= 1;
            }
        }

        if (groupBits > 0 && groupBits < size) {
            StringBuffer sb = new StringBuffer();
            sb.append(charBits);
            int groupCount = size / groupBits;

            for(i = 0; i < groupCount; ++i) {
                int offset = groupBits * (i + 1) + i;
                if (offset >= sb.length()) {
                    break;
                }

                sb.insert(offset, ' ');
            }

            charBits = new char[sb.length()];
            sb.getChars(0, charBits.length, charBits, 0);
        }

        return charBits;
    }

    public static int binChars2Int(char[] chars) throws Exception {
        int intValue = 0;

        for(int i = 1; i <= chars.length; ++i) {
            char c = chars[i - 1];
            if (c == '1') {
                intValue += 1 << chars.length - i;
            } else if (c == '0') {
                intValue += 0 << chars.length - i;
            }
        }

        return intValue;
    }

    public static void main(String[] args) throws Exception {
        int intValue = -99123;
        char[] bin = intToBin(intValue, true, 0);
        intValue = binChars2Int(bin);
        System.out.println(intValue);
        System.out.println(binChars2Int(new char[]{'0', '1', '1'}));
        String str = "00000000111111110000000100000011";
        System.out.println(str + "\t=>\t" + binChars2Int(str.toCharArray()));
        Object o = new Integer[]{1, 2, 3};
        System.out.println(o instanceof Object[]);
    }
}
