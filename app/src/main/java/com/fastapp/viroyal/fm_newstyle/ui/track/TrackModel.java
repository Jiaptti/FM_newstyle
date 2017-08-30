package com.fastapp.viroyal.fm_newstyle.ui.track;


import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.api.Api;
import com.fastapp.viroyal.fm_newstyle.model.base.Data;
import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.TrackInfoBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksBeanList;
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
    public Observable<TrackInfoBean> getTrackInfoBean(int trackId) {
        return Api.getInstance().getApiService().getTrackInfo(trackId);
    }

    @Override
    public Observable<Data<HimalayanBean>> getAlbumList(int albumId, int pageSize) {
        return Api.getInstance().getApiService().getAlbumsList(albumId, pageSize);
    }

    @Override
    public List<TracksBeanList> getTracksList(){
        List<TracksBeanRealm> tracksBeanRealms = AppContext.getRealmHelper().getAllTracks();
        List<TracksBeanList> trackList = new ArrayList<>();
        TracksBeanList bean = null;
        for(TracksBeanRealm entity : tracksBeanRealms){
            bean = new TracksBeanList();
            bean.setAlbumId(entity.getAlbumId());
            bean.setTitle(entity.getTitle());
            bean.setCoverSmall(entity.getCoverSmall());
            bean.setCoverLarge(entity.getCoverLarge());
            bean.setCoverMiddle(entity.getCoverMiddle());
            bean.setCreatedAt(entity.getCreatedAt());
            bean.setNickname(entity.getNickname());
            bean.setDuration(entity.getDuration());
            bean.setPlayUrl32(entity.getPlayUrl32());
            bean.setTrackId(entity.getTrackId());
            bean.setPlaytimes(entity.getPlaytimes());
            bean.setPlayPathHq(entity.getPlayPathHq());
            bean.setPlayUrl64(entity.getPlayUrl64());
            bean.setPlayPathAacv164(entity.getPlayPathAacv164());
            bean.setPlayPathAacv224(entity.getPlayPathAacv224());
            trackList.add(bean);
        }
        return trackList;
    }

    @Override
    public NowPlayTrack getNowPlayTrack() {
        return AppContext.getRealmHelper().getNowPlayingTrack();
    }
}
