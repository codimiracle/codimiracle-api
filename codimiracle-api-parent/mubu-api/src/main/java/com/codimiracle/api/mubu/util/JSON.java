package com.codimiracle.api.mubu.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JSON {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String stringify(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            log.debug("stringify object error: {}", object, e);
            return null;
        }
    }

    public static <T> T parse(String json, Class<T> targetClass) {
        try {
            return (T) MAPPER.readValue(json, targetClass);
        } catch (Exception e) {
            log.debug("parse json error: {}", json, e);
            return null;
        }
    }

    public static <T> T parse(String json, TypeReference<T> typeReference) {
        try {
            return (T) MAPPER.readValue(json, typeReference);
        } catch (Exception e) {
            log.debug("parse json error: {}", json, e);
            return null;
        }
    }
}
