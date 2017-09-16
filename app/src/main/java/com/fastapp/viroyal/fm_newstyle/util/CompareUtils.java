package com.fastapp.viroyal.fm_newstyle.util;

import com.fastapp.viroyal.fm_newstyle.model.entity.TracksBeanList;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by hanjiaqi on 2017/9/14.
 */

public class CompareUtils implements Comparator {
    public int compare(Object obj1, Object obj2) {
        TracksBeanList t1 = (TracksBeanList) obj1;
        TracksBeanList t2 = (TracksBeanList) obj2;
        Date d1 = new Date(t1.getCreatedAt());
        Date d2 = new Date(t2.getCreatedAt());
        if (d1.before(d2)) {
            return -1;
        } else {
            return 1;
        }
    }
}
