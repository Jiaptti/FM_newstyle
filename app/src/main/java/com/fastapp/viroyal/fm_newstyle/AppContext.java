package com.fastapp.viroyal.fm_newstyle;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.*;
import android.content.res.Resources;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fastapp.viroyal.fm_newstyle.db.RealmHelper;
import com.fastapp.viroyal.fm_newstyle.service.AlbumPlayService;
import com.fastapp.viroyal.fm_newstyle.util.NetWorkUtils;

/**
 * Created by hanjiaqi on 2017/6/27.
 */

public class AppContext extends Application{
    private static AppContext mApp;
    private static long sLastToastTime;
    private static String sLastToast = "";
    private static int playState = AppConstant.STATUS_NONE;
    private static AlbumPlayService.PlayBinder mBinder;

    public static int getTempPageId() {
        return tempPageId;
    }

    public static void setTempPageId(int tempPageId) {
        AppContext.tempPageId = tempPageId;
    }

    private static int tempPageId = 1;

    public static boolean isFromWindow() {
        return fromWindow;
    }

    public static void setFromWindow(boolean fromWindow) {
        AppContext.fromWindow = fromWindow;
    }

    private static boolean fromWindow = false;

    private static boolean sIsAtLeastGB;

    private boolean isBound;

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            sIsAtLeastGB = true;
        }
    }

    public static AppContext getInstance() {
        return mApp;
    }

    public static void setAutoCacheMode(boolean isChecked) {
        Editor editor = getPersistPreferences().edit();
        editor.putBoolean(AppConstant.KEY_MODE_AUTO_CACHE, isChecked);
        apply(editor);
    }

    public boolean isAutoCacheMode() {
        return getPersistPreferences().getBoolean(AppConstant.KEY_MODE_AUTO_CACHE, false);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        checkNet();
        bindMediaService();
    }

    public static SharedPreferences getPersistPreferences() {
        return mApp.getSharedPreferences(AppConstant.PREF_NAME, Context.MODE_PRIVATE);
    }

    public static Editor getEditor(){
        return getPersistPreferences().edit();
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void apply(Editor editor) {
        if (sIsAtLeastGB) {
            editor.apply();
        } else {
            editor.commit();
        }
    }

    public static AlbumPlayService.PlayBinder getMediaPlayService(){
        if(mBinder != null){
            return mBinder;
        }
        return null;
    }

    public void bindMediaService(){
        isBound = bindService(new Intent(mApp, AlbumPlayService.class), connection, Context.BIND_AUTO_CREATE);
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

    public void unBindMediaService(){
        if(isBound){
            unbindService(connection);
            isBound = false;
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
