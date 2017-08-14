package com.buhanzhe.gank.utils;

import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.buhanzhe.gank.App;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by buhanzhe on 17/8/6.
 */

public class ImageLoaderUtil {

    public static void LoadImage(String imgUrl,@DrawableRes int defaultImg,ImageView imageView) {
        Glide.with(App.getContext()).load(imgUrl).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(defaultImg).centerCrop().into(imageView);
    }

    public static void loadRoundedCornersImage(String imgUrl, @DrawableRes int defaultImg, ImageView imageView) {
        Glide.with(App.getContext()).load(imgUrl).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(defaultImg).centerCrop().bitmapTransform(new RoundedCornersTransformation(App.getContext(), 20, 0, RoundedCornersTransformation.CornerType.ALL))
                .into(imageView);
    }

    public static void loadCircleImage(String imgUrl, @DrawableRes int defaultImg, ImageView imageView) {
        Glide.with(App.getContext()).load(imgUrl).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(defaultImg).centerCrop().bitmapTransform(new CropCircleTransformation(App.getContext())).into(imageView);
    }
}
