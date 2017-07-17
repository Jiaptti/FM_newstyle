package com.fastapp.viroyal.fm_newstyle.view.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseViewHolder;
import com.fastapp.viroyal.fm_newstyle.data.entity.HimalayanEntity;
import com.fastapp.viroyal.fm_newstyle.ui.album.AlbumActivity;
import com.fastapp.viroyal.fm_newstyle.util.CommonUtils;
import com.fastapp.viroyal.fm_newstyle.util.ImageUtils;

/**
 * Created by hanjiaqi on 2017/7/6.
 */

public class CategoryVH extends BaseViewHolder<HimalayanEntity> {
    ImageView albumImage;
    TextView albumTitle;
    TextView albumIntro;
    TextView playsCounts;
    TextView tracksCounts;

    public CategoryVH(View itemView) {
        super(itemView);
    }

    @Override
    public int getType() {
        return R.layout.category_layout;
    }

    @Override
    public void initViewHolder(View itemView) {
        albumImage = (ImageView) itemView.findViewById(R.id.category_image);
        albumTitle = (TextView) itemView.findViewById(R.id.category_title);
        albumIntro = (TextView) itemView.findViewById(R.id.category_intro);
        playsCounts = (TextView) itemView.findViewById(R.id.plays_counts);
        tracksCounts = (TextView) itemView.findViewById(R.id.tracks_counts);
    }

    @Override
    public void onBindViewHolder(View view, final HimalayanEntity entity) {
        ImageUtils.loadImage(mContext, entity.getCoverSmall(), albumImage);
        albumTitle.setText(entity.getTitle());
        albumIntro.setText(entity.getIntro());
        playsCounts.setText(CommonUtils.getOmitPlayCounts(entity.getPlaysCounts()));
        tracksCounts.setText(entity.getTracks() + "");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(AppConstant.ALBUM_ID, entity.getAlbumId());
                Intent intent = new Intent(mContext, AlbumActivity.class);
                intent.putExtra(AppConstant.ALBUM_BUNDLE,bundle);
                ActivityCompat.startActivity((Activity) mContext, intent, null);
            }
        });
    }
}