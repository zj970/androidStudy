package com.example.bounceball;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Movable {
    int startX = 0; 			// 初始X坐标
    int startY = 0; 			// 初始Y坐标
    int x; 				// 实时X坐标
    int y; 				// 实时Y坐标
    float startVX = 0f; 		// 初始水平方向的速度
    float startVY = 0f; 		// 初始竖直方向的速度
    float v_x = 0f; 			// 实时水平方向的速度
    float v_y = 0f;			// 实时竖直方向的速度
    int r;				// 可移动物体半径
    double timeX;			// X方向上的运动时间
    double timeY;			// Y方向上的运动时间
    Bitmap bitmap=null; 		// 可移动物体图片
    BallThread bt=null; 		// 负责小球移动
    boolean bFall=false;		// 小球是否已经从木板上下落
    float impactFactor=0.25f; 		// 小球撞地后速度的损失系数

    public Movable(int x,int y,int r,Bitmap bitmap) {
        this.startX=x;
        this.x=x;
        this.startY=y;
        this.y=y;
        this.r=r;
        this.bitmap=bitmap;
        timeX=System.nanoTime(); 	// 获取系统时间初始化timeX
        this.v_x=BallView.V_MIN+(int)((BallView.V_MAX-BallView.V_MIN)*Math.random());
        bt=new BallThread(this); 	// 创建并启动BallThread
        bt.start();
    }

    public void drawSelf(Canvas canvas) {
        canvas.drawBitmap(bitmap,x,y,null);
    }

}
