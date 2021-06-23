package com.example.flappybird;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;

import java.util.Random;

public class GameEngine {
    //height and width of screen
    int height,width;

    //bird height and width
    int bSize, gap, score;
    static int color=0;

    //co-ordinates of the bird
    public int x,y;

    //location of the bars
    public int[] lines;
    public int[] length;
    public int[] isSpecialPipe;

    //bitmaps of everything
    public Bitmap bird, bird1, bird2, pipe, base, pipe0, pipe1, pipe2;

    //base location
    public int baseX;

    //random variable
    public Random r;

    //velocity
    float v;

    //animation counter
    public int counter = 0;
    public int gapCounter = 0;

    //Game State
    public boolean GameState;

    public GameEngine(int h, int w, int c, Context context) {
        x = (int)(w*2.5f/10);
        y = h/2;

        height = (int)(8f*h/10);
        width = w;

        bSize = (int)(w/8f);
        gap = bSize*4;

        v = 0f;
        score=0;

        r = new Random();

        lines = new int[]{w,(int)(1.6*w)};
        length = new int[]{(int)(0.08f*h*(r.nextInt(3)+1)), (int)(0.08f*h*(r.nextInt(3)+1))};
        isSpecialPipe = new int[]{0,0};
        if(r.nextInt(9)==6){
            isSpecialPipe[0]=1;
        }if(r.nextInt(9)==6){
            isSpecialPipe[1]=1;
        }

        switch (color){
            case 0:
                bird = BitmapFactory.decodeResource(context.getResources(), R.drawable.bird);
                bird1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.bird1);
                bird2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.bird2);
                break;
            case 1:
                bird = BitmapFactory.decodeResource(context.getResources(), R.drawable.bird_red);
                bird1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.bird_red1);
                bird2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.bird_red2);
                break;
            case 2:
                bird = BitmapFactory.decodeResource(context.getResources(), R.drawable.bird_blue);
                bird1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.bird_blue1);
                bird2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.bird_blue2);
        }


        bird = Bitmap.createScaledBitmap(bird, (int)(bSize*1.309), bSize, false);
        bird1= Bitmap.createScaledBitmap(bird1, (int)(bSize*1.309), bSize, false);
        bird2 = Bitmap.createScaledBitmap(bird2, (int)(bSize*1.309), bSize, false);

        pipe = BitmapFactory.decodeResource(context.getResources(), R.drawable.pipe);
        pipe = Bitmap.createScaledBitmap(pipe, (int)(bSize*1.5), (int)(1.5*5.74038*bSize), false);

        pipe1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.pipe_red);
        pipe1 = Bitmap.createScaledBitmap(pipe1, (int)(bSize*1.5), (int)(1.5*5.74038*bSize), false);

        base = BitmapFactory.decodeResource(context.getResources(), R.drawable.base);
        base = Bitmap.createScaledBitmap(base, (int)(w*1.077), (int)(2f*h/10), false);

        Matrix matrix = new Matrix();
        matrix.postRotate(180f);
        pipe0 =  Bitmap.createBitmap(pipe, 0, 0, pipe.getWidth(), pipe.getHeight(), matrix, true);
        pipe2 =  Bitmap.createBitmap(pipe1, 0, 0, pipe1.getWidth(), pipe1.getHeight(), matrix, true);

        baseX = 0;

        GameState = false;
        game_loop();
    }

    public int game_loop(){
        //if paused then stop
        if(!GameState){return -1;}

        //update position and velocity of bird
        y -= (int)v;
        if(y<0){ y = 0; v = -(0.74f)*v;}
        else if(y>height-bSize){ y = height-bSize; v = -(0.74f)*v;}
        else {v -= 2.93f*height/1900f;}

        //update position of pipe
        lines[0] -= 3;
        lines[1] -= 3;

        if(lines[0]< -bSize*1.6){
            if(isSpecialPipe[0]==1){
                isSpecialPipe[0]=0;
                score+=2;
            }
            if(r.nextInt(9)==6){
                isSpecialPipe[0]=1;
            }
            score++;
            lines[0] = width;
            length[0] = (int)(0.16f*height*(r.nextInt(3)+1));
        }else if(lines[1]< -bSize*1.6){
            if(isSpecialPipe[1]==1){
                isSpecialPipe[1]=0;
                score+=2;
            }
            if(r.nextInt(9)==6){
                isSpecialPipe[1]=1;
            }
            score++;
            lines[1] = width;
            length[1] = (int)(0.16f*height*(r.nextInt(3)+1));
        }

        //decrease the gap
        if(gap>bSize*3) {
            if (gapCounter == 0) {
                gapCounter = 100;
                gap--;
            }
            gapCounter--;
        }

        //collision detection

        Rect bird = new Rect(x,y,(int)(x+bSize*1.1),(int)(y+0.8*bSize));
        Rect a,b;

        a = new Rect(lines[0],0,(int)(lines[0]+bSize*1.5),length[0]);
        b = new Rect(lines[0],length[0]+gap,(int)(lines[0]+bSize*1.5),height);

        if(bird.intersect(a)||bird.intersect(b)){
            GameState = false;
            return score;
        }

        a = new Rect(lines[1],0,(int)(lines[1]+bSize*1.5),length[1]);
        b = new Rect(lines[1],length[1]+gap,(int)(lines[1]+bSize*1.5),height);

        if(bird.intersect(a)||bird.intersect(b)){
            GameState = false;
            return score;
        }


        return -1;
    }

    public void onPress(){
        v = 21.53f*height/1600f;
        GameState = true;
    }

    public Bitmap getBirdBitmap(){
        /*
        float angle;

        if(v>=0){angle = -30*v/20f;}
        else{angle = -90*v/100f;}

        Matrix matrix = new Matrix();
        matrix.setRotate(angle,0, bird.getHeight());

        Bitmap newBird = Bitmap.createBitmap(bird, 0, 0, bird.getWidth(), bird.getHeight(), matrix, true);
         */

        if(counter == 0){counter = 30;}
        counter--;

        if(counter<10){return bird2;}

        if(counter<20){return bird1;}

        return bird;
    }

    public int getTopPipeY(int i){
        return (int)(length[i] - 1.5*bSize*5.74038);
    }

    public int getBottomPipeY(int i){
        return length[i] + gap;
    }

    public int getBaseX(){
        baseX += 3;
        if(baseX > 0.077*width){
            baseX = 0;
        }
        return -baseX;
    }

    public int getBaseY(){
        return height;
    }

    public int getBirdX(){
        return x;
    }

    public int getBirdY(){
        return y;
    }

    public Bitmap getTopPipe(int i){
        if(isSpecialPipe[i]==1){
            return pipe2;
        }
        return pipe0;
    }

    public Bitmap getBottomPipe(int i){
        if(isSpecialPipe[i]==1){
            return pipe1;
        }
        return pipe;
    }
}
