package com.fastapp.viroyal.fm_newstyle.ui.settings;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseActivity;
import com.fastapp.viroyal.fm_newstyle.base.BaseModel;
import com.fastapp.viroyal.fm_newstyle.base.BasePresenter;
import com.fastapp.viroyal.fm_newstyle.view.fragment.settings.SettingsFragment;

import butterknife.Bind;

/**
 * Created by hanjiaqi on 2017/9/20.
 */

public class SettingsActivity extends BaseActivity<BasePresenter, BaseModel> {
    @Bind(R.id.settings_main)
    FrameLayout main;

    @Override
    protected int layoutResID() {
        return R.layout.settings_main_layout;
    }

    @Override
    protected void initView() {
        setActionBarTitle(AppContext.getStringById(R.string.settings_title));
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        SettingsFragment fragment = SettingsFragment.newInstance(AppConstant.SETTINGS_TYPE);
        transaction.add(R.id.settings_main, fragment);
        transaction.commit();
    }

    public void setFragmentTitle(String title){
        setActionBarTitle(title);
    }

    @Override
    public boolean supportActionBar() {
        return true;
    }

    @Override
    public boolean hasBackButton() {
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
