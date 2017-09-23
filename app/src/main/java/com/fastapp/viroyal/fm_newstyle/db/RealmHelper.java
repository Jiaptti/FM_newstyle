package com.fastapp.viroyal.fm_newstyle.db;

import android.content.Context;
import android.util.Log;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanEntity;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksBeanList;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksInfo;
import com.fastapp.viroyal.fm_newstyle.model.realm.CollectTrackRealm;
import com.fastapp.viroyal.fm_newstyle.model.realm.NowPlayTrack;
import com.fastapp.viroyal.fm_newstyle.model.realm.RecentListen;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.RecentListenRealmProxy;

/**
 * Created by hanjiaqi on 2017/7/24.
 */

public class RealmHelper {
    private Realm mRealm;
    private static RealmHelper instance;
    private RecentListen recent;

    private RealmHelper(Context context) {
        mRealm = Realm.getInstance(new RealmConfiguration.Builder(context).deleteRealmIfMigrationNeeded().name(AppConstant.DB_NAME).build());
    }

    public static RealmHelper getRealmHelper(Context context) {
        if (instance == null) {
            synchronized (RealmHelper.class) {
                if (instance == null) {
                    instance = new RealmHelper(context);
                }
            }
        }
        return instance;
    }

    public void updateRecentTrackId(final int albumId, final int trackId) {
        RecentListen bean = mRealm.where(RecentListen.class).equalTo("albumId", albumId).findFirst();
        if (bean != null && bean.getTrackId() != trackId) {
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RecentListen bean = realm.where(RecentListen.class).equalTo("albumId", albumId).findFirst();
                    bean.setTrackId(trackId);
                }
            });
        }
    }

    public void removeNowPlayTrack() {
        mRealm.beginTransaction();
        mRealm.clear(NowPlayTrack.class);
        mRealm.commitTransaction();
    }

    public void setNowTrack(NowPlayTrack playTrack) {
        removeNowPlayTrack();
        mRealm.beginTransaction();
        mRealm.copyToRealm(playTrack);
        mRealm.commitTransaction();
    }

    public NowPlayTrack getPlayTrack(String url) {
        NowPlayTrack nowPlayTrack = mRealm.where(NowPlayTrack.class).equalTo("playUrl32", url).findFirst();
        return nowPlayTrack;
    }

    public RecentListen getRecentListen(int albumId) {
        RecentListen recentListen = mRealm.where(RecentListen.class).equalTo("albumId", albumId).findFirst();
        return recentListen;
    }

    public NowPlayTrack getNowPlayingTrack() {
        NowPlayTrack nowPlayTracks = mRealm.where(NowPlayTrack.class).findFirst();
        return nowPlayTracks;
    }

    public List<RecentListen> getAllRecent() {
        RealmResults<RecentListen> recentList = mRealm.where(RecentListen.class).findAll();
        return mRealm.copyToRealm(recentList);
    }

    public List<CollectTrackRealm> getAllCollect() {
        RealmResults<CollectTrackRealm> collectTrackRealms = mRealm.where(CollectTrackRealm.class).findAll();
        return mRealm.copyToRealm(collectTrackRealms);
    }

    public void removeAllCollect() {
        mRealm.beginTransaction();
        mRealm.clear(CollectTrackRealm.class);
        mRealm.commitTransaction();
    }

    public void removeAllRecent() {
        mRealm.beginTransaction();
        mRealm.clear(RecentListen.class);
        mRealm.commitTransaction();
    }

    public void setNowPlayTrack(TracksBeanList entity) {
        NowPlayTrack playTrack = null;
        if (getPlayTrack(entity.getPlayUrl32()) == null) {
            playTrack = new NowPlayTrack();
            playTrack.setTrackId(entity.getTrackId());
            playTrack.setTitle(entity.getTitle());
            playTrack.setTrackId(entity.getTrackId());
            playTrack.setDuration(entity.getDuration());
            playTrack.setAlbumId(entity.getAlbumId());
            playTrack.setFromTrack(entity.getFromTrack());
            playTrack.setAlbumTitle(entity.getAlbumTitle());
            if (entity.getCoverLarge() != null) {
                playTrack.setCoverLarge(entity.getCoverLarge());
            } else if (entity.getCoverMiddle() != null) {
                playTrack.setCoverMiddle(entity.getCoverMiddle());
            }
            playTrack.setCoverSmall(entity.getCoverSmall());
            playTrack.setCreatedAt(entity.getCreatedAt());
            playTrack.setNickname(entity.getNickname());
            playTrack.setDuration(entity.getDuration());
            if (entity.getPlayUrl32() == null) {
                playTrack.setPlayUrl32(entity.getPlayPath32());
            } else {
                playTrack.setPlayUrl32(entity.getPlayUrl32());
            }
            playTrack.setTrackId(entity.getTrackId());
            if (entity.getPlaytimes() == 0) {
                playTrack.setPlaytimes(entity.getPlaysCounts());
            } else {
                playTrack.setPlaytimes(entity.getPlaytimes());
            }
            playTrack.setPlayPathHq(entity.getPlayPathHq());
            playTrack.setPlayUrl64(entity.getPlayUrl64());
            playTrack.setPlayPathAacv164(entity.getPlayPathAacv164());
            playTrack.setPlayPathAacv224(entity.getPlayPathAacv224());
            playTrack.setPosition(entity.getPosition());
            setNowTrack(playTrack);
        }
    }

    public void setNowPlayTrack(TracksInfo entity) {
        NowPlayTrack playTrack = null;
        if (getPlayTrack(entity.getPlayUrl32()) == null) {
            playTrack = new NowPlayTrack();
            playTrack.setTrackId(entity.getTrackId());
            playTrack.setTitle(entity.getTitle());
            playTrack.setTrackId(entity.getTrackId());
            playTrack.setDuration(entity.getDuration());
            playTrack.setAlbumId(entity.getAlbumId());
            playTrack.setFromTrack(false);
            playTrack.setAlbumTitle(entity.getAlbumTitle());
            playTrack.setCoverLarge(entity.getCoverLarge());
            playTrack.setCoverMiddle(entity.getCoverMiddle());
            playTrack.setCoverSmall(entity.getCoverSmall());
            playTrack.setCreatedAt(entity.getCreatedAt());
            playTrack.setNickname(entity.getNickname());
            playTrack.setDuration(entity.getDuration());
            playTrack.setPlayUrl32(entity.getPlayUrl32());
            playTrack.setTrackId(entity.getTrackId());
            playTrack.setPlaytimes(entity.getPlaytimes());
            playTrack.setPlayPathHq(entity.getPlayPathHq());
            playTrack.setPlayUrl64(entity.getPlayUrl64());
            playTrack.setPlayPathAacv164(entity.getPlayPathAacv164());
            playTrack.setPlayPathAacv224(entity.getPlayPathAacv224());
            playTrack.setIntro(entity.getIntro());
            playTrack.setPosition(0);
            setNowTrack(playTrack);
        }
    }

    public void setRecentListen(HimalayanEntity entity) {
        recent = new RecentListen();
        recent.setCoverSmall(entity.getCoverSmall());
        recent.setTitle(entity.getTitle());
        recent.setIntro(entity.getIntro());
        recent.setPlaysCounts(entity.getPlaysCounts());
        recent.setAlbumId(entity.getAlbumId());
        recent.setTrackId(entity.getTrackId());
        recent.setTracks(entity.getTracks());
    }

    public void addRecentListen(int albumId) {
        if (getRecentListen(albumId) == null) {
            mRealm.beginTransaction();
            mRealm.copyToRealm(recent);
            mRealm.commitTransaction();
        }
    }
}
