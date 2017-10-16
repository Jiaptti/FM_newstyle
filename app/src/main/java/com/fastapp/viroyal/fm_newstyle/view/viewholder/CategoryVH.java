package com.fastapp.viroyal.fm_newstyle.view.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseViewHolder;
import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanEntity;
import com.fastapp.viroyal.fm_newstyle.ui.album.AlbumActivity;
import com.fastapp.viroyal.fm_newstyle.util.CommonUtils;
import com.fastapp.viroyal.fm_newstyle.util.ImageUtils;

/**
 * Created by hanjiaqi on 2017/7/6.
 */

public class CategoryVH extends BaseViewHolder<HimalayanEntity> {
    ImageView category_image;
    TextView category_title;
    TextView category_intro;
    TextView plays_counts;
    TextView tracks_counts;

    public CategoryVH(View itemView) {
        super(itemView);
    }

    @Override
    public int getType() {
        return R.layout.category_item_layout;
    }

    @Override
    public void onBindViewHolder(final View view, final HimalayanEntity entity) {
        if(!entity.isIsPaid()){
            ImageUtils.loadImage(mContext, entity.getCoverSmall(), category_image);
            category_title.setText(entity.getTitle());
            category_intro.setText(entity.getIntro());
            plays_counts.setText(CommonUtils.getOmitPlayCounts(entity.getPlaysCounts()));
            tracks_counts.setText(entity.getTracks() + "");
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.startActivity((Activity) mContext, new Intent(mContext, AlbumActivity.class).putExtra(AppConstant.ALBUM_BUNDLE, entity)
                            , ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, category_image, AlbumActivity.TRANSLATE_VIEW).toBundle());
                }
            });
        }
    }
}