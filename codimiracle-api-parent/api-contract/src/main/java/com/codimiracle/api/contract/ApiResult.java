package com.codimiracle.api.contract;

public interface ApiResult<T> {
    int getCode();

    String getMessage();

    T getData();
}
