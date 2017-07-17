package com.fastapp.viroyal.fm_newstyle.util.test;

import android.util.Log;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.api.Api;
import com.fastapp.viroyal.fm_newstyle.base.RxManager;
import com.fastapp.viroyal.fm_newstyle.data.base.Data;
import com.fastapp.viroyal.fm_newstyle.data.entity.HimalayanBean;
import com.fastapp.viroyal.fm_newstyle.data.entity.TracksBeanList;
import com.fastapp.viroyal.fm_newstyle.util.RxSchedulers;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by hanjiaqi on 2017/7/4.
 */

public class TestUtils {
    public static void TestData(){
        RxManager manager = new RxManager();
        Subscription observable = Api.getInstance().getApiService().getCommentList1("hot",12,"android",1, 5,"5.4.93")
                .compose(RxSchedulers.<HimalayanTest<HimalayanEntityTest>>io_main()).
                        subscribe(new Action1<HimalayanTest<HimalayanEntityTest>>() {
            @Override
            public void call(HimalayanTest<HimalayanEntityTest> himalayan) {
                Log.i(AppConstant.TAG,"himalayanTest.getData() = " + himalayan.getList());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.i(AppConstant.TAG,"throwable = " + throwable);
            }
        });
        manager.add(observable);
    }

    public static void testData1(){
        RxManager manager = new RxManager();
        Subscription observable = Api.getInstance().getApiService().getAlbumsList1(5163889,1,10)
                .compose(RxSchedulers.<Data<HimalayanBean>>io_main()).subscribe(new Action1<Data<HimalayanBean>>() {
                    @Override
                    public void call(Data<HimalayanBean> data) {
                        if(data.getData().getClass().equals(HimalayanBean.class)){
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.i(AppConstant.TAG,"throwable = " + throwable);
                    }
                });
        manager.add(observable);
    }

}
