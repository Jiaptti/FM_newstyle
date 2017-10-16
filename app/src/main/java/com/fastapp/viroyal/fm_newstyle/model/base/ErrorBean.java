package com.fastapp.viroyal.fm_newstyle.model.base;

import com.fastapp.viroyal.fm_newstyle.base.BaseViewHolder;

/**
 * Created by hanjiaqi on 2017/8/23.
 */

public class ErrorBean {
    private Class<? extends BaseViewHolder> clazz;
    private int statusCode;
    private int code;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
