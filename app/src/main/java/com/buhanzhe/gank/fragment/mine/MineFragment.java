package com.buhanzhe.gank.fragment.mine;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.buhanzhe.gank.Constants;
import com.buhanzhe.gank.R;
import com.buhanzhe.gank.activity.MyContentActivity;
import com.buhanzhe.gank.bean.EventMessage;
import com.buhanzhe.gank.bean.LoginSuccess;
import com.buhanzhe.gank.bean.RegisterSuccess;
import com.buhanzhe.gank.bean.User;
import com.buhanzhe.gank.utils.ImageLoaderUtil;
import com.buhanzhe.gank.utils.ImageUtil;
import com.buhanzhe.gank.utils.SharedPrefsUtils;
import com.buhanzhe.gank.utils.ToastUtil;
import com.king.base.util.ToastUtils;
import com.king.frame.mvp.base.BaseFragment;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import skin.support.SkinCompatManager;

/**
 * Created by buhanzhe on 17/7/12.
 */

public class MineFragment extends BaseFragment<MineView, MinePresenter> implements MineView {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.login_bt)
    Button startLogin;
    @BindView(R.id.theme_switch_rl)
    RelativeLayout themeSwitchRL;
    @BindView(R.id.me_nightTh_sw)
    Switch SwitchThemeBtn;
    @BindView(R.id.me_rl)
    RelativeLayout userView;
    @BindView(R.id.login_rl)
    RelativeLayout LoginBtnView;
    @BindView(R.id.me_tv)
    TextView userName;
    @BindView(R.id.me_iv)
    ImageView userIcon;
    Unbinder unbinder;

    public static MineFragment newInstance() {

        Bundle args = new Bundle();

        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getRootViewId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initUI() {
        unbinder = ButterKnife.bind(this, getRootView());
        tvTitle.setText(R.string.tab_me);
    }

    @Override
    public void initData() {
        EventBus.getDefault().register(this);
        String userIdFromSP = SharedPrefsUtils.getStringPreference(Constants.USERID);
        String username = SharedPrefsUtils.getStringPreference(Constants.USERNAME);
        String iconImgStr = SharedPrefsUtils.getStringPreference(Constants.ICONIMG);
        if (!TextUtils.isEmpty(userIdFromSP)) {
            LoginBtnView.setVisibility(View.GONE);
            userView.setVisibility(View.VISIBLE);
            userName.setText(username);
            ImageLoaderUtil.loadCircleImage(iconImgStr,R.drawable.ic_default_user,userIcon);
        }
    }

    @Override
    public MinePresenter createPresenter() {
        return new MinePresenter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);

    }

    @Override
    public void onGetUserInfo(User user) {
        LoginBtnView.setVisibility(View.GONE);
        userView.setVisibility(View.VISIBLE);
        userName.setText(user.getUsername());
        SharedPrefsUtils.setStringPreference(Constants.USERID, user.getId());
        SharedPrefsUtils.setStringPreference(Constants.USERNAME, user.getUsername());
        SharedPrefsUtils.setStringPreference(Constants.ICONIMG, user.getIconUrl());
        ImageLoaderUtil.loadCircleImage(user.getIconUrl(), R.drawable.ic_default_user, userIcon);
    }

    @Override
    public void onGetUserInfoFail(Throwable e) {
        ToastUtils.showToast(getContext(), e.getMessage());
    }

    @Override
    public void onUpdateUser(ResponseBody responseBody, String iconUrl) {
        ImageLoaderUtil.loadCircleImage(iconUrl, R.drawable.ic_default_user, userIcon);
        SharedPrefsUtils.setStringPreference(Constants.ICONIMG, iconUrl);
    }

    @OnClick({R.id.theme_switch_rl, R.id.login_bt, R.id.me_iv, R.id.me_setting_ll})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.theme_switch_rl:
                boolean themeSwitch = SwitchThemeBtn.isChecked();
                SwitchThemeBtn.setChecked(!themeSwitch);
                SharedPrefsUtils.setBooleanPreference(Constants.THEME_IS_OPEN, !themeSwitch);
                if (!themeSwitch) {
                    SkinCompatManager.getInstance().loadSkin("night.skin", new SkinCompatManager.SkinLoaderListener() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onSuccess() {
                            ToastUtil.showToast("加载皮肤成功");
                        }

                        @Override
                        public void onFailed(String errMsg) {
                            ToastUtil.showToast("加载皮肤失败");
                        }
                    });
                } else {
                    SkinCompatManager.getInstance().restoreDefaultTheme();
                    ToastUtil.showToast("关闭夜间模式");
                }
                break;
            case R.id.login_bt:
                Intent intent = new Intent(getContext(), MyContentActivity.class);
                intent.putExtra(Constants.KEY_FRAGMENT, Constants.LOGIN_FRAGMENT);
                startActivity(intent);
                break;
            case R.id.me_iv:
                Intent intent1 = new Intent();
                /* 开启Pictures画面Type设定为image */
                intent1.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
                intent1.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
                startActivityForResult(intent1, 1);
                break;
            case R.id.me_setting_ll:
                Intent setIntent = new Intent(getContext(), MyContentActivity.class);
                setIntent.putExtra(Constants.KEY_FRAGMENT, Constants.SETTING_FRAGMENT);
                startActivity(setIntent);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventMessage event) {
        String userId = "";
        if (event.what == Constants.LOGIN_TO_MINEFRAG) {
            userId = ((LoginSuccess) event.obj).getUserId();
            getPresenter().getUserInfo(userId);
        }
        if (event.what == Constants.REGISTER_TO_MINEFRAG) {
            userId = ((RegisterSuccess) event.obj).getId();
            getPresenter().getUserInfo(userId);
        }
        if (event.what == Constants.SETTING_TO_MINEFRAG) {
            LoginBtnView.setVisibility(View.VISIBLE);
            userView.setVisibility(View.GONE);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            Uri uri = data.getData();
            Log.e("uri", uri.toString());
            ContentResolver cr = getContext().getContentResolver();
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 设置图片名字
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            final String key = "icon_" + sdf.format(new Date());
            getResponseFromQiniu(ImageUtil.compressImage(bitmap),key)
                    .subscribe(new Observer<Response>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull Response response) {
                            if (response.isOK()) {
                                getPresenter().updateUser("http://ou00es574.bkt.clouddn.com/"+key,SharedPrefsUtils.getStringPreference(Constants.USERID));
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        }
    }

    Observable<Response> getResponseFromQiniu(final byte[] bytes,final String key) {
        return Observable.create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Response> e) throws Exception {
                UploadManager uploadManager = new UploadManager(new Configuration());
                Auth auth = Auth.create("N_C1rfMi2RBirW9MeXReFtcpFhj7W7tovlBLaBiz", "8t7Y_v1f7wK18am2fhaNsll5QKDDJ_ntFYt2MFqq");
                String token = auth.uploadToken("gank");
                e.onNext(uploadManager.put(bytes, key, token));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
