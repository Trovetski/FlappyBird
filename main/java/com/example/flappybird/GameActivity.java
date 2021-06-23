package com.example.flappybird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class GameActivity extends AppCompatActivity {

    GamePanel gPanel;
    public static Context mContext;
    public static int bestScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        int height = Resources.getSystem().getDisplayMetrics().heightPixels;

        gPanel = new GamePanel(this,null, height, width);
        setContentView(gPanel);

        mContext = this;

        bestScore = getIntent().getExtras().getInt("best score");
    }

    public static void GameOver(int score){
        Intent intent = new Intent(mContext,RestartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtra("score",score);
        if(score>=bestScore){bestScore = score;}
        intent.putExtra("best score",bestScore);
        mContext.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gPanel.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gPanel.resume();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(GameActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }
}
