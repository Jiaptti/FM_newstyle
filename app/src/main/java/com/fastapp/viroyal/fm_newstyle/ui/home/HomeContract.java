package com.fastapp.viroyal.fm_newstyle.ui.home;

import android.support.v4.app.Fragment;

import com.fastapp.viroyal.fm_newstyle.base.BaseModel;
import com.fastapp.viroyal.fm_newstyle.base.BasePresenter;
import com.fastapp.viroyal.fm_newstyle.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * Created by hanjiaqi on 2017/6/26.
 */

public interface HomeContract {

    interface View extends BaseView{
        void showTabFragment(List<Fragment> fragments);
    }

    interface Model extends BaseModel{
        Observable<String> getTabFragment();

    }

    abstract class Presenter extends BasePresenter<View, Model> {
        abstract void getTabList();
    }
}
