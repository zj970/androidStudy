package com.zj970.goodnews.repository

import android.util.Log
import com.zj970.goodnews.App
import com.zj970.goodnews.database.bean.ListItem
import com.zj970.goodnews.database.bean.TodayNews
import com.zj970.goodnews.network.Constant.CODE
import com.zj970.goodnews.network.Constant.REQUEST_TIMESTAMP
import com.zj970.goodnews.network.Constant.SUCCESS
import com.zj970.goodnews.network.NetworkRequest
import com.zj970.goodnews.utils.EasyDataStore
import com.zj970.goodnews.utils.EasyDate
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@ViewModelScoped
class TodayNewsRepository @Inject constructor() : BaseRepository() {


    companion object {
        lateinit var todayNews: TodayNews
        private const val TAG = "TodayNewsRepository"
    }

    fun getTodayNews() = fire(Dispatchers.IO) {
        //val todayNews = NetworkRequest.getTodayNews()
        if (EasyDate.timestamp <= EasyDataStore.getData(REQUEST_TIMESTAMP,1694659438000)){
            todayNews = getLocalForNews()
        }else{
            todayNews = NetworkRequest.getTodayNews()
            saveNews(todayNews)
        }

        if (todayNews.code == CODE) {
            App.db.newsItemDao().delete()
            todayNews.result.list?.let { App.db.newsItemDao().insertAll(it) }
            Result.success(todayNews)
        } else Result.failure(
            RuntimeException
                ("getNews response code is ${todayNews.code} msg is ${todayNews.msg}")
        )
    }

    /**
     * 保存到本地数据库
     */
    private suspend fun saveNews(todayNews: TodayNews) {
        Log.d(TAG, "saveNews: 保存到本地数据库")
        EasyDataStore.putData(REQUEST_TIMESTAMP, EasyDate.getMillisNextEarlyMorning())
        App.db.newsItemDao().delete()
        todayNews.result.list?.let { App.db.newsItemDao().insertAll(it) }
    }

    /**
     * 从本地数据库中加载
     * 协程中访问数据库 suspend
     */
    private suspend fun getLocalForNews() = TodayNews(
        SUCCESS,
        com.zj970.goodnews.database.bean.Result(
            curpage = 1,
            list = App.db.newsItemDao().getAll(),
            allnum = App.db.newsItemDao().getAll().size
        ), CODE
    )

}

