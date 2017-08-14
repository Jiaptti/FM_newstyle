package com.fastapp.viroyal.fm_newstyle.media;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.*;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.base.RxManager;
import com.fastapp.viroyal.fm_newstyle.db.RealmHelper;

import java.io.IOException;

/**
 * Created by hanjiaqi on 2017/7/31.
 */

public class MediaPlayerManager implements OnCompletionListener, OnErrorListener, OnBufferingUpdateListener, OnPreparedListener {
    private HandlerThread playHandlerThread;
    private Handler playHandler;
    private Handler updateHandler;
    private Handler timeChangeHandler;
    private String url;
    private int position;
    private MediaPlayer mediaPlayer;
    private PlayCompleteListener listener;
    private PlayTimeChangeListener timeListener;

    private static class Singleton {
        public static MediaPlayerManager media = new MediaPlayerManager();
    }

    private MediaPlayerManager() {
        updateHandler = new Handler(Looper.getMainLooper());
        initHandler();
        initPlayer();
    }

    private void playMediaPlayer() {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(url);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setLooping(false);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pauseFM() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            position = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
            playHandler.removeCallbacks(TimeChangeRunnable);
        }
    }

    private void resumePlayFM() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(position);
            mediaPlayer.start();
//            playHandler.postAtTime(TimeChangeRunnable, 1000);
        }
    }

    private void stopFM() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
        listener = null;
        timeListener = null;
        if(playHandler != null && TimeChangeRunnable != null){
            playHandler.removeCallbacks(TimeChangeRunnable);
            playHandler.removeCallbacksAndMessages(null);
        }
        if(updateHandler != null){
            updateHandler.removeCallbacksAndMessages(null);
        }
    }

    public void onDestroy(){
        stopFM();
        if (mediaPlayer != null) {
            mediaPlayer.setOnPreparedListener(null);
            mediaPlayer.setOnCompletionListener(null);
            mediaPlayer.release();
            mediaPlayer = null;
            position = 0;
        }
    }

    private void initPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnBufferingUpdateListener(this);
        }
    }

    public static MediaPlayerManager newInstance() {
        return Singleton.media;
    }

    public void playFM(String url) {
        this.url = url;
        playHandler.sendEmptyMessage(AppConstant.STATUS_PLAY);
//        playHandler.postAtTime(TimeChangeRunnable, 1000);
        AppContext.setPlayState(AppConstant.STATUS_PLAY);
    }

    public Runnable TimeChangeRunnable = new Runnable() {
        @Override
        public void run() {
            playHandler.sendEmptyMessage(AppConstant.STATUS_UPDATE_TIME);
        }
    };

    public void setPlayerCompleteListener(PlayCompleteListener listener){
        this.listener = listener;
    }

    public void setPlayTimeChangeListener(PlayTimeChangeListener timeListener){
        this.timeListener = timeListener;
    }

    public void stopMediaPlayer() {
        playHandler.sendEmptyMessage(AppConstant.STATUS_STOP);
        AppContext.setPlayState(AppConstant.STATUS_STOP);
    }

    public void pauseMediaPlayer() {
        playHandler.sendEmptyMessage(AppConstant.STATUS_PAUSE);
        AppContext.setPlayState(AppConstant.STATUS_PAUSE);
    }

    public void resumeMediaPlayer() {
        playHandler.sendEmptyMessage(AppConstant.STATUS_RESUME);
        AppContext.setPlayState(AppConstant.STATUS_RESUME);
    }

    public int getDuration(){
        if(mediaPlayer != null){
            return mediaPlayer.getDuration();
        }
        return 0;
    }

    public boolean isPlaying(){
        if(mediaPlayer != null){
            return mediaPlayer.isPlaying();
        }
        return false;
    }


    public interface PlayCompleteListener {
        void playMusicComplete();
    }

    public interface PlayTimeChangeListener{
        void playTimeChange(int time);
    }


    private void initHandler() {
        if (playHandlerThread == null) {
            playHandlerThread = new HandlerThread("playHandlerThread");
            playHandlerThread.start();
        }
        if (playHandler == null) {
            playHandler = new Handler(playHandlerThread.getLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case AppConstant.STATUS_PLAY:
                            playMediaPlayer();
                            break;
                        case AppConstant.STATUS_PAUSE:
                            pauseFM();
                            break;
                        case AppConstant.STATUS_STOP:
                            stopFM();
                            break;
                        case AppConstant.STATUS_RESUME:
                            resumePlayFM();
                            break;
                        case AppConstant.STATUS_UPDATE_TIME:
                            if(timeListener != null)
                                timeListener.playTimeChange(mediaPlayer.getCurrentPosition());
                            break;
                    }
                }
            };
        }
    }


    private void playFMComplete() {
        updateHandler.post(new Runnable() {
            @Override
            public void run() {
                if (listener != null) {
                    listener.playMusicComplete();
                }
            }
        });
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        playFMComplete();
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }
}
