package com.fastapp.viroyal.fm_newstyle;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fastapp.viroyal.fm_newstyle.db.RealmHelper;
import com.fastapp.viroyal.fm_newstyle.model.base.Data;
import com.fastapp.viroyal.fm_newstyle.service.AlbumPlayService;
import com.fastapp.viroyal.fm_newstyle.util.NetWorkUtils;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

/**
 * Created by hanjiaqi on 2017/6/27.
 */

public class AppContext extends Application{
    private static AppContext mApp;
    private static long sLastToastTime;
    private static String sLastToast = "";
    private static int playState = AppConstant.STATUS_NONE;
    private static AlbumPlayService.PlayBinder mBinder;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        checkNet();
        bindMediaService();
    }

    public static AlbumPlayService.PlayBinder getMediaPlayService(){
        if(mBinder != null){
            return mBinder;
        }
        return null;
    }

    private void bindMediaService(){
        bindService(new Intent(mApp, AlbumPlayService.class), connection, Context.BIND_AUTO_CREATE);
    }

    public static RealmHelper getRealmHelper(){
        return RealmHelper.getRealmHelper(mApp);
    }

    public static void setPlayState(int state){
        playState = state;
    }

    public static int getPlayState(){
        return playState;
    }

    public static String getStringById(int resId){
        return mApp.getString(resId);
    }

    public static Context getAppContext(){
        return mApp;
    }

    public static int getScreenHeight(){
        WindowManager wm = (WindowManager) mApp.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    public static boolean checkNet(){
        if(!NetWorkUtils.hasNet()){
            AppContext.toastShort(R.string.no_net);
            return false;
        }
        return true;
    }

    public static Resources getAppResources(){
        return mApp.getResources();
    }

    public static void toastShort(int message){
        toastShort(message, Toast.LENGTH_SHORT, 0);
    }

    public static void toastShort(String message){
        toast(message, Toast.LENGTH_SHORT, 0,
                Gravity.FILL_HORIZONTAL | Gravity.TOP);
    }

    public static void toastShort(int message,int duration , int icon){
        toast(getAppContext().getResources().getString(message),duration , icon , Gravity.FILL_HORIZONTAL | Gravity.TOP);
    }


    public static void toast(String message) {
        toast(message, Toast.LENGTH_LONG, 0, Gravity.FILL_HORIZONTAL
                | Gravity.TOP);
    }

    public static void toast(String message,int duration , int icon , int gravity){
        if (message != null && !message.equalsIgnoreCase("")) {
            long time = System.currentTimeMillis();
            if (!message.equalsIgnoreCase(sLastToast)
                    || Math.abs(time - sLastToastTime) > 2000) {

                View view = LayoutInflater.from(getAppContext()).inflate(
                        R.layout.view_toast, null);
                ((TextView) view.findViewById(R.id.title_tv)).setText(message);
                if (icon != 0) {
                    ((ImageView) view.findViewById(R.id.icon_iv))
                            .setImageResource(icon);
                    view.findViewById(R.id.icon_iv)
                            .setVisibility(View.VISIBLE);
                }
                Toast toast = new Toast(getAppContext());
                toast.setView(view);
                toast.setDuration(duration);
                toast.show();

                sLastToast = message;
                sLastToastTime = System.currentTimeMillis();
            }
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBinder = (AlbumPlayService.PlayBinder) service;
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };
}
