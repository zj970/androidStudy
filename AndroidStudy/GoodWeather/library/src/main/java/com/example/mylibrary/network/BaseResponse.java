package com.example.mylibrary.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @auther zj970
 * @create 2023-05-19 下午11:19
 */
public class BaseResponse {

    /**
     * 结果码
     */
    @SerializedName("res_code")
    @Expose
    public Integer responseCode;

    /**
     * 返回的错误信息
     */
    @SerializedName("res_error")
    @Expose
    public String responseError;
}

