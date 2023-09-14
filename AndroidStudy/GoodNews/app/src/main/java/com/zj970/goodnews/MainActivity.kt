package com.zj970.goodnews

import android.os.Bundle
import android.text.TextUtils
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberImagePainter
import com.zj970.goodnews.database.bean.ListItem
import com.zj970.goodnews.ui.theme.GoodNewsTheme
import com.zj970.goodnews.utils.showToast
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
                    //MainScreen()
                    initDate()
                    //Greeting("Compose")
                }
            }
        }
    }
}

@Composable
fun initDate(viewModel: MainViewModel = viewModel()) {
    /*    val dataState = viewModel.result.observeAsState()
        dataState.value?.let {
            *//*        var orNull = it.getOrNull()
                if (orNull != null) {
                    Greeting(name = orNull.msg)
                }*//*
            result ->
        result.getOrNull()?.result?.list?.let { MainScreen(it) }
    }*/
    //MainScreen(news = App.db.newsItemDao().getAll())
    val dataState = viewModel.result.observeAsState()
    dataState.value?.let { result ->
        result.getOrNull()?.result?.list.let {
            it?.let { it1 -> MainScreen(it1) }
        }
    }
}


@Composable
private fun MainScreen(news: List<ListItem>) {
    //Scaffold 可让您实现具有基本 Material Design 布局结构的界面。
    //Scaffold 可以为最常见的顶层 Material 组件
    // 例如 TopAppBar、BottomAppBar、FloatingActionButton 和 Drawer）提供槽位
    // 使用 Scaffold 时，您可以确保这些组件能够正确放置并协同工作
    Scaffold(
        topBar = {
            //顶部应用栏
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { "Person".showToast() }) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Person",
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { "Settings".showToast() }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings",
                        )
                    }
                }
            )
        })
    { innerPadding ->
        BodyContent(news, Modifier.padding(innerPadding))
    }
}


@Composable
fun BodyContent(news: List<ListItem>, modifier: Modifier = Modifier) {
    LazyColumn(
        state = rememberLazyListState(),
        modifier = modifier.padding(8.dp)
    ) {

        items(news) { new ->
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = new.title,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(0.dp, 10.dp)
                )
                Text(text = new.description, fontSize = 12.sp)

                if (new.picUrl != null && !TextUtils.isEmpty(new.picUrl)) {
                    Image(
                        modifier = Modifier.size(120.dp, 120.dp),
                        painter = rememberImagePainter(
                            data = new.picUrl,
                            builder = {
                                placeholder(androidx.loader.R.drawable.notification_bg_low)//占位图
                                crossfade(true)//淡出效果
                            }),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
                Row(modifier = Modifier.padding(0.dp, 10.dp)) {
                    Text(text = new.source, fontSize = 12.sp)
                    Text(
                        text = new.ctime,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(8.dp, 0.dp)
                    )
                }
                Divider(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = colorResource(id = R.color.black).copy(alpha = 0.08f)
                )
            }
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

/*@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GoodNewsTheme {
        Greeting("Android")
        //initDate()
    }
}*/
