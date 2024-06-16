package com.waterstation.waterstation.entity;

public class Result<T> {
    private boolean success; // 操作是否成功
    private int code; // 返回码
    private String message; // 提示信息
    private T data; // 数据

    // 构造函数
    public Result(boolean success, int code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 成功的快速创建方法
    public static <T> Result<T> success(T data) {
        return new Result<>(true, 200, "操作成功", data);
    }

    // 失败的快速创建方法
    public static <T> Result<T> error(int code, String message) {
        return new Result<>(false, code, message, null);
    }

}