package com.fastapp.viroyal.fm_newstyle.ui.album;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseActivity;
import com.fastapp.viroyal.fm_newstyle.data.base.Data;
import com.fastapp.viroyal.fm_newstyle.data.entity.HimalayanBean;
import com.fastapp.viroyal.fm_newstyle.util.CommonUtils;
import com.fastapp.viroyal.fm_newstyle.util.ImageUtils;
import com.fastapp.viroyal.fm_newstyle.view.SquareImageView;
import com.fastapp.viroyal.fm_newstyle.view.layout.TRecyclerView;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.AlbumVH;

import butterknife.Bind;

/**
 * Created by hanjiaqi on 2017/7/3.
 */

public class AlbumActivity extends BaseActivity <AlbumPresenter, AlbumModel> implements AlbumContract.View{
    @Bind(R.id.album_title)
    TextView albumTitle;
    @Bind(R.id.album_image)
    SquareImageView albumImage;
    @Bind(R.id.album_author)
    TextView albumAuthor;
    @Bind(R.id.album_play_counts)
    TextView albumPlayCounts;
    @Bind(R.id.album_type)
    TextView albumType;
    @Bind(R.id.album_list)
    TRecyclerView albumList;
    private int albumId;

    @Override
    protected int layoutResID() {
        return R.layout.album_layout;
    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getBundleExtra(AppConstant.ALBUM_BUNDLE);
        if(bundle != null){
            albumId = bundle.getInt(AppConstant.ALBUM_ID);
            presenter.getAlbumsList(albumId);
        }
    }

    @Override
    public void showAlbumMessage(Data<HimalayanBean> data) {
        albumTitle.setText(data.getData().getAlbum().getTitle());
        ImageUtils.loadImage(this, data.getData().getAlbum().getCoverSmall(), albumImage);
        albumAuthor.setText(data.getData().getUser().getNickname());
        albumPlayCounts.setText(CommonUtils.getOmitAlbumCounts(data.getData().getAlbum().getPlayTimes()));
        albumType.setText(data.getData().getAlbum().getCategoryName());
        albumList.setViewById(AlbumVH.class, albumId);
    }
}
