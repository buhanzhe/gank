package com.buhanzhe.gank.fragment.register;

import com.buhanzhe.gank.bean.RegisterSuccess;
import com.king.frame.mvp.base.BaseView;

/**
 * Created by buhanzhe on 17/8/1.
 */

public interface RegisterView extends BaseView {
    void onRegisterSuccess(RegisterSuccess registerSuccess);
    void onRegisterError(Throwable e);
}
