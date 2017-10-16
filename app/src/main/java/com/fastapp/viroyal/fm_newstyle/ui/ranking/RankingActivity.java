package com.fastapp.viroyal.fm_newstyle.ui.ranking;

import android.util.Log;
import android.widget.LinearLayout;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseActivity;
import com.fastapp.viroyal.fm_newstyle.base.RxManager;
import com.fastapp.viroyal.fm_newstyle.model.base.ErrorBean;
import com.fastapp.viroyal.fm_newstyle.util.JsonUtils;
import com.fastapp.viroyal.fm_newstyle.view.layout.TRecyclerView;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.RankingVH;

import butterknife.Bind;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017/9/10.
 */

public class RankingActivity extends BaseActivity<RankingPresenter,RankingModel>{
    @Bind(R.id.ranking_content)
    LinearLayout rankingContent;
    @Bind(R.id.ranking_list)
    TRecyclerView rankingList;
    private RxManager manager = new RxManager();

    @Override
    protected int layoutResID() {
        return R.layout.ranking_track_layout;
    }

    @Override
    protected void initView() {
        manager.on(AppConstant.UPDATE_ITEM_STATUS, new Action1() {
            @Override
            public void call(Object o) {
                rankingList.getAdapter().notifyDataSetChanged();
            }
        });

        manager.on(AppConstant.SAVE_DATA, new Action1() {
            @Override
            public void call(Object o) {
                if(o instanceof ErrorBean && ((ErrorBean)o).getClazz() == RankingVH.class){
                    JsonUtils.saveData(rankingList.getAdapter().getData(),rankingList.getMaxCount(), rankingList.getMaxPageId(), AppContext.getTempPageId());
                }
            }
        });
        setActionBarTitle(AppContext.getStringById(R.string.ranking_list));
        rankingList.setView(RankingVH.class, AppConstant.HOT_TRACKS_ID);
        rankingList.sendRequest();
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
    protected void onDestroy() {
        super.onDestroy();
        manager.clear(AppConstant.SAVE_DATA);
        manager.clear(AppConstant.UPDATE_ITEM_STATUS);
    }
}
