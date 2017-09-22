package com.fastapp.viroyal.fm_newstyle.view.fragment.recent;

import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanEntity;
import com.fastapp.viroyal.fm_newstyle.model.realm.RecentListen;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by hanjiaqi on 2017/9/21.
 */

public class RecentPresenter extends RecentContract.Presenter{

    @Override
    void getRecentList() {
        getManager().add(Observable.just(model.getRecentTrackList())
                .flatMap(new Func1<List<RecentListen>, Observable<List<HimalayanEntity>>>() {
                    @Override
                    public Observable<List<HimalayanEntity>> call(List<RecentListen> recentListenTracks) {
                        List<HimalayanEntity> records = new ArrayList<>();
                        HimalayanEntity entity = null;
                        for (RecentListen track : recentListenTracks) {
                            entity = new HimalayanEntity();
                            entity.setCoverSmall(track.getCoverSmall());
                            entity.setTitle(track.getTitle());
                            entity.setIntro(track.getIntro());
                            entity.setNickname(track.getNickname());
                            entity.setPlaysCounts(track.getPlaysCounts());
                            entity.setCategoryName(track.getCategoryName());
                            entity.setAlbumId(track.getAlbumId());
                            entity.setTrackId(track.getTrackId());
                            entity.setTracks(track.getTracks());
                            records.add(entity);
                        }
                        return Observable.just(records);
                    }
                }).subscribe(new Subscriber<List<HimalayanEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<HimalayanEntity> list) {
                        view.showRecentCategory(list);
                    }
                }));
    }

    @Override
    protected void onStart() {
        getRecentList();
    }
}
