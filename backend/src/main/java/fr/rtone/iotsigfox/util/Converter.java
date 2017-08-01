package fr.rtone.iotsigfox.util;

/**
 * @Author: Hani
 */
public class Converter {

    /**
     * Convert decimal long value to big endian hexadecimal string
     *
     * @param value decimal long value to convert
     * @return hex value represented in big endian
     */
    public static String longToBigEndianHex(long value, int length) {

        String format = "%0" + length + "X";

        return String.format(format, value);
    }

}
