package com.codimiracle.api.douban.provider.rawmodel;

import lombok.Data;

import java.util.List;

@Data
public class RawSearchResult {
    private Integer count;
    private String errorInfo;
    private List<RawSearchItem> items;
    private Integer start;
    private Long total;
}
