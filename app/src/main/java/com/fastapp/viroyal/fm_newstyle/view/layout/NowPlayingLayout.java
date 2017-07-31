package com.fastapp.viroyal.fm_newstyle.view.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.RxManager;
import com.fastapp.viroyal.fm_newstyle.db.RealmHelper;
import com.fastapp.viroyal.fm_newstyle.model.realm.NowPlayTrack;
import com.fastapp.viroyal.fm_newstyle.util.ImageUtils;
import com.fastapp.viroyal.fm_newstyle.view.SquareImageView;

import rx.functions.Action1;

/**
 * Created by hanjiaqi on 2017/7/25.
 */

public class NowPlayingLayout extends LinearLayout{
    private SquareImageView mNowPlayingImg;
    private SquareImageView mNowPlayingStatus;
    private RxManager mRxManager = new RxManager();
    private RealmHelper mHelper;

    public int mWidth;
    public int mHeight;

    public NowPlayingLayout(Context context) {
        super(context);
        init(context);
    }

    public NowPlayingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.now_playing_layout, this);
        mHelper = new RealmHelper(context);
        mNowPlayingImg = (SquareImageView) view.findViewById(R.id.now_playing_image);
        mNowPlayingStatus = (SquareImageView) view.findViewById(R.id.now_playing_status);
        mWidth = mNowPlayingStatus.getLayoutParams().width;
        mHeight = mNowPlayingStatus.getLayoutParams().height;
        NowPlayTrack nowPlayTrack = mHelper.getNowPlayingTrack();

        mRxManager.on(AppConstant.UPDATE_ITEM_STATUS, new Action1() {
            @Override
            public void call(Object o) {
                if(o instanceof NowPlayTrack){
                    Log.i(AppConstant.TAG, "NowPlayingLayout UPDATE_ITEM_STATUS");
//                    setNowPlayingImg(((NowPlayTrack)o).getCoverSmall());
//                    mNowPlayingStatus.setBackgroundResource(R.mipmap.play_icon_bg);
                }
            }
        });

        if(nowPlayTrack != null){
            ImageUtils.loadCircleImage(AppContext.getAppContext(), nowPlayTrack.getCoverSmall(), mNowPlayingImg);
        }
        mNowPlayingStatus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void setNowPlayingImg(String url){
        ImageUtils.loadCircleImage(AppContext.getAppContext(), url, mNowPlayingImg);
    }
}
