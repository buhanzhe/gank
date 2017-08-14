package com.buhanzhe.gank;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.buhanzhe.gank.fragment.HomeFragment;
import com.buhanzhe.gank.fragment.girl.GirlFragment;
import com.buhanzhe.gank.fragment.mine.MineFragment;
import com.buhanzhe.gank.listener.PageListener;
import com.buhanzhe.gank.utils.SharedPrefsUtils;
import com.buhanzhe.gank.utils.StatusBarUtil;
import com.buhanzhe.gank.widget.MyBottomNavigationView;
import com.king.base.BaseActivity;

public class MainActivity extends BaseActivity implements PageListener {

    MyBottomNavigationView myBottomNavigationView;
    private HomeFragment homeFragment;
    private GirlFragment girlFragment;
    private MineFragment mineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void initUI() {
        showHomeFragment();
        myBottomNavigationView = (MyBottomNavigationView) findViewById(R.id.navigation);
        myBottomNavigationView.inflateMenu(R.menu.navigation);

        myBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        StatusBarUtil.transparencyBar(this);
        if (Build.VERSION.SDK_INT > 19) {
            StatusBarUtil.StatusBarLightMode(MainActivity.this, 1);
        }

        if(SharedPrefsUtils.getStringPreference(Constants.FIRST) == "") {
            SharedPrefsUtils.setBooleanPreference(Constants.WIFI_ONLY,true);
            SharedPrefsUtils.setBooleanPreference(Constants.THEME_IS_OPEN,false);
            SharedPrefsUtils.setStringPreference(Constants.FIRST,"open");
        }


    }

    @Override
    public void initData() {

    }

    @Override
    public void addListeners() {

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void hideBottomBar() {
        if (!myBottomNavigationView.isHidden()) {
            myBottomNavigationView.hide();
        }
    }

    @Override
    public void showBottomBar() {
        if (myBottomNavigationView.isHidden()) {
            myBottomNavigationView.show();
        }
    }

    public void showHomeFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        if (homeFragment == null) {
            homeFragment = HomeFragment.newInstance();
            fragmentTransaction.add(R.id.myFragmentContent, homeFragment);
        }
        commitShowFragment(fragmentTransaction, homeFragment);
    }


    public void showGirlFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        hideAllFragment(fragmentTransaction);
        if (girlFragment == null) {
            girlFragment = GirlFragment.newInstance();
            fragmentTransaction.add(R.id.myFragmentContent, girlFragment);
        }

        commitShowFragment(fragmentTransaction, girlFragment);

    }

    public void showMineFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        hideAllFragment(fragmentTransaction);
        if (mineFragment == null) {
            mineFragment = MineFragment.newInstance();
            fragmentTransaction.add(R.id.myFragmentContent, mineFragment);
        }

        commitShowFragment(fragmentTransaction, mineFragment);

    }

    public void commitShowFragment(FragmentTransaction fragmentTransaction, Fragment fragment) {
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }

    public void hideAllFragment(FragmentTransaction fragmentTransaction) {
        hideFragment(fragmentTransaction, homeFragment);
        hideFragment(fragmentTransaction, girlFragment);
        hideFragment(fragmentTransaction, mineFragment);
    }

    private void hideFragment(FragmentTransaction fragmentTransaction, Fragment fragment) {
        if (fragment != null) {
            fragmentTransaction.hide(fragment);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    showHomeFragment();
                    break;
                case R.id.navigation_girl:
                    showGirlFragment();
                    break;
                case R.id.navigation_me:
                    showMineFragment();
                    break;
            }
            return true;
        }
    };
}
