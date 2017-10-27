package com.github.felix;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText mEtUrl;

    private Button mBtStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mEtUrl = (EditText) findViewById(R.id.mEtUrl);
        mBtStart = (Button) findViewById(R.id.mBtStart);
        mBtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGame();
            }
        });
        String url = getPreferences(MODE_PRIVATE).getString("open_url", "");
        if (!TextUtils.isEmpty(url)) {
            mEtUrl.setText(url);
        }
    }

    private void openGame() {
        String url = mEtUrl.getText().toString().trim();
        if (!TextUtils.isEmpty(url)) {
            getPreferences(MODE_PRIVATE).edit().putString("open_url", url).commit();
            startActivity(PlayGameActivity.newIntent(this, url));
        }
    }
}
