package com.codimiracle.api.douban.provider;

import com.codimiracle.api.douban.contract.Book;
import com.codimiracle.api.douban.contract.BookQuery;
import com.codimiracle.api.douban.contract.Page;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DoubanSpiderTest {
    @Test
    public void testSearch() {
        DoubanSpider doubanSpider = new DoubanSpider();

        BookQuery query = new BookQuery();
        query.setKeyword("Ruby");
        Page<Book> bookPage = doubanSpider.searchBooks(query);
        assertNotNull(bookPage);
    }

    @Test
    public void testGetSubject() {
        DoubanSpider doubanSpider = new DoubanSpider();
        Book book = doubanSpider.getBook("27074564");
        assertNotNull(book);
    }

}