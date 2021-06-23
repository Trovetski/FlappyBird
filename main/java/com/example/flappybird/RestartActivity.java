package com.example.flappybird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class RestartActivity extends AppCompatActivity {

    ImageView imageView, medal;

    ImageButton btnMenu, btnOk;

    TextView textView,textView1;

    int score = 0, bestScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int width = Resources.getSystem().getDisplayMetrics().widthPixels;

        score = getIntent().getExtras().getInt("score");
        bestScore = getIntent().getExtras().getInt("best score");

        setContentView(R.layout.acitvity_restart);

        imageView = findViewById(R.id.imageView2);
        imageView.setMinimumHeight((int)(width/1.62));

        textView = findViewById(R.id.textView2);
        textView.setPaddingRelative((int)(0.58*width),(int)(0.28*width),0,0);
        textView.setText(String.valueOf(score));
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(width*0.04f);

        textView1 = findViewById(R.id.textView);
        textView1.setPaddingRelative((int)(0.58*width),(int)(0.43*width),0,0);
        textView1.setText(String.valueOf(bestScore));
        textView1.setTextColor(Color.BLACK);
        textView1.setTextSize(width*0.04f);


        btnMenu = findViewById(R.id.imageButton2);
        btnMenu.setMinimumHeight((int)(width/7.2));
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestartActivity.this,menuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                menuActivity.bestScore = bestScore;
                startActivity(intent);
                finish();
            }
        });

        btnOk = findViewById(R.id.imageButton3);
        btnOk.setMinimumHeight((int)(width/7.2));
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestartActivity.this,GameActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra("best score",bestScore);
                startActivity(intent);
                finish();
            }
        });

        medal = findViewById(R.id.imageView3);
        if(score<bestScore){
            Drawable drawable = getDrawable(R.drawable.silver_bitmap);
            medal.setImageDrawable(drawable);
        }
        medal.setScaleType(ImageView.ScaleType.FIT_XY);
        medal.setMinimumHeight((int)(0.5*width));
        medal.setMinimumWidth((int)(0.2311*width));
        medal.setPaddingRelative((int)(0.09*width),(int)(0.34*width),0,0);
        // 0.588, 0.123

        getWindow().setLayout(3*width/4,(int)(0.769*width));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RestartActivity.this,GameActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtra("best score",bestScore);
        startActivity(intent);
        finish();
    }
}
