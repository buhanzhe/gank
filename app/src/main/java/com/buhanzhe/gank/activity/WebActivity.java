package com.buhanzhe.gank.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.buhanzhe.gank.Constants;
import com.buhanzhe.gank.R;
import com.buhanzhe.gank.utils.http.NetWorkUtil;
import com.buhanzhe.gank.widget.MyToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends AppCompatActivity {

    private static final String TAG = "WebActivity";
    @BindView(R.id.gankcontent_pg)
    ProgressBar progressBar;
    @BindView(R.id.gank_content_wb)
    WebView webView;
    @BindView(R.id.toolbar)
    MyToolbar mToolbar;
    @BindView(R.id.ErrorLinearLayout)
    LinearLayout ErrorLinearLayout;
    private String URL;
    private String TITLE;
    WebSettings webSettings;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        getIntentData();
        setTitle(TITLE);
        ButterKnife.bind(this);
        initView();

    }


    private void getIntentData() {
        Intent intent = getIntent();
        URL = intent.getStringExtra(Constants.KEY_URL);
        TITLE = intent.getStringExtra(Constants.KEY_TITLE);
    }

    private void initView() {
        if (mToolbar==null) throw new IllegalStateException("The BaseToolbarActivity must be contain a toolbar");
        setUpToolBar();

        webSettings = webView.getSettings();
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(getApplicationContext().getDir("cache", 0).getPath());
        if (!NetWorkUtil.isNetWorkAvailable(this))
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        else
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setBuiltInZoomControls(true); // 支持手势缩放
        webSettings.setDisplayZoomControls(false); // 不显示缩放按钮

        webSettings.setDatabaseEnabled(true);
        webSettings.setSaveFormData(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setUseWideViewPort(true); // 将图片调整到适合WebView的大小
        webSettings.setLoadWithOverviewMode(true); // 自适应屏幕

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                ErrorLinearLayout.addView(LayoutInflater.from(WebActivity.this).inflate(R.layout.layout_error,null));
            }
        });
        webView.setWebChromeClient(chromeClient);
        webView.loadUrl(URL);
    }

    protected void setUpToolBar(){
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    private WebChromeClient chromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (progressBar != null) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    if (progressBar.getVisibility() == View.GONE) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    progressBar.setProgress(newProgress);
                }
            }
            super.onProgressChanged(view, newProgress);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_refresh:
                webView.reload();
                break;
            case R.id.menu_copy:
                copyUrlToClipBard();
                break;
            case R.id.menu_share:
                shareUrl();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareUrl() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, URL);
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, "分享到..."));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }

    private void copyUrlToClipBard() {
        ClipboardManager clipboardManager = (ClipboardManager) this.getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("URL", URL);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, "链接已复制到粘贴板", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}
