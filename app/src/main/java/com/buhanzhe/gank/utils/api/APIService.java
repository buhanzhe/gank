package com.buhanzhe.gank.utils.api;


import com.buhanzhe.gank.bean.LoginSuccess;
import com.buhanzhe.gank.bean.RegisterSuccess;
import com.buhanzhe.gank.bean.User;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */

public interface APIService {

    @POST("/mcm/api/user/login")
    Observable<LoginSuccess> Login(@HeaderMap Map<String,String> headers, @Body JSONObject jsonObject);

    @POST("/mcm/api/user")
    Observable<RegisterSuccess> register(@HeaderMap HashMap<String,String> headers, @Body JSONObject jsonObject);

    @GET("/mcm/api/user/{userId}")
    Observable<User> getUser(@HeaderMap HashMap<String,String> headers, @Path("userId") String userIdStr);

    @POST("/mcm/api/user/{userId}")
    Observable<ResponseBody> updateUser(@HeaderMap HashMap<String,String> headers, @Path("userId") String userIdStr, @Body JSONObject jsonObject);

    @FormUrlEncoded
    @POST("/mcm/api/user/login")
    Observable<LoginSuccess> Login1(@HeaderMap Map<String,String> headers, @Field("username") String username, @Field("password") String password);


}
