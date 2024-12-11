package com.codimiracle.api.douban.contract;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    private Long total;
    private Integer size;
    private Integer page;
    private List<T> records;
}
