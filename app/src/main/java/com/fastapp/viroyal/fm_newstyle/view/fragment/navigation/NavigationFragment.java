package com.fastapp.viroyal.fm_newstyle.view.fragment.navigation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.AppContext;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseListFragment;
import com.fastapp.viroyal.fm_newstyle.base.RxManager;
import com.fastapp.viroyal.fm_newstyle.model.base.ErrorBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.NavigationBean;
import com.fastapp.viroyal.fm_newstyle.ui.navigation.NavigationActivity;
import com.fastapp.viroyal.fm_newstyle.view.adapter.navigation.NavigationAdapter;
import com.fastapp.viroyal.fm_newstyle.view.fragment.category.CategoryFragment;
import com.fastapp.viroyal.fm_newstyle.view.layout.RecycleViewDivider;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.CategoryVH;
import com.fastapp.viroyal.fm_newstyle.view.viewholder.NavigationVH;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by hanjiaqi on 2017/9/19.
 */

public class NavigationFragment extends Fragment implements NavigationVH.NavigationOnClickListener {
    private Context context;
    private RecyclerView mRecycleView;
    private GridLayoutManager mGridLayoutManager;
    private NavigationAdapter mAdapter;
    private List<NavigationBean> list;

    public static NavigationFragment newInstance(int type) {
        NavigationFragment fragment = new NavigationFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(AppConstant.TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        View view = inflater.inflate(R.layout.navigation_fragment_layout, container, false);
        mRecycleView = (RecyclerView) view.findViewById(R.id.navigation_content);
        mGridLayoutManager = new GridLayoutManager(context, 2);
        mRecycleView.setLayoutManager(mGridLayoutManager);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        mRecycleView.addItemDecoration(new RecycleViewDivider(context, GridLayoutManager.VERTICAL, 2, R.color.light_gray));
        return view;
    }

    public void setData(List<NavigationBean> list) {
        this.list = list;
        mAdapter = new NavigationAdapter(context, list);
        mRecycleView.setAdapter(mAdapter);
        mAdapter.setViewClickListener(this);
    }

    @Override
    public void onViewclick(View view, int position) {
        NavigationBean bean = list.get(position);
        CategoryFragment categoryFragment = (CategoryFragment) BaseListFragment.newInstance(new CategoryFragment(), CategoryVH.class, bean.getId());
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.hide(this);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(R.id.main_content, categoryFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        ((NavigationActivity) getActivity()).setFragmentTitle(bean.getTitle());
    }
}
