package com.fastapp.viroyal.fm_newstyle.view.viewholder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseViewHolder;
import com.fastapp.viroyal.fm_newstyle.data.entity.TracksBeanList;
import com.fastapp.viroyal.fm_newstyle.util.CommonUtils;
import com.fastapp.viroyal.fm_newstyle.util.ImageUtils;
import com.fastapp.viroyal.fm_newstyle.view.CircleImageView;
import com.fastapp.viroyal.fm_newstyle.view.SquareImageView;

/**
 * Created by hanjiaqi on 2017/7/3.
 */

public class AlbumVH extends BaseViewHolder<TracksBeanList>{
    private TextView mAlbumName;
    private TextView mPlayTimes;
    private SquareImageView mAlbumImage;
    private SquareImageView mFlagWave;
    private TextView mTimesAgo;
    private TextView mTimeDuration;

    public AlbumVH(View itemView) {
        super(itemView);
    }

    @Override
    public int getType() {
        return R.layout.album_item_layout;
    }

    @Override
    public void initViewHolder(View view) {
        mAlbumName = (TextView) view.findViewById(R.id.album_item_name);
        mPlayTimes = (TextView) view.findViewById(R.id.album_play_times);
        mTimesAgo = (TextView) view.findViewById(R.id.times_ago);
        mTimeDuration = (TextView) view.findViewById(R.id.album_plays_duration);
        mAlbumImage = (SquareImageView) view.findViewById(R.id.album_item_image);
        mFlagWave = (SquareImageView) view.findViewById(R.id.play_flag_wave);
    }

    @Override
    public void onBindViewHolder(View view, final TracksBeanList entity) {
        mAlbumName.setText(entity.getTitle());
        mPlayTimes.setText(CommonUtils.getOmitPlayCounts(entity.getPlaytimes()));
        ImageUtils.loadCircleImage(view.getContext(), entity.getCoverSmall(), mAlbumImage);
        mAlbumImage.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        mTimesAgo.setText(CommonUtils.getIntervalDays(entity.getCreatedAt()));
        mTimeDuration.setText(CommonUtils.getPlayTime(entity.getDuration()));
        mFlagWave.setBackgroundResource(R.drawable.play_flag_wave);
        final AnimationDrawable animation = (AnimationDrawable)mFlagWave.getBackground();

        mAlbumImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        mAlbumImage.clearColorFilter();
                        if(!animation.isRunning()){
                            animation.start();
                            mAlbumName.setTextColor(Color.RED);
                        } else {
                            animation.stop();
                            mAlbumName.setTextColor(Color.BLACK);
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        mAlbumImage.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }
}
