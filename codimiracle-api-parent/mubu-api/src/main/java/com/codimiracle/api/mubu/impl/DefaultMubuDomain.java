package com.codimiracle.api.mubu.impl;

import com.codimiracle.api.mubu.MubuDomain;
import com.codimiracle.api.mubu.contract.Document;
import com.codimiracle.api.mubu.contract.DocumentQuery;
import com.codimiracle.api.mubu.contract.MubuApiResult;
import com.codimiracle.api.mubu.contract.MubuConfig;
import com.codimiracle.api.mubu.util.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;

@Slf4j
public class DefaultMubuDomain implements MubuDomain {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3";
    private final MubuConfig mubuConfig;

    public DefaultMubuDomain(MubuConfig mubuConfig) {
        this.mubuConfig = mubuConfig;
    }

    @Override
    public Document getDocument(DocumentQuery query) {
        String url = String.format("%s/api/document/share/get", mubuConfig.getBaseUrl());
        OkHttpClient client = new OkHttpClient();

        try {
            RequestBody requestBody = RequestBody.create(JSON.stringify(query), MediaType.parse("application/json"));
            Request request = new Request.Builder()
                    .header("user-agent", USER_AGENT)
                    .method("POST", requestBody)
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            MubuApiResult<Document> result = JSON.parse(response.body().string(), new TypeReference<MubuApiResult<Document>>() {
            });
            return result.getData();
        } catch (IOException e) {
            log.error("failed to get mubu document", e);
        }
        return null;
    }
}
