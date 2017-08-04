package com.fastapp.viroyal.fm_newstyle.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.fastapp.viroyal.fm_newstyle.R;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.util.concurrent.ExecutionException;


/**
 * Created by hanjiaqi on 2017/7/3.
 */

public class ImageUtils {
    public static void loadImage(Context context, String url, ImageView view){
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.ic_tyq_albums)
                .error(R.mipmap.ic_tyq_albums)
                .into(view);
    }

    public static void loadImageByBitmap(Context context, String url, final SimpleExoPlayerView mExoPlayerView){
        Glide.with(context).load(url).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.ic_tyq_albums)
                .error(R.mipmap.ic_tyq_albums).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                mExoPlayerView.setDefaultArtwork(resource);
            }
        });
    }

    public static void loadCircleImage(final Context context, String url, ImageView view){

        Glide.with(context).load(url).asBitmap().centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.ic_tyq_albums)
                .error(R.mipmap.ic_tyq_albums)
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
