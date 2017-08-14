package com.buhanzhe.gank.utils.api;


import com.buhanzhe.gank.utils.api.converter.JsonToRequestBodyFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * @since 2017/2/13
 */

public class APIRetrofit {

    /**
     *  默认超时时间 单位/秒
     */
    private static final int DEFAULT_TIME_OUT = 10;

    private static Retrofit sRetrofit;

    private static OkHttpClient sOKHttpClient;

    public static APIService getAPIService(){
        return getInstance().create(APIService.class);
    }

    public static Retrofit getInstance(){
        if(sRetrofit== null){
            synchronized (APIRetrofit.class){
                if(sRetrofit == null){
                    GsonBuilder builder = new GsonBuilder();
                    builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                            return new Date(json.getAsJsonPrimitive().getAsLong());
                        }
                    });
                    Gson gson = builder.create();
                    sRetrofit = new Retrofit.Builder()
                            .baseUrl("https://d.apicloud.com")
                            .addConverterFactory(JsonToRequestBodyFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(getsOKHttpClient())
                            .build();

                }
            }
        }

        return sRetrofit;
    }


    public static OkHttpClient getsOKHttpClient(){
        if(sOKHttpClient == null){
            synchronized (APIRetrofit.class){

                if(sOKHttpClient == null){
                    sOKHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                            .readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                            .writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                            .build();
                }
            }
        }

        return sOKHttpClient;
    }


}
