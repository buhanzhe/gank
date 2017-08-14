package com.buhanzhe.gank.fragment.mine;

import com.buhanzhe.gank.bean.User;
import com.buhanzhe.gank.utils.APICloudHelper;
import com.buhanzhe.gank.utils.api.APIRetrofit;
import com.buhanzhe.gank.utils.api.APIService;
import com.king.frame.mvp.base.BasePresenter;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by buhanzhe on 17/8/6.
 */

public class MinePresenter extends BasePresenter<MineView> {

    public void getUserInfo(String userIdStr) {
        APIService apiService = APIRetrofit.getAPIService();
        apiService.getUser(APICloudHelper.getApiCloudHeaders(),userIdStr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull User user) {
                        getView().onGetUserInfo(user);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getView().onGetUserInfoFail(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void updateUser(final String iconUrl,String userId) {
        APIService apiService = APIRetrofit.getAPIService();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("iconUrl", iconUrl);
            jsonObject.put("_method", "PUT");
        } catch (JSONException e) {
            Logger.e("updateUserJson", e.getMessage());
        }
        apiService.updateUser(APICloudHelper.getApiCloudHeaders(),userId,jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        getView().onUpdateUser(responseBody,iconUrl);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getView().onGetUserInfoFail(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
