package com.buhanzhe.gank.fragment.mine;

import com.buhanzhe.gank.bean.User;
import com.king.frame.mvp.base.BaseView;

import okhttp3.ResponseBody;

/**
 * Created by buhanzhe on 17/8/6.
 */

public interface MineView extends BaseView {
    void onGetUserInfo(User user);
    void onGetUserInfoFail(Throwable e);
    void onUpdateUser(ResponseBody responseBody, String iconUrl);
}
