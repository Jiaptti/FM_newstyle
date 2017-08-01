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
    private String url;
    private int position;
    private MediaPlayer mediaPlayer;
    private PlayCompleteListener listener;

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
            this.position = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
        }
    }

    private void resumePlayFM() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(this.position);
            mediaPlayer.start();
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
    }

    public void onDestroy(){
        stopFM();
        if (mediaPlayer != null) {
            mediaPlayer.setOnPreparedListener(null);
            mediaPlayer.setOnCompletionListener(null);
            mediaPlayer.release();
            mediaPlayer = null;
            listener = null;
            position = 0;
            updateHandler.removeCallbacksAndMessages(null);
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

    public void playFM(String url, PlayCompleteListener listener) {
        this.url = url;
        this.listener = listener;
        playHandler.sendEmptyMessage(AppConstant.STATUS_PLAY);
        AppContext.setPlayState(AppConstant.STATUS_PLAY);
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
