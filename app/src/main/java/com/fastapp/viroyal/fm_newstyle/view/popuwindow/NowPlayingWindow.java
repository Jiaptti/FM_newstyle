package com.fastapp.viroyal.fm_newstyle.view.popuwindow;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.base.RxManager;
import com.fastapp.viroyal.fm_newstyle.model.realm.NowPlayTrack;
import com.fastapp.viroyal.fm_newstyle.view.layout.NowPlayingLayout;

import rx.functions.Action1;

/**
 * Created by hanjiaqi on 2017/7/25.
 */

public class NowPlayingWindow {
    private static NowPlayingWindow mInstance;
    private static WindowManager winManager;
    private static Context context;
    private WindowManager.LayoutParams params;
    private NowPlayingLayout mPlayingView;

    private NowPlayingWindow(){}

    public static synchronized NowPlayingWindow getPlayingWindow(Context context){
        if(mInstance == null){
            NowPlayingWindow.context = context;
            winManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            mInstance = new NowPlayingWindow();
        }
        return mInstance;
    }

    public void dismiss() {
        winManager.removeView(mPlayingView);
        mPlayingView = null;
    }

    public void show() {
        mPlayingView = getView();
        if (mPlayingView.getParent() == null) {
            winManager.addView(mPlayingView, params);
        }
    }

    public NowPlayingLayout getView() {
        if (mPlayingView == null) {
            mPlayingView = new NowPlayingLayout(context);
        }
        if (params == null) {
            params = new WindowManager.LayoutParams();
            params.type = WindowManager.LayoutParams.TYPE_PHONE;
            params.format = PixelFormat.RGBA_8888;
            params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            params.gravity = Gravity.BOTTOM | Gravity.CENTER;
            params.width = mPlayingView.mWidth;
            params.height = mPlayingView.mHeight;
        }
        return mPlayingView;
    }
}
