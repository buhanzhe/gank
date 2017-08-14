package com.buhanzhe.gank.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.buhanzhe.gank.Constants;
import com.buhanzhe.gank.bean.GankContent;
import com.buhanzhe.gank.activity.PhotoActivity;
import com.buhanzhe.gank.activity.WebActivity;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghao on 2017/3/17.
 */

public class ActivityUtil {
    private static final String TAG = "ActivityUtil";


    public static void gotoWebActivity(Context context,String url,String title){
        Intent intent=new Intent(context, WebActivity.class);
        intent.putExtra(Constants.KEY_URL,url);
        intent.putExtra(Constants.KEY_TITLE,title);
        context.startActivity(intent);
    }

    public static void gotoPhotoActivity(Activity activity, View view, List<MultiItemEntity> list, int pos){
        ArrayList<String> urls=new ArrayList<>();
        ArrayList<String> ids = new ArrayList<>();
        for (MultiItemEntity itemEntity : list) {
            if (itemEntity instanceof GankContent){
                GankContent content = (GankContent) itemEntity;
                urls.add(content.getUrl());
                ids.add(content.get_id());
            }
        }
        Intent intent = new Intent(activity, PhotoActivity.class);
        intent.putStringArrayListExtra("urls",urls);
        intent.putStringArrayListExtra("ids",ids);
        intent.putExtra("position",pos);
        ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeClipRevealAnimation(view,view.getWidth()/2,view.getHeight()/2,0,0);
        ActivityCompat.startActivity(activity,intent,optionsCompat.toBundle());

    }

    public static void gotoPhotoActivityFromRecommend(Activity activity,View view,List<GankContent> list,int pos){
        ArrayList<String> urls=new ArrayList<>();
        ArrayList<String> ids = new ArrayList<>();
        for (GankContent content : list) {
                urls.add(content.getUrl());
                ids.add(content.get_id());
        }
        Intent intent = new Intent(activity, PhotoActivity.class);
        intent.putStringArrayListExtra("urls",urls);
        intent.putStringArrayListExtra("ids",ids);
        intent.putExtra("position",pos);
        ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeClipRevealAnimation(view,view.getWidth()/2,view.getHeight()/2,0,0);
        ActivityCompat.startActivity(activity,intent,optionsCompat.toBundle());

    }

    public static void gotoPhotoActivityH(Activity activity,View view,
                                          List<MultiItemEntity> itemEntities,int pos){
        ArrayList<String> urls=new ArrayList<>();
        ArrayList<String> ids=new ArrayList<>();
        List<GankContent> gankContentList = new ArrayList<>();
        for(MultiItemEntity item :itemEntities){
            GankContent gankContent = (GankContent) item;
            if(gankContent.getType().equals("福利")){
                gankContentList.add(gankContent);
                urls.add(gankContent.getUrl());
                ids.add(gankContent.get_id());
            }
        }
        //获得被选中的图片在图片列表的位置
        GankContent gankContent= (GankContent) itemEntities.get(pos);
        int realPos=gankContentList.indexOf(gankContent);
        LogUtil.d(TAG,realPos);
        Intent intent = new Intent(activity, PhotoActivity.class);
        intent.putStringArrayListExtra("urls",urls);
        intent.putStringArrayListExtra("ids",ids);
        intent.putExtra("position",realPos);
        ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeClipRevealAnimation(view,view.getWidth()/2,view.getHeight()/2,0,0);
        ActivityCompat.startActivity(activity,intent,optionsCompat.toBundle());

    }

    public static void gotoSetting(Context context) {
        Uri uri=Uri.parse("package:" + context.getPackageName());
        context.startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,uri));
    }

    public static void gotoAboutBrowser(Context context) {
        Uri uri = Uri.parse("https://github.com/mask-hao/MyGank");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }
}
