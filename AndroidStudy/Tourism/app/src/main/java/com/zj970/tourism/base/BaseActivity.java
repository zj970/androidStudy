package com.zj970.tourism.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * <p>
 *
 * </p>
 *
 * @author: zj970
 * @date: 2023/1/1
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if  (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}
