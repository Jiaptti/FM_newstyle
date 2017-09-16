package com.fastapp.viroyal.fm_newstyle.service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.base.RxManager;
import com.fastapp.viroyal.fm_newstyle.media.MediaPlayerManager;

/**
 * Created by hanjiaqi on 2017/7/19.
 */

public class AlbumPlayService extends Service{
    private PlayBinder playBinder;
    private RxManager manager = new RxManager();
    private MediaPlayerManager playerManager;

    @Override
    public void onCreate() {
        super.onCreate();
        playBinder = new PlayBinder();
        playerManager = MediaPlayerManager.newInstance();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return playBinder;
    }

    public class PlayBinder extends Binder {
        public String playUrl;

        public void playMedia(String url) {
            if (playUrl == null || !playUrl.equals(url)) {
                playerManager.playFM(url);
                this.playUrl = url;
            }  else {
                if(!playerManager.isPlaying())
                    playerManager.resumeMediaPlayer();
            }
            manager.post(AppConstant.UPDATE_ITEM_STATUS, AppConstant.STATUS_PLAY);
        }

        public void resumePlay(){
            playerManager.resumeMediaPlayer();
        }

        public void setPlayBufferingUpdateListener(MediaPlayerManager.PlayBufferingUpdate listener){
            playerManager.setPlayBufferingUpdateListener(listener);
        }

        public void setTimeListener(MediaPlayerManager.PlayTimeChangeListener listener){
            playerManager.setPlayTimeChangeListener(listener);
        }

        public void pauseMedia() {
            playerManager.pauseMediaPlayer();
            manager.post(AppConstant.UPDATE_ITEM_STATUS, AppConstant.STATUS_PAUSE);
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

        public void setPlayCompleteListener(MediaPlayerManager.PlayCompleteListener listener){
            playerManager.setPlayerCompleteListener(listener);
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
