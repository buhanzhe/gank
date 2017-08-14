package com.buhanzhe.gank.fragment.login;

import com.buhanzhe.gank.bean.LoginSuccess;
import com.king.frame.mvp.base.BaseView;

/**
 * Created by buhanzhe on 17/7/6.
 */

public interface LoginView extends BaseView {
    void onLogin(LoginSuccess loginInfo);
    void onFail(String message);
}
