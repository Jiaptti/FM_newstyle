package com.fastapp.viroyal.fm_newstyle.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.fastapp.viroyal.fm_newstyle.R;


/**
 * Created by hanjiaqi on 2017/7/3.
 */

public class ImageUtils {
    public static void loadImage(Context context, String url, ImageView view){
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);
    }

    public static void loadCircleImage(final Context context, String url, ImageView view){

        Glide.with(context).load(url).asBitmap().centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.kf5_image_loading)
                .error(R.mipmap.kf5_image_loading_failed)
                .into(new BitmapImageViewTarget(view) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                view.setImageDrawable(circularBitmapDrawable);
            }
        });
    }
}
