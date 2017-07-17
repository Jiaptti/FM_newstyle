package com.fastapp.viroyal.fm_newstyle.data.base;

import rx.Observable;

/**
 * Created by hanjiaqi on 2017/6/29.
 */

public interface IListBean {
    Observable getPageAt(int categoryId, int pageId, int pageSize);
}
