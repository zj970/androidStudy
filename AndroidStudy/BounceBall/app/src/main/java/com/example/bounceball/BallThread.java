package com.example.bounceball;

public class BallThread extends Thread {
    Movable father; // Movable对象引用
    boolean flag = false; // 线程执行标识位
    int sleepSpan = 40; // 休眠时间
    float g = 200; // 球下落的加速度
    double current; // 记录当前时间

    public BallThread(Movable father) {
        this.father = father;
        this.flag = true;
    }

    @Override
    public void run() {
        while (flag) {
            current = System.nanoTime(); // 获取当前时间，单位为纳秒，处理水平方向上的运动
            double timeSpanX = (double) ((current - father.timeX) / 1000 / 1000 / 1000); // 获取水平方向走过的时间
            father.x = (int) (father.startX + father.v_x * timeSpanX);
            if (father.bFall) { // 处理竖直方向上的运动，判断球是否已经移出挡板

                double timeSpanY = (double) ((current - father.timeY) / 1000 / 1000 / 1000);
                father.y = (int) (father.startY + father.startVY * timeSpanY + timeSpanY
                        * timeSpanY * g / 2);
                father.v_y = (float) (father.startVY + g * timeSpanY);
                // 判断小球是否到达最高点
                if (father.startVY < 0 && Math.abs(father.v_y) <= BallView.UP_ZERO) {
                    father.timeY = System.nanoTime();
                    father.v_y = 0;
                    father.startVY = 0;
                    father.startY = father.y;
                }
                // 判断小球是否撞地
                if (father.y + father.r * 2 >= BallView.GROUND_LING && father.v_y > 0) {
                    father.v_x = father.v_x * (1 - father.impactFactor); // 衰减水平方向的速度
                    father.v_y = 0 - father.v_y * (1 - father.impactFactor); // 衰减竖直方向的速度并改变方向
                    if (Math.abs(father.v_y) < BallView.DOWN_ZERO) { // 判断撞地衰减后的速度，太小就停止运动
                        this.flag = false;
                    } else {
                        // 撞地后的速度还可以弹起继续下一阶段的运动
                        father.startX = father.x;
                        father.timeX = System.nanoTime();
                        father.startY = father.y;
                        father.timeY = System.nanoTime();
                        father.startVY = father.v_y;
                    }
                }
                //此处先省略检测和处理特殊事件的代码，随后补全
            } else if (father.x + father.r / 2 >= BallView.WOOD_EDGE) {// 通过X坐标判断球是否移出了挡板
                father.timeY = System.nanoTime();
                father.bFall = true; // 确定下落
            }
            try {
                Thread.sleep(sleepSpan);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}