package com.fastapp.viroyal.fm_newstyle.ui.track;


import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.api.Api;
import com.fastapp.viroyal.fm_newstyle.model.base.Data;
import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.TrackInfoBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksBeanList;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksInfo;
import com.fastapp.viroyal.fm_newstyle.model.realm.NowPlayTrack;
import com.fastapp.viroyal.fm_newstyle.model.realm.TracksBeanRealm;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by hanjiaqi on 2017/8/3.
 */

public class TrackModel implements TrackContract.Model {


    @Override
    public NowPlayTrack getNowPlayTrack() {
        return AppContext.getRealmHelper().getNowPlayingTrack();
    }

    @Override
    public Observable<TracksInfo> getTracksInfo(int trackId) {
        return Api.getInstance().getApiService().getTracksInfo(trackId);
    }
}
