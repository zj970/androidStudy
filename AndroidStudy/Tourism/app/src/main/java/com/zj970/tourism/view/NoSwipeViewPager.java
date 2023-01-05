package com.zj970.tourism.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * <p>
 *  自定义不能滑动
 * </p>
 *
 * @author: zj970
 * @date: 2023/1/5
 */
public class NoSwipeViewPager  extends ViewPager {
    private boolean canSwipe = true;

    public void setCanSwipe(boolean canSwipe) {
        this.canSwipe = canSwipe;
    }

    public NoSwipeViewPager(@NonNull Context context) {
        super(context);
    }

    public NoSwipeViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return canSwipe && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return canSwipe && super.onTouchEvent(ev);
    }
}
