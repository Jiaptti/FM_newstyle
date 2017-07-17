package com.fastapp.viroyal.fm_newstyle.ui.home;

import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;

/**
 * Created by hanjiaqi on 2017/6/26.
 */

public class HomeModel implements HomeContract.Model{
    @Override
    public String[] getTabList() {
        String[] tabs = {AppContext.getStringById(R.string.tabs_crosstalk_sketch),
                AppContext.getStringById(R.string.tabs_exquisite_article),
                AppContext.getStringById(R.string.tabs_literati_writings)};
        return tabs;
    }
}
