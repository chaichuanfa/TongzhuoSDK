package com.github.felix;

import com.github.felix.model.GameInfo;
import com.tongzhuo.tzopengame.callback.GameResultCallback;
import com.tongzhuo.tzopengame.ui.PlayGameFragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

public class PlayGameActivity extends AppCompatActivity implements GameResultCallback {

    private GameInfo mGameInfo;

    public static Intent newIntent(Context context, GameInfo gameInfo) {
        Intent intent = new Intent(context, PlayGameActivity.class);
        intent.putExtra("game_info", gameInfo);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        mGameInfo = (GameInfo) getIntent().getSerializableExtra("game_info");
        if (mGameInfo == null || TextUtils.isEmpty(mGameInfo.getGameUrl())) {
            finish();
            return;
        }
        if (mGameInfo != null && !mGameInfo.isPortrait()) {
            //landscape game
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_view, PlayGameFragment.newInstance(mGameInfo.getGameUrl()),
                            "PlayGameFragment").commit();
        }
    }

    @Override
    public void onResult(String value) {
        Toast.makeText(this, "game result : " + value, Toast.LENGTH_SHORT).show();
    }
}
