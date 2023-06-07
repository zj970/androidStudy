package com.example.mylibrary.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @auther zj970
 * @create 2023-05-20 下午4:01
 */
public abstract class BaseVBActivity<VB extends ViewBinding> extends BaseActivity {
    protected VB binding;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        onRegister();
        super.onCreate(savedInstanceState);
        Type type = this.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            try {
                /**
                 * 继承自BaseActivity，主要就是反射拿到具体的编译时类，然后设置内容视图，
                 */
                Class<VB> vbClass = (Class<VB>) ((ParameterizedType) type).getActualTypeArguments()[0];
                //反射
                Method method = null;
                method = vbClass.getMethod("inflate", LayoutInflater.class);
                binding = (VB) method.invoke(null, getLayoutInflater());
            } catch (Exception e) {
                e.printStackTrace();
            }
            setContentView(binding.getRoot());
        }
        initData();
    }

    protected void onRegister() {

    }

    protected abstract void initData();
}
