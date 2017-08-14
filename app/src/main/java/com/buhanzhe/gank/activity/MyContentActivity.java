package com.buhanzhe.gank.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.buhanzhe.gank.Constants;
import com.buhanzhe.gank.R;
import com.buhanzhe.gank.fragment.SettingFragment;
import com.buhanzhe.gank.fragment.login.LoginFragment;
import com.buhanzhe.gank.utils.StatusBarUtil;
import com.king.base.util.LogUtils;

/**
 * Created by buhanzhe on 17/7/27.
 */

public class MyContentActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);
        StatusBarUtil.transparencyBar(this);
        StatusBarUtil.StatusBarLightMode(this, 1);
        switchFragment(getIntent());
    }

    protected void replaceFragment(Fragment fragment) {
        replaceFragment(R.id.fragmentContent, fragment);
    }

    protected void replaceFragment(@IdRes int id, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(id, fragment).commit();
    }

    protected void switchFragment(Intent intent) {
        int fragmentKey = intent.getIntExtra(Constants.KEY_FRAGMENT, 0);
        switch (fragmentKey) {

            case Constants.WEB_FRAGMENT:
                String title = intent.getStringExtra(Constants.KEY_TITLE);
                String url = intent.getStringExtra(Constants.KEY_URL);

                break;
            case Constants.LOGIN_FRAGMENT:
                replaceFragment(LoginFragment.newInstance());
                break;
            case Constants.SETTING_FRAGMENT:
                replaceFragment(SettingFragment.newInstance());
                break;
            default:
                LogUtils.d("Not found fragment:" + Integer.toHexString(fragmentKey));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }
}
