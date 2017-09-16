package com.fastapp.viroyal.fm_newstyle.view.viewholder;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseViewHolder;


import butterknife.Bind;

/**
 * Created by hanjiaqi on 2017/6/29.
 */

public class CommFooterVH extends BaseViewHolder<Object>{
    public ProgressBar foot_progress;
    public TextView foot_text;
    public static final int FOOT_TYPE = R.layout.foot_view_layout;

    public CommFooterVH(View itemView) {
        super(itemView);
    }

    @Override
    public int getType() {
        return FOOT_TYPE;
    }


    @Override
    public void onBindViewHolder(View view, Object obj) {
        boolean hasmore = (obj != null? true : false);
        foot_progress.setVisibility(hasmore ? View.VISIBLE : View.GONE);
        foot_text.setText(hasmore ? AppContext.getStringById(R.string.foot_load_more)
                : AppContext.getStringById(R.string.foot_has_no_more));
    }
}
