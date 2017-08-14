package com.buhanzhe.gank.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.buhanzhe.gank.App;
import com.buhanzhe.gank.Constants;
import com.buhanzhe.gank.R;
import com.buhanzhe.gank.bean.GankContent;
import com.buhanzhe.gank.listener.LikeListener;
import com.buhanzhe.gank.utils.ComUtil;
import com.buhanzhe.gank.utils.LogUtil;
import com.buhanzhe.gank.utils.SharedPrefsUtils;
import com.buhanzhe.gank.utils.http.NetWorkUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

/**
 * Created by zhanghao on 2017/4/30.
 */

public class GirlAdapter extends BaseMultiItemQuickAdapter<GankContent, BaseViewHolder> {
    private static final String TAG = "GirlAdapter";

    private SparseArray<Integer> heightArray;
    private int mWidth;
    private Context context;
    private LikeListener likeListener;


    public GirlAdapter(List<GankContent> data, LikeListener likeListener) {
        super(data);
        this.context = App.getContext();
        heightArray=new SparseArray<>();
        mWidth= (int) ComUtil.getScreenWidth(context)/2;
        addItemType(Constants.IMG, R.layout.girl_item_img_layout);
        addItemType(Constants.CONTENT, R.layout.gankitem_layout);
        addItemType(Constants.CONTENT_IMG, R.layout.gankitem_layout_with_img);
        this.likeListener = likeListener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, GankContent item) {

        LikeButton likeBt = null;
        if (helper.getItemViewType() == Constants.CONTENT_IMG || helper.getItemViewType() == Constants.CONTENT) {
            likeBt = helper.getView(R.id.gankitem_like_bt);
            likeBt.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    likeListener.onLiked(helper.getLayoutPosition() - 1);
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    likeListener.onUnLiked(helper.getLayoutPosition() - 1);
                }
            });
        }

        switch (helper.getItemViewType()) {
            case Constants.CONTENT_IMG:
                ImageView iv = helper.getView(R.id.gankitem_iv);
                if (item instanceof GankContent) {
                    GankContent gankContent = (GankContent) item;
                    assert likeBt != null;
                    likeBt.setTag(gankContent.get_id());
                    helper.setText(R.id.gankitem_title_tv, gankContent.getDesc());
                    helper.setText(R.id.gankitem_source_tv, "来源：" + gankContent.getSource());
                    helper.setText(R.id.gankitem_who_tv, "作者：" + gankContent.getWho());
                    boolean fav = gankContent.isFav();

                    LogUtil.d(TAG, String.valueOf(fav));

                    if (!fav && likeBt.getTag().equals(gankContent.get_id()))
                        likeBt.setLiked(false);
                    else
                        likeBt.setLiked(true);
                    boolean wifi = NetWorkUtil.isWifiConnected(context);
                    boolean wifi_only = SharedPrefsUtils.getBooleanPreference(Constants.WIFI_ONLY, false);
                    if (wifi_only) {
                        if (wifi)
                            Glide.with(context).load(gankContent.getImages().get(0)).centerCrop().placeholder(R.drawable.shadow).into(iv);
                    } else {
                        Glide.with(context).load(gankContent.getImages().get(0)).placeholder(R.drawable.shadow).centerCrop().into(iv);
                    }
                }
                break;
            case Constants.CONTENT:
                if (item instanceof GankContent) {
                    GankContent gankContent = (GankContent) item;
                    assert likeBt != null;
                    likeBt.setTag(gankContent.get_id());
                    helper.setText(R.id.gankitem_title_tv, gankContent.getDesc());
                    helper.setText(R.id.gankitem_source_tv, "来源：" + gankContent.getSource());
                    helper.setText(R.id.gankitem_who_tv, "作者：" + gankContent.getWho());
                    boolean fav = gankContent.isFav();
                    LogUtil.d(TAG, String.valueOf(fav));
                    if (!fav && likeBt.getTag().equals(gankContent.get_id()))
                        likeBt.setLiked(false);
                    else
                        likeBt.setLiked(true);
                }
                break;
            case Constants.IMG:
                ImageView photoIv = helper.getView(R.id.ganktype_photo_iv);
                setPhotos(helper,item,photoIv);
                break;
        }
    }



    private void setPhotos(BaseViewHolder helper, MultiItemEntity item, final ImageView photoIv){
        GankContent content= (GankContent) item;
        final int position=helper.getLayoutPosition();
        if (heightArray.get(position)==null){
            Glide.with(context)
                    .load(content.getUrl())
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            int originalHeight=resource.getHeight();
                            int originalWidth=resource.getWidth();
                            FrameLayout.LayoutParams layoutParams=
                                    (FrameLayout.LayoutParams) photoIv.getLayoutParams();
                            int height=resizeHeight(originalHeight,originalWidth);
                            layoutParams.height=height;
                            photoIv.setLayoutParams(layoutParams);
                            heightArray.put(position,height);
                        }
                        private int resizeHeight(int originalHeight,int originalWidth) {
                            int scale=originalWidth/mWidth;
                            return scale<=0?1:originalHeight/scale;
                        }
                    });
        }else{
            int height=heightArray.get(position);
            FrameLayout.LayoutParams layoutParams=
                    (FrameLayout.LayoutParams) photoIv.getLayoutParams();
            layoutParams.height=height;
            photoIv.setLayoutParams(layoutParams);
        }
        Glide.with(context)
                .load(content.getUrl())
                .fitCenter()
                .into(photoIv);
    }


}
