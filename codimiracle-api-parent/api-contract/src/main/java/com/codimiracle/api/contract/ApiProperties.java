package com.codimiracle.api.contract;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ApiProperties {
    private String appId;
    private String apiKey;
    private String secret;
    private Map<String, String> options;
    private boolean usingSSL;
}
