package com.fastapp.viroyal.fm_newstyle.ui.album;

import android.graphics.drawable.AnimationDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseActivity;
import com.fastapp.viroyal.fm_newstyle.base.BaseListFragment;
import com.fastapp.viroyal.fm_newstyle.model.base.ErrorBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanEntity;
import com.fastapp.viroyal.fm_newstyle.util.CommonUtils;
import com.fastapp.viroyal.fm_newstyle.util.ImageUtils;
import com.fastapp.viroyal.fm_newstyle.view.SquareImageView;
import com.fastapp.viroyal.fm_newstyle.view.fragment.AlbumDetailsFragment;
import com.fastapp.viroyal.fm_newstyle.view.fragment.AlbumFragment;
import com.fastapp.viroyal.fm_newstyle.view.fragment.adapter.FragmentAdapter;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.AlbumVH;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import rx.functions.Action1;

/**
 * Created by hanjiaqi on 2017/7/3.
 */

public class AlbumActivity extends BaseActivity<AlbumPresenter, AlbumModel> implements AlbumContract.View {
    public static final String TRANSLATE_VIEW = "share_img";
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
    @Bind(R.id.album_content)
    LinearLayout albumContent;
    @Bind(R.id.album_tabs)
    TabLayout albumTabs;
    @Bind(R.id.album_pager)
    ViewPager albumPager;

    @Bind(R.id.loading_layout)
    LinearLayout loadingLayout;
    @Bind(R.id.loading_img)
    SquareImageView loadingImg;
    private AnimationDrawable animation;

    @Bind(R.id.net_error_layout)
    LinearLayout errorLayout;
    @Bind(R.id.reload)
    TextView reload;

    @Override
    protected int layoutResID() {
        return R.layout.album_layout;
    }


    @Override
    protected void initView() {
        HimalayanEntity himalayanEntity = (HimalayanEntity) getIntent().getSerializableExtra(AppConstant.ALBUM_BUNDLE);
        if (himalayanEntity != null) {
            initData(himalayanEntity);
        }
        setActionBarTitle(AppContext.getStringById(R.string.album_actionbar_title));
        ViewCompat.setTransitionName(albumImage, TRANSLATE_VIEW);
        presenter.getManager().on(AppConstant.LOADING_STATUS, new Action1() {
            @Override
            public void call(Object o) {
//                dismissLoading();
            }
        });

        presenter.getManager().on(AppConstant.ERROR_MESSAGE, new Action1() {
            @Override
            public void call(Object o) {
                ErrorBean errorBean = (ErrorBean) o;
                if (errorBean.getClazz() == AlbumVH.class) {
                    errorLayout.setVisibility(View.VISIBLE);
                    albumContent.setVisibility(View.GONE);
                }
            }
        });
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errorLayout.setVisibility(View.GONE);
                albumContent.setVisibility(View.VISIBLE);
//                presenter.getAlbumsList(albumId, tracks);
            }
        });


    }

    private void initData(HimalayanEntity himalayanEntity){
        albumTitle.setText(himalayanEntity.getTitle());
        ImageUtils.loadImage(mContext, himalayanEntity.getCoverSmall(), albumImage);
        albumAuthor.setText(himalayanEntity.getNickname());
        albumPlayCounts.setText(CommonUtils.getOmitAlbumCounts(himalayanEntity.getPlaysCounts()));
        albumType.setText(himalayanEntity.getCategoryName());
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(AlbumDetailsFragment.newInstance(himalayanEntity));
        fragments.add(BaseListFragment.newInstance(new AlbumFragment(), AlbumVH.class, himalayanEntity.getAlbumId()));
        albumPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragments,
                Arrays.asList(new String[]{AppContext.getStringById(R.string.album_details),
                        AppContext.getStringById(R.string.album_show) + "(" + himalayanEntity.getTracks() + ")"})));
        albumTabs.setupWithViewPager(albumPager);
        albumTabs.getTabAt(1).select();
    }

    @Override
    public boolean hasBackButton() {
        return true;
    }

    @Override
    public boolean supportActionBar() {
        return true;
    }

    @Override
    public boolean supportBottomPlay() {
        return true;
    }


    @Override
    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        albumContent.setVisibility(View.GONE);
        animation = (AnimationDrawable) loadingImg.getBackground();
        animation.start();
    }

    @Override
    public void dismissLoading() {
        loadingLayout.setVisibility(View.GONE);
        albumContent.setVisibility(View.VISIBLE);
        animation.stop();
    }
}
