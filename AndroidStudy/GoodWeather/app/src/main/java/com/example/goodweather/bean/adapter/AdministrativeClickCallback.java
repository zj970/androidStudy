package com.example.goodweather.bean.adapter;

import android.view.View;
import com.example.goodweather.util.AdministrativeType;

/**
 * @auther zj970
 * @create 2023-06-03 上午12:38
 */
public interface AdministrativeClickCallback {
    /**
     * 行政区 点击事件
     *
     * @param view     点击视图
     * @param position 点击位置
     * @param type     行政区类型
     */
    void onAdministrativeItemClick(View view, int position, AdministrativeType type);
}
