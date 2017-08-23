package com.fastapp.viroyal.fm_newstyle.api;

import android.content.Context;

import java.net.ConnectException;


/**
 * Created by hanjiaqi on 2017/8/22.
 */

public class ApiException {

    public static <T> void handleCommonError(Context context, Throwable e, T view) {
        if(e instanceof ConnectException){

        }
//        if (e instanceof HttpException || e instanceof IOException) {
//            AppContext.toastShort(AppContext.getStringById(R.string.error_no_internet));
//            Log.i(AppConstant.TAG, e.getMessage());
//        }
    }
}
