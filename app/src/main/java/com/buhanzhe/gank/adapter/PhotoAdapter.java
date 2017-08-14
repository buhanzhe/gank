package com.buhanzhe.gank.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buhanzhe.gank.R;
import com.buhanzhe.gank.utils.LogUtil;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

/**
 * Created by zhanghao on 2017/4/30.
 */

public class PhotoAdapter extends PagerAdapter{

    private Context context;
    private ArrayList<String> mDatas;


    public PhotoAdapter(Context context, ArrayList<String> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LogUtil.d("ViewPager","初始化");

        View view= LayoutInflater.from(context).inflate(R.layout.photo_item_layout,container,false);

        PhotoView photoView= (PhotoView) view.findViewById(R.id.photo_pv);

        Glide.with(context).load(mDatas.get(position)).into(photoView);
        container.addView(view);



        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        LogUtil.d("ViewPager","销毁");
        View view = (View) object;
        container.removeView(view);
    }


}
