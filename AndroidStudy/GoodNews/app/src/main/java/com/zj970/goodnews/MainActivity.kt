package com.zj970.goodnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zj970.goodnews.ui.theme.GoodNewsTheme
import com.zj970.goodnews.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint // Android 类添加 @AndroidEntryPoint 注解会创建一个沿袭 Android 类生命周期的依赖项容器。
// Hilt 可创建附着于 ComponentActivity 生命周期的依赖项容器，并能够将实例注入 MainActivity
//对于要进行注入的字段（例如 logger 和 dateFormatter），我们可以利用 @Inject 注解让 Hilt 注入不同类型的实例
//由 Hilt 注入的字段不能是私有字段
class MainActivity : ComponentActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoodNewsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    initDate()
                    //Greeting("Compose")
                }
            }
        }
    }
}

@Composable
fun initDate(viewModel: MainViewModel = viewModel()) {
    val dataState = viewModel.result.observeAsState()
    dataState.value?.let {
        var orNull = it.getOrNull()
        if (orNull != null) {
            Greeting(name = orNull.msg)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

/*
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GoodNewsTheme {
        Greeting("Android")
        //initDate()
    }
}*/
