package com.zj970.networktest.callback;

public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
