package com.qihoo.qsql.security.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;

public class Utils {

    public static String str2Base(String str) {
        if (null != str) {
            Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(str.getBytes());
        }
        return null;
    }

    public static String base2Str(String str) {
        if (null != str) {
            Base64.Decoder decoder = Base64.getDecoder();
            try {
                return new String(decoder.decode(str.getBytes()), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }
        return null;
    }

    public static BigDecimal getBitMapValue(List<Integer> bitMapIndex) {
        if (bitMapIndex.size() > 0) {
            return new BigDecimal(String.valueOf(bitMapIndex.stream()
                .mapToDouble(p -> Double.valueOf(p.toString()))
                .reduce(0, (a, b) -> a + Math.pow
                    (2, b))));
        }
        return new BigDecimal("0");
    }
}
