package com.fastapp.viroyal.fm_newstyle.ui.ranking;

import android.widget.LinearLayout;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseActivity;
import com.fastapp.viroyal.fm_newstyle.view.layout.TRecyclerView;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.RankingVH;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/9/10.
 */

public class RankingActivity extends BaseActivity<RankingPresenter,RankingModel>{
    @Bind(R.id.ranking_content)
    LinearLayout rankingContent;
    @Bind(R.id.ranking_list)
    TRecyclerView rankingList;

    @Override
    protected int layoutResID() {
        return R.layout.ranking_track_layout;
    }

    @Override
    protected void initView() {
        rankingList.setView(RankingVH.class, AppConstant.HOT_TRACKS_ID);
        rankingList.sendRequest();
    }
}
