package com.codimiracle.api.douban.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JSON {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    static {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    public static <T> T parse(String json, Class<T> targetClass) {
        try {
            return (T) MAPPER.readValue(json, targetClass);
        } catch (Throwable e) {
            log.debug("parse json error: {}", json, e);
            return null;
        }
    }

    public static String stringify(Object target) {
        try {
            return MAPPER.writeValueAsString(target);
        } catch (Throwable e) {
            log.debug("stringify object error: {}", target, e);
            return null;
        }
    }
}
