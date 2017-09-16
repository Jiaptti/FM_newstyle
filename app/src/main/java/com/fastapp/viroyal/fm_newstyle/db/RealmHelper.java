package com.fastapp.viroyal.fm_newstyle.db;

import android.content.Context;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksBeanList;
import com.fastapp.viroyal.fm_newstyle.model.realm.NowPlayTrack;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by hanjiaqi on 2017/7/24.
 */

public class RealmHelper {
    private Realm mRealm;
    private static RealmHelper instance ;

    private RealmHelper(Context context){
        mRealm = Realm.getInstance(new RealmConfiguration.Builder(context).deleteRealmIfMigrationNeeded().name(AppConstant.DB_NAME).build());
    }

    public static RealmHelper getRealmHelper(Context context){
        if(instance == null){
            synchronized (RealmHelper.class){
                if(instance == null){
                    instance = new RealmHelper(context);
                }
            }
        }
        return instance;
    }

    public void removeNowPlayTrack(){
        mRealm.beginTransaction();
        mRealm.clear(NowPlayTrack.class);
        mRealm.commitTransaction();
    }

    public void setNowTrack(NowPlayTrack playTrack){
        removeNowPlayTrack();
        mRealm.beginTransaction();
        mRealm.copyToRealm(playTrack);
        mRealm.commitTransaction();
    }

    public NowPlayTrack isNowTrack(String url){
        NowPlayTrack nowPlayTrack = mRealm.where(NowPlayTrack.class).equalTo("playUrl32", url).findFirst();
        return nowPlayTrack;
    }

    public NowPlayTrack getNowPlayingTrack(){
        NowPlayTrack nowPlayTracks = mRealm.where(NowPlayTrack.class).findFirst();
        return nowPlayTracks;
    }

    public void setNowPlayTrack(TracksBeanList entity){
        NowPlayTrack playTrack = null;
        if(isNowTrack(entity.getPlayUrl32()) == null){
            playTrack = new NowPlayTrack();
            playTrack.setTrackId(entity.getTrackId());
            playTrack.setTitle(entity.getTitle());
            playTrack.setTrackId(entity.getTrackId());
            playTrack.setDuration(entity.getDuration());
            playTrack.setAlbumId(entity.getAlbumId());
            playTrack.setFromTrack(entity.getFromTrack());
            playTrack.setAlbumTitle(entity.getAlbumTitle());
            if(entity.getCoverLarge() != null){
                playTrack.setCoverLarge(entity.getCoverLarge());
            } else if(entity.getCoverMiddle() != null){
                playTrack.setCoverMiddle(entity.getCoverMiddle());
            }
            playTrack.setCoverSmall(entity.getCoverSmall());
            playTrack.setCreatedAt(entity.getCreatedAt());
            playTrack.setNickname(entity.getNickname());
            playTrack.setDuration(entity.getDuration());
            if(entity.getPlayUrl32() == null){
                playTrack.setPlayUrl32(entity.getPlayPath32());
            } else {
                playTrack.setPlayUrl32(entity.getPlayUrl32());
            }
            playTrack.setTrackId(entity.getTrackId());
            if(entity.getPlaytimes() == 0){
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
}
