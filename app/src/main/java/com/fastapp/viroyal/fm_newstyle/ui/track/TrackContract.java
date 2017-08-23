package com.fastapp.viroyal.fm_newstyle.ui.track;

import com.fastapp.viroyal.fm_newstyle.base.BaseModel;
import com.fastapp.viroyal.fm_newstyle.base.BasePresenter;
import com.fastapp.viroyal.fm_newstyle.base.BaseView;
import com.fastapp.viroyal.fm_newstyle.model.base.Data;
import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.TrackInfoBean;
import com.fastapp.viroyal.fm_newstyle.model.realm.NowPlayTrack;

import java.util.List;

import rx.Observable;


/**
 * Created by hanjiaqi on 2017/8/3.
 */

public interface TrackContract {
    interface View extends BaseView{
        void setNowPlayerMessage(TrackInfoBean trackInfoBean);
        void loadAlbumList(Data<HimalayanBean> list);
        void setNowPlayerTrack(NowPlayTrack nowPlayerTrack);
    }

    interface Model extends BaseModel{
        Observable<TrackInfoBean> getTrackInfoBean(int trackId);
        Observable<Data<HimalayanBean>> getAlbumList(int albumId, int pageSize);
        NowPlayTrack getNowPlayTrack();
    }

    abstract class Presenter extends BasePresenter<View, Model>{
        abstract void getTrack(int trackId);
        abstract void getAlumList(int albumId, int pageSize);
        abstract void getNowTrack();
    }
}
