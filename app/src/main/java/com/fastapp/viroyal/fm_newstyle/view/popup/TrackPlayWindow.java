package com.fastapp.viroyal.fm_newstyle.view.popup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;

import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.service.AlbumPlayService;
import com.fastapp.viroyal.fm_newstyle.view.SquareImageView;

/**
 * Created by Administrator on 2017/8/6.
 */

public class TrackPlayWindow extends PopupWindow{
    private Context mContext;
    private SquareImageView playBack;
    private SquareImageView playForward;
    private AlbumPlayService.PlayBinder mBinder;

    public TrackPlayWindow(Context context, AlbumPlayService.PlayBinder mBinder){
        View view = LayoutInflater.from(context).inflate(R.layout.track_popup_view, null);
        playBack = (SquareImageView) view.findViewById(R.id.play_back_time);
        playForward = (SquareImageView) view.findViewById(R.id.play_forward_time);
        this.mBinder = mBinder;
    }

    private OnClickListener playOnclick = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view == playBack){
            }else if(view == playForward){

            }
        }
    };
}
