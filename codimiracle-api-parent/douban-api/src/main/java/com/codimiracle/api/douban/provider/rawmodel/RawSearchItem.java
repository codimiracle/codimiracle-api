package com.codimiracle.api.douban.provider.rawmodel;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class RawSearchItem {
    private String id;
    @JsonAlias("abstract")
    private String abstractPrimary;
    @JsonAlias("abstract_2")
    private String abstractSecondary;
    private String title;
    private String url;
    private String coverUrl;
    private RawRating rating;
    private String tplName;
    
    public boolean isSubject() {
        return "search_subject".equals(this.tplName);
    }
}
