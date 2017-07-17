package com.fastapp.viroyal.fm_newstyle.util;

import android.net.ConnectivityManager;

import com.fastapp.viroyal.fm_newstyle.AppContext;

/**
 * Created by hanjiaqi on 2017/6/29.
 */

public class NetWorkUtils {
    public static boolean hasNet(){
        return ((ConnectivityManager) AppContext.getAppContext().
                getSystemService("connectivity")).getActiveNetworkInfo() != null;
    }
}
