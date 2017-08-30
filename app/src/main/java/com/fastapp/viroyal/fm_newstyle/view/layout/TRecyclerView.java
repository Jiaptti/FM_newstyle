package com.fastapp.viroyal.fm_newstyle.view.layout;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseViewHolder;
import com.fastapp.viroyal.fm_newstyle.base.RxManager;
import com.fastapp.viroyal.fm_newstyle.model.base.Data;
import com.fastapp.viroyal.fm_newstyle.model.base.BaseEntity;
import com.fastapp.viroyal.fm_newstyle.model.base.ErrorBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanEntity;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksBeanList;
import com.fastapp.viroyal.fm_newstyle.model.realm.TracksBeanRealm;
import com.fastapp.viroyal.fm_newstyle.util.RxSchedulers;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.CategoryVH;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.CommFooterVH;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.fastapp.viroyal.fm_newstyle.R.string.list;

/**
 * Created by hanjiaqi on 2017/6/29.
 */

public class TRecyclerView<T extends BaseEntity> extends LinearLayout {
    private Context mContext;
    @Bind(R.id.recycle_list)
    RecyclerView recyclerView;
    @Bind(R.id.empty_layout)
    LinearLayout emptyLayout;
    private LinearLayoutManager mLayoutManager;
    private int begin;
    private boolean isEmpty = false;
    private T model;
    public CoreAdapter<T> mAdatper = new CoreAdapter<>();
    private RxManager mRxManager;
    private List<T> listData;
    private int type = AppConstant.PAGE_CROSSTALK;
    private int mId = 0;
    private int pageSize = 10;
    private int maxPage = 0;
    private int length = 0;
    private ErrorBean errorBean;

