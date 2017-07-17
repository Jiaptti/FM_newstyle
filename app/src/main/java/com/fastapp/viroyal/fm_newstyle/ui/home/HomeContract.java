package com.fastapp.viroyal.fm_newstyle.ui.home;

import com.fastapp.viroyal.fm_newstyle.base.BaseModel;
import com.fastapp.viroyal.fm_newstyle.base.BasePresenter;
import com.fastapp.viroyal.fm_newstyle.base.BaseView;

/**
 * Created by hanjiaqi on 2017/6/26.
 */

public interface HomeContract {

    interface View extends BaseView{
        void showTabList(String[] tabs);
    }

    interface Model extends BaseModel{
        String[] getTabList();
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        abstract void getTabList();
    }
}
