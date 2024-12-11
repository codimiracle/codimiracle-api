package com.codimiracle.api.douban.contract;

import lombok.Data;

@Data
public class Book extends Subject {
    /**
     * 书名
     */
    private String title;
    /**
     * 作者
     */
    private String author;
    /**
     * 出版社
     */
    private String publisher;
    /**
     * 出品方
     */
    private String producer;
    /**
     * 原作名
     */
    private String originalTitle;
    /**
     * 译者
     */
    private String translator;
    /**
     * 出版年
     */
    private String publicationYear;
    /**
     * 页数
     */
    private Long pageCount;
    /**
     * 定价
     */
    private Double price;
    /**
     * 装帧
     */
    private String binding;
    /**
     * 丛书
     */
    private String series;
    /**
     * ISBN
     */
    private String isbn;
    /**
     * 封面
     */
    private String coverUrl;
    /**
     * 评分
     */
    private String rating;
}
