package com.example.mylibrary.network.interceptor;

import android.util.Log;
import androidx.constraintlayout.solver.state.State;
import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

/**
 * 返回拦截器
 * 在网络数据交互的时候有请求和返回，那么在两个过程中我们可以获取一些信息，就需要拦截器
 * @auther zj970
 * @create 2023-05-19 下午11:21
 */
public class ResponseInterceptor implements Interceptor {

    private static final String TAG = ResponseInterceptor.class.getSimpleName();

    /**
     * 拦截
     */


    @Override
    public Response intercept(Chain chain) throws IOException {
        long requestTime = System.currentTimeMillis();
        Response response = chain.proceed(chain.request());
        Log.i(TAG, "requestSpendTime=" + (System.currentTimeMillis() - requestTime) + "ms");
        return response;
    }
}
