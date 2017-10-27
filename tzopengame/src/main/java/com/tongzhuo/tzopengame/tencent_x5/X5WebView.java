package com.tongzhuo.tzopengame.tencent_x5;


import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tongzhuo.tzopengame.utils.TZLog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chaichuanfa on 17/8/22.
 */
public class X5WebView extends WebView {

    private ILoadAction mILoadAction;

    public X5WebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public X5WebView(Context context) {
        super(context);
        init();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mILoadAction = null;
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        boolean ret = super.drawChild(canvas, child, drawingTime);
        if (TZLog.isDebug) {
            canvas.save();
            Paint paint = new Paint();
            paint.setColor(0x7fff0000);
            paint.setTextSize(24.f);
            paint.setAntiAlias(true);

            if (getX5WebViewExtension() != null) {
                canvas.drawText(
                        this.getContext().getPackageName() + "-pid:" + android.os.Process.myPid(),
                        10,
                        50, paint);
                canvas.drawText("X5  Core:" + QbSdk.getTbsVersion(this.getContext()), 10, 100,
                        paint);
            } else {
                canvas.drawText(
                        this.getContext().getPackageName() + "-pid:" + android.os.Process.myPid(),
                        10,
                        50, paint);
                canvas.drawText("X5 webView version : " + QbSdk.getTbsVersion(this.getContext())
                        + "   Sys Core", 10, 100, paint);
            }
            canvas.drawText(Build.MANUFACTURER, 10, 150, paint);
            canvas.drawText(Build.MODEL, 10, 200, paint);
            canvas.restore();
        }
        return ret;
    }

    private void init() {
        WebViewClient client = new WebViewClient() {
            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
                if (mILoadAction != null) {
                    mILoadAction.loadStart();
                }
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                if (mILoadAction != null) {
                    mILoadAction.loadFinish();
                }
            }

            /**
             * 防止加载网页时调起系统浏览器
             */
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        };
        this.setWebViewClient(client);
        initWebViewSettings();
        this.getView().setClickable(true);
    }

    private void initWebViewSettings() {
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setLoadsImagesAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(false);
        webSetting.setBuiltInZoomControls(false);
        webSetting.setDisplayZoomControls(false);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSetting.setAppCachePath(getContext().getCacheDir().getPath());
        webSetting.setDatabasePath(getContext().getCacheDir().getPath());
        webSetting.setGeolocationDatabasePath(getContext().getCacheDir().getPath());
    }

    public void setLoadAction(ILoadAction ILoadAction) {
        mILoadAction = ILoadAction;
    }
}
