package com.fastapp.viroyal.fm_newstyle.model.cache;

import android.os.Parcelable;

import com.fastapp.viroyal.fm_newstyle.model.entity.TracksBeanList;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hanjiaqi on 2017/9/26.
 */

public class PlayListCache implements Serializable {
    private int pageId;
    private int maxPage;
    private int maxCount;
    private List<TracksBeanList> data;

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public List<TracksBeanList> getData() {
        return data;
    }

    public void setData(List<TracksBeanList> data) {
        this.data = data;
    }
}
