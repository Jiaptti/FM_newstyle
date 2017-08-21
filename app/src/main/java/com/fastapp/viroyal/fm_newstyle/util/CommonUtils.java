package com.fastapp.viroyal.fm_newstyle.util;

import android.util.Log;
import android.widget.TextView;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;

import java.util.Calendar;
import java.util.Date;

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

    public static String getIntervalDays(long time){
        long times = System.currentTimeMillis();
        Date date = new Date(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        long intervalMilli = times - time;
        int days = (int)(intervalMilli / (24 * 60 * 60 * 1000));
        if(days < 1){
            if(calendar.get(Calendar.HOUR) < 1){
                if(calendar.get(Calendar.MINUTE) < 1){
                    return calendar.get(Calendar.SECOND) + AppContext.getStringById(R.string.seconds_ago);
                } else if(calendar.get(Calendar.MINUTE) < 60){
                    return calendar.get(Calendar.MINUTE) + AppContext.getStringById(R.string.minutes_ago);
                }
            } else {
                return calendar.get(Calendar.HOUR) + AppContext.getStringById(R.string.hours_ago);
            }
        } else if(days < 30){
            return days + AppContext.getStringById(R.string.days_ago);
        } else {
            if((calendar.get(Calendar.MONTH) + 1) < 10){
                return calendar.get(Calendar.YEAR) + "-0" + (calendar.get(Calendar.MONTH) + 1) ;
            }
            return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1);
        }
        return "";
    }

    public static String getPlayTime(int duration){
        if((duration % 60) < 10){
            return (duration / 60) + ":0" + (duration % 60);
        }
        return (duration / 60) + ":" + (duration % 60);
    }

    public static int getType(String str){
        int type = AppConstant.PAGE_CROSSTALK;
        if(str.equalsIgnoreCase(AppContext.getStringById(R.string.tabs_crosstalk_sketch))){
            type =  AppConstant.PAGE_CROSSTALK;
        } else if(str.equalsIgnoreCase(AppContext.getStringById(R.string.tabs_exquisite_article))){
            type = AppConstant.PAGE_STORY;
        } else if(str.equalsIgnoreCase(AppContext.getStringById(R.string.tabs_literati_writings))){
            type = AppConstant.PAGE_BOOK;
        }
        return type;
    }

    public static String[] getAllTabs(){
        String[] tabs = {AppContext.getStringById(R.string.tabs_crosstalk_sketch),
                AppContext.getStringById(R.string.tabs_exquisite_article),
                AppContext.getStringById(R.string.tabs_literati_writings)};
        return tabs;
    }

    public static void setTotalTime(int time, TextView totalTime){
        if(totalTime != null){
            if(time % 60 < 10){
                totalTime.setText((time / 60) + ":0" + (time % 60));
            } else {
                totalTime.setText((time / 60) + ":" + (time % 60));
            }
        }
    }

    public static void setCurrentTime(int time, TextView currentTime){
        if(currentTime != null){
            if(time < 10){
                currentTime.setText("00:0" + time);
            } else if(time < 60){
                currentTime.setText("00:" + time);
            } else if((time / 60) > 0 && (time / 60) < 10){
                if((time % 60) < 10){
                    currentTime.setText("0" + (time / 60) + ":0" + (time % 60));
                } else {
                    currentTime.setText("0" + (time / 60) + ":" + (time % 60));
                }
            } else if((time / 60) > 10){
                if((time % 60) < 10){
                    currentTime.setText((time / 60) + ":0" + (time % 60));
                } else {
                    currentTime.setText((time / 60) + ":" + (time % 60));
                }
            }
        }
    }

}
