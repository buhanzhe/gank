package com.buhanzhe.gank.fragment.register;


import com.buhanzhe.gank.bean.RegisterSuccess;
import com.buhanzhe.gank.utils.APICloudHelper;
import com.buhanzhe.gank.utils.api.APIRetrofit;
import com.buhanzhe.gank.utils.api.APIService;
import com.king.frame.mvp.base.BasePresenter;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by buhanzhe on 17/8/1.
 */

public class RegisterPresenter extends BasePresenter<RegisterView> {
    public void toRegisterUser(String phone,String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", phone);
            jsonObject.put("password", password);
            jsonObject.put("mobile", phone);
        } catch (JSONException e) {
            Logger.e("setRegisterJson", e.toString());
        }
        HashMap<String, String> headers = new HashMap<>();
        headers.put("X-APICloud-AppId", "A6944878645421");
        headers.put("X-APICloud-AppKey", APICloudHelper.getSecurityAppKey());
        headers.put("Content-Type", "application/json");

        APIService APIService = APIRetrofit.getAPIService();
        APIService.register(headers,jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterSuccess>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull RegisterSuccess registerSuccess) {
                        getView().onRegisterSuccess(registerSuccess);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getView().onRegisterError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
