package com.zj970.goodnews.bean

//Get https://apis.tianapi.com/topnews/index

data class TodayNews(val msg: String = "",
                     val result: Result,
                     val code: Int = 0)

data class Result(val curpage: Int = 0,
                  val list: List<ListItem>?,
                  val allnum: Int = 0)

data class ListItem(val picUrl: String = "",
                    val ctime: String = "",
                    val description: String = "",
                    val id: String = "",
                    val source: String = "",
                    val title: String = "",
                    val url: String = "")