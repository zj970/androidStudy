package com.example.recyclerviewdemo.listener;

import android.view.View;

/**
 * 列表长按
 * @auther zj970
 * @create 2023-05-06 上午11:42
 */
public interface OnItemLongClickListener {
    /**
     * 长按
     * @param view
     * @param position
     * @return 按下true/放开false
     */
    boolean onItemLongClick(View view, int position);
}
