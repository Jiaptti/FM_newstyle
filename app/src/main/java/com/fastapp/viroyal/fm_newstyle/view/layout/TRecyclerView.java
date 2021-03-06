package com.fastapp.viroyal.fm_newstyle.view.layout;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
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
import com.fastapp.viroyal.fm_newstyle.base.BaseSubscriber;
import com.fastapp.viroyal.fm_newstyle.base.BaseViewHolder;
import com.fastapp.viroyal.fm_newstyle.base.RxManager;
import com.fastapp.viroyal.fm_newstyle.db.RealmHelper;
import com.fastapp.viroyal.fm_newstyle.model.base.Data;
import com.fastapp.viroyal.fm_newstyle.model.base.BaseEntity;
import com.fastapp.viroyal.fm_newstyle.model.base.ErrorBean;
import com.fastapp.viroyal.fm_newstyle.model.cache.PlayListCache;
import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanEntity;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksBeanList;
import com.fastapp.viroyal.fm_newstyle.util.JsonUtils;
import com.fastapp.viroyal.fm_newstyle.util.RxSchedulers;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.AlbumVH;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.CategoryVH;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.CommFooterVH;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.TrackListVH;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by hanjiaqi on 2017/6/29.
 */

public class TRecyclerView<T extends BaseEntity> extends LinearLayout implements SwipeRefreshLayout.OnRefreshListener {
    private Context mContext;
    @Bind(R.id.swiperefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.recycle_list)
    RecyclerView recyclerView;
    public GridLayoutManager mGridLayoutManager;
    public int begin;
    private T model;
    public CoreAdapter<T> mAdatper = new CoreAdapter<>();
    public RxManager mRxManager;
    private int type = AppConstant.PAGE_CROSSTALK;
    private int pageSize = 20;
    public ErrorBean errorBean;
    public boolean hasMore;
    private Observable observable;
    private boolean isLoading = false;
    private boolean inFoot = false;
    private int maxCount;
    private int maxPageId;
    private String title;

    private RealmHelper helper = AppContext.getRealmHelper();

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
        mRxManager = new RxManager();
        addView(view);
        ButterKnife.bind(this, view);
        initView();
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    private void initView() {
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mGridLayoutManager = new GridLayoutManager(mContext, AppConstant.SPAN_COUNT_ONE);
        recyclerView.setLayoutManager(mGridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.smoothScrollToPosition(0);
        recyclerView.setAdapter(mAdatper);
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            protected int mLastItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.getAdapter() != null && hasMore && !isLoading
                        && mLastItem + 1 == recyclerView.getAdapter().getItemCount()
                        && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    sendRequest();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastItem = mGridLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    public void setRefreshLoadingState() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(true);
            mSwipeRefreshLayout.setEnabled(false);
        }
    }


