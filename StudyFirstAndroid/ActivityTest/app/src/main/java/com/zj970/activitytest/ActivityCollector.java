package com.zj970.activitytest;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动管理器
 */
public class ActivityCollector {
    /**
     * 在活动管理器中，我们通过一个List来暂存活动，
     * 然后提供了一个addActivity()方法用于向List中添加一个活动
     * 提供一个removeActivity()方法用于从List中移除活动
     * 最后提供了一个finish()方法用于将List中存储的火哦的那个全部销毁掉
     */
    public static List<Activity> activities = new ArrayList<>();

    /**
     * 在BaseActivity的onCreate()方法中调用了ActivityCollector的addActivity()方法，
     * 表明将当前正在创建的活动添加到活动管理器里。
     * @param activity
     */
    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    /**
     * 然后在BaseActivity中重写onDestroy()方法
     * 并调用了ActivityCollector的removeActivity方法
     * 表明将一个马上要销毁的活动从活动管理器移除
     * @param activity
     */
    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    /**
     * 从此以后，不管你想在什么地方退出程序，只需要调用ActivityCollector.finishALl
     *
     */
    public static void finishAll(){
        for (Activity activity : activities){
            activity.finish();
            /**
             * 还可以在销毁所有活动的代码后面再加上杀掉当前进程的代码
             * 以保证程序完全退出，杀掉进程的代码如下
             */
            android.os.Process.killProcess(android.os.Process.myPid());
            /**
             * 其中，killProcess()方法用于杀掉一个进程，它接收一个进程id参数，我们可以通过myPid()方法来获得当前程序的进程id。
             * 需要注意的是,killProcess()方法只能用于杀掉当前程序的进程
             * 不能使用此方法去杀掉其他程序
             */
        }
    }
}
