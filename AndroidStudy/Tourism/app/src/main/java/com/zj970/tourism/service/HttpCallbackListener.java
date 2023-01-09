package com.zj970.tourism.service;

/**
 * <p>
 *
 * </p>
 * @author: zj970
 * @date: 2023/1/9
 */
public interface HttpCallbackListener {
    /**
     * 网络请求成功
     * @param response 请求体
     */
    void onFinish(String response);

    /**
     * 网络请求失败
     * @param e 异常
     */
    void onError(Exception e);
}
