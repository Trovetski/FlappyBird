package com.example.flappybird;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class GamePanel extends SurfaceView implements Runnable{

    Bitmap background;

    GameEngine ge;

    public volatile boolean playing = true;

    private Thread gameThread;

    private Paint paint;
    public SurfaceHolder surfaceHolder;
    public Canvas canvas;

    public GamePanel(Context context, @Nullable AttributeSet attrs, int h, int w) {
        super(context, attrs);

        //check for day or night and set background
        Boolean isNight;
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        isNight = hour < 6 || hour > 18;

        if(isNight){background = BitmapFactory.decodeResource(context.getResources(), R.drawable.night);}
        else{background = BitmapFactory.decodeResource(context.getResources(),R.drawable.day);}

        background = Bitmap.createScaledBitmap(background,w,h,false);

        paint = new Paint();
        paint.setColor(Color.BLACK);

        surfaceHolder = getHolder();

        ge = new GameEngine(h,w,0,context);
    }

    @Override
    public void run() {
        int score;
        while(playing){
            score = ge.game_loop();
            if(score>=0){
                GameActivity.GameOver(score);
                break;
            }
            draw();
        }
    }

    public void draw() {
        //check valid surface
        if (surfaceHolder.getSurface().isValid()) {
            //locking the canvas
            canvas = surfaceHolder.lockCanvas();
            //drawing a background color for canvas
            canvas.drawBitmap(background,0,0,paint);
            //drawing the bird
            canvas.drawBitmap(ge.getBirdBitmap(),ge.getBirdX(),ge.getBirdY(),paint);
            //drawing the top pipes
            canvas.drawBitmap(ge.getTopPipe(0),ge.lines[0],ge.getTopPipeY(0),paint);
            canvas.drawBitmap(ge.getTopPipe(1),ge.lines[1],ge.getTopPipeY(1),paint);
            //drawing the bottom pipes
            canvas.drawBitmap(ge.getBottomPipe(0),ge.lines[0],ge.getBottomPipeY(0),paint);
            canvas.drawBitmap(ge.getBottomPipe(1),ge.lines[1],ge.getBottomPipeY(1),paint);
            //drawing the base
            canvas.drawBitmap(ge.base,ge.getBaseX(),ge.getBaseY(),paint);
            //unlocking and painting
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                ge.onPress();
                break;
            case MotionEvent.ACTION_DOWN:
                //When the user releases the screen
                //do something here
                break;
        }
        return true;
    }

    public void pause() {
        //when the game is paused
        //setting the variable to false
        playing = false;
        try {
            //stopping the thread
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        //when the game is resumed
        //starting the thread again
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}