package com.example.bounceball;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {
    BallView bv;
    SurfaceHolder surfaceHolder;
    boolean flag=false;
    int sleepSpan=30;
    long start =System.nanoTime(); //记录起始时间，该变量用于计算帧速率
    int count=0 ; //记录帧数

    public DrawThread(BallView bv,SurfaceHolder surfaceHolder) {
        this.bv=bv;
        this.surfaceHolder=surfaceHolder;
        this.flag=true;
    }

    public void run() {
        Canvas canvas=null;
        while(flag) {
            try {
                canvas=surfaceHolder.lockCanvas(null); //获取BallView的画布
                synchronized (surfaceHolder) {
                    bv.doDraw(canvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(canvas!=null) {
                    surfaceHolder.unlockCanvasAndPost(canvas); // surfaceHolder解锁，并将画布传回
                }
            }
            this.count++;
            if(count==20) { //计满20帧时计算一次帧速率
                count=0;
                long tempStamp=System.nanoTime();
                long span=tempStamp-start;
                start=tempStamp;
                double fps=Math.round(100000000000.0/span*20)/100.0;
                bv.fps="FPS:"+fps;
            }
            try {
                Thread.sleep(sleepSpan);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}