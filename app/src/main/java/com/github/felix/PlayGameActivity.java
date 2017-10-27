package com.github.felix;

import com.tongzhuo.tzopengame.callback.GameResultCallback;
import com.tongzhuo.tzopengame.ui.PlayGameFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

public class PlayGameActivity extends AppCompatActivity implements GameResultCallback {

    private String game_url;

    public static Intent newIntent(Context context, String open_url) {
        Intent intent = new Intent(context, PlayGameActivity.class);
        intent.putExtra("game_url", open_url);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        game_url = getIntent().getStringExtra("game_url");
        if (TextUtils.isEmpty(game_url)) {
            finish();
            return;
        }
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_view, PlayGameFragment.newInstance(game_url),
                            "PlayGameFragment").commit();
        }
    }

    @Override
    public void onResult(String value) {
        Toast.makeText(this, "game result : " + value, Toast.LENGTH_SHORT).show();
    }
}
