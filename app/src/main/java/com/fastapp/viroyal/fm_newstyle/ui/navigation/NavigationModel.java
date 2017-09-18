package com.fastapp.viroyal.fm_newstyle.ui.navigation;

import com.fastapp.viroyal.fm_newstyle.api.Api;
import com.fastapp.viroyal.fm_newstyle.model.base.Data;
import com.fastapp.viroyal.fm_newstyle.model.entity.NavigationBean;

import rx.Observable;

/**
 * Created by hanjiaqi on 2017/9/18.
 */

public class NavigationModel implements NavigationContract.Model {
    @Override
    public Observable<Data<NavigationBean>> getNavigation() {
        return Api.getInstance().getApiService().getNavigationList("android", 13, 2);
    }
}
