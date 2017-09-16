package com.fastapp.viroyal.fm_newstyle.ui.track;

import com.fastapp.viroyal.fm_newstyle.base.BaseModel;
import com.fastapp.viroyal.fm_newstyle.base.BasePresenter;
import com.fastapp.viroyal.fm_newstyle.base.BaseView;
import com.fastapp.viroyal.fm_newstyle.model.base.Data;
import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.TrackInfoBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksBeanList;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksInfo;
import com.fastapp.viroyal.fm_newstyle.model.realm.NowPlayTrack;

import java.util.List;

import rx.Observable;


/**
 * Created by hanjiaqi on 2017/8/3.
 */

public interface TrackContract {
    interface View extends BaseView{
        void setNowPlayerTrack(NowPlayTrack nowPlayerTrack);
        void initTracksInfo(TracksInfo tracksInfo);
    }

    interface Model extends BaseModel{
//        Observable<Data<HimalayanBean>> getAlbumList(int albumId, int pageSize);
        NowPlayTrack getNowPlayTrack();
        Observable<TracksInfo> getTracksInfo(int trackId);
    }

    abstract class Presenter extends BasePresenter<View, Model>{
        abstract void getTracksInfo(int trackId);
        abstract void getNowTrack();
    }
}
