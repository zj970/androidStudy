package com.zj970.goodnews.database.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

//Get https://apis.tianapi.com/topnews/index

data class TodayNews(var msg: String = "",
                     var result: Result,
                     var code: Int = 0)

data class Result(var curpage: Int = 0,
                  var list: List<ListItem>?,
                  var allnum: Int = 0)

@Entity
data class ListItem(val picUrl: String = "",
                    val ctime: String = "",
                    val description: String = "",
                    @PrimaryKey val id: String = "",
                    val source: String = "",
                    val title: String = "",
                    val url: String = "")