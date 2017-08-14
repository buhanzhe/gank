package com.buhanzhe.gank.utils.api;


import com.buhanzhe.gank.bean.GankItem;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by zhanghao on 2017/4/20.
 */

public interface GankService {

    @GET("data/{type}/{count}/{page}")
    Observable<GankItem> getTypeData
            (
                    @Path("type") String type,
                    @Path("count") int count,
                    @Path("page") int page
            );
}
