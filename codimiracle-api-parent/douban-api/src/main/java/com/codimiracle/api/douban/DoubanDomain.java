package com.codimiracle.api.douban;

import com.codimiracle.api.douban.contract.Book;
import com.codimiracle.api.douban.contract.BookQuery;
import com.codimiracle.api.douban.contract.Page;

public interface DoubanDomain {
    Page<Book> searchBooks(BookQuery query);
}
