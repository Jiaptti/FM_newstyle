package com.fastapp.viroyal.fm_newstyle.base;

import android.content.Context;
import android.util.Log;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.api.ExceptionHandle;
import com.fastapp.viroyal.fm_newstyle.model.base.ErrorBean;
import com.fastapp.viroyal.fm_newstyle.util.NetWorkUtils;
import com.fastapp.viroyal.fm_newstyle.view.layout.TRecyclerView;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.AlbumVH;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Created by hanjiaqi on 2017/9/15.
 */

public class BaseSubscriber<T> extends Subscriber<T> {
    private Context mContext;
    private ErrorBean errorBean;
    private RxManager manager = new RxManager();

    public BaseSubscriber() {
    }

    public BaseSubscriber(Context context, ErrorBean errorBean) {
        this.mContext = context;
        this.errorBean = errorBean;
    }

    @Override
    public void onStart() {
        if (!NetWorkUtils.hasNet()) {
            AppContext.toastShort(R.string.no_net);
            return;
        }
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();

        ExceptionHandle.ResponseThrowable response;
        if(throwable instanceof Exception){
            response = ExceptionHandle.handleException(throwable);
        }else {
            response = new ExceptionHandle.ResponseThrowable(throwable, AppConstant.UNKNOWN);
        }
        AppContext.toastShort(response.message);
        int statusCode = response.code;
        errorBean.setStatusCode(statusCode);
        switch (statusCode){
            case AppConstant.SSL_ERROR:
                manager.post(AppConstant.ERROR_MESSAGE, errorBean);
                break;
            case AppConstant.UNKNOWN:
                manager.post(AppConstant.ERROR_MESSAGE, errorBean);
                break;
            case AppConstant.PARSE_ERROR:
                manager.post(AppConstant.ERROR_MESSAGE, errorBean);
                break;
            case AppConstant.NETWORD_ERROR:
                manager.post(AppConstant.ERROR_MESSAGE, errorBean);
                break;
            case AppConstant.HTTP_ERROR:
                manager.post(AppConstant.ERROR_MESSAGE, errorBean);
                break;
            case AppConstant.SOCKET_TIMEOUT_ERROR:
                manager.post(AppConstant.ERROR_MESSAGE, errorBean);
                break;
            case AppConstant.CONNECT_ERROR:
                manager.post(AppConstant.ERROR_MESSAGE, errorBean);
                break;
        }
    }

    @Override
    public void onNext(T t) {
    }
}
