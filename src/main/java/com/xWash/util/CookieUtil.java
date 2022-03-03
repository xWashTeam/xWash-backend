package com.xWash.util;

import java.util.UUID;

public class CookieUtil {
    public static String generateRandUserCookie() {
        return String.valueOf(UUID.randomUUID())
                .replace("-", "");

    }
}
