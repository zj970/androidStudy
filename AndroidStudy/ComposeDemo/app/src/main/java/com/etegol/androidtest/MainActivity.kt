package com.etegol.androidtest

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.etegol.androidtest.ui.theme.AndroidTestTheme

class MainActivity : ComponentActivity() {

    private val names: List<String> = listOf("Jetpack", "Room", "LiveData", "DataBinding")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.onPrimary) {
                    //Greeting("Android")
                    Conversation(names)
                }
            }
        }
    }


}

@Composable
fun Conversation(names: List<String>) {
    LazyColumn {
        items(names) { name ->
            Greeting(name)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val context= LocalContext.current
    Row(modifier = Modifier.padding(all = 8.dp)
        .fillMaxWidth()
        .clickable { Toast.makeText(context,name,Toast.LENGTH_LONG).show() }
    ) {
        Image(
            painter = painterResource(R.drawable.app_logo),
            contentDescription = "app logo",
            modifier = Modifier.size(60.dp)
                .clip(CircleShape)//clip(CircleShape)shape = RoundedCornerShape(16)
                .border(width = 2.dp, color = Color.Red, shape = CircleShape)
        )
        Column(modifier = Modifier.padding(all = 8.dp)) {
            Text(
                text = "Hello $name!",
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Hello1234 $name!",
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidTestTheme {
        //Greeting("World")
        Conversation(listOf("Jetpack", "Room", "LiveData", "DataBinding"))
    }
}