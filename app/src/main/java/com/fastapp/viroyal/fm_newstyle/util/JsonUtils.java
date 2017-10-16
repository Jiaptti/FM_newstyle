package com.fastapp.viroyal.fm_newstyle.util;

import com.fastapp.viroyal.fm_newstyle.model.cache.PlayListCache;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksBeanList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hanjiaqi on 2017/9/15.
 */

public class JsonUtils {
    public static void saveData(List<TracksBeanList> value,int maxCount,int maxPage, int pageId) {
        Gson gson = new Gson();
        Map<Integer, PlayListCache> map = getPlayListCacheMap(gson);
        PlayListCache playListCache = new PlayListCache();
        int albumId = value.get(0).getAlbumId();
        if(map.get(albumId) != null){
            map.remove(albumId);
        }
        playListCache.setMaxCount(maxCount);
        playListCache.setMaxPage(maxPage);
        playListCache.setPageId(pageId);
        playListCache.setData(value);
        map.put(albumId, playListCache);
        FileUtils.saveData(gson.toJson(map));
    }

    public static Map<Integer, PlayListCache> getPlayListCacheMap(Gson gson){
        String data = FileUtils.readData();
        Map<Integer, PlayListCache> map = gson.fromJson(data, new TypeToken<Map<Integer, PlayListCache>>(){}.getType());
        if(map == null){
            map = new HashMap<>();
            return map;
        }
        return map;
    }

    public static PlayListCache loadData(int albumId) {
        Gson gson = new Gson();
        Map<Integer, PlayListCache> map = getPlayListCacheMap(gson);
        return map.get(albumId);
    }
}
