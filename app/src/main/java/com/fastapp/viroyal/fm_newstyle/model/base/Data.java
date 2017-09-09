package com.fastapp.viroyal.fm_newstyle.model.base;

import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksBean;

import java.util.List;

/**
 * Created by hanjiaqi on 2017/7/6.
 */

public class Data<T> {
    private List<T> list;
    public int ret;
    private HimalayanBean data;
    private String msg;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public HimalayanBean getData() {
        return data;
    }

    public void setData(HimalayanBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
