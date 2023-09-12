package com.zj970.goodnews.repository

import androidx.lifecycle.liveData
import kotlin.coroutines.CoroutineContext

open class BaseRepository {

    fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure(e)
            }
            //通知数据变化
            emit(result)
        }
}
