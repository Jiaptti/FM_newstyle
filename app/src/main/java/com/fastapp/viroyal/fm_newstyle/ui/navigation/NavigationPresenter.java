package com.fastapp.viroyal.fm_newstyle.ui.navigation;

import com.fastapp.viroyal.fm_newstyle.base.BaseSubscriber;
import com.fastapp.viroyal.fm_newstyle.model.base.Data;
import com.fastapp.viroyal.fm_newstyle.model.entity.NavigationBean;
import com.fastapp.viroyal.fm_newstyle.util.RxSchedulers;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by hanjiaqi on 2017/9/18.
 */

public class NavigationPresenter extends NavigationContract.Presenter {
    private List<NavigationBean> list = new ArrayList<>();
    @Override
    void initNavigation() {
        getManager().add(model.getNavigation().compose(RxSchedulers.<Data<NavigationBean>>io_main())
                .flatMap(new Func1<Data<NavigationBean>, Observable<NavigationBean>>() {
                    @Override
                    public Observable<NavigationBean> call(Data<NavigationBean> data) {
                        return Observable.from(data.getList());
                    }
                }).filter(new Func1<NavigationBean, Boolean>() {
                    @Override
                    public Boolean call(NavigationBean bean) {
                        return !bean.isIsPaid();
                    }
                }).subscribe(new BaseSubscriber<NavigationBean>() {
                    @Override
                    public void onNext(NavigationBean entity) {
                        list.add(entity);
                        view.showNavigation(list);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }
                }));
    }


    @Override
    protected void onStart() {
        initNavigation();
    }
}
