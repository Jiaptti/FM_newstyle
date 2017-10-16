package com.fastapp.viroyal.fm_newstyle.api;

import android.util.Log;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by hanjiaqi on 2017/9/27.
 */

public class ExceptionHandle {

    public static ResponseThrowable handleException(Throwable throwable){
        ResponseThrowable ex;
        Log.i(AppConstant.TAG, "ExceptionHandle throwable = " + throwable);
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            ex = new ResponseThrowable(throwable, AppConstant.HTTP_ERROR);
            switch (httpException.code()) {
                case AppConstant.UNAUTHORIZED:
                case AppConstant.FORBIDDEN:
                case AppConstant.NOT_FOUND:
                case AppConstant.REQUEST_TIMEOUT:
                case AppConstant.GATEWAY_TIMEOUT:
                case AppConstant.INTERNAL_SERVER_ERROR:
                case AppConstant.BAD_GATEWAY:
                case AppConstant.SERVICE_UNAVAILABLE:
                default:
                    ex.message = AppContext.getStringById(R.string.net_error);
                    break;
            }
            return ex;
        }else if (throwable instanceof ServerException) {
            ServerException resultException = (ServerException) throwable;
            ex = new ResponseThrowable(resultException, resultException.code);
            ex.message = resultException.message;
            return ex;
        } else if (throwable instanceof JsonParseException
                || throwable instanceof JSONException) {
            ex = new ResponseThrowable(throwable, AppConstant.PARSE_ERROR);
            ex.message = AppContext.getStringById(R.string.parse_error);
            return ex;
        } else if (throwable instanceof ConnectException) {
            ex = new ResponseThrowable(throwable, AppConstant.NETWORD_ERROR);
            ex.message = AppContext.getStringById(R.string.connection_error);
            return ex;
        } else if (throwable instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ResponseThrowable(throwable, AppConstant.SSL_ERROR);
            ex.message = AppContext.getStringById(R.string.ssl_error);
            return ex;
        } else if(throwable instanceof SocketTimeoutException){
            ex = new ResponseThrowable(throwable, AppConstant.SOCKET_TIMEOUT_ERROR);
            ex.message = AppContext.getStringById(R.string.socket_timeout_error);
            return ex;
        } else if(throwable instanceof ConnectException){
            ex = new ResponseThrowable(throwable, AppConstant.CONNECT_ERROR);
            ex.message = AppContext.getStringById(R.string.connect_error);
            return ex;
        } else {
            ex = new ResponseThrowable(throwable, AppConstant.UNKNOWN);
            ex.message = AppContext.getStringById(R.string.unknown_error);
            return ex;
        }

    }

    public static class ResponseThrowable extends Exception {
        public int code;
        public String message;

        public ResponseThrowable(Throwable throwable, int code) {
            super(throwable);
            this.code = code;
        }
    }

    class ServerException extends RuntimeException {
        int code;
        String message;
    }

}
