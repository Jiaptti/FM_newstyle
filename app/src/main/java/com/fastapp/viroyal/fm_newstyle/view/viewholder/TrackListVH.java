package com.fastapp.viroyal.fm_newstyle.view.viewholder;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseViewHolder;
import com.fastapp.viroyal.fm_newstyle.base.RxManager;
import com.fastapp.viroyal.fm_newstyle.db.RealmHelper;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksBeanList;
import com.fastapp.viroyal.fm_newstyle.service.AlbumPlayService;
import com.fastapp.viroyal.fm_newstyle.view.SquareImageView;

import rx.functions.Action1;

/**
 * Created by hanjiaqi on 2017/8/25.
 */

public class TrackListVH extends BaseViewHolder<TracksBeanList>{
    private SquareImageView track_item_wave_flag;
    private TextView track_item_name;
    private AnimationDrawable animation;
    private RealmHelper helper = AppContext.getRealmHelper();
    private RxManager manager = new RxManager();
    private AlbumPlayService.PlayBinder mBinder = AppContext.getMediaPlayService();

    public TrackListVH(View itemView) {
        super(itemView);
        if (itemView instanceof RelativeLayout) {
            manager.on(AppConstant.MEDIA_START_PLAY, new Action1() {
                @Override
                public void call(Object o) {
                    if (o instanceof Integer) {
                        switch ((Integer) o) {
                            case AppConstant.STATUS_PLAY:
                            case AppConstant.STATUS_RESUME:
                                if(animation != null && !animation.isRunning())
                                    animation.start();
                                break;
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getType() {
        return R.layout.play_list_popup_item_layout;
    }

    @Override
    public void onBindViewHolder(View view, final TracksBeanList entity) {
        track_item_name.setText(entity.getTitle());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (helper.getNowPlayingTrack().getTrackId() != entity.getTrackId()
                        || (helper.getNowPlayingTrack().getTrackId() == entity.getTrackId()
                        && AppContext.getPlayState() == AppConstant.STATUS_PAUSE)) {
                    if(AppContext.getRealmHelper().getNowPlayingTrack().isFromTrack()){
                        entity.setFromTrack(true);
                    }
                    entity.setPosition(getPosition());
//                    helper.setNowPlayTrack(entity);
                    manager.post(AppConstant.CURRENT_POSITION_VIEW, entity);
                    manager.post(AppConstant.UPDATE_TRACKS_UI, null);
                }
            }
        });
        setPlayStatus(entity);
    }

    private void setPlayStatus(TracksBeanList entity) {
        if (helper.getNowPlayingTrack().getTrackId() == entity.getTrackId()) {
            animation = (AnimationDrawable) track_item_wave_flag.getBackground();
            track_item_wave_flag.setVisibility(View.VISIBLE);
            if (mBinder.isPlaying()) {
                if (animation != null && !animation.isRunning()) {
                    animation.start();
                }
            } else {
                if (animation != null && animation.isRunning()) {
                    animation.stop();
                }
            }
            if(AppContext.getPlayState() == AppConstant.STATUS_PLAY || AppContext.getPlayState() == AppConstant.STATUS_RESUME){
                track_item_name.setTextColor(Color.RED);
            } else {
                track_item_name.setTextColor(Color.BLACK);
            }
        } else {
            track_item_wave_flag.setVisibility(View.GONE);
            track_item_name.setTextColor(Color.BLACK);
        }
    }
}
