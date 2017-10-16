package com.fastapp.viroyal.fm_newstyle.model.base;

import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksData;

import java.util.List;

/**
 * Created by hanjiaqi on 2017/7/6.
 */

public class Data<T> {
    public int ret;
    private String msg;
    private T list;
    private T data;
    private int totalCount;
    private int maxPageId;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getMaxPageId() {
        return maxPageId;
    }

    public void setMaxPageId(int maxPageId) {
        this.maxPageId = maxPageId;
    }

    public T getList() {
        return list;
    }

    public void setList(T list) {
        this.list = list;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    //    private TracksData bean;

//    public TracksData getBean() {
//        return bean;
//    }
//
//    public void setBean(TracksData bean) {
//        this.bean = bean;
//    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

//    public HimalayanBean getData() {
//        return data;
//    }
//
//    public void setData(HimalayanBean data) {
//        this.data = data;
//    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

//    public List<T> getList() {
//        return list;
//    }
//
//    public void setList(List<T> list) {
//        this.list = list;
//    }


}