    public TRecyclerView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public TRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.recycle_list_layout, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT));
        addView(view);
        ButterKnife.bind(this, view);
        initView(view);
        mRxManager = new RxManager();
    }

    private void initView(View view) {
        mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.smoothScrollToPosition(mAdatper.getItemCount());
        recyclerView.setAdapter(mAdatper);
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            protected int mLastItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.getAdapter() != null && mAdatper.hasMore
                        && mLastItem + 1 == recyclerView.getAdapter().getItemCount()
                        && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (errorBean != null && errorBean.getClazz() == CategoryVH.class) {
                        sendRequest();
                    } else {
                        loadData();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
        emptyLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
    }

    private int getPageSize() {
        return pageSize;
    }

    public CoreAdapter getAdapter() {
        return mAdatper;
    }

    public void sendRequest() {
        begin++;
        if (isEmpty) {
            emptyLayout.setVisibility(View.GONE);
        }
        if (model == null) {
            Log.i(AppConstant.TAG, "model is null!");
            return;
        }
        mRxManager.add(model.getPageAt(type, begin, getPageSize())
                .distinct()
                .compose(RxSchedulers.io_main())
                .flatMap(new Func1<Data<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(Data<T> tData) {
                        return Observable.from(tData.getList());
                    }
                })
                .subscribe(new Action1<T>() {
                    @Override
                    public void call(T o) {
                        mAdatper.setBeans(o);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        Log.i(AppConstant.TAG, "sendRequest error = " + throwable.getMessage());
                        if (throwable instanceof SocketTimeoutException) {
                            mRxManager.post(AppConstant.LOADING_STATUS, null);
                            mRxManager.post(AppConstant.ERROR_MESSAGE, errorBean);
                        } else if (throwable instanceof ConnectException) {

                        } else {

                        }
                    }
                }));
    }

    public void setData(List<T> data) {
        this.listData = data;
        maxPage = listData.size();
    }

    public int getMaxPage(){
        return maxPage;
    }


    public List getData(){
        return listData;
    }

    public void loadData() {
//        begin++;
        if (listData != null) {
            List<T> list = listData;
            if (length + 10 < maxPage) {
                list = list.subList(length, length + 10);
                length += 10;
            } else {
                list = list.subList(length, maxPage);
            }
            mRxManager.add(Observable.from(list)
                    .distinct()
                    .compose(RxSchedulers.<T>io_main())
                    .subscribe(new Action1<T>() {
                        @Override
                        public void call(T data) {
                            mAdatper.setBeansById(data);
                        }
            }));
        }
    }


    public void refresh() {
        begin = 1;
        sendRequest();
    }


    private void setEmpty() {
        if (!isEmpty) {
            isEmpty = true;
            if (emptyLayout != null)
                emptyLayout.setVisibility(View.VISIBLE);
        }
    }

    public TRecyclerView setViewByTab(Class<? extends BaseViewHolder<T>> clazz, int tabType) {
        try {
            errorBean = new ErrorBean();
            BaseViewHolder mIVH = ((BaseViewHolder) (clazz.getConstructor(View.class)
                    .newInstance(new View(mContext))));
            int type = mIVH.getType();
            this.model = ((Class<T>) ((ParameterizedType) (clazz
                    .getGenericSuperclass())).getActualTypeArguments()[0])
                    .newInstance();
            errorBean.setClazz(clazz);
            mAdatper.setViewType(clazz, type);
            this.type = tabType;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public TRecyclerView setViewById(Class<? extends BaseViewHolder<T>> clazz, int id) {
        try {
            errorBean = new ErrorBean();
            BaseViewHolder mIVH = ((BaseViewHolder) (clazz.getConstructor(View.class)
                    .newInstance(new View(mContext))));
            int type = mIVH.getType();
            this.model = ((Class<T>) ((ParameterizedType) (clazz
                    .getGenericSuperclass())).getActualTypeArguments()[0])
                    .newInstance();
            mAdatper.setViewType(clazz, type);
            errorBean.setClazz(clazz);
            mId = id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public TRecyclerView setBaseView(Class<? extends BaseViewHolder<T>> clazz) {
        try {
            BaseViewHolder mIVH = ((BaseViewHolder) (clazz.getConstructor(View.class)
                    .newInstance(new View(mContext))));
            this.model = ((Class<T>) ((ParameterizedType) (clazz
                    .getGenericSuperclass())).getActualTypeArguments()[0])
                    .newInstance();
            int type = mIVH.getType();
            mAdatper.setViewType(clazz, type);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return this;
    }

    public class CoreAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<T> mDates = new ArrayList<>();
        public boolean hasMore;
        private Class<? extends BaseViewHolder> mItemViewClass;
        private Class<? extends BaseViewHolder> mFootViewClass = CommFooterVH.class;
        private int mItemType, mFootType = CommFooterVH.FOOT_TYPE;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
            boolean isFoot = viewType == mFootType;
            try {
                if (isFoot) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(mFootType, parent, false);
                    if (!hasMore) {
                        view.setVisibility(View.GONE);
                    }
                    return (mFootViewClass).getConstructor(View.class).newInstance(view);
                } else {
                    View view = LayoutInflater.from(parent.getContext()).inflate(mItemType, parent, false);
                    return (mItemViewClass).getConstructor(View.class).newInstance(view);
                }
//                return (isFoot ? mFootViewClass : mItemViewClass).getConstructor(View.class).newInstance(
//                        LayoutInflater.from(parent.getContext()).inflate(isFoot ? mFootType : mItemType, parent,
//                                false));
            } catch (Exception e) {
                Log.i(AppConstant.TAG, AppContext.getStringById(R.string.wrong_xml));
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            ((BaseViewHolder) holder).onBindViewHolder(holder.itemView,
                    position + 1 == getItemCount() ? (hasMore ? new Object() : null) : mDates.get(position));

        }

        @Override
        public int getItemViewType(int position) {
            return position + 1 == getItemCount() ? mFootType : mItemType;
        }

        public void setViewType(Class<? extends BaseViewHolder> tagClass, int type) {
            mItemType = type;
            mItemViewClass = tagClass;
            mDates = new ArrayList<>();
            hasMore = true;
            notifyDataSetChanged();
        }

        public void setBeans(T data) {
//            hasMore = dates.size() >= AppConstant.PAGESIZE;
            if(data instanceof HimalayanEntity){
                HimalayanEntity entity = (HimalayanEntity)data;
                if(!entity.isIsPaid()){
                    this.mDates.add(data);
                }
            }
            notifyDataSetChanged();
        }

        public void setBeansById(T data) {
            hasMore = length + 10 < maxPage;
            this.mDates.add(data);
            notifyDataSetChanged();
            mRxManager.post(AppConstant.LOADING_STATUS, null);
        }

        @Override
        public int getItemCount() {
            return mDates.size() + 1;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ButterKnife.unbind(this);
    }
}
