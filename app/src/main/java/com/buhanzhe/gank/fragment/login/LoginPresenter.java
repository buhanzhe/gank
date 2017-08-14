package com.buhanzhe.gank.fragment.login;

import com.buhanzhe.gank.utils.api.APIRetrofit;
import com.buhanzhe.gank.utils.api.APIService;
import com.buhanzhe.gank.bean.LoginSuccess;
import com.buhanzhe.gank.utils.APICloudHelper;
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
 * Created by buhanzhe on 17/7/6.
 */

public class LoginPresenter extends BasePresenter<LoginView> {
    public void setLoginParameter(String username, String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            Logger.e("setLoginJson", e.toString());
        }
        HashMap<String, String> headers = new HashMap<>();
        headers.put("X-APICloud-AppId", "A6944878645421");
        headers.put("X-APICloud-AppKey", APICloudHelper.getSecurityAppKey());
        headers.put("Content-Type", "application/json");

        APIService APIService = APIRetrofit.getAPIService();
        APIService.Login(headers, jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginSuccess>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull LoginSuccess loginSuccess) {
                        getView().onLogin(loginSuccess);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getView().onFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
