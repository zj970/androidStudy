package com.zj970.goodnews.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.zj970.goodnews.database.bean.ListItem

@Dao
interface ListItemDao {
    @Query("SELECT * FROM listitem")
    suspend fun getAll(): List<ListItem>

    @Insert
    suspend fun insertAll(news: List<ListItem>)

    @Query("DELETE FROM listitem")
    suspend fun delete()
}