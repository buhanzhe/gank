package com.buhanzhe.gank.fragment.register;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.buhanzhe.gank.Constants;
import com.buhanzhe.gank.R;
import com.buhanzhe.gank.bean.EventMessage;
import com.buhanzhe.gank.bean.RegisterSuccess;
import com.buhanzhe.gank.utils.ToastUtil;
import com.king.base.util.ToastUtils;
import com.king.frame.mvp.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by buhanzhe on 17/8/1.
 */

public class RegisterFragment extends BaseFragment<RegisterView, RegisterPresenter> implements RegisterView {


    private static final String TAG ="RegisterFragment";
    @BindView(R.id.ivLeft)
    ImageView ivLeft;
    @BindView(R.id.etUsername)
    EditText etUsername;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etConfirmPassword)
    EditText etConfirmPassword;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    Unbinder unbinder;
    private String phoneNumber;
    private String passwordString;
    private String confirmPasswordString;

    public static RegisterFragment newInstance() {

        Bundle args = new Bundle();

        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getRootViewId() {
        return R.layout.fragment_rigister;
    }

    @Override
    public void initUI() {


    }

    @Override
    public void initData() {

    }

    @Override
    public RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }


    @Override
    public void onRegisterSuccess(RegisterSuccess registerSuccess) {
        dismissProgressDialog();
        ToastUtil.showToast(getString(R.string.register_success));
        EventBus.getDefault().post(new EventMessage(Constants.REGISTER_TO_MINEFRAG,registerSuccess));
        getActivity().finish();
    }

    @Override
    public void onRegisterError(Throwable e) {
        ToastUtil.showToast(e.getMessage());
    }

    @OnClick({R.id.btnRegister,R.id.ivLeft})
    public void onClick(View view) {
        phoneNumber = etUsername.getText().toString();
        passwordString = etPassword.getText().toString();
        confirmPasswordString = etConfirmPassword.getText().toString();
        switch (view.getId()) {
            case R.id.btnRegister:
               if (TextUtils.isEmpty(phoneNumber)) {
                   ToastUtils.showToast(getContext(),"用户名不能为空");
                   break;
                }
                if (TextUtils.isEmpty(passwordString)||TextUtils.isEmpty(confirmPasswordString)) {
                    ToastUtils.showToast(getContext(),"密码不能为空");
                    break;
                }
                if (!passwordString.equals(confirmPasswordString)) {
                    ToastUtils.showToast(getContext(),"两次密码不一致");
                    break;
                }
                showProgressDialog();
                getPresenter().toRegisterUser(phoneNumber,passwordString);
                break;
            case R.id.ivLeft:
                break;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventMessage event) {

    }
}
