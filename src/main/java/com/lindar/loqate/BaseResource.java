package com.lindar.loqate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

abstract class BaseResource {
    String urlEncode(String param) {
        if (param == null) {
            return "";
        }

        try {
            return URLEncoder.encode(
                    param,
                    java.nio.charset.StandardCharsets.UTF_8.toString()
            );
        } catch (UnsupportedEncodingException e) {
            // utf8 is supported everywhere, shouldn't happen
            return param;
        }
    }
}
