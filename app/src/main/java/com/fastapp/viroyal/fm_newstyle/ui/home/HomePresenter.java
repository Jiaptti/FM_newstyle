package com.fastapp.viroyal.fm_newstyle.ui.home;


import android.support.v4.app.Fragment;
import android.util.Log;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.base.BaseListFragment;
import com.fastapp.viroyal.fm_newstyle.util.CommonUtils;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.CategoryVH;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

/**
 * Created by hanjiaqi on 2017/6/27.
 */

public class HomePresenter extends HomeContract.Presenter {

    @Override
    protected void onStart() {
        getTabList();
    }

    @Override
    void getTabList() {
        final List<Fragment> fragments = new ArrayList<>();
        getManager().add(model.getTabFragment().doOnSubscribe(new Action0() {
            @Override
            public void call() {
                view.showLoading();
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                view.dismissLoading();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(String s) {
                fragments.add(BaseListFragment.newInstance(CategoryVH.class, CommonUtils.getType(s)));
                view.showTabFragment(fragments);
            }
        }));
    }
}
