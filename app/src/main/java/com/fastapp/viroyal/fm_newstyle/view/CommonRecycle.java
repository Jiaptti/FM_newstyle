package com.fastapp.viroyal.fm_newstyle.view;

import android.content.Context;

import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.view.layout.TRecyclerView;

/**
 * Created by hanjiaqi on 2017/9/6.
 */

public class CommonRecycle {
    private static CommonRecycle commonRecycle = null;
    private TRecyclerView tRecyclerView;

    private CommonRecycle(Context context) {
        tRecyclerView = new TRecyclerView(context);
    }

    public static CommonRecycle getInstance(Context context) {
        if (commonRecycle == null) {
            synchronized (CommonRecycle.class) {
                if (commonRecycle == null) {
                    commonRecycle = new CommonRecycle(context);
                }
            }
        }
        return commonRecycle;
    }


    public TRecyclerView getTRecycleView(){
        return this.tRecyclerView;
    }
}
