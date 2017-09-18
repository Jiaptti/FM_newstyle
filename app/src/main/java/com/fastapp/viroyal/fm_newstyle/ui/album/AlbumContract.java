package com.fastapp.viroyal.fm_newstyle.ui.album;

import com.fastapp.viroyal.fm_newstyle.base.BaseModel;
import com.fastapp.viroyal.fm_newstyle.base.BasePresenter;
import com.fastapp.viroyal.fm_newstyle.base.BaseView;
import com.fastapp.viroyal.fm_newstyle.model.base.Data;
import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanBean;

import rx.Observable;

/**
 * Created by hanjiaqi on 2017/7/3.
 */

public interface AlbumContract {
    interface Model extends BaseModel{
    }

    interface View extends BaseView{
    }

    abstract class Presenter extends BasePresenter<View, Model>{
        @Override
        protected void onStart() {}
    }
}
