package com.buhanzhe.gank.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.buhanzhe.gank.R;
import com.king.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by buhanzhe on 17/8/6.
 */

public class AboutFragment extends BaseFragment {

    @BindView(R.id.ivLeft)
    ImageView ivLeft;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvVersion)
    TextView tvVersion;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    Unbinder unbinder;

    public static AboutFragment newInstance() {

        Bundle args = new Bundle();

        AboutFragment fragment = new AboutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initUI() {


    }

    @Override
    public void initData() {

    }

    @Override
    public void addListeners() {

    }

    @OnClick({R.id.ivLeft,R.id.tvEmail})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                break;
            case R.id.tvEmail:
                Uri uri = Uri.parse("mailto:buhanzhe@163.com");
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                startActivity(Intent.createChooser(intent, "Choose Email Client"));
                break;
        }
    }

    @Override
    public int inflaterRootView() {
        return R.layout.fragment_about;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
