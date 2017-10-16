package com.fastapp.viroyal.fm_newstyle.ui.navigation;

import com.fastapp.viroyal.fm_newstyle.base.BaseSubscriber;
import com.fastapp.viroyal.fm_newstyle.model.base.Data;
import com.fastapp.viroyal.fm_newstyle.model.entity.NavigationBean;
import com.fastapp.viroyal.fm_newstyle.util.RxSchedulers;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.NavigationVH;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;

/**
 * Created by hanjiaqi on 2017/9/18.
 */

public class NavigationPresenter extends NavigationContract.Presenter {
    private List<NavigationBean> list = new ArrayList<>();

    @Override
    void initNavigation() {
        getManager().add(model.getNavigation().compose(RxSchedulers.<Data<List<NavigationBean>>>io_main()).
                doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        view.showLoading();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Data<List<NavigationBean>>, Observable<NavigationBean>>() {
                    @Override
                    public Observable<NavigationBean> call(Data<List<NavigationBean>> data) {
                        return Observable.from(data.getList());
                    }
                })
                .filter(new Func1<NavigationBean, Boolean>() {
                    @Override
                    public Boolean call(NavigationBean bean) {
                        return !bean.isIsPaid();
                    }
                })
                .subscribe(new BaseSubscriber<NavigationBean>(mContext, errorBean) {
                    @Override
                    public void onNext(NavigationBean entity) {
                        list.add(entity);
                    }

                    @Override
                    public void onCompleted() {
                        view.dismissLoading();
                        view.showNavigation(list);
                        super.onCompleted();
                    }
                }));
    }


    @Override
    protected void onStart() {
        errorBean.setClazz(NavigationVH.class);
        initNavigation();
    }
}
