package com.codimiracle.api.mubu.contract;

import lombok.Data;

@Data
public class MubuApiResult<T> {
    private int code;
    private T data;
    private String msg;
}
