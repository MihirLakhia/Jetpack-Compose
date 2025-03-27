package com.generic.kotlin.data_management

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.generic.kotlin.data_management.ui.main.MainViewModel
import com.generic.kotlin.data_management.ui.theme.DataManagementTheme

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setContent {
            DataManagementTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android",viewModel)
                }
            }
        }

        viewModel.monsterData.observe(this, Observer { it ->

            val monsterNames = StringBuilder()
            for (monster in it) {
                monsterNames.append(monster.monsterName)
                    .append("\n")
            }
            Log.d("Monster Name", monsterNames.toString())
        })

        val testScore = mapOf("a" to 80,"b" to 78,"c" to 66,"d" to 89,"e" to 90,"f" to 80)
        testScore.toList()
            .sortedBy { it.second }
            .map { it.first }
            .take(5)
            .forEach(){
                Log.d("list",it)
            }
    }
}

@Composable
fun Greeting(name: String,viewModel: MainViewModel, modifier: Modifier = Modifier) {

    val data = viewModel.monsterData.observeAsState().value
    val monsterNames = StringBuilder()
    for (monster in data?: emptyList()) {
        monsterNames.append(monster.monsterName)
            .append("\n")
    }
    Text(
        text = "Hello $monsterNames!",
        modifier = modifier
    )
}
