package com.example.recyclerviewdemo.listener;

import android.view.View;

/**
 * 列表子控件短按
 * @auther zj970
 * @create 2023-05-06 上午11:33
 */
public interface OnItemChildClickListener {
    /**
     * 列表项子控件的响应事件
     * @param view
     * @param position
     */
    void onItemChildClick(View view, int position);
}
