package com.fastapp.viroyal.fm_newstyle.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.design.widget.AppBarLayout;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.R;

import java.util.concurrent.ExecutionException;


/**
 * Created by hanjiaqi on 2017/7/3.
 */

public class ImageUtils {
    public static void loadImage(Context context, String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.player_cover_default)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .preload();

        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.player_cover_default)
                .error(R.mipmap.player_cover_default)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target,
                                               boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model,
                                                   Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(view);
    }

    public static void loadCircleImage(final Context context, String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .preload();

        Glide.with(context).load(url).asBitmap().centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.player_cover_default)
                .error(R.mipmap.player_cover_default)
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
