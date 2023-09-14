package com.zj970.goodnews.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zj970.goodnews.database.bean.ListItem
import com.zj970.goodnews.database.dao.ListItemDao

@Database(entities = [ListItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun newsItemDao() : ListItemDao
    companion object{
        @Volatile
        private var instance:AppDatabase? = null
        private const val DATABASE_NAME = "good_news.db"
        fun getInstance(context: Context) : AppDatabase{
            return instance ?: synchronized(this){
                instance ?: Room.databaseBuilder(context ,AppDatabase::class.java, DATABASE_NAME)
                    //.allowMainThreadQueries()避免了从主线程中访问数据库 DataStore 本地缓存
                    .build()
                    .also { instance = it }
            }
        }
    }
}