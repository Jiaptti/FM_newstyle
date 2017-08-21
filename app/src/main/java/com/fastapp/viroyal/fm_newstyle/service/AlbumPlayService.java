package com.fastapp.viroyal.fm_newstyle.service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.*;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.SeekBar;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.RxManager;
import com.fastapp.viroyal.fm_newstyle.db.RealmHelper;
import com.fastapp.viroyal.fm_newstyle.media.MediaPlayerManager;
import com.fastapp.viroyal.fm_newstyle.model.realm.TracksBeanRealm;

import java.io.IOException;

/**
 * Created by hanjiaqi on 2017/7/19.
 */

public class AlbumPlayService extends Service implements MediaPlayerManager.PlayCompleteListener{
    private PlayBinder playBinder;
    private RealmHelper realmHelper;
    private RxManager manager = new RxManager();
    private MediaPlayerManager playerManager;

    @Override
    public void onCreate() {
        super.onCreate();
        playBinder = new PlayBinder();
        realmHelper = AppContext.getRealmHelper();
        playerManager = MediaPlayerManager.newInstance();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return playBinder;
    }

    @Override
    public void playMusicComplete() {
    }

    public class PlayBinder extends Binder {
        public String playUrl;

        public void playMedia(String url) {
            if (playUrl == null || !playUrl.equals(url)) {
                playerManager.playFM(url);
                playerManager.setPlayerCompleteListener(AlbumPlayService.this);
                this.playUrl = url;
            }  else {
                playerManager.resumeMediaPlayer();
            }
            manager.post(AppConstant.UPDATE_ITEM_STATUS, realmHelper.getNowPlayingTrack());
        }

        public void resumePlay(){
            playerManager.resumeMediaPlayer();
            manager.post(AppConstant.UPDATE_ITEM_STATUS, realmHelper.getNowPlayingTrack());
        }

        public void setPlayBufferingUpdateListener(MediaPlayerManager.PlayBufferingUpdate listener){
            playerManager.setPlayBufferingUpdateListener(listener);
        }

        public void setTimeListener(MediaPlayerManager.PlayTimeChangeListener listener){
            playerManager.setPlayTimeChangeListener(listener);
        }

        public void pauseMedia() {
            playerManager.pauseMediaPlayer();
            manager.post(AppConstant.UPDATE_ITEM_STATUS, realmHelper.getNowPlayingTrack());
        }

        public void stopMedia() {
            playerManager.stopMediaPlayer();
        }

        public int getDuration() {
            return playerManager.getDuration();
        }

        public int getCurrentPosition(){
            return playerManager.getCurrentPosition();
        }

        public boolean isPlaying() {
            return playerManager.isPlaying();
        }

        public void seekTo(int time){
            playerManager.seekTo(time);
        }
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        playerManager.onDestroy();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        playerManager.onDestroy();
    }
}
