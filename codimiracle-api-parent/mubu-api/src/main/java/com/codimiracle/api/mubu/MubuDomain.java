package com.codimiracle.api.mubu;

import com.codimiracle.api.mubu.contract.Document;
import com.codimiracle.api.mubu.contract.DocumentQuery;

public interface MubuDomain {
    Document getDocument(DocumentQuery query);
}
