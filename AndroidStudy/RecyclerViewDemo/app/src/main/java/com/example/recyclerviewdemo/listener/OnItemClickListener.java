package com.example.recyclerviewdemo.listener;

import android.view.View;

/**
 * 列表短按
 * @auther zj970
 * @create 2023-05-06 上午11:00
 */
public interface OnItemClickListener {
    /**
     * 列表项点击响应事件
     * @param view
     * @param position
     */
    void onItemClick(View view, int position);
}
