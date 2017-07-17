package com.fastapp.viroyal.fm_newstyle;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import com.fastapp.viroyal.fm_newstyle.util.NetWorkUtils;

/**
 * Created by hanjiaqi on 2017/6/27.
 */

public class AppContext extends Application{
    private static AppContext mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        checkNet();
    }

    public static String getStringById(int resId){
        return mApp.getString(resId);
    }

    public static Context getAppContext(){
        return mApp;
    }

    private void checkNet(){
        if(!NetWorkUtils.hasNet()){
            Toast.makeText(this,AppContext.getStringById(R.string.no_net), Toast.LENGTH_SHORT).show();
        }
    }

    public static Resources getAppResources(){
        return mApp.getResources();
    }
}
