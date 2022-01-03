package com.xWash.model.entity;

public class APIResult {
    private int code;
    private String message;
    private Object data;

    public static APIResult createOnlyCode(int code){
        APIResult apiResult = new APIResult();
        apiResult.setCode(code);
        return apiResult;
    }

    public static APIResult createWithMsg(int code,String msg){
        APIResult apiResult = createOnlyCode(code);
        apiResult.setMessage(msg);
        return apiResult;
    }
    public static APIResult createWithData(int code,Object data){
        APIResult apiResult = createOnlyCode(code);
        apiResult.setData(data);
        return apiResult;
    }

    public static APIResult createWithDataAndMsg(int code,String msg, Object data){
        APIResult withMsg = createWithMsg(code, msg);
        withMsg.setData(data);
        return withMsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
