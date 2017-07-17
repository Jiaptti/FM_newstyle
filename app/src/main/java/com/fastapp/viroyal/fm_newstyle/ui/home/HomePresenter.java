package com.fastapp.viroyal.fm_newstyle.ui.home;


/**
 * Created by hanjiaqi on 2017/6/27.
 */

public class HomePresenter extends HomeContract.Presenter{

    @Override
    protected void onStart() {
        getTabList();
    }

    @Override
    void getTabList() {
        view.showTabList(model.getTabList());
    }
}
