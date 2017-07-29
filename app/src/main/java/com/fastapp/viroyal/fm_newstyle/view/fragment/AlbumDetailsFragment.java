package com.fastapp.viroyal.fm_newstyle.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.model.base.Data;
import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanBean;

/**
 * Created by hanjiaqi on 2017/7/20.
 */

public class AlbumDetailsFragment extends Fragment{
    private static Data<HimalayanBean> entity;
    private TextView mAlbumDes;

    public static AlbumDetailsFragment newInstance(Data<HimalayanBean> data){
        Bundle bundle = new Bundle();
        AlbumDetailsFragment fragment = new AlbumDetailsFragment();
        entity = data;
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.album_detalis_layout,container,false);
        mAlbumDes = (TextView) view.findViewById(R.id.album_content_desc);
        mAlbumDes.setText(entity.getData().getAlbum().getIntro());
        return view;
    }
}
