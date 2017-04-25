package com.quascenta.petersroad.droidtag.Utils;

/**
 * Created by AKSHAY on 4/20/2017.
 */

public class Hex {
    /**
     * Array of bytes with the Hex values representing ASCII characters '0' thru '9' and 'A' thru 'F'
     */
    private static byte HEX_BYTE_MAP[] = {0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x41, 0x42, 0x43, 0x44, 0x45, 0x46};

    /**
     * Array of chars with the values '0' thru '9' and 'A' thru 'F'
     */
    private static char HEX_CHAR_MAP[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * Returns a string representation of the specified range of bytes in a byte array.
     * The hexadecimal value of each byte is converted to its ASCII representation and
     * followed by a space.  <pre>For example: x'327F' ==> "32 7F "</pre>
     *
     * @param b      - byte array containing the byte(s) to convert
     * @param offset - starting position in the array (0 being the leftmost position)
     * @param n      - length or number of bytes to convert
     * @return string representing the bytes specified
     */
    public static String toHex(byte b[], int offset, int n) {
        int end = Math.min(offset + n, b.length);
        StringBuffer buffer = new StringBuffer();

        for (int i = offset; i < end; i++) {
            buffer.append(toHex(b[i]));
            buffer.append(" ");
        }

        return buffer.toString();
    }

    /**
     * Returns a string representation of all the bytes in a byte array.  The
     * hexadecimal value of each byte is converted to its ASCII representation and
     * followed by a space.  <pre>For example: x'327F' ==> "32 7F "</pre>
     *
     * @param b - byte array to convert
     * @return string representing the bytes in the array
     */
    public static String toHex(byte b[]) {
        if (b == null)
            return "(null)";

        return toHex(b, 0, b.length);
    }

    /**
     * Returns a string representation of byte passed to this method. The
     * hexadecimal value of the byte is converted to its ASCII representation.
     * <pre>For example: x'7F' ==> "7F"</pre>
     *
     * @param b - byte to convert
     * @return string representation of the byte
     */
    public static String toHex(byte b) {
        // Build an array of the characters corresponding to the left and right hex digits (nibbles)
        char[] array = {HEX_CHAR_MAP[(b >> 4) & 0x0f], HEX_CHAR_MAP[b & 0x0f]};

        return new String(array);
    }

    /**
     * Returns a string representation of character passed to this method. The
     * ASCII value of the character is converted to its hexadecimal representation
     * and is prefixed with two zeros.
     * <pre>For example: "N" ==> "004E"</pre>
     *
     * @param c - character to convert
     * @return string representation of the character
     */
    public static String toHex(char c) {
        // The shifting right 8 bits for the hi byte results in 0!
        byte hi = (byte) (c >>> 8);
        // The lo byte contains the hex value for the character
        byte lo = (byte) (c & 0xff);

        return toHex(hi) + toHex(lo);
    }

    /**
     * Returns a string representation of the specified range of bytes in a byte array.
     * The hexadecimal value of each byte is used to look up its corresponding ASCII
     * character for display. Any nulls (value of x'00') are removed.
     * <pre>For example: x'31006A' ==> "1j"</pre>
     *
     * @param b      - byte array containing the byte(s) to convert
     * @param start  - starting position in the array (0 being the leftmost position)
     * @param length - number of bytes to convert
     * @return string representing the bytes specified
     */
    public static String toASCII(byte b[], int start, int length) {
        StringBuffer asciiString = new StringBuffer();

        for (int i = start; i < (length + start); i++) {
            // exclude nulls from the ASCII representation
            if (b[i] != (byte) 0x00) {
                asciiString.append((char) b[i]);
            }
        }

        return asciiString.toString();
    }


    /**
     * Returns a byte array with hexadecimal values of the specified ASCII representation
     * string.  Starting characters "0x" in the string are not converted.  Odd sized strings
     * will be padded with a zero for the leftmost nibble.
     * <pre>For example: "0xF310D6A" ==> x'0F310D6A'</pre>
     *
     * @param str - string to convert
     * @return byte array with hex values
     */
    public static byte[] toBytes(String str) {
        StringBuffer convString;

        // Remove hex prefix of "0x" if exists
        if (str.length() > 1 && str.toLowerCase().startsWith("0x")) {
            convString = new StringBuffer(str.substring(2));
        } else {
            convString = new StringBuffer(str);
        }

        // For odd sized strings, pad on the left with a 0
        if (convString.length() % 2 == 1) {
            convString.insert(0, '0');
        }

        byte[] result = new byte[convString.length() / 2];

        for (int i = 0; i < convString.length(); i += 2) {
            result[i / 2] = (byte) (Integer.parseInt(convString.substring(i, i + 2), 16) & 0xFF);
        }

        return result;
    }

    /**
     * Converts an ASCII representation of a hexadecimal value into an actual hexadecimal byte.
     * <pre>For Example: hi="F" lo="8" ==> x'F8'</pre>
     *
     * @param hi - high nibble or left hex digit in ASCII format
     * @param lo - low nibble or right hex digit in ASCII format
     * @return byte in hexadecimal format
     */
    public static byte fromASCII(byte hi, byte lo) {
        return (byte) Integer.parseInt(new String(new byte[]{hi, lo}), 16);
    }

    /**
     * Returns the decimal value of the specified range of ASCII represented
     * hexadecimal nibbles.
     * <pre>For Example: x'007D' ==> "125"</pre>
     *
     * @param asciiArray - array containing the bytes of ASCII to convert
     * @param startPos - starting position in the array (0 being the leftmost position)
     * @param length - number of bytes to convert
     * @return string representation of the decimal value
     */

    /**
     * Converts a hexadecimal value's high nibble into an ASCII representation byte.
     * <pre>For Example: x'F8' ==> "F"</pre>
     *
     * @param hexByte - byte with the desired hex value
     * @return byte in ASCII format representing the left hex digit
     */
    public static byte nibHiToASCII(byte hexByte) {
        return HEX_BYTE_MAP[(hexByte & 0xF0) >> 4];
    }

    /**
     * Converts a hexadecimal value's low nibble into an ASCII representation byte.
     * <pre>For Example: x'F8' ==> "8"</pre>
     *
     * @param hexByte - byte with the desired hex value
     * @return byte in ASCII format representing the right hex digit
     */
    public static byte nibLoToASCII(byte hexByte) {
        return HEX_BYTE_MAP[hexByte & 0x0F];
    }


    private static byte[] fromHexString(final String encoded) {
        if ((encoded.length() % 2) != 0)
            throw new IllegalArgumentException("Input string must contain an even number of characters");

        final byte result[] = new byte[encoded.length() / 2];
        final char enc[] = encoded.toCharArray();
        for (int i = 0; i < enc.length; i += 2) {
            StringBuilder curr = new StringBuilder(2);
            curr.append(enc[i]).append(enc[i + 1]);

            result[i / 2] = (byte) Integer.parseInt(curr.toString(), 16);
        }
        return result;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public String addHexString(String time_interval, String lowerlimit, String upperLimit, String command) {
        StringBuilder stringBuilder = new StringBuilder();
        String x = stringBuilder.append(time_interval).append(lowerlimit.toUpperCase()).append(upperLimit.toUpperCase()).append(command).toString();
        return x;
    }
}

