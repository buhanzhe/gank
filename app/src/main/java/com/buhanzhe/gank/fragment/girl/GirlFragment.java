package com.buhanzhe.gank.fragment.girl;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.buhanzhe.gank.R;
import com.buhanzhe.gank.adapter.GirlAdapter;
import com.buhanzhe.gank.bean.GankContent;
import com.buhanzhe.gank.listener.LikeListener;
import com.buhanzhe.gank.listener.PageListener;
import com.buhanzhe.gank.listener.RecyclerScrollListener;
import com.buhanzhe.gank.utils.ActivityUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.king.frame.mvp.base.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by buhanzhe on 17/7/13.
 */

public class GirlFragment extends BaseFragment<GirlView, GirlPresenter> implements GirlView,LikeListener {

    @BindView(R.id.tvTitle)
    TextView title;
    @BindView(R.id.load_failed_textView)
    TextView loadFailedTextView;
    @BindView(R.id.load_failed_ll)
    LinearLayout loadFailedLl;
    @BindView(R.id.girl_recyclerView)
    RecyclerView girlRecyclerView;
    private GirlAdapter girlAdapter;
    private PageListener pageListener;
    private int currentPageNumber = 1;

    public static GirlFragment newInstance() {

        Bundle args = new Bundle();

        GirlFragment fragment = new GirlFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getRootViewId() {
        return R.layout.fragment_girl;
    }

    @Override
    public void initUI() {
        ButterKnife.bind(this, getRootView());
        title.setText(R.string.tab_girl);
    }

    @Override
    public void initData() {
        showProgressDialog();
        getPresenter().getGirldata(currentPageNumber);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PageListener)
            pageListener = (PageListener) context;
    }

    @Override
    public GirlPresenter createPresenter() {
        return new GirlPresenter();
    }

    @Override
    public void onGetData(List<GankContent> gankContentList) {
        dismissProgressDialog();
        if(currentPageNumber==1){
            girlAdapter = new GirlAdapter(gankContentList,this);
            girlAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
            girlAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    initData();
                }
            },girlRecyclerView);
            girlAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    ActivityUtil.gotoPhotoActivityH(getActivity(), view, adapter.getData(), position);
                }
            });
            girlRecyclerView.setAdapter(girlAdapter);
            girlRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            girlRecyclerView.addOnScrollListener(recyclerScrollListener);
        }else {
            girlAdapter.addData(gankContentList);
            girlAdapter.loadMoreComplete();
        }
        currentPageNumber++;

    }

    @Override
    public void onLiked(int pos) {

    }

    @Override
    public void onUnLiked(int pos) {

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
