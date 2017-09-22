package com.fastapp.viroyal.fm_newstyle.view.fragment.recent;

import com.fastapp.viroyal.fm_newstyle.base.BaseModel;
import com.fastapp.viroyal.fm_newstyle.base.BasePresenter;
import com.fastapp.viroyal.fm_newstyle.base.BaseView;
import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanEntity;
import com.fastapp.viroyal.fm_newstyle.model.realm.RecentListen;

import java.util.List;

/**
 * Created by hanjiaqi on 2017/9/21.
 */

public interface RecentContract {
    interface View extends BaseView{
        void showRecentCategory(List<HimalayanEntity> data);
    }

    interface Model extends BaseModel{
        List<RecentListen> getRecentTrackList();
    }

    abstract class Presenter extends BasePresenter<View, Model>{
        abstract void getRecentList();
    }
}
