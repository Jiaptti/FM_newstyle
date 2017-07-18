package com.fastapp.viroyal.fm_newstyle.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fastapp.viroyal.fm_newstyle.R;
import com.fastapp.viroyal.fm_newstyle.view.GlideCircleTransform;

/**
 * Created by hanjiaqi on 2017/7/3.
 */

public class ImageUtils {
    public static void loadImage(Context context, String url, ImageView view){
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .crossFade()
                .into(view);
    }

    public static void loadCircleImage(Context context, String url, ImageView view){
        Glide.with(context).load(url).transform(new GlideCircleTransform(context)).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .crossFade()
                .into(view);
    }
}
