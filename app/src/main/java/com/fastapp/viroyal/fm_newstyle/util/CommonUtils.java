package com.fastapp.viroyal.fm_newstyle.util;

import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;

/**
 * Created by hanjiaqi on 2017/7/14.
 */

public class CommonUtils {
    public static String getOmitPlayCounts(int playCounts){
        if(playCounts > 0 && playCounts < 10000){
            return playCounts + "";
        } else if(playCounts > 10000 && playCounts < 100000000){
            int tempCounts = playCounts / 10000;
            if(((playCounts - tempCounts * 10000) / 1000) == 0){
                return tempCounts + AppContext.getStringById(R.string.ten_thousand);
            }
            return (tempCounts + "." + ((playCounts - tempCounts * 10000) / 1000))+ AppContext.getStringById(R.string.ten_thousand);
        } else {
            return playCounts / 100000000 + AppContext.getStringById(R.string.ten_thousand);
        }
    }

    public static String getOmitAlbumCounts(int playCounts){
        if(playCounts > 0 && playCounts < 10000){
            return playCounts + "";
        } else if(playCounts > 10000 && playCounts < 100000000){
            int tempCounts = playCounts / 10000;
            if(((playCounts - tempCounts * 10000) / 1000) == 0){
                return tempCounts + AppContext.getStringById(R.string.ten_thousand_times);
            }
            return (tempCounts + "." + ((playCounts - tempCounts * 10000) / 1000))+ AppContext.getStringById(R.string.ten_thousand_times);
        } else {
            return playCounts / 100000000 + AppContext.getStringById(R.string.ten_thousand_times);
        }
    }
}
