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
import com.fastapp.viroyal.fm_newstyle.util.RxSchedulers;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.CommFooterVH;

import java.lang.reflect.ParameterizedType;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

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
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            protected int mLastItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.getAdapter() != null && mAdatper.hasMore
                        && mLastItem + 1 == recyclerView.getAdapter().getItemCount()
                        && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    sendRequest();
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
        if (mId > 0) {
            type = mId;
            pageSize = pageSize + 10;
        }
        mRxManager.add(model.getPageAt(type, begin, getPageSize()).compose(RxSchedulers.io_main())
                .subscribe(new Action1<Data<T>>() {
                    @Override
                    public void call(Data<T> data) {
                        if (mId > 0) {
                            maxPage = data.getData().getTracks().getTotalCount();
                            List<T> list = (List<T>) data.getData().getTracks().getList();
                            mAdatper.setBeansById(list.subList(length, list.size()), begin);
                            length = data.getData().getTracks().getList().size();
                        } else {
                            mAdatper.setBeans(data.getList(), begin);
                        }
                        if (data.getList() == null || data.getData() == null) {
                            setEmpty();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        Log.i(AppConstant.TAG, "error = " + throwable.getMessage());
                        if(throwable instanceof SocketTimeoutException) {
                            mRxManager.post(AppConstant.ERROR_MESSAGE, errorBean);
                        } else if(throwable instanceof ConnectException){

                        }

                    }
                }));
    }

    public void refresh() {
        begin = 1;
        sendRequest();
    }


    private void setEmpty() {
        if (!isEmpty) {
            isEmpty = true;
            if(emptyLayout != null)
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

    public class CoreAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<T> mDates = new ArrayList<>();
        public boolean hasMore;
        private Class<? extends BaseViewHolder> mItemViewClass;
        private Class<? extends BaseViewHolder> mFootViewClass = CommFooterVH.class;
        private int mItemType, mFootType = CommFooterVH.FOOT_TYPE;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            boolean isFoot = viewType == mFootType;
            try {
                return (isFoot ? mFootViewClass : mItemViewClass).getConstructor(View.class).newInstance(
                        LayoutInflater.from(parent.getContext()).inflate(isFoot ? mFootType : mItemType, parent,
                                false));
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

        public void setBeans(List<T> dates, int begin) {
            if (dates == null) dates = new ArrayList<>();
            hasMore = dates.size() >= AppConstant.PAGESIZE;
            if (begin > 1) {
                this.mDates.addAll(dates);
            } else {
                this.mDates = dates;
            }
            notifyDataSetChanged();
            mRxManager.post(AppConstant.LOADING_STATUS , null);
        }

        public void setBeansById(List<T> dates, int begin) {
            if (dates == null) dates = new ArrayList<>();
            hasMore = dates.size() < maxPage;
            if (begin > 1) {
                this.mDates.addAll(dates);
            } else {
                this.mDates = dates;
            }
            notifyDataSetChanged();
            mRxManager.post(AppConstant.LOADING_STATUS , null);
        }

        public void removeItem(int position) {
            mDates.remove(position);
            notifyItemChanged(position);
        }

        public void updateItem(int position, T data) {
            mDates.remove(position);
            mDates.add(position, data);
            notifyItemChanged(position);
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
