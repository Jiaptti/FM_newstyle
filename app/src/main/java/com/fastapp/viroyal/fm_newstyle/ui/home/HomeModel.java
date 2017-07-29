package com.fastapp.viroyal.fm_newstyle.ui.home;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseListFragment;
import com.fastapp.viroyal.fm_newstyle.util.CommonUtils;
import com.fastapp.viroyal.fm_newstyle.util.RxSchedulers;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.CategoryVH;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * Created by hanjiaqi on 2017/6/26.
 */

public class HomeModel implements HomeContract.Model{

    @Override
    public Observable<String> getTabFragment() {
        return Observable.from(CommonUtils.getAllTabs()).compose(RxSchedulers.<String>io_main());
    }
}
