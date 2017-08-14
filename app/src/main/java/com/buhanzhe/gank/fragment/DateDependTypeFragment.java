package com.buhanzhe.gank.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.buhanzhe.gank.Constants;
import com.buhanzhe.gank.R;
import com.buhanzhe.gank.activity.MyContentActivity;
import com.buhanzhe.gank.adapter.DataDependTypeAdapter;
import com.buhanzhe.gank.bean.GankContent;
import com.buhanzhe.gank.bean.GankItem;
import com.buhanzhe.gank.listener.PageListener;
import com.buhanzhe.gank.listener.RecyclerScrollListener;
import com.buhanzhe.gank.utils.ActivityUtil;
import com.buhanzhe.gank.utils.api.GankApi;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.king.base.BaseFragment;
import com.king.base.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by buhanzhe on 17/7/21.
 */

@SuppressLint("ValidFragment")
public class DateDependTypeFragment extends BaseFragment {

    private static final String TAG = "DateDependTypeFragment";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private String dataType;
    DataDependTypeAdapter dateDependTypeAdapter;
    PageListener pageListener;
    private int pageValua = 1;


    public DateDependTypeFragment(String type) {
        dataType = type;
    }

    @Override
    public int inflaterRootView() {
        return R.layout.recycler_layout;
    }

    @Override
    public void initUI() {
        ButterKnife.bind(this, rootView);
    }

    @Override
    public void initData() {
        showProgressDialog();
        getData();
    }

    @Override
    public void addListeners() {

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PageListener)
            pageListener = (PageListener) context;
    }
    public void getData(){
        GankApi gankApi = GankApi.getInstance();
        gankApi.service.getTypeData(dataType,10,pageValua)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankItem>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull GankItem gankItem) {
                        List<MultiItemEntity> multiItemEntityList = new ArrayList<>();
                        multiItemEntityList.addAll(gankItem.getResult());
                        if (pageValua == 1) {
                            dateDependTypeAdapter = new DataDependTypeAdapter(multiItemEntityList);
                            dateDependTypeAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
                            dateDependTypeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                                @Override
                                public void onLoadMoreRequested() {
                                    getData();
                                }
                            },recyclerView);
                            dateDependTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    MultiItemEntity multiItemEntity = dateDependTypeAdapter.getItem(position);
                                    int type = multiItemEntity.getItemType();
                                    if (type == Constants.CONTENT_IMG || type == Constants.CONTENT) {
                                        GankContent content = (GankContent) multiItemEntity;
                                        //添加历史记录
                                        String url = content.getUrl();
                                        String title = content.getDesc();
                                        ActivityUtil.gotoWebActivity(getContext(),url,title);
                                    }
                                    if (type == Constants.IMG) {
                                        try {
                                            ActivityUtil.gotoPhotoActivityH(getActivity(), view, adapter.getData(), position);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }
                            });
                            recyclerView.setAdapter(dateDependTypeAdapter);
                            dismissProgressDialog();
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerView.addOnScrollListener(recyclerScrollListener);
                        } else {
                            dateDependTypeAdapter.addData(multiItemEntityList);
                            dateDependTypeAdapter.loadMoreComplete();
                        }
                        pageValua++;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LogUtils.d(TAG,e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    protected void startLogin(){
        Intent intent = getFragmentIntent(Constants.LOGIN_FRAGMENT);
        startActivity(intent);
    }

    protected void startWeb(String title,String url){
        Intent intent = getFragmentIntent(Constants.WEB_FRAGMENT);
        intent.putExtra(Constants.KEY_TITLE,title);
        intent.putExtra(Constants.KEY_URL,url);
        startActivity(intent);
    }

    protected Intent getFragmentIntent(int fragmentKey){
        Intent intent = new Intent(context, MyContentActivity.class);
        intent.putExtra(Constants.KEY_FRAGMENT,fragmentKey);
        return intent;
    }

    RecyclerScrollListener recyclerScrollListener = new RecyclerScrollListener() {
        @Override
        public void hideBottomBar() {
            if (pageListener!=null) {
                pageListener.hideBottomBar();
            }
        }

        @Override
        public void showBottomBar() {
            if (pageListener!=null) {
                pageListener.showBottomBar();
            }
        }

        @Override
        public void firstVisibleItemPosition(int position) {

        }
    };

}
