package com.buhanzhe.gank.utils;

import android.content.Context;
import android.widget.Toast;

import com.buhanzhe.gank.App;

/**
 * Created by buhanzhe on 17/8/13.
 */

public class ToastUtil {

    private static Toast toast;
    private static Context context = App.getContext();

    private ToastUtil(){
        throw new AssertionError();
    }

    public static void showToast(int resId){
        showToast(context.getResources().getString(resId));
    }

    public static void showToast(int resId,int duration){
        showToast(context.getResources().getString(resId),duration);
    }

    public static void showToast(CharSequence text){
        showToast(text,Toast.LENGTH_SHORT);
    }

    public static void showToast(String text,int duration,Object...args){
        showToast(String.format(text,args),duration);
    }

    public static void showToast(CharSequence text,int duration){

        if(toast == null){
            toast =  Toast.makeText(context,text,duration);
        }else{
            toast.setText(text);
            toast.setDuration(duration);
        }
        toast.show();
    }
}
