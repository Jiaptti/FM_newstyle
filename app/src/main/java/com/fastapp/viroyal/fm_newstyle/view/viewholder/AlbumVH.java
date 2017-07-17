package com.fastapp.viroyal.fm_newstyle.view.viewholder;

import android.view.View;
import android.widget.TextView;

import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.base.BaseViewHolder;
import com.fastapp.viroyal.fm_newstyle.data.entity.TracksBeanList;

/**
 * Created by hanjiaqi on 2017/7/3.
 */

public class AlbumVH extends BaseViewHolder<TracksBeanList>{
    private TextView mAlbumName;

    public AlbumVH(View itemView) {
        super(itemView);
    }

    @Override
    public int getType() {
        return R.layout.album_item_layout;
    }

    @Override
    public void initViewHolder(View itemView) {
        mAlbumName = (TextView) itemView.findViewById(R.id.album_item_name);
    }

    @Override
    public void onBindViewHolder(View view, final TracksBeanList entity) {
        mAlbumName.setText(entity.getTitle());
    }


}
