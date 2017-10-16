package com.fastapp.viroyal.fm_newstyle.ui.navigation;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseActivity;
import com.fastapp.viroyal.fm_newstyle.base.BaseListFragment;
import com.fastapp.viroyal.fm_newstyle.base.RxManager;
import com.fastapp.viroyal.fm_newstyle.model.base.ErrorBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.NavigationBean;
import com.fastapp.viroyal.fm_newstyle.ui.settings.SettingsActivity;
import com.fastapp.viroyal.fm_newstyle.view.SquareImageView;
import com.fastapp.viroyal.fm_newstyle.view.fragment.navigation.NavigationFragment;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.NavigationVH;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * Created by hanjiaqi on 2017/9/18.
 */

public class NavigationActivity extends BaseActivity<NavigationPresenter, NavigationModel>
        implements NavigationContract.View, BaseListFragment.IFragmentTitle{
    @Bind(R.id.main_content)
    FrameLayout mainContent;
    @Bind(R.id.loading_layout)
    LinearLayout mLoadingLayout;
    @Bind(R.id.loading_img)
    SquareImageView mLoadingImg;
    @Bind(R.id.net_error_layout)
    LinearLayout mErrorLayout;
    @Bind(R.id.reload)
    TextView mReload;

    private NavigationFragment fragment;
    private long firstClickTime;
    private AnimationDrawable animation;

    @Override
    protected int layoutResID() {
        return R.layout.navigation_layout;
    }

    @Override
    protected void initView() {
        setActionBarTitle(AppContext.getStringById(R.string.navigation_title));
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        fragment = NavigationFragment.newInstance(AppConstant.NAVIGATION_TYPE);
        transaction.add(R.id.main_content, fragment, AppConstant.FRAGMENT_MAIN);
        transaction.commit();

        presenter.getManager().on(AppConstant.ERROR_MESSAGE, new Action1() {
            @Override
            public void call(Object o) {
                ErrorBean errorBean = (ErrorBean)o;
                if(errorBean.getClazz() == NavigationVH.class){
                    dismissLoading();
                    mainContent.setVisibility(View.GONE);
                    mErrorLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @OnClick(R.id.reload)
    public void onClick(View view){
        presenter.initNavigation();
        mainContent.setVisibility(View.VISIBLE);
        mErrorLayout.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_settings) {
            startActivity(new Intent(mContext, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean supportBottomPlay() {
        return true;
    }

    @Override
    public boolean supportActionBar() {
        return true;
    }

    @Override
    public void showNavigation(List<NavigationBean> list) {
        fragment.setData(list);
    }

    @Override
    public void showLoading() {
        mLoadingLayout.setVisibility(View.VISIBLE);
        mainContent.setVisibility(View.GONE);
        animation = (AnimationDrawable) mLoadingImg.getBackground();
        animation.start();
    }

    @Override
    public void dismissLoading() {
        mLoadingLayout.setVisibility(View.GONE);
        mainContent.setVisibility(View.VISIBLE);
        animation.stop();
    }

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - firstClickTime < 2000){
            AppContext.getInstance().unBindMediaService();
            super.onBackPressed();
        } else {
            if(getSupportFragmentManager().getBackStackEntryCount() > 0){
                getSupportFragmentManager().popBackStack();
            } else {
                firstClickTime = System.currentTimeMillis();
                AppContext.toastShort(R.string.tip_click_back_again_to_exist);
            }
        }
    }

    @Override
    public void setFragmentTitle(String title) {
        setActionBarTitle(title);
    }
}
