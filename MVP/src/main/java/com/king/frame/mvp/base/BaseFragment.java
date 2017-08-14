package com.king.frame.mvp.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.king.base.BaseProgressDialog;

/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */

public abstract class BaseFragment<V extends BaseView, P extends BasePresenter<V>> extends MvpFragment<V, P> {


    private View mRootView;

    private BaseProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        mRootView = inflater.inflate(getRootViewId(),container,false);
        return mRootView;
    }

    protected View getRootView(){
        return mRootView;
    }

    public <T extends View> T findView(@IdRes int id){
        return (T)mRootView.findViewById(id);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated( savedInstanceState );
        initUI();
        initData();
    }

    public void replaceFragment(@IdRes int id, Fragment fragment) {
        replaceFragment(id,fragment,false);
    }

    public void replaceChildFragment(@IdRes int id, Fragment fragment) {
        getChildFragmentManager().beginTransaction().replace(id, fragment).commit();
    }
    public void replaceFragment(@IdRes int resId, Fragment fragment, boolean isBackStack) {
        FragmentTransaction fragmentTransaction =  getFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(resId, fragment);
        if(isBackStack){
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    public abstract int getRootViewId();

    public abstract void initUI();

    public abstract void initData();

    protected void dismissProgressDialog(){
        dismissDialog(progressDialog);
    }

    protected void dismissDialog(Dialog dialog){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    protected void showProgressDialog(){
        showProgressDialog(new ProgressBar(getContext()));
    }

    protected void showProgressDialog(View v){
        dismissProgressDialog();
        progressDialog = BaseProgressDialog.newInstance(getContext());
        progressDialog.setContentView(v);
        progressDialog.show();
    }
}
