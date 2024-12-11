package com.codimiracle.api.mubu.contract;

import lombok.Data;

@Data
public class Document {
    private String summary;
    private Boolean isExpert;
    private Author author;
    private Boolean isAuthorVip;
    private Doc doc;
    private Boolean isApp;
    private Integer sermon;
    private Content content;
}
