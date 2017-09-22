package com.fastapp.viroyal.fm_newstyle.ui.navigation;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseActivity;
import com.fastapp.viroyal.fm_newstyle.base.BaseListFragment;
import com.fastapp.viroyal.fm_newstyle.model.entity.NavigationBean;
import com.fastapp.viroyal.fm_newstyle.ui.settings.SettingsActivity;
import com.fastapp.viroyal.fm_newstyle.view.fragment.navigation.NavigationFragment;

import java.util.List;

import butterknife.Bind;

/**
 * Created by hanjiaqi on 2017/9/18.
 */

public class NavigationActivity extends BaseActivity<NavigationPresenter, NavigationModel>
        implements NavigationContract.View, BaseListFragment.IFragmentTitle{
    @Bind(R.id.main_content)
    FrameLayout mainContent;
    private NavigationFragment fragment;
    private long firstClickTime;

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

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - firstClickTime < 2000){
            AppContext.getInstance().unBindMediaService();
            super.onBackPressed();
        } else {
            if(getSupportFragmentManager().getBackStackEntryCount() > 0){
                setSwitchState(View.GONE);
                setActionBarTitle(AppContext.getStringById(R.string.navigation_title));
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
