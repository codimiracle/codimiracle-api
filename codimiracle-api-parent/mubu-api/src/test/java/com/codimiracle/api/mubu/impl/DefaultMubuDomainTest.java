package com.codimiracle.api.mubu.impl;

import com.codimiracle.api.mubu.MubuDomain;
import com.codimiracle.api.mubu.contract.Document;
import com.codimiracle.api.mubu.contract.DocumentQuery;
import com.codimiracle.api.mubu.contract.MubuConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DefaultMubuDomainTest {
    @Test
    void getDocument() {
        MubuConfig config = new MubuConfig();
        config.setBaseUrl("https://api2.mubu.com/v3");
        MubuDomain mubuDomain = new DefaultMubuDomain(config);

        DocumentQuery query = new DocumentQuery();
        query.setShareId("2w3AYxikYlE");
        Document document = mubuDomain.getDocument(query);
        assertNotNull(document);
    }


}