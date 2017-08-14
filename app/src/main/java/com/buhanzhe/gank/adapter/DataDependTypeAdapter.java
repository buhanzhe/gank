package com.buhanzhe.gank.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.buhanzhe.gank.App;
import com.buhanzhe.gank.Constants;
import com.buhanzhe.gank.R;
import com.buhanzhe.gank.bean.GankContent;
import com.buhanzhe.gank.bean.GankSection;
import com.buhanzhe.gank.utils.ComUtil;
import com.buhanzhe.gank.utils.ImageLoaderUtil;
import com.buhanzhe.gank.utils.SharedPrefsUtils;
import com.buhanzhe.gank.utils.http.NetWorkUtil;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by buhanzhe on 17/7/22.
 */

public class DataDependTypeAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity,BaseViewHolder> {

    private Context context;
    public DataDependTypeAdapter(List<MultiItemEntity> datas) {
        super(datas);
        this.context = App.getContext();
        addItemType(Constants.IMG, R.layout.gankitem_img_layout);
        addItemType(Constants.SECTION, R.layout.gankitem_section_layout);
        addItemType(Constants.CONTENT, R.layout.gankitem_layout);
        addItemType(Constants.CONTENT_IMG, R.layout.gankitem_layout_with_img);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case Constants.SECTION:
                if (item instanceof GankSection) {
                    GankSection section = (GankSection) item;
                    helper.setText(R.id.gankitem_section_tv, section.getSection());
                }
                break;
            case Constants.CONTENT_IMG:
                ImageView iv = helper.getView(R.id.gankitem_iv);

                if (item instanceof GankContent) {
                    GankContent gankContent = (GankContent) item;
                    helper.setText(R.id.gankitem_title_tv, gankContent.getDesc());
                    helper.setText(R.id.gankitem_source_tv, "来源：" + gankContent.getSource());
                    helper.setText(R.id.gankitem_who_tv, "作者：" + gankContent.getWho());
                    boolean fav = gankContent.isFav();
                    boolean wifi = NetWorkUtil.isWifiConnected(context);
                    boolean wifi_only = SharedPrefsUtils.getBooleanPreference(Constants.WIFI_ONLY, false);
                    if (wifi_only) {
                        if (wifi)
                            ImageLoaderUtil.loadRoundedCornersImage(gankContent.getImages().get(0),R.drawable.shadow,iv);
                    } else {
                        ImageLoaderUtil.loadRoundedCornersImage(gankContent.getImages().get(0),R.drawable.shadow,iv);
                    }
                }

                break;
            case Constants.CONTENT:
                if (item instanceof GankContent) {
                    GankContent gankContent = (GankContent) item;
                    helper.setText(R.id.gankitem_title_tv, gankContent.getDesc());
                    helper.setText(R.id.gankitem_source_tv, "来源：" + gankContent.getSource());
                    helper.setText(R.id.gankitem_who_tv, "作者：" + gankContent.getWho());
                    boolean fav = gankContent.isFav();
                }

                break;
            case Constants.IMG:
                ImageView ivHeader = helper.getView(R.id.gankitem_photo_iv);
                if (item instanceof GankContent) {
                    GankContent gankContent = (GankContent) item;
                    ImageLoaderUtil.LoadImage(gankContent.getUrl(),R.drawable.shadow,ivHeader);
                    helper.setText(R.id.gankitem_time_tv, ComUtil.getFormatDate(gankContent.getPublishedAt()));
                }
                break;
        }
    }
}
