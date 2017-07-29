package com.fastapp.viroyal.fm_newstyle.util;

import android.util.Log;

import com.fastapp.viroyal.fm_newstyle.AppConstant;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

/**
 * Created by hanjiaqi on 2017/6/26.
 */

public class TUtils {
    public static  Class<?> forName(String className){
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T getT(Object tag, int index){
        try {
            return ((Class<T>)(((ParameterizedType)(tag.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[index]))
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
