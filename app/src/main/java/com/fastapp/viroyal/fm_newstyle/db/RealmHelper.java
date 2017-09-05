package com.fastapp.viroyal.fm_newstyle.db;

import android.content.Context;
import android.util.Log;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksBeanList;
import com.fastapp.viroyal.fm_newstyle.model.realm.NowPlayTrack;
import com.fastapp.viroyal.fm_newstyle.model.realm.TracksBeanRealm;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

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


//    public TracksBeanRealm getNextTracks(String url){
//        RealmResults<TracksBeanRealm> results = mRealm.where(TracksBeanRealm.class).findAll();
//        List<TracksBeanRealm> list = mRealm.copyToRealm(results);
//        int position = list.indexOf(getTracksRealm(url));
//        if(list.get(position + 1) != null){
//            return list.get(position + 1);
//        }
//        return null;
//    }
//
//    public TracksBeanRealm getPreTracks(String url){
//        RealmResults<TracksBeanRealm> results = mRealm.where(TracksBeanRealm.class).findAll();
//        List<TracksBeanRealm> list = mRealm.copyToRealm(results);
//        int position = list.indexOf(getTracksRealm(url));
//        if(list.get(position - 1) != null){
//            return list.get(position - 1);
//        }
//        return null;
//    }
//

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
            playTrack.setTitle(entity.getTitle());
            playTrack.setTrackId(entity.getTrackId());
            playTrack.setDuration(entity.getDuration());
            playTrack.setAlbumId(entity.getAlbumId());
            playTrack.setCoverSmall(entity.getCoverSmall());
            playTrack.setCoverLarge(entity.getCoverLarge());
            playTrack.setCoverMiddle(entity.getCoverMiddle());
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
            playTrack.setPosition(entity.getPosition());
            setNowTrack(playTrack);
        }
    }
}
