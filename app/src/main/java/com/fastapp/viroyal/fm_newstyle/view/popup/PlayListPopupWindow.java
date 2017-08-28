package com.fastapp.viroyal.fm_newstyle.view.popup;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.model.base.Data;
import com.fastapp.viroyal.fm_newstyle.view.layout.TRecyclerView;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.TrackListVH;
import android.widget.TextView;

/**
 * Created by hanjiaqi on 2017/8/24.
 */

public class PlayListPopupWindow extends PopupWindow{
    private Context mContext;
    private TRecyclerView mTRecyclerView;
    private TextView popupClose;

    public PlayListPopupWindow(Context context){
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.play_list_popup_layout, null);
        setContentView(view);
        mTRecyclerView = (TRecyclerView) view.findViewById(R.id.play_list_view);
        popupClose = (TextView)  view.findViewById(R.id.popup_close);
        mTRecyclerView.setBaseView(TrackListVH.class);
        backgroundAlpha(1f);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight((AppContext.getScreenHeight() / 2) + (AppContext.getScreenHeight() / 4));
        setFocusable(true);
        setAnimationStyle(R.style.anim_popup_window);
        setOnDismissListener(dismissListener);
        popupClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isShowing())
                    dismiss();
            }
        });
    }

    public <T> void setViewData(Data<T> data){
        if(mTRecyclerView != null){
            mTRecyclerView.setData(data);
        }
    }

    OnDismissListener dismissListener = new OnDismissListener() {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    };

    public void show(View parent){
        backgroundAlpha(0.7f);
        if(!isShowing()){
            if(mTRecyclerView != null){
                mTRecyclerView.loadData();
            }
            showAtLocation(parent, Gravity.NO_GRAVITY, 0, AppContext.getScreenHeight() / 4);
        } else {
            dismiss();
        }
    }

    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = ((Activity)mContext).getWindow().getAttributes();
        lp.alpha = bgAlpha;
        ((Activity)mContext).getWindow().setAttributes(lp);
    }
}
