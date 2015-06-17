package com.dryhome.utils;

import org.apache.commons.lang.StringUtils;

public class StringPrintUtils {
    public static String valueOrEmpty(String value) {
        return StringUtils.defaultIfEmpty(value, " ");
    }
}
