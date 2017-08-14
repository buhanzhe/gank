package com.buhanzhe.gank.fragment.girl;

import com.buhanzhe.gank.utils.api.GankApi;
import com.buhanzhe.gank.bean.GankItem;
import com.king.frame.mvp.base.BasePresenter;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by buhanzhe on 17/7/13.
 */

public class GirlPresenter extends BasePresenter<GirlView> {
    void getGirldata(int page) {
        GankApi gankApi = GankApi.getInstance();
        gankApi.service.getTypeData("福利",20,page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankItem>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull GankItem gankItem) {
                        getView().onGetData(gankItem.getResult());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
