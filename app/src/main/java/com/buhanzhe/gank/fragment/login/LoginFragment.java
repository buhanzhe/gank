package com.buhanzhe.gank.fragment.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.buhanzhe.gank.Constants;
import com.buhanzhe.gank.R;
import com.buhanzhe.gank.bean.EventMessage;
import com.buhanzhe.gank.bean.LoginSuccess;
import com.buhanzhe.gank.fragment.register.RegisterFragment;
import com.king.frame.mvp.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.buhanzhe.gank.utils.ToastUtil.showToast;

/**
 * Created by buhanzhe on 17/7/12.
 */

public class LoginFragment extends BaseFragment<LoginView,LoginPresenter> implements LoginView {

    @BindView(R.id.ivLeft)
    ImageView leftImageView;
    @BindView(R.id.tvRight)
    TextView rightTextView;
    @BindView(R.id.etUsername)
    EditText etUsername;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    Toast mToast;


    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getRootViewId() {
        return R.layout.fragment_login;
    }

    @Override
    public void initUI() {
        ButterKnife.bind(this,getRootView());
    }

    @Override
    public void initData() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @OnClick({R.id.btnLogin,R.id.ivLeft,R.id.tvRight})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                    showProgressDialog();
                    getPresenter().setLoginParameter(username,password);
                }else{
                    showToast("账号密码不能为空");
                }
                break;
            case R.id.ivLeft:
                getActivity().finish();
                break;
            case R.id.tvRight:
                replaceFragment(R.id.fragmentContent, RegisterFragment.newInstance(),true);
                break;
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventMessage event) {

    }

    @Override
    public void onLogin(LoginSuccess loginSuccess) {
        showToast("登录成功！");
        EventBus.getDefault().post(new EventMessage(Constants.LOGIN_TO_MINEFRAG,loginSuccess));
        dismissProgressDialog();
        getActivity().finish();
    }

    @Override
    public void onFail(String message) {
        dismissProgressDialog();
        showToast("登录失败！失败信息为："+message);
    }
}
