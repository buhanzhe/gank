package com.buhanzhe.gank.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.buhanzhe.gank.fragment.DateDependTypeFragment;

import java.util.HashMap;

/**
 * Created by buhanzhe on 17/7/20.
 */

public class HomeFragmentPagerAdapter extends MyFragmentPagerAdapter {
    private String[] titles = new String[]{"all", "Android", "iOS", "休息视频", "福利","拓展资源","前端","瞎推荐","App"};
    private Context context;
    private HashMap<String,DateDependTypeFragment> dateDependTypeFragmentHashMap;

    public HomeFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        dateDependTypeFragmentHashMap = new HashMap<>();
    }

    @Override
    public Fragment getItem(int position) {
        String presentType = titles[position];
        if(dateDependTypeFragmentHashMap.containsKey(presentType)) {
            return dateDependTypeFragmentHashMap.get(presentType);
        } else {
            DateDependTypeFragment dateDependTypeFragment = new DateDependTypeFragment(presentType);
            dateDependTypeFragmentHashMap.put(presentType,dateDependTypeFragment);
            return dateDependTypeFragment;
        }
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}