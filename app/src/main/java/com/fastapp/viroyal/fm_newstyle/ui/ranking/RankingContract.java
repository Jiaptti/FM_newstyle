package com.fastapp.viroyal.fm_newstyle.ui.ranking;

import com.fastapp.viroyal.fm_newstyle.base.BaseModel;
import com.fastapp.viroyal.fm_newstyle.base.BasePresenter;
import com.fastapp.viroyal.fm_newstyle.base.BaseView;

/**
 * Created by Administrator on 2017/9/10.
 */

public interface RankingContract {
    interface View extends BaseView{

    }

    interface Model extends BaseModel{
    }

    abstract class Presenter extends BasePresenter<View, Model>{

    }
}
