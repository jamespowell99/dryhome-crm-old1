package com.dryhome.utils;

import com.google.common.base.Objects;

public class StringPrintUtils {
    public static String valueOrEmpty(String value) {
        return Objects.firstNonNull(value, " ");
    }
}
