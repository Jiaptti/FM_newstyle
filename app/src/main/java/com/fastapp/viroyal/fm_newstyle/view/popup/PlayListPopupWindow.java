package com.fastapp.viroyal.fm_newstyle.view.popup;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.RxManager;
import com.fastapp.viroyal.fm_newstyle.db.RealmHelper;
import com.fastapp.viroyal.fm_newstyle.model.base.ErrorBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksBeanList;
import com.fastapp.viroyal.fm_newstyle.service.AlbumPlayService;
import com.fastapp.viroyal.fm_newstyle.util.CompareUtils;
import com.fastapp.viroyal.fm_newstyle.util.JsonUtils;
import com.fastapp.viroyal.fm_newstyle.view.layout.TRecyclerView;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.TrackListVH;

import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by hanjiaqi on 2017/8/24.
 */

public class PlayListPopupWindow extends PopupWindow {
    private TRecyclerView mTRecyclerView;
    private TextView popupClose;
    private LinearLayout trackListContent;
    private LinearLayout errorLayout;
    private TextView reload;

    private Context mContext;
    private RxManager manager = new RxManager();
    private AlbumPlayService.PlayBinder mBinder;
    private RealmHelper realmHelper;
    private IPlayTrackListener listener;

    public PlayListPopupWindow(Context context) {
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.play_list_popup_layout, null);
        setContentView(view);
        AppContext.setFromWindow(true);
        realmHelper = AppContext.getRealmHelper();
        mBinder = AppContext.getMediaPlayService();
        mTRecyclerView = (TRecyclerView) view.findViewById(R.id.play_list_view);
        popupClose = (TextView) view.findViewById(R.id.popup_close);
        errorLayout = (LinearLayout) view.findViewById(R.id.net_error_layout);
        trackListContent = (LinearLayout) view.findViewById(R.id.track_list_content);
        reload = (TextView) view.findViewById(R.id.reload);
        backgroundAlpha(1f);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight((AppContext.getScreenHeight() / 2) + (AppContext.getScreenHeight() / 4));
        setFocusable(true);
        setAnimationStyle(R.style.anim_popup_window);
        setOnDismissListener(dismissListener);
        manager.on(AppConstant.UPDATE_ITEM_STATUS, new Action1() {
            @Override
            public void call(Object o) {
                mTRecyclerView.getAdapter().notifyDataSetChanged();
            }
        });

        manager.on(AppConstant.ERROR_MESSAGE, new Action1() {
            @Override
            public void call(Object o) {
                ErrorBean errorBean = (ErrorBean) o;
                if (errorBean.getClazz() == TrackListVH.class) {
                    errorLayout.setVisibility(View.VISIBLE);
                    trackListContent.setVisibility(View.GONE);
                }
            }
        });

        manager.on(AppConstant.CURRENT_POSITION_VIEW, new Action1() {
            @Override
            public void call(Object o) {
                loadListData();
                if(o instanceof TracksBeanList){
                    TracksBeanList entity = (TracksBeanList) o;
                    listener.playTrack(entity);
                }
            }
        });
    }

    @OnClick({R.id.popup_close, R.id.reload})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.popup_close:
                if (isShowing()) {
                    dismiss();
                }
                break;
            case R.id.reload:
                errorLayout.setVisibility(View.GONE);
                trackListContent.setVisibility(View.VISIBLE);
                mTRecyclerView.sendRequest();
                break;
        }
    }

    public void setPlayTrackListener(IPlayTrackListener listener){
        this.listener = listener;
    }

    public void initListData(int albumId) {
        if(mTRecyclerView.getAdapter().getData().size() == 0){
            mTRecyclerView.setView(TrackListVH.class, albumId);
            mTRecyclerView.sendRequest();
        }
    }

    private void saveData() {
        JsonUtils.saveData(mTRecyclerView.getAdapter().getData(), mTRecyclerView.getMaxCount(), mTRecyclerView.getMaxPageId(), AppContext.getTempPageId());
    }

    private void loadListData() {
        int count = mTRecyclerView.getMaxCount();
        int position = realmHelper.getNowPlayingTrack().getPosition() + 4;
        if (position > count && mTRecyclerView.hasMore()) {
            mTRecyclerView.sendRequest();
        }
    }

    public void destroyManager() {
        manager.clear(AppConstant.UPDATE_ITEM_STATUS);
        manager.clear(AppConstant.CURRENT_POSITION_VIEW);
        manager.clear(AppConstant.ERROR_MESSAGE);
    }

    public void playNext() {
        List<TracksBeanList> beanList = mTRecyclerView.getAdapter().getData();
        TracksBeanList entity = beanList.get(realmHelper.getNowPlayingTrack().getPosition() + 1);
        if (realmHelper.getNowPlayingTrack().isFromTrack()) {
            entity.setFromTrack(true);
        }
        entity.setPosition(realmHelper.getNowPlayingTrack().getPosition() + 1);
        realmHelper.setNowPlayTrack(entity);
        listener.playTrack(entity);
    }

    public void playPrev() {
        List<TracksBeanList> beanList = mTRecyclerView.getAdapter().getData();
        TracksBeanList entity = beanList.get(realmHelper.getNowPlayingTrack().getPosition() - 1);
        if (realmHelper.getNowPlayingTrack().isFromTrack()) {
            entity.setFromTrack(true);
        }
        entity.setPosition(realmHelper.getNowPlayingTrack().getPosition() - 1);
        realmHelper.setNowPlayTrack(entity);
        listener.playTrack(entity);
    }

    OnDismissListener dismissListener = new OnDismissListener() {
        @Override
        public void onDismiss() {
            saveData();
            backgroundAlpha(1f);
        }
    };

    public void show(View parent) {
        backgroundAlpha(0.7f);
        if (!isShowing()) {
            showAtLocation(parent, Gravity.NO_GRAVITY, 0, AppContext.getScreenHeight() / 4);
            if (mTRecyclerView.getRecyclerView() != null){
                mTRecyclerView.getRecyclerView().scrollToPosition(realmHelper.getNowPlayingTrack().getPosition());
            }
        } else {
            saveData();
        }
    }

    public boolean hasNext() {
        int count = mTRecyclerView.getMaxCount();
        return ((realmHelper.getNowPlayingTrack().getPosition() + 1) != count);
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }

    public interface IPlayTrackListener {
        void playTrack(TracksBeanList track);
    }
}
