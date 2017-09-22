package com.fastapp.viroyal.fm_newstyle.view.fragment.recent;

import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.model.realm.RecentListen;

import java.util.List;

/**
 * Created by hanjiaqi on 2017/9/21.
 */

public class RecentModel implements RecentContract.Model{
    @Override
    public List<RecentListen> getRecentTrackList() {
        return AppContext.getRealmHelper().getAllRecent();
    }
}
