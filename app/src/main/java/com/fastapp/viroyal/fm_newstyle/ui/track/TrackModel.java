package com.fastapp.viroyal.fm_newstyle.ui.track;


import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.api.Api;
import com.fastapp.viroyal.fm_newstyle.model.base.Data;
import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.TrackInfoBean;
import com.fastapp.viroyal.fm_newstyle.model.realm.NowPlayTrack;

import rx.Observable;

/**
 * Created by hanjiaqi on 2017/8/3.
 */

public class TrackModel implements TrackContract.Model {
    @Override
    public Observable<TrackInfoBean> getTrackInfoBean(int trackId) {
        return Api.getInstance().getApiService().getTrackInfo(trackId);
    }

    @Override
    public Observable<Data<HimalayanBean>> getAlbumList(int albumId, int pageSize) {
        return Api.getInstance().getApiService().getAlbumsList(albumId, pageSize);
    }

    @Override
    public NowPlayTrack getNowPlayTrack() {
        return AppContext.getRealmHelper().getNowPlayingTrack();
    }
}
