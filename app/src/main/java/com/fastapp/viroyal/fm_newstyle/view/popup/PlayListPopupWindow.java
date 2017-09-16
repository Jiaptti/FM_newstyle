package com.fastapp.viroyal.fm_newstyle.view.popup;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.RxManager;
import com.fastapp.viroyal.fm_newstyle.db.RealmHelper;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksBeanList;
import com.fastapp.viroyal.fm_newstyle.service.AlbumPlayService;
import com.fastapp.viroyal.fm_newstyle.util.JsonUtils;
import com.fastapp.viroyal.fm_newstyle.view.layout.TRecyclerView;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.TrackListVH;
import android.widget.TextView;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by hanjiaqi on 2017/8/24.
 */

public class PlayListPopupWindow extends PopupWindow{
    private Context mContext;
    private TRecyclerView mTRecyclerView;
    private TextView popupClose;
    private RxManager manager = new RxManager();
    private AlbumPlayService.PlayBinder mBinder;
    private RealmHelper realmHelper;

    public PlayListPopupWindow(Context context){
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.play_list_popup_layout, null);
        setContentView(view);
        AppContext.setFromWindow(true);
        realmHelper = AppContext.getRealmHelper();
        mBinder = AppContext.getMediaPlayService();
        mTRecyclerView = (TRecyclerView) view.findViewById(R.id.play_list_view);
        popupClose = (TextView) view.findViewById(R.id.popup_close);
        backgroundAlpha(1f);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight((AppContext.getScreenHeight() / 2) + (AppContext.getScreenHeight() / 4));
        setFocusable(true);
        setAnimationStyle(R.style.anim_popup_window);
        setOnDismissListener(dismissListener);
        popupClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isShowing()){
                    dismiss();
                }
            }
        });
        manager.on(AppConstant.UPDATE_ITEM_STATUS, new Action1() {
            @Override
            public void call(Object o) {
                mTRecyclerView.getAdapter().notifyDataSetChanged();
            }
        });

        manager.on(AppConstant.CURRENT_POSITION_VIEW, new Action1() {
            @Override
            public void call(Object o) {
                loadListData();
            }
        });
    }

    public void initListData(int albumId){
        mTRecyclerView.setView(TrackListVH.class, albumId);
        mTRecyclerView.sendRequest();
//        mTRecyclerView.getRecyclerView().scrollToPosition(realmHelper.getNowPlayingTrack().getPosition());
    }

    private void saveData(){
        JsonUtils.createJson(mTRecyclerView.getAdapter().getData());
    }

    private void loadListData(){
        int count = mTRecyclerView.getAdapter().getItemCount() - 1;
        int position = realmHelper.getNowPlayingTrack().getPosition() + 4;
        if(position > count && mTRecyclerView.hasMore()){
            mTRecyclerView.sendRequest();
        }
    }

    public void destroyManager(){
        manager.clear(AppConstant.UPDATE_ITEM_STATUS);
        manager.clear(AppConstant.CURRENT_POSITION_VIEW);
    }

    public void playNext(){
        List<TracksBeanList> beanList = mTRecyclerView.getAdapter().getData();
        TracksBeanList entity = beanList.get(realmHelper.getNowPlayingTrack().getPosition() + 1);
        if(realmHelper.getNowPlayingTrack().isFromTrack()){
            entity.setFromTrack(true);
        }
        entity.setPosition(realmHelper.getNowPlayingTrack().getPosition() + 1);
        realmHelper.setNowPlayTrack(entity);
        mBinder.playMedia(realmHelper.getNowPlayingTrack().getPlayUrl32());
        loadListData();
    }

    public void playPrev(){
        List<TracksBeanList> beanList = mTRecyclerView.getAdapter().getData();
        TracksBeanList entity = beanList.get(realmHelper.getNowPlayingTrack().getPosition() - 1);
        if(realmHelper.getNowPlayingTrack().isFromTrack()){
            entity.setFromTrack(true);
        }
        entity.setPosition(realmHelper.getNowPlayingTrack().getPosition() - 1);
        realmHelper.setNowPlayTrack(entity);
        mBinder.playMedia(realmHelper.getNowPlayingTrack().getPlayUrl32());
        loadListData();
    }

    OnDismissListener dismissListener = new OnDismissListener() {
        @Override
        public void onDismiss() {
            saveData();
            backgroundAlpha(1f);
        }
    };

    public void show(View parent){
        backgroundAlpha(0.7f);
        if(!isShowing()){
            showAtLocation(parent, Gravity.NO_GRAVITY, 0, AppContext.getScreenHeight() / 4);
            mTRecyclerView.getRecyclerView().scrollToPosition(realmHelper.getNowPlayingTrack().getPosition());
        } else {
            dismiss();
        }
    }

    public boolean hasNext(){
        int count = AppContext.getPersistPreferences().getInt(AppConstant.MAX_COUNT, 0);
        return ((realmHelper.getNowPlayingTrack().getPosition() + 1) != count);
    }

    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = ((Activity)mContext).getWindow().getAttributes();
        lp.alpha = bgAlpha;
        ((Activity)mContext).getWindow().setAttributes(lp);
    }
}
