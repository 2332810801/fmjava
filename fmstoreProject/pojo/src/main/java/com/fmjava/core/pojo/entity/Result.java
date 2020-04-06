package com.fmjava.core.pojo.entity;

import lombok.Data;

@Data
public class Result {
    private boolean success;//操作是否成功
    private String message;//相应信息

    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
