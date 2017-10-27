package com.tongzhuo.tzopengame.ui;

import com.tongzhuo.tzopengame.R;
import com.tongzhuo.tzopengame.callback.GameResultCallback;
import com.tongzhuo.tzopengame.tencent_x5.ILoadAction;
import com.tongzhuo.tzopengame.tencent_x5.X5WebView;
import com.tongzhuo.tzopengame.utils.TZLog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.ProgressBar;

public class PlayGameFragment extends Fragment {

    private static final String TZ_GAME_URL = "tz_game_url";

    private GameResultCallback mResultCallback;

    private String mGameUrl;

    private X5WebView mGameView;

    private ProgressBar mProgressView;

    public static PlayGameFragment newInstance(String game_url) {
        PlayGameFragment fragment = new PlayGameFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TZ_GAME_URL, game_url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGameUrl = getArguments().getString(TZ_GAME_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_play_game, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof GameResultCallback) {
            mResultCallback = (GameResultCallback) context;
        } else {
            throw new RuntimeException(context.getClass().getSimpleName() +
                    " must implements GameResultCallback");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mGameView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mGameView.onPause();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mResultCallback = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGameView.destroy();
        mGameView = null;
    }

    private void initView(View view) {
        mProgressView = (ProgressBar) view.findViewById(R.id.mProgressView);
        mGameView = (X5WebView) view.findViewById(R.id.mGameView);
        mGameView.addJavascriptInterface(this, "TzOpen");
        if (!TextUtils.isEmpty(mGameUrl)) {
            TZLog.d("load game url : "+mGameUrl);
            mGameView.loadUrl(mGameUrl);
        } else {
            getActivity().finish();
        }
        mGameView.setLoadAction(new ILoadAction() {
            @Override
            public void loadStart() {
                mProgressView.setVisibility(View.VISIBLE);
            }

            @Override
            public void loadFinish() {
                mProgressView.setVisibility(View.GONE);
            }
        });
    }

    @JavascriptInterface
    public void getResult(String value) {
        TZLog.d("game result : "+value);
        if (mResultCallback != null) {
            mResultCallback.onResult(value);
        }
    }
}
