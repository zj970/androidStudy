package com.zj970.broadcastbestpractice;

import android.app.Activity;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {
    /**
     * This set of Activities is designed to manage Activities.
     */
    public static List<Activity> activities = new ArrayList<>();

    /**
     * add activity to {@link #activities}
     * @param activity
     */
    public static void addActivity(@NonNull Activity activity){
        activities.add(activity);
    }

    /**
     * remove activity at the {@link #activities}
     * @param activity
     */
    public static void removeActivity(@NonNull Activity activity){
        activities.remove(activity);
    }

    /**
     * destroy activity
     */
    public static void finshAll(){
        for (Activity activity : activities){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
