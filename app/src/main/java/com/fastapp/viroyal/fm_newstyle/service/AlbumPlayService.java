package com.fastapp.viroyal.fm_newstyle.service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.*;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.RxManager;
import com.fastapp.viroyal.fm_newstyle.db.RealmHelper;
import com.fastapp.viroyal.fm_newstyle.model.realm.TracksBeanRealm;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hanjiaqi on 2017/7/19.
 */

public class AlbumPlayService extends Service implements OnPreparedListener, OnBufferingUpdateListener, OnCompletionListener {
    private PlayBinder playBinder;

    private MediaPlayer player;
    private static ExecutorService executorService = Executors.newCachedThreadPool();
    private RealmHelper realmHelper;
    private RxManager manager = new RxManager();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            player.start();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        playBinder = new PlayBinder();
        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnCompletionListener(this);
        player.setOnPreparedListener(this);
        player.setOnBufferingUpdateListener(this);
        realmHelper = new RealmHelper(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return playBinder;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.i(AppConstant.TAG, "onPrepared");
        mediaPlayer.start();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int percent) {
        Log.i(AppConstant.TAG, "percent = " + percent);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.i(AppConstant.TAG, "onCompletion");
    }

    public class PlayBinder extends Binder {
        public String playUrl;

        public void setUrl(String url) {
            try {
                if (playUrl == null || !playUrl.equals(url)) {
                    this.playUrl = url;
                    player.reset();
                    player.setDataSource(url);
                    player.prepare();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void playMedia() {
            manager.post(AppConstant.UPDATE_ITEM_STATUS, null);
            executorService.execute(new PlayThread());
        }

        public void playNext() {
            try {
                player.reset();
                TracksBeanRealm entity = realmHelper.getNextTracks(playUrl);
                if (entity == null) {
                    stopMedia();
                    AppContext.toastShort(R.string.has_no_next);
                    return;
                }
                player.setDataSource(entity.getPlayUrl32());
                this.playUrl = entity.getPlayUrl32();
                player.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void playPrev() {
            try {
                player.reset();
                TracksBeanRealm entity = realmHelper.getPreTracks(playUrl);
                if (entity == null) {
                    stopMedia();
                    AppContext.toastShort(R.string.has_no_prev);
                    return;
                }
                player.setDataSource(entity.getPlayUrl32());
                this.playUrl = entity.getPlayUrl32();
                player.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void pauseMedia() {
            if (player != null && player.isPlaying()) {
                player.pause();
                manager.post(AppConstant.UPDATE_ITEM_STATUS, null);
            }
        }

        public void stopMedia() {
            if (player != null) {
                player.stop();
                player.release();
            }
        }

        public int getDuration() {
            if (player != null) {
                return player.getDuration();
            }
            return 0;
        }

        public int getPlayPosition() {
            if (player != null) {
                return player.getCurrentPosition();
            }
            return 0;
        }

        public void seekTo(int position) {
            if (player != null) {
                player.seekTo(position);
            }
        }

        public boolean isPlaying() {
            if (player != null) {
                return player.isPlaying();
            }
            return false;
        }
    }

    class PlayThread extends Thread {
        @Override
        public void run() {
            super.run();
            if (player != null && !player.isPlaying()) {
                try {
                    Thread.sleep(1000);
                    mHandler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!AppContext.checkNet()) {
                    return;
                }
            }
        }
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
    }
}
