package com.fastapp.viroyal.fm_newstyle.ui.navigation;

import com.fastapp.viroyal.fm_newstyle.base.BaseModel;
import com.fastapp.viroyal.fm_newstyle.base.BasePresenter;
import com.fastapp.viroyal.fm_newstyle.base.BaseView;
import com.fastapp.viroyal.fm_newstyle.model.base.Data;
import com.fastapp.viroyal.fm_newstyle.model.entity.NavigationBean;

import java.util.List;

import rx.Observable;

/**
 * Created by hanjiaqi on 2017/9/18.
 */

public interface NavigationContract {
    interface View extends BaseView{
        void showNavigation(List<NavigationBean> list);
    }

    interface Model extends BaseModel{
        Observable<Data<List<NavigationBean>>> getNavigation();
    }

    abstract class Presenter extends BasePresenter<View, Model>{
       abstract void initNavigation();
    }
}
