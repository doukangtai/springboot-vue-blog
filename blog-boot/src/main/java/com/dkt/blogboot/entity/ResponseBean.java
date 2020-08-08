package com.dkt.blogboot.entity;

/**
 * @Author 窦康泰
 * @Date 2020-08-04 18:03
 */
public class ResponseBean {

    private String status;
    private String msg;

    public ResponseBean(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ResponseBean() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
