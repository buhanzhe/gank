package com.buhanzhe.gank;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.buhanzhe.gank.utils.NeverCrash;

import skin.support.SkinCompatManager;
import skin.support.app.SkinCardViewInflater;
import skin.support.design.app.SkinMaterialViewInflater;


public class App extends Application{


    private static App app;


    @Override
    public void onCreate() {
        super.onCreate();
        //绝不闪退
        NeverCrash.init(new NeverCrash.CrashHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Log.e("NeverCrash", e.getMessage());
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        //皮肤
        SkinCompatManager.init(this)
                .addInflater(new SkinCardViewInflater())
                .addInflater(new SkinMaterialViewInflater())
                .loadSkin();
        app =this;
    }

    public static Context getContext(){
        return app;
    }

}
