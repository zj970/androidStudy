package com.zj970.goodnews.network

import com.zj970.goodnews.bean.TodayNews
import com.zj970.goodnews.network.Constant.API_KEY
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    /**
     * 获取新闻数据 https://apis.tianapi.com/topnews/index
     */
    @GET("/topnews/index?key=$API_KEY")
    fun getTodayNews(): Call<TodayNews>
}
