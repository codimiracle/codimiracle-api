package com.codimiracle.api.douban.provider.rawmodel;

import lombok.Data;

@Data
public class RawSubject {
    private String id;
    private String title;
    private String subtitle;
    private String intro;
    private String contents;
    private String author;
    private String publisher;
    private String producer;
    private String translator;
    private String publishedYear;
    private String publishedAt;
    private String price;
    private String pages;
    private String binding;
    private String isbn;
    private String coverLargeUrl;
    private String coverSmallUrl;
}
