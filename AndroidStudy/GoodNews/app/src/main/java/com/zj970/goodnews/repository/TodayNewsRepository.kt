package com.zj970.goodnews.repository

import com.zj970.goodnews.network.Constant.CODE
import com.zj970.goodnews.network.NetworkRequest
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@ViewModelScoped
class TodayNewsRepository @Inject constructor() : BaseRepository() {


    fun getTodayNews() = fire(Dispatchers.IO) {
        val epidemicNews = NetworkRequest.getTodayNews()
        if (epidemicNews.code == CODE) Result.success(epidemicNews)
        else Result.failure(RuntimeException("getNews response code is ${epidemicNews.code} msg is ${epidemicNews.msg}"))
    }
}

