package com.fastapp.viroyal.fm_newstyle.view.fragment.recent;

import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanEntity;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksBeanList;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksData;
import com.fastapp.viroyal.fm_newstyle.model.realm.RecentListen;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by hanjiaqi on 2017/9/21.
 */

public class RecentPresenter extends RecentContract.Presenter{

    @Override
    void getRecentList() {
        getManager().add(Observable.just(model.getRecentTrackList()).subscribe(new Action1<List<RecentListen>>() {
            @Override
            public void call(List<RecentListen> recentListens) {
                List<TracksBeanList> beanList = new ArrayList();
                for(RecentListen entity : recentListens){
                    TracksBeanList bean = new TracksBeanList();
                    bean.setAlbumTitle(entity.getAlbumTitle());
                    bean.setTrackId(entity.getTrackId());
                    bean.setAlbumId(entity.getAlbumId());
                    bean.setCoverSmall(entity.getCoverSmall());
                    bean.setTitle(entity.getTitle());
                    beanList.add(bean);
                }
                if(recentListens.size() == 0){
                    view.noContent();
                } else {
                    view.showRecentCategory(beanList);
                }
            }
        }));
    }

    @Override
    protected void onStart() {
        getRecentList();
    }
}
