package com.fastapp.viroyal.fm_newstyle.util;

import com.fastapp.viroyal.fm_newstyle.model.entity.TracksBeanList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by hanjiaqi on 2017/9/15.
 */

public class JsonUtils {
    public static void createJson(Object value) {
        Gson gson = new Gson();
        FileUtils.saveData(gson.toJson(value));
    }

    public static List<TracksBeanList> getListJson() {
        Gson gson = new Gson();
        String data = FileUtils.readData();
        return gson.fromJson(data, new TypeToken<List<TracksBeanList>>(){}.getType());
    }

    public static void cleanData(){
        FileUtils.removeData();
    }

}
