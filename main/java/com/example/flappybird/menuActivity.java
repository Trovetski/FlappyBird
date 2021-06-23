package com.example.flappybird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class menuActivity extends AppCompatActivity {

    ImageView imageView;
    static int bestScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final int width = Resources.getSystem().getDisplayMetrics().widthPixels;

        final int w = (int)(width/1.62);

        final int wf = 3*width/4;

        imageView = findViewById(R.id.imageView5);
        imageView.setMinimumHeight(w);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    int x = (int)event.getX();
                    int y = (int)event.getY();
                    if((x<wf/3)&&(y>w/3)){
                        GameEngine.color = 0;
                    }else if((x<2*wf/3)&&(y>w/3)){
                        GameEngine.color = 1;
                    }else if((x<wf)&&(y>w/3)){
                        GameEngine.color = 2;
                    }

                    Intent intent = new Intent(menuActivity.this,GameActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    intent.putExtra("best score",bestScore);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });

        getWindow().setLayout(3*width/4,(int)(0.469*width));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(menuActivity.this,GameActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtra("best score",bestScore);
        startActivity(intent);
        finish();
    }
}
