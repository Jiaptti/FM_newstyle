package com.fastapp.viroyal.fm_newstyle.model.entity;

import java.util.List;

/**
 * Created by hanjiaqi on 2017/7/6.
 */

public class TracksBean {
    private int pageId;
    private int pageSize;
    private int maxPageId;
    private int totalCount;
    private List<TracksBeanList> list;

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getMaxPageId() {
        return maxPageId;
    }

    public void setMaxPageId(int maxPageId) {
        this.maxPageId = maxPageId;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<TracksBeanList> getList() {
        return list;
    }

    public void setList(List<TracksBeanList> list) {
        this.list = list;
    }
}
