package com.fastapp.viroyal.fm_newstyle.data;

import com.fastapp.viroyal.fm_newstyle.AppConstant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hanjiaqi on 2017/9/7.
 */

public class TrackListCache {
    private Map<String, List> cacheData;
    private static TrackListCache trackListCache;
    private TrackListCache(){
        cacheData = new HashMap<>();
    }

    public static TrackListCache getInstance(){
        synchronized (TrackListCache.class){
            if(trackListCache == null){
                synchronized (TrackListCache.class){
                    if(trackListCache == null){
                        trackListCache = new TrackListCache();
                    }
                }
            }
        }
        return trackListCache;
    }

    public void setCacheData(List data){
        cacheData.put(AppConstant.TRACK_LIST_CACHE_DATA, data);
    }

    public void clearCache(){
        cacheData.clear();
    }

    public List getCacheData(){
        if(cacheData.get(AppConstant.TRACK_LIST_CACHE_DATA) != null){
            return cacheData.get(AppConstant.TRACK_LIST_CACHE_DATA);
        }
        return null;
    }

}
