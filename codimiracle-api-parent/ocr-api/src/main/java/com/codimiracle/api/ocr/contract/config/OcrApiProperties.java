package com.codimiracle.api.ocr.contract.config;

import com.codimiracle.api.contract.ApiProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OcrApiProperties extends ApiProperties {
    private boolean resolvingLocation;
    private String recognizedLanguages;
}
