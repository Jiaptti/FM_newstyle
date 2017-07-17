package com.fastapp.viroyal.fm_newstyle.base;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * Created by hanjiaqi on 2017/6/26.
 */

public class RxBus {
    private static RxBus mInstance;
    private  ConcurrentHashMap<Object, List<Subject>> subjectMapper = new ConcurrentHashMap<>();

    private RxBus(){}

    public static synchronized RxBus newInstance(){
        if(mInstance == null){
            mInstance = new RxBus();
        }
        return mInstance;
    }

    public <T> Observable<T> regiest(Object tag){
        List<Subject> subjectList = subjectMapper.get(tag);
        if(subjectList == null){
            subjectList = new ArrayList<>();
            subjectMapper.put(tag, subjectList);
        }
        Subject<T,T> subject = PublishSubject.create();
        subjectList.add(subject);
        return subject;
    }

    public RxBus unRegiest(@NonNull Object tag, @NonNull Observable<?> observable){
        if(observable == null){
            return newInstance();
        }
        List<Subject> subjectList = subjectMapper.get(tag);
        if(subjectList != null){
            subjectMapper.remove((Subject<?,?>) observable);
            if(isEmpty(subjectList)){
               subjectMapper.remove(tag);
            }
        }
        return newInstance();
    }

    public void post(Object tag, Object content){
        List<Subject> subjectList = subjectMapper.get(tag);
        if(!isEmpty(subjectList)){
            for(Subject subject : subjectList){
                subject.onNext(content);
            }
        }
    }

    private boolean isEmpty(Collection<Subject> subjectList){
        return subjectList == null || subjectList.isEmpty();
    }
}
