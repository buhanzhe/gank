package com.buhanzhe.gank.fragment.girl;

import com.buhanzhe.gank.bean.GankContent;
import com.king.frame.mvp.base.BaseView;

import java.util.List;

/**
 * Created by buhanzhe on 17/7/13.
 */

public interface GirlView extends BaseView {
    void onGetData(List<GankContent> gankContentList);
}