    public void setRefreshLoadedState() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.setEnabled(true);
        }
    }

    public void setTitle(String title){
        this.title = title;
    }

    public int getMaxPageId() {
        return maxPageId;
    }

    private int getPageSize() {
        return pageSize;
    }

    public CoreAdapter getAdapter() {
        return mAdatper;
    }

    public void sendRequest() {
        begin++;
        isLoading = true;
        if (model == null) {
            Log.i(AppConstant.TAG, "model is null!");
            return;
        }
        if (errorBean.getClazz() == CategoryVH.class) {
            observable = getCategoryModel(type, begin);
        } else if (errorBean.getClazz() == AlbumVH.class) {
            observable = getTrackModel(type, begin);
            AppContext.setTempPageId(begin);
        } else if (errorBean.getClazz() == TrackListVH.class) {
            PlayListCache cache = JsonUtils.loadData(helper.getNowPlayingTrack().getAlbumId());
            if (cache.getData() != null && begin == 1) {
                observable = Observable.from(cache.getData());
                begin = cache.getPageId();
                maxPageId = cache.getMaxPage();
            } else {
                if (helper.getNowPlayingTrack().isFromTrack()) {
                    observable = getRankingModel(AppConstant.HOT_TRACKS_ID, begin);
                } else {
                    observable = getTrackModel(type, begin);
                }
                AppContext.setTempPageId(begin);
            }
        } else {
            observable = getRankingModel(type, begin);
            AppContext.setTempPageId(begin);
        }
        mRxManager.add(observable.subscribe(
                new BaseSubscriber<T>(mContext, errorBean) {
                    @Override
                    public void onNext(T o) {
                        mAdatper.setBeans(o);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                        begin = 0;
                        setRefreshLoadedState();
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        setRefreshLoadedState();
                    }
                }));
    }

    private Observable getRankingModel(int type, final int begin) {
        return model.getPageAt(type, begin, getPageSize()).compose(RxSchedulers.io_main())
                .flatMap(new Func1<Data<List<TracksBeanList>>, Observable<TracksBeanList>>() {
                    @Override
                    public Observable<TracksBeanList> call(Data<List<TracksBeanList>> data) {
                        maxPageId = data.getMaxPageId();
                        hasMore = begin <= maxPageId;
                        return Observable.from(data.getList());
                    }
                });
    }

    private synchronized Observable getTrackModel(final int type, final int begin) {
        return model.getPageAt(type, begin, getPageSize()).compose(RxSchedulers.io_main())
                .flatMap(new Func1<Data<TracksBean>, Observable<TracksBeanList>>() {
                    @Override
                    public Observable<TracksBeanList> call(Data<TracksBean> track) {
                        maxPageId = track.getData().getMaxPageId();
                        maxCount = track.getData().getTotalCount();
                        hasMore = begin <= maxPageId;
                        return Observable.from(track.getData().getList());
                    }
                });
    }

    private Observable getCategoryModel(int type, int begin) {
        return model.getPageAt(type, begin, getPageSize()).compose(RxSchedulers.io_main())
                .flatMap(new Func1<Data<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(Data<T> list) {
                        List<T> data = (List<T>) list.getList();
                        hasMore = data.size() >= AppConstant.PAGESIZE;
                        return Observable.from(data);
                    }
                });
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void refresh() {
        if (errorBean.getClazz() != TrackListVH.class) {
            begin = 0;
            mAdatper.getData().clear();
            sendRequest();
        }
    }

    public boolean hasMore() {
        return hasMore;
    }

    public TRecyclerView setView(Class<? extends BaseViewHolder<T>> clazz, int tabType) {
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

    @Override
    public void onRefresh() {
        if (recyclerView != null)
            recyclerView.scrollToPosition(0);
        refresh();
        setRefreshLoadingState();
    }


    public class CoreAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<T> mDatas = new ArrayList<>();
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
                    if (mItemViewClass == CategoryVH.class) {
                        if (viewType == AppConstant.SPAN_COUNT_THREE) {
                            mItemType = R.layout.category_item_vertical_layout;
                        } else {
                            mItemType = R.layout.category_item_layout;
                        }
                    }
                    View view = LayoutInflater.from(parent.getContext()).inflate(mItemType, parent, false);
                    return (mItemViewClass).getConstructor(View.class).newInstance(view);
                }
            } catch (Exception e) {
                Log.i(AppConstant.TAG, AppContext.getStringById(R.string.wrong_xml));
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            inFoot = position + 1 == getItemCount();
            if (errorBean.getClazz() == TrackListVH.class || errorBean.getClazz() == AlbumVH.class && helper.getNowPlayingTrack() != null) {
                if (((position == helper.getNowPlayingTrack().getPosition() + 1)
                        && getItemViewType(helper.getNowPlayingTrack().getPosition() + 1) == CommFooterVH.FOOT_TYPE)
                        || (begin == maxPageId && inFoot)) {
                    inFoot = true;
                    hasMore = false;
                }
            }
            ((BaseViewHolder) holder).onBindViewHolder(holder.itemView,
                    inFoot ? (hasMore ? new Object() : null) : mDatas.get(position));
        }

        @Override
        public int getItemViewType(int position) {
            return position + 1 == getItemCount() ? mFootType
                    : mGridLayoutManager.getSpanCount() == AppConstant.SPAN_COUNT_ONE ? mItemType : AppConstant.SPAN_COUNT_THREE;
        }

        public void setViewType(Class<? extends BaseViewHolder> tagClass, int type) {
            mItemType = type;
            mItemViewClass = tagClass;
            mDatas = new ArrayList<>();
            hasMore = true;
            notifyDataSetChanged();
        }

        public void setBeans(T data) {
            if (data instanceof HimalayanEntity) {
                HimalayanEntity entity = (HimalayanEntity) data;
                if (!entity.isIsPaid()) {
                    entity.setCategoryName(title);
                    this.mDatas.add(data);
                }
            } else {
                if (data instanceof TracksBeanList) {
                    TracksBeanList entity = (TracksBeanList) data;
                    if (!entity.isPaid()) {
                        this.mDatas.add(data);
                    }
                } else {
                    this.mDatas.add(data);
                }
                notifyDataSetChanged();
            }
            isLoading = false;
            notifyDataSetChanged();
        }

        public List<T> getData() {
            return mDatas;
        }


        @Override
        public int getItemCount() {
            return mDatas.size() + 1;
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ButterKnife.unbind(this);
        if (!AppContext.isFromWindow()) {
            mRxManager.clear();
        }
    }
}
