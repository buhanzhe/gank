package com.buhanzhe.gank.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.buhanzhe.gank.R;
import com.buhanzhe.gank.adapter.HomeFragmentPagerAdapter;
import com.king.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buhanzhe on 17/7/12.
 */

public class HomeFragment extends BaseFragment {


    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int inflaterRootView() {
        return R.layout.fragment_home;
    }

    @Override
    public void initUI() {
        ButterKnife.bind(this, rootView);
    }

    @Override
    public void initData() {
        //Fragment+ViewPager+FragmentViewPager组合的使用
        HomeFragmentPagerAdapter adapter = new HomeFragmentPagerAdapter(getFragmentManager(),
                getContext());
        viewPager.setAdapter(adapter);

        //TabLayout
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void addListeners() {

    }
}
