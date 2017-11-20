package com.github.felix;

import com.github.felix.model.GameInfo;
import com.tongzhuo.tzopengame.tencent_x5.ILoadAction;
import com.tongzhuo.tzopengame.tencent_x5.X5WebView;
import com.tongzhuo.tzopengame.utils.TZLog;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;

public class CustomGameViewActivity extends AppCompatActivity {

    private X5WebView mGameView;

    private FrameLayout mRootView;

    private GameInfo mGameInfo;

    public static Intent newIntent(Context context, GameInfo gameInfo) {
        Intent intent = new Intent(context, CustomGameViewActivity.class);
        intent.putExtra("game_info", gameInfo);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_game_view);
        mGameInfo = (GameInfo) getIntent().getSerializableExtra("game_info");
        if (mGameInfo != null && !mGameInfo.isPortrait()) {
            //landscape game
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        mRootView = (FrameLayout) findViewById(R.id.content_view);
        createGameView();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {
        mGameView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mGameView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mRootView != null && mGameView != null) {
            mRootView.removeView(mGameView);
            mGameView.removeAllViews();
            mGameView.destroy();
            mGameView = null;
        }
        super.onDestroy();
    }

    private void createGameView() {
        mGameView = new X5WebView(getApplicationContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mRootView.addView(mGameView, params);
        mGameView.addJavascriptInterface(this, "TzOpen");
        if (mGameInfo != null && !TextUtils.isEmpty(mGameInfo.getGameUrl())) {
            TZLog.d("load game url : " + mGameInfo.getGameUrl());
            mGameView.loadUrl(mGameInfo.getGameUrl());
        } else {
            finish();
        }
        mGameView.setLoadAction(new ILoadAction() {
            @Override
            public void loadStart() {
                TZLog.d("load game start");
            }

            @Override
            public void loadFinish() {
                TZLog.d("load game finish");
            }
        });
    }

    /**
     * game result callback
     * @param value
     */
    @JavascriptInterface
    public void getResult(String value) {
        TZLog.d("game result : "+value);

    }
}
