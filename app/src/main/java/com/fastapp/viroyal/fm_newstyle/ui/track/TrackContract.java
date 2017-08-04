package com.fastapp.viroyal.fm_newstyle.ui.track;

import com.fastapp.viroyal.fm_newstyle.base.BaseModel;
import com.fastapp.viroyal.fm_newstyle.base.BasePresenter;
import com.fastapp.viroyal.fm_newstyle.base.BaseView;


/**
 * Created by hanjiaqi on 2017/8/3.
 */

public interface TrackContract {
    interface View extends BaseView{
    }

    interface Model extends BaseModel{
    }

    abstract class Presenter extends BasePresenter<View, Model>{

    }
}
