package com.codimiracle.api.douban.provider;

import com.codimiracle.api.douban.DoubanDomain;
import com.codimiracle.api.douban.contract.Book;
import com.codimiracle.api.douban.contract.BookQuery;
import com.codimiracle.api.douban.contract.Page;
import com.codimiracle.api.douban.provider.rawmodel.RawSearchItem;
import com.codimiracle.api.douban.provider.rawmodel.RawSearchResult;
import com.codimiracle.api.douban.provider.rawmodel.RawSubject;
import com.codimiracle.api.douban.util.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

@Slf4j
public class DoubanSpider implements DoubanDomain {
    private static final String DOUBAN_BASE = "https://www.douban.com";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36";
    private static final String FIELD_MAPPING_BOOK = "标题|title,副标题|subtitle,丛书|series,作者|author,出品方|producer,出版社|publisher,出版年|publishedYear,出版日期|publishedAt,ISBN|isbn,定价|price,页数|pages,装帧|binding,评分|rating";

    @Override
    public Page<Book> searchBooks(BookQuery query) {
        String json = searchSubjects("book", query.getKeyword());
        if (json == null) {
            return null;
        }
        RawSearchResult searchResult = JSON.parse(json, RawSearchResult.class);

        List<Book> records = new ArrayList<>();
        for (RawSearchItem item : searchResult.getItems()) {
            if (!item.isSubject()) {
                continue;
            }
            Book book = new Book();
            book.setId(item.getId());
            book.setTitle(item.getTitle());
            book.setUrl(item.getUrl());
            book.setCoverUrl(item.getCoverUrl());
            book.setMeta(item.getAbstractPrimary());
            records.add(book);
        }

        Page<Book> bookPage = new Page<>();
        bookPage.setTotal(searchResult.getTotal());
        bookPage.setRecords(records);
        bookPage.setPage(searchResult.getStart());
        bookPage.setSize(searchResult.getCount());
        return bookPage;
    }

    private String searchSubjects(String type, String searchText) {
        String url = String.format("%s/%s/subject_search?search_text=%s", DOUBAN_BASE, type, searchText);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header("user-agent", USER_AGENT)
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String body = response.body().string();
            return parseRawSubjectListStr(body);
        } catch (IOException e) {
            log.error("failed to get subject html", e);
        }
        return null;
    }

    private String parseRawSubjectListStr(String response) {
        int startIndex = response.indexOf("window.__DATA__");
        if (startIndex == -1) {
            return null;
        }
        int endIndex = response.indexOf("window", startIndex + 1);
        response = response.substring(startIndex, endIndex).trim();
        response = response.substring(response.indexOf("=") + 1).trim();
        if (response.endsWith(";")) {
            response = response.substring(0, response.length() - 1);
        }
        return response;
    }

    private String parseRawSubjectStr(String subjectId, String response) {
        Map<String, Object> data = new HashMap<>();
        data.put("id", subjectId);

        Document document = Jsoup.parse(response);
        Elements mainPic = document.select("#mainpic a");
        String largePic = mainPic.attr("href");
        String smallPick = mainPic.select("img").attr("src");

        String title = mainPic.attr("title");
        data.put("title", title);

        data.put("coverLargeUrl", largePic);
        data.put("coverSmallUrl", smallPick);

        StringBuilder builder = new StringBuilder();
        for (Node child : document.select("#info").first().childNodes()) {
            if (child instanceof Element) {
                Element element = (Element) child;
                if (element.tagName().equals("br")) {
                    builder.append("\n");
                } else {
                    builder.append(element.text());
                }
            } else {
                builder.append(child.toString());
            }
        }
        String infoStr = builder.toString();
        StringTokenizer tokenizer = new StringTokenizer(infoStr, "\n");

        Map<String, String> fieldNameMap = new HashMap<>();
        for (String nameField : FIELD_MAPPING_BOOK.split(",")) {
            String[] parts = nameField.split("\\|");
            ;
            fieldNameMap.put(parts[0], parts[1]);
        }
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            int colonIndex = token.indexOf(":");
            String attrName = token.substring(0, colonIndex);
            String attrValue = token.substring(colonIndex + 1);
            attrName = Entities.unescape(attrName).trim();
            attrValue = Entities.unescape(attrValue).trim();
            data.put(fieldNameMap.get(attrName), attrValue);
        }

        String introStr = document.select("#content .intro").last().text();
        data.put("intro", introStr);

        StringBuilder contentBuilder = new StringBuilder();
        Elements contentElements = document.select("#dir_" + subjectId + "_full");
        for (Node childNode : contentElements.first().childNodes()) {
            if (childNode instanceof Element) {
                Element element = (Element) childNode;
                if (element.tagName().equals("br")) {
                    contentBuilder.append("\n");
                } else if (element.tagName().equals("a")) {
                    contentBuilder.append("EOF");
                } else {
                    contentBuilder.append(element.text().trim());
                }
            } else {
                contentBuilder.append(childNode.toString().trim());
            }
        }
        String contents = contentBuilder.toString();
        int eofIndex = contents.lastIndexOf("(EOF)");
        if (eofIndex > -1) {
            contents = contents.substring(0, eofIndex);
        }
        data.put("contents", contents.trim());

        return JSON.stringify(data);
    }

    private String getSubject(String subjectId) {
        if (subjectId != null) {
            try {
                String response = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("douban_subject.html"));
                return parseRawSubjectStr(subjectId, response);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String url = String.format("%s/subject/%s/", DOUBAN_BASE, subjectId);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header("user-agent", USER_AGENT)
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return parseRawSubjectStr(subjectId, response.body().string());
        } catch (IOException e) {
            log.error("failed to get subject html", e);
        }
        return null;
    }

    public Book getBook(String subjectId) {
        String json = getSubject(subjectId);
        if (json == null) {
            return null;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            RawSubject subject = objectMapper.readValue(json, RawSubject.class);

            Book book = new Book();
            book.setId(subjectId);
            book.setTitle(subject.getTitle());
            book.setAuthor(subject.getAuthor());
            book.setTranslator(subject.getTranslator());
            book.setPublisher(subject.getPublisher());
            book.setBinding(subject.getBinding());
            book.setPageCount(Long.parseLong(subject.getPages()));
            book.setIsbn(subject.getIsbn());
            return book;
        } catch (JsonProcessingException e) {
            log.debug("failed to parse json", e);
            return null;
        }
    }
}
