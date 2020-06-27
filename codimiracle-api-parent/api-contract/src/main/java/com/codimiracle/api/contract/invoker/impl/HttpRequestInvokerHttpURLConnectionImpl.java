package com.codimiracle.api.contract.invoker.impl;

import com.codimiracle.api.contract.invoker.Invoker;
import com.codimiracle.api.contract.invoker.exception.ApiInvokingException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpRequestInvokerHttpURLConnectionImpl implements Invoker {
    private String url;
    private String body;
    private String method;
    private Map<String, String> headers = new HashMap<>();
    private HttpURLConnection connection;

    public HttpRequestInvokerHttpURLConnectionImpl(String url, String method, String body, Map<String, String> headers) {
        this.url = url;
        this.body = body;
        this.method = method;
        this.headers.putAll(headers);
    }

    public void setHeader(String name, String value) {
        this.headers.put(name, value);
    }

    public void getHeader(String name) {
        this.headers.get(name);
    }

    @Override
    public String invokeToString() throws IOException {
        return IOUtils.toString(invokeToInputStream());
    }

    @Override
    public InputStream invokeToInputStream() throws IOException {
        URL url = new URL(this.url);
        connection = (HttpURLConnection) url.openConnection();

        // setting request method
        connection.setRequestMethod(Objects.toString(this.method, "get").toUpperCase());

        // setting header
        for (String key : headers.keySet()) {
            connection.setRequestProperty(key, headers.get(key));
        }

        // setting body
        connection.setDoInput(true);
        if (Objects.nonNull(body)) {
            connection.setDoOutput(true);
            IOUtils.write(body, connection.getOutputStream());
        }

        if (HttpURLConnection.HTTP_OK != connection.getResponseCode()) {
            throw new ApiInvokingException("internet connecting failed.");
        }

        return connection.getInputStream();
    }

    @Override
    public void close() throws Exception {
        if (Objects.nonNull(connection)) {
            connection.disconnect();
        }
    }
}
