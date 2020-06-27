package com.codimiracle.api.contract;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class AbstractApiResult<T> implements ApiResult<T> {
    private int code;
    private String message;
    private T data;

    public String getDataAsString() {
        return Objects.toString(this.data, "");
    }
}
