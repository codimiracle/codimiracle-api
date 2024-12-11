package com.codimiracle.api.douban.impl;

import com.codimiracle.api.douban.DoubanDomain;
import com.codimiracle.api.douban.contract.Book;
import com.codimiracle.api.douban.contract.BookQuery;
import com.codimiracle.api.douban.contract.Page;

public class DefaultDoubanDomain implements DoubanDomain {
    private final DoubanDomain provider;

    public DefaultDoubanDomain(DoubanDomain provider) {
        this.provider = provider;
    }


    @Override
    public Page<Book> searchBooks(BookQuery query) {
        return provider.searchBooks(query);
    }
}
