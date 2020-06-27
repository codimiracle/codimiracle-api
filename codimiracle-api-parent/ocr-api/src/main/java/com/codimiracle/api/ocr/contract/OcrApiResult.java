package com.codimiracle.api.ocr.contract;

import com.codimiracle.api.contract.AbstractApiResult;
import com.codimiracle.api.contract.ApiResult;
import lombok.Data;

import java.io.InputStream;

public abstract class OcrApiResult<T> extends AbstractApiResult<T> {
    public abstract InputStream getDataAsStream();
}
