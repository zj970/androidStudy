package com.zj970.goodnews.viewmodel

import androidx.lifecycle.ViewModel
import com.zj970.goodnews.repository.TodayNewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    repository: TodayNewsRepository
) : ViewModel() {
    val result = repository.getTodayNews()
}