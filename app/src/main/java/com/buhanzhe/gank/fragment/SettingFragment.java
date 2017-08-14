package com.buhanzhe.gank.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.buhanzhe.gank.Constants;
import com.buhanzhe.gank.R;
import com.buhanzhe.gank.bean.EventMessage;
import com.buhanzhe.gank.utils.SharedPrefsUtils;
import com.king.base.BaseFragment;
import com.king.base.util.FileUtil;
import com.king.base.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by buhanzhe on 17/8/4.
 */

public class SettingFragment extends BaseFragment {


    @BindView(R.id.ivLeft)
    ImageView ivLeft;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.notify_setting_switch)
    Switch notifySettingSwitch;
    @BindView(R.id.setting_access_notification)
    RelativeLayout settingAccessNotification;
    @BindView(R.id.setting_savenet_sw)
    Switch settingNetSw;
    @BindView(R.id.setting_only_wifi)
    RelativeLayout settingOnlyWifi;
    @BindView(R.id.setting_feedback)
    RelativeLayout settingFeedback;
    @BindView(R.id.setting_about_ll)
    RelativeLayout settingAboutLl;
    @BindView(R.id.setting_logout_ll)
    LinearLayout settingLogoutLl;
    Unbinder unbinder;
    @BindView(R.id.cacheSizeTextView)
    TextView cacheSizeTextView;
    @BindView(R.id.setting_cache)
    RelativeLayout settingCache;

    public static SettingFragment newInstance() {

        Bundle args = new Bundle();

        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int inflaterRootView() {
        return R.layout.fragment_setting;
    }

    @Override
    public void initUI() {
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        tvTitle.setText(getResources().getText(R.string.setting));
        if (SharedPrefsUtils.getStringPreference(Constants.USERID) == "") {
            settingLogoutLl.setVisibility(View.GONE);
        }

    }

    @Override
    public void initData() {
        setCacheSize();


    }

    @Override
    public void addListeners() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventMessage event) {

    }

    @OnClick({R.id.ivLeft,R.id.setting_cache,R.id.setting_about_ll,R.id.setting_feedback,R.id.setting_only_wifi,R.id.setting_logout_ll})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                finish();
                break;
            case R.id.setting_cache:
                AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                builder.setMessage("确定清除缓存？");
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ToastUtils.showToast(getContext(), FileUtil.clearCache(getContext())?"删除缓存成功":"删除缓存失败");
                        setCacheSize();
                    }
                });
                builder.show();
                break;
            case R.id.setting_about_ll:
                replaceFragment(R.id.fragmentContent,AboutFragment.newInstance());
                break;
            case R.id.setting_feedback:
                Uri uri = Uri.parse("mailto:buhanzhe@163.com");
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                startActivity(Intent.createChooser(intent, "Choose Email Client"));
                break;
            case R.id.setting_only_wifi:
                boolean netSwitch = settingNetSw.isChecked();
                settingNetSw.setChecked(!netSwitch);
                SharedPrefsUtils.setBooleanPreference(Constants.WIFI_ONLY, !netSwitch);
                break;
            case R.id.setting_logout_ll:
                AlertDialog.Builder logoutBuilder = new android.support.v7.app.AlertDialog.Builder(getContext());
                logoutBuilder.setMessage("确定退出当前用户？");
                logoutBuilder.setNegativeButton("取消", null);
                logoutBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPrefsUtils.clearAllPreference();
                        EventBus.getDefault().post(new EventMessage(Constants.SETTING_TO_MINEFRAG,"退出登录"));
                        finish();
                    }
                });
                logoutBuilder.show();
                break;
        }
    }

    private void setCacheSize(){
        try {
            cacheSizeTextView.setText(FileUtil.calculateCacheSize(getContext()));
        } catch (Exception e) {
            Log.d("settingFragment",e.getMessage());
        }
    }
}
