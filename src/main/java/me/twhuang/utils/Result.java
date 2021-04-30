package me.twhuang.utils;

import lombok.Data;

/**
 * @Author: tw.huang
 * @DateTime: 2020/5/25 23:33
 * @Description: 返回值
 */
@Data
public class Result {

    private Boolean success;

    private Integer code;

    private String msg;

    private Object data;


    public static Result success() {
        Result result = new Result();
        result.success = true;
        result.data = null;
        result.msg = ResultEnum.SUCCESS.getMsg();
        result.code = ResultEnum.SUCCESS.getCode();
        return result;
    }

    public static Result success(String msg) {
        Result result = new Result();
        result.success = true;
        result.data = null;
        result.msg = msg;
        result.code = ResultEnum.SUCCESS.getCode();
        return result;
    }


    public static Result success(Integer code, String msg) {
        Result result = new Result();
        result.success = true;
        result.data = null;
        result.msg = msg;
        result.code = code;
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.success = true;
        result.data = data;
        result.msg = ResultEnum.SUCCESS.getMsg();
        result.code = ResultEnum.SUCCESS.getCode();
        return result;
    }

    public static Result success(Object data, String msg) {
        Result result = new Result();
        result.success = true;
        result.data = data;
        result.msg = msg;
        result.code = ResultEnum.SUCCESS.getCode();
        return result;
    }

    public static Result success(Object data, Integer code, String msg) {
        Result result = new Result();
        result.success = true;
        result.data = data;
        result.msg = msg;
        result.code = code;
        return result;
    }

    public static Result failure() {
        Result result = new Result();
        result.success = false;
        result.data = null;
        result.msg = ResultEnum.FAILURE.getMsg();
        result.code = ResultEnum.FAILURE.getCode();
        return result;
    }

    public static Result failure(String msg) {
        Result result = new Result();
        result.success = false;
        result.data = null;
        result.msg = msg;
        result.code = ResultEnum.FAILURE.getCode();
        return result;
    }

    public static Result failure(Integer code, String msg) {
        Result result = new Result();
        result.success = false;
        result.data = null;
        result.msg = msg;
        result.code = code;
        return result;
    }

}
