package com.generic.kotlin.robinhoodtest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.generic.kotlin.robinhoodtest.data.Asset
import com.generic.kotlin.robinhoodtest.ui.theme.RobinhoodTestTheme

class MainActivity : ComponentActivity() {
    lateinit var viewModel: AssetViewModel
    lateinit var assetData: List<Asset>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AssetViewModel::class.java)
        viewModel.getFilterData("stock")
        viewModel.assestData.observe(this, Observer {
            Log.d("Asset Data here", it.toString())
            assetData = it
           // viewModel.getFilterData("stock")
        })
        //viewModel.getFilterData("stock")

        setContent {
            RobinhoodTestTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    topBar = {
                        TextField(value = "", onValueChange = {})
                    }
                ) {
                    Box(modifier = Modifier.padding(it)) {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {

                        }
                    }
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RobinhoodTestTheme {
        Greeting("Android")
    }
}